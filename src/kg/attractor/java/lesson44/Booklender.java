package kg.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import kg.attractor.java.library.Book;
import kg.attractor.java.library.LibraryService;
import kg.attractor.java.server.*;
import kg.attractor.java.user.User;
import kg.attractor.java.user.UserService;
import kg.attractor.java.utils.Utils;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Booklender extends BasicServer {
    private final static Configuration freemarker = initFreeMarker();

    private final LibraryService libraryService = new LibraryService();

    private final UserService userService = new UserService();

    private final Map<String, User> sessions = new HashMap<>();


    public Booklender(String host, int port) throws IOException {
        super(host, port);
        registerGet("/", checkAuth(this::indexHandler));

        registerGet("/books", checkAuth(this::booksHandler));
        registerGet("/book", this::bookHandler);

        registerGet("/login", this::loginGet);
        registerPost("/login", this::loginPost);

        registerGet("/register", this::regGet);
        registerPost("/register", this::regPost);

        registerGet("/profile", checkAuth(this::profileGet));

        registerGet("/checkout", checkAuth(this::checkoutHandler));
        registerGet("/return", this::returnHandler);

        registerGet("/logout", this::logoutHandler);
    }

    private void logoutHandler(HttpExchange httpExchange) {
        String cookieString = getCookies(httpExchange);
        Map<String, String> cookies = Cookie.parse(cookieString);
        String sessionId = cookies.get("sessionId");

        if (sessionId != null) {
            sessions.remove(sessionId);
        }

        Cookie expiredCookie = new Cookie("sessionId", "");
        expiredCookie.setMaxAge(0);
        expiredCookie.setHttpOnly(true);
        setCookie(httpExchange, expiredCookie);

        redirect303(httpExchange, "/login");
    }

    private RouteHandler checkAuth(RouteHandlerAuth handler) {
        return exchange -> {
            Optional<User> userOpt = getCurrentUser(exchange);

            handler.handle(exchange, userOpt);
        };
    }

    private void returnHandler(HttpExchange httpExchange) {
        String query = httpExchange.getRequestURI().getQuery();
        Map<String, String> params = queryToMap(query);
        int bookId = Integer.parseInt(params.get("id"));

        libraryService.returnBook(bookId);

        redirect303(httpExchange, "/books");
    }


    private void checkoutHandler(HttpExchange httpExchange, Optional<User> userOpt) {
        if (userOpt.isEmpty()) {
            redirect303(httpExchange, "/login");
            return;
        }
        User currentUser = userOpt.get();

        List<Book> userBooks = libraryService.getBooksTakenByUser(currentUser);
        if (userBooks.size() >= 2) {
            redirect303(httpExchange, "/books?error=limit");
            return;
        }
        String query = httpExchange.getRequestURI().getQuery();
        Map<String, String> params = queryToMap(query);
        int bookId = Integer.parseInt(params.get("id"));

        libraryService.checkoutBook(bookId, currentUser);

        redirect303(httpExchange, "/books");
    }

    private void indexHandler(HttpExchange exchange, Optional<User> userOpt) {
        Map<String, Object> data = new HashMap<>();
        userOpt.ifPresent(user -> data.put("user", user));
        renderTemplate(exchange, "index.ftlh", data);
    }

    private void profileGet(HttpExchange httpExchange, Optional<User> userOpt) {
        Map<String, Object> data = new HashMap<>();
        User currentUser = userOpt.orElse(null);
        data.put("user", currentUser);

        if (currentUser != null) {
            List<Book> userBooks = libraryService.getBooksTakenByUser(currentUser);

            data.put("books", userBooks);
        }

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
            User user = userLogin.get();

            String sessionId = UUID.randomUUID().toString();

            sessions.put(sessionId, user);
            System.out.println("Создана сессия: " + sessionId + " для " + user.getName());

            Cookie sessionCookie = new Cookie("sessionId", sessionId);
            sessionCookie.setMaxAge(600);
            sessionCookie.setHttpOnly(true);

            setCookie(exchange, sessionCookie);

            redirect303(exchange, "/profile");

        } else {
            System.out.println("Неудачная попытка входа для: " + email);
            Map<String, Object> data = new HashMap<>();
            data.put("message", "Авторизоваться не удалось, неверный идентификатор или пароль");
            renderTemplate(exchange, "login.ftlh", data);
        }

    }

    private void booksHandler(HttpExchange httpExchange, Optional<User> userOpt) {
        Map<String, Object> data = new HashMap<>();
        data.put("books", libraryService.getBooks());
        userOpt.ifPresent(user -> data.put("user", user));
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

    private Optional<User> getCurrentUser(HttpExchange httpExchange) {
        String cookieString = getCookies(httpExchange);
        Map<String, String> cookies = Cookie.parse(cookieString);
        String sessionId = cookies.get("sessionId");

        if (sessionId != null && sessions.containsKey(sessionId)) {
            return Optional.of(sessions.get(sessionId));
        }

        return Optional.empty();
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

    private SampleDataModel getSampleDataModel() {
//         возвращаем экземпляр тестовой модели-данных
//         которую freemarker будет использовать для наполнения шаблона
        return new SampleDataModel();
    }
}
