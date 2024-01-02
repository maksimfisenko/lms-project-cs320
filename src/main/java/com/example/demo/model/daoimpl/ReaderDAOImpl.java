package com.example.demo.model.daoimpl;

import com.example.demo.model.dao.ReaderDAO;
import com.example.demo.model.entities.Reader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReaderDAOImpl implements ReaderDAO {
    private final Connection connection;

    public ReaderDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addReader(Reader reader) {
        String insertQuery = "INSERT INTO readers (name, email) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, reader.getName());
            preparedStatement.setString(2, reader.getLogin());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reader getReaderById(int id) {
        String selectQuery = "SELECT * FROM readers WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Reader(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("login"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reader> getAllReaders() {
        List<Reader> readers = new ArrayList<>();
        String selectAllQuery = "SELECT * FROM readers";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(selectAllQuery)) {
            while (resultSet.next()) {
                readers.add(new Reader(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("login")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readers;
    }

    @Override
    public void updateReader(Reader reader) {
        String updateQuery = "UPDATE readers SET name = ?, email = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, reader.getName());
            preparedStatement.setString(2, reader.getLogin());
            preparedStatement.setInt(3, reader.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteReader(int id) {
        String deleteQuery = "DELETE FROM readers WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
