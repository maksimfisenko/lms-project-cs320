package com.example.demo.model.daoimpl;

import com.example.demo.model.dao.AuthorDAO;
import com.example.demo.model.entities.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAOImpl implements AuthorDAO {
    private final Connection connection;

    public AuthorDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addAuthor(Author author) {

        String query = "INSERT INTO authors (name, surname, year_of_birth, year_of_death) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getSurname());
            preparedStatement.setInt(3, author.getYearOfBirth());
            preparedStatement.setInt(4, author.getYearOfDeath());

            preparedStatement.executeUpdate();

            Statement statement = connection.createStatement();
            ResultSet generatedKeys = statement.executeQuery("SELECT last_insert_rowid()");
            if (generatedKeys.next()) {
                int generatedKey = generatedKeys.getInt(1);
                author.setId(generatedKey);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Author getAuthorById(int authorId) {

        String query = "SELECT * FROM authors WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, authorId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToAuthor(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Author> getAllAuthors() {

        List<Author> authors = new ArrayList<>();

        String query = "SELECT * FROM authors";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                authors.add(mapResultSetToAuthor(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    @Override
    public void updateAuthor(Author author) {

        String query = "UPDATE authors SET name = ?, surname = ?, year_of_birth = ?, year_of_death = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getSurname());
            preparedStatement.setInt(3, author.getYearOfBirth());
            preparedStatement.setInt(4, author.getYearOfDeath());
            preparedStatement.setInt(5, author.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAuthor(int authorId) {

        String query = "DELETE FROM authors WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, authorId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Author mapResultSetToAuthor(ResultSet resultSet) throws SQLException {

        Author author = new Author();

        author.setId(resultSet.getInt("id"));
        author.setName(resultSet.getString("name"));
        author.setSurname(resultSet.getString("surname"));
        author.setYearOfBirth(resultSet.getInt("year_of_birth"));
        author.setYearOfDeath(resultSet.getInt("year_of_death"));

        return author;
    }
}
