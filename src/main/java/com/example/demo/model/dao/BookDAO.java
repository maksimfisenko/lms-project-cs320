package com.example.demo.model.dao;

import com.example.demo.model.entities.Book;

import java.util.List;

public interface BookDAO {
    void addBook(Book book);
    Book getBookById(int id);
    List<Book> getAllBooks();
    void updateBook(Book book);
    void deleteBook(int id);
    List<Book> getReservedBooks();
    List<Book> getNotReservedBooks();
}
