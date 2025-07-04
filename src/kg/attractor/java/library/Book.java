package kg.attractor.java.library;

import kg.attractor.java.user.User;

import java.util.Objects;

public class Book {
    private int id;
    private String title;
    private String author;
    private User isTaken;
    private String description;
    private String imageUrl;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isTaken = null;
    }

    public void setIsTaken(User isTaken) {
        this.isTaken = isTaken;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public User getIsTaken() {
        return isTaken;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
