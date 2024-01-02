package com.example.demo.model.daoimpl;

import com.example.demo.model.dao.BookDAO;
import com.example.demo.model.entities.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {
    private final Connection connection;

    public BookDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addBook(Book book) {

        String query = "INSERT INTO books (isbn, title, genre, num_of_pages, description, cover_type, publisher, condition, is_reserved) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getGenre());
            preparedStatement.setInt(4, book.getNumOfPages());
            preparedStatement.setString(5, book.getDescription());
            preparedStatement.setString(6, book.getCoverType());
            preparedStatement.setString(7, book.getPublisher());
            preparedStatement.setString(8, book.getCondition());
            preparedStatement.setBoolean(9, book.isReserved());

            preparedStatement.executeUpdate();

            Statement statement = connection.createStatement();
            ResultSet generatedKeys = statement.executeQuery("SELECT last_insert_rowid()");
            if (generatedKeys.next()) {
                int generatedKey = generatedKeys.getInt(1);
                book.setId(generatedKey);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book getBookById(int bookId) {

        String query = "SELECT * FROM books WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, bookId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToBook(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() {

        List<Book> books = new ArrayList<>();

        String query = "SELECT * FROM books";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                books.add(mapResultSetToBook(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public void updateBook(Book book) {

        String query = "UPDATE books SET isbn = ?, title = ?, genre = ?, num_of_pages = ?, description = ?, cover_type = ?, publisher = ?, condition = ?, is_reserved = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getGenre());
            preparedStatement.setInt(4, book.getNumOfPages());
            preparedStatement.setString(5, book.getDescription());
            preparedStatement.setString(6, book.getCoverType());
            preparedStatement.setString(7, book.getPublisher());
            preparedStatement.setString(8, book.getCondition());
            preparedStatement.setBoolean(9, book.isReserved());
            preparedStatement.setInt(10, book.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBook(int bookId) {

        String query = "DELETE FROM books WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, bookId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> getReservedBooks() {

        List<Book> reservedBooks = new ArrayList<>();

        String query = "SELECT * FROM books WHERE is_reserved = 1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                reservedBooks.add(mapResultSetToBook(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservedBooks;
    }

    @Override
    public List<Book> getNotReservedBooks() {

        List<Book> reservedBooks = new ArrayList<>();

        String query = "SELECT * FROM books WHERE is_reserved = 0";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                reservedBooks.add(mapResultSetToBook(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservedBooks;
    }

    private Book mapResultSetToBook(ResultSet resultSet) throws SQLException {

        Book book = new Book();

        book.setId(resultSet.getInt("id"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setTitle(resultSet.getString("title"));
        book.setGenre(resultSet.getString("genre"));
        book.setNumOfPages(resultSet.getInt("num_of_pages"));
        book.setDescription(resultSet.getString("description"));
        book.setCoverType(resultSet.getString("cover_type"));
        book.setPublisher(resultSet.getString("publisher"));
        book.setCondition(resultSet.getString("condition"));
        book.setReserved(resultSet.getBoolean("is_reserved"));

        return book;
    }
}
