package kg.attractor.java.user;

import kg.attractor.java.library.Book;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String name;
    private String email;
    private String password;
    private transient Set<Book> exBooks;

    public User(String name) {
        this.name = name;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.exBooks = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Book> getExBooks() {
        if (exBooks == null) {
            exBooks = new HashSet<>();
        }
        return exBooks;
    }

    public void addExBook(Book book) {
        if (exBooks == null) {
            exBooks = new HashSet<>();
        }
        exBooks.add(book);
    }
}
