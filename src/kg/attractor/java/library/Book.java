package kg.attractor.java.library;

import kg.attractor.java.user.User;

public class Book {
    private int id;
    private String title;
    private String author;
    private User isTaken;
    private String description;
    private String imgUrl;

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

    public String getImgUrl() {
        return imgUrl;
    }
}
