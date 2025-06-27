package kg.attractor.java.library;

import kg.attractor.java.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibraryService {
    private final List<Book> books = new ArrayList<>();

    public LibraryService() {

//        List<User> users = new ArrayList<>();
//        users.add(new User( "John Doe"));
//        users.add(new User( "Alex Trueman"));
//        users.add(new User( "Steve Cool"));

        books.add(new Book(1, "Война и Мир", "Л.Н. Толстой"));
        books.add(new Book(2, "Преступление и наказание", "Ф.М. Достоевский"));
        books.add(new Book(3, "Мастер и Маргарита", "М.А. Булгаков"));
        books.add(new Book(4, "Мартин Иден", "Джек Лондон"));

//        books.get(2).setIsTaken(users.get(1));
    }

    public List<Book> getBooks() {
        return books;
    }

    public Optional<Book> getBookById(int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst();
    }
}
