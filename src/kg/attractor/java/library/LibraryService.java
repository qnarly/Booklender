package kg.attractor.java.library;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import kg.attractor.java.user.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LibraryService {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH_BOOKS = Paths.get("data/dataBase/library.json");


    public List<Book> getBooks() {
        return readBooks();
    }

    public Optional<Book> getBookById(int id) {
        return getBooks().stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }

    public List<Book> getBooksTakenByUser(User user) {
        return getBooks().stream()
                .filter(book -> book.getIsTaken() != null && book.getIsTaken().getEmail().equals(user.getEmail()))
                .collect(Collectors.toList());
    }

    public void checkoutBook(int bookId, User user) {
        List<Book> books = readBooks();
        books.stream()
                .filter(b -> b.getId() == bookId)
                .findFirst()
                .ifPresent(book -> book.setIsTaken(user));
        writeBooks(books);
    }

    public void returnBook(int bookId) {
        List<Book> books = readBooks();
        books.stream()
                .filter(b -> b.getId() == bookId)
                .findFirst()
                .ifPresent(book -> book.setIsTaken(null));
        writeBooks(books);
    }


    private List<Book> readBooks() {
        if (!Files.exists(PATH_BOOKS)) return new ArrayList<>();
        try {
            String json = Files.readString(PATH_BOOKS);
            return GSON.fromJson(json, new TypeToken<List<Book>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeBooks(List<Book> books) {
        String json = GSON.toJson(books);
        try {
            Files.writeString(PATH_BOOKS, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}