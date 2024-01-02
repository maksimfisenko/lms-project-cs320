package com.example.demo.model.entities;

import java.util.List;
import java.util.Objects;

public class Book {

    private int id;
    private String isbn;
    private String title;
    private String genre;
    private int numOfPages;
    private String description;
    private String coverType;
    private String publisher;
    private String condition;
    private boolean isReserved;
    private List<Author> authors;

    public Book() {
        this.isReserved = false;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverType() {
        return coverType;
    }

    public void setCoverType(String coverType) {
        this.coverType = coverType;
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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return numOfPages == book.numOfPages && Objects.equals(isbn, book.isbn) && Objects.equals(title, book.title) && Objects.equals(genre, book.genre) && Objects.equals(description, book.description) && Objects.equals(coverType, book.coverType) && Objects.equals(publisher, book.publisher) && Objects.equals(condition, book.condition) && Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, genre, numOfPages, description, coverType, publisher, condition, authors);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", numOfPages=" + numOfPages +
                ", description='" + description + '\'' +
                ", coverType='" + coverType + '\'' +
                ", publisher='" + publisher + '\'' +
                ", condition='" + condition + '\'' +
                ", authors=" + authors +
                '}';
    }
}
