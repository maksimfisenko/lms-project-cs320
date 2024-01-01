package com.example.demo.model.dao;

import java.util.List;

public interface Reader {
    void addReader(Reader reader);
    Reader getReaderById(int id);
    List<Reader> getAllReaders();
    void updateReader(Reader reader);
    void deleteReader(int id);
}
