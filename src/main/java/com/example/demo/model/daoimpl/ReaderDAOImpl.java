package com.example.demo.model.daoimpl;

import com.example.demo.model.dao.ReaderDAO;
import com.example.demo.model.entities.Book;
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
        String query = "INSERT INTO readers (login, password, name, surname, num_of_strikes) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, reader.getLogin());
            preparedStatement.setString(2, reader.getPassword());
            preparedStatement.setString(3,reader.getName());
            preparedStatement.setString(4,reader.getSurname());
            preparedStatement.setInt(5,reader.getNumOfStrikes());

            preparedStatement.executeUpdate();

            Statement statement = connection.createStatement();
            ResultSet generatedKeys = statement.executeQuery("SELECT last_insert_rowid()");
            if (generatedKeys.next()) {
                int generatedKey = generatedKeys.getInt(1);
                reader.setId(generatedKey);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reader getReaderById(int id) {
        String query = "SELECT * FROM readers WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToReader(resultSet);
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
        String query = "SELECT * FROM readers";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                readers.add(mapResultSetToReader(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readers;
    }

    @Override
    public void updateReader(Reader reader) {
        String query = "UPDATE readers SET login = ?, password = ?, name = ?, surname = ?, num_of_strikes = ?  WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, reader.getLogin());
            preparedStatement.setString(2, reader.getPassword());
            preparedStatement.setString(3, reader.getName());
            preparedStatement.setString(4, reader.getSurname());
            preparedStatement.setInt(5,reader.getNumOfStrikes());
            preparedStatement.setInt(6, reader.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteReader(int id) {
        String query = "DELETE FROM readers WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean readerExists(String login, String password) {
        String query = "SELECT * FROM readers WHERE login = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Reader getReaderByLogin(String login) {
        String query = "SELECT * FROM readers WHERE login = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToReader(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean loginCheck(String login){
        String query = "SELECT * FROM readers WHERE login = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, login);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private Reader mapResultSetToReader(ResultSet resultSet) throws SQLException {

        Reader reader = new Reader(
                resultSet.getInt("id"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("surname")
        );
        reader.setNumOfStrikes(resultSet.getInt("num_of_strikes"));

        return reader;
    }
}
