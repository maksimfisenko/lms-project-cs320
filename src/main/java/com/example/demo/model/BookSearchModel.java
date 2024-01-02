package com.example.demo.model;

import com.example.demo.model.entities.Author;

import java.util.List;

public class BookSearchModel {
    private String title;
    private List<Author> authors;
    private String genre;
    private String isbn;
    private String publisher;
    private String condition;
    private String description;

    public BookSearchModel(String title, List<Author> authors, String genre,
                           String isbn, String publisher, String condition, String description) {
        this.title = title;
        this.authors = authors;
        this.genre = genre;
        this.isbn = isbn;
        this.publisher = publisher;
        this.condition = condition;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
