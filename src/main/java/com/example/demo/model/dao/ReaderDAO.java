package com.example.demo.model.dao;

import java.util.List;

public interface ReaderDAO {
    void addReader(ReaderDAO readerDAO);
    ReaderDAO getReaderById(int id);
    List<ReaderDAO> getAllReaders();
    void updateReader(ReaderDAO readerDAO);
    void deleteReader(int id);
}
