package congress.dwp.libraryservices.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Book {
    private @Id
    @GeneratedValue Long id;
    private String title;
    private String author;
    private String genre;
    private Date fullPublishDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getFullPublishDate() {
        return fullPublishDate;
    }

    public void setFullPublishDate(Date fullPublishDate) {
        this.fullPublishDate = fullPublishDate;
    }
}
