package com.example.demo.model.dao;

import com.example.demo.model.entities.Author;

import java.util.List;

public interface AuthorDAO {
    void addAuthor(Author author);
    Author getAuthorById(int id);
    List<Author> getAllAuthors();
    void updateAuthor(Author author);
    void deleteAuthor(int id);
}
