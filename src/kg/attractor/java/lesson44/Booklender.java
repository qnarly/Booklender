package kg.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import kg.attractor.java.library.LibraryService;
import kg.attractor.java.server.BasicServer;
import kg.attractor.java.server.ContentType;
import kg.attractor.java.server.ResponseCodes;
import kg.attractor.java.user.User;
import kg.attractor.java.user.UserService;
import kg.attractor.java.utils.Utils;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Booklender extends BasicServer {
    private final static Configuration freemarker = initFreeMarker();

    private final LibraryService libraryService = new LibraryService();

    private final UserService userService = new UserService();


    public Booklender(String host, int port) throws IOException {
        super(host, port);
        registerGet("/sample", this::freemarkerSampleHandler);
        registerGet("/books", this::booksHandler);
        registerGet("/book", this::bookHandler);
        registerGet("/login", this::loginGet);
        registerPost("/login", this::loginPost);
        registerGet("/register", this::regGet);
        registerPost("/register", this::regPost);
        registerGet("/profile", this::profileGet);
    }

    private void profileGet(HttpExchange httpExchange) {
        User defaultUser = new User("Некий пользователь", "default@example.com", "---");

        Map<String, Object> data = new HashMap<>();

        data.put("user", defaultUser);

        renderTemplate(httpExchange, "profile.ftlh", data);
    }

    private void regGet(HttpExchange exchange) {
        Path path = makeFilePath("register.ftlh");
        renderTemplate(exchange, "register.ftlh", null);
    }

    private void regPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        String name = parsed.get("nickname");
        String email = parsed.get("email");
        String password = parsed.get("user-password");

        boolean userExist = userService.isUserExist(email);

        if (userExist) {
            System.out.println("Попытка регистрации с существующим email: " + email);
            Map<String, Object> data = new HashMap<>();
            data.put("message", "Регистрация не удалась. Пользователь с таким email уже зарегистрирован! Попробуйте еще раз");

            renderTemplate(exchange, "register.ftlh", data);

        } else {
            User user = new User(name, email, password);
            userService.addUser(user);

            renderTemplate(exchange, "register_success.ftlh", null);
        }

    }

    private void loginGet(HttpExchange exchange) {
        Path path = makeFilePath("login.ftlh");
        renderTemplate(exchange, "login.ftlh", null);
    }

    private void loginPost(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> parsed = Utils.parseUrlEncoded(raw, "&");
        String email = parsed.get("email");
        String password = parsed.get("user-password");


        Optional<User> userLogin = userService.loginChecker(email);

        if (userLogin.isPresent() && userLogin.get().getPassword().equals(password)) {
            System.out.println("Успешный вход для пользователя: " + email);

            Map<String, Object> data = new HashMap<>();
            data.put("user", userLogin.get());

            renderTemplate(exchange, "profile.ftlh", data);
        } else {
            System.out.println("Неудачная попытка входа для: " + email);

            Map<String, Object> data = new HashMap<>();
            data.put("message", "Авторизоваться не удалось, неверный идентификатор или пароль");

            renderTemplate(exchange, "login.ftlh", data);
        }
    }


    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            // путь к каталогу в котором у нас хранятся шаблоны
            // это может быть совершенно другой путь, чем тот, откуда сервер берёт файлы
            // которые отправляет пользователю
            cfg.setDirectoryForTemplateLoading(new File("data"));

            // прочие стандартные настройки о них читать тут
            // https://freemarker.apache.org/docs/pgui_quickstart_createconfiguration.html
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void booksHandler(HttpExchange httpExchange) {
        var books = libraryService.getBooks();

        Map<String, Object> data = new HashMap<>();
        data.put("books", books);

        renderTemplate(httpExchange, "books.ftlh", data);
    }

    private void bookHandler(HttpExchange exchange) {
        String query = exchange.getRequestURI().getQuery();
        Map<String, String> params = queryToMap(query);

        String idParam = params.get("id");

        java.util.Optional<kg.attractor.java.library.Book> bookOpt;

        if (idParam != null) {
            try {
                int bookId = Integer.parseInt(idParam);
                bookOpt = libraryService.getBookById(bookId);
            } catch (NumberFormatException e) {
                bookOpt = java.util.Optional.empty();
            }
        } else {
            bookOpt = libraryService.getBooks().stream().findFirst();
        }

        Map<String, Object> data = new HashMap<>();
        data.put("book", bookOpt.orElse(null));

        renderTemplate(exchange, "book.ftlh", data);
    }

    public static Map<String, String> queryToMap(String query) {
        if (query == null) {
            return Map.of();
        }
        return java.util.Arrays.stream(query.split("&"))
                .map(s -> s.split("=", 2))
                .collect(Collectors.toMap(
                        a -> URLDecoder.decode(a[0], StandardCharsets.UTF_8),
                        a -> a.length > 1 ? URLDecoder.decode(a[1], StandardCharsets.UTF_8) : "",
                        (v1, v2) -> v1
                ));
    }

    private void freemarkerSampleHandler(HttpExchange exchange) {
        renderTemplate(exchange, "sample.ftlh", getSampleDataModel());
    }

    protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) {
        try {
            // Загружаем шаблон из файла по имени.
            // Шаблон должен находится по пути, указанном в конфигурации
            Template temp = freemarker.getTemplate(templateFile);

            // freemarker записывает преобразованный шаблон в объект класса writer
            // а наш сервер отправляет клиенту массивы байт
            // по этому нам надо сделать "мост" между этими двумя системами

            // создаём поток, который сохраняет всё, что в него будет записано в байтовый массив
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // создаём объект, который умеет писать в поток и который подходит для freemarker
            try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {

                // обрабатываем шаблон заполняя его данными из модели
                // и записываем результат в объект "записи"
                temp.process(dataModel, writer);
                writer.flush();

                // получаем байтовый поток
                var data = stream.toByteArray();

                // отправляем результат клиенту
                sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private SampleDataModel getSampleDataModel() {
//         возвращаем экземпляр тестовой модели-данных
//         которую freemarker будет использовать для наполнения шаблона
        return new SampleDataModel();
    }
}
