package com.example.demo.model.dao;

import com.example.demo.model.entities.Reader ;

import java.util.List;

public interface ReaderDAO {
    void addReader(Reader reader);
    Reader getReaderById(int id);
    List<Reader> getAllReaders();
    void updateReader(Reader reader);
    void deleteReader(int id);
    boolean readerExists(String login, String password);
}
