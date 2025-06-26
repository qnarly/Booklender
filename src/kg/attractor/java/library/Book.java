package kg.attractor.java.library;

public class Book {
    private int id;
    private String title;
    private String author;
    private User isTaken;

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
}
