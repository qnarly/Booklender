package kg.attractor.java.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibraryService {
    private List<Employee> employees = new ArrayList<>();
    private List<Book> books = new ArrayList<>();

    public LibraryService() {
        employees.add(new Employee(1, "John Doe"));
        employees.add(new Employee(2, "Alex True"));

        books.add(new Book(1, "Война и Мир", "Л.Н. Толстой"));
        books.add(new Book(2, "Преступление и наказание", "Ф.М. Достоевский"));
        books.add(new Book(3, "Мастер и Маргарита", "М.А. Булгаков"));
        books.add(new Book(4, "Мартин Иден", "Джек Лондон"));

        books.get(2).setIsTaken(employees.get(1));
    }

    public List<Book> getBooks() {
        return books;
    }

    public Optional<Book> getBookById(int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst();
    }
}
