package com.example.demo.model.daoimpl;

import com.example.demo.model.dao.ReaderDAO;
import com.example.demo.model.entities.Reader ;

import java.sql.Connection;
import java.util.List;

public class ReaderDAOImpl implements ReaderDAO {
    private final Connection connection ;


    public ReaderDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addReader(Reader reader) {

    }

    @Override
    public Reader getReaderById(int id) {
        return null;
    }

    @Override
    public List<Reader> getAllReaders() {
        return null;
    }

    @Override
    public void updateReader(Reader reader) {

    }

    @Override
    public void deleteReader(int id) {

    }
}
