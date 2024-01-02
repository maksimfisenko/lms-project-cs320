package com.example.demo.daoimpl;

import com.example.demo.model.daoimpl.BookDAOImpl;
import com.example.demo.model.entities.Book;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.*;

import java.sql.*;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
public class BookDAOImplTest {

    private BookDAOImpl bookDAO;
    private Connection connection;

    @BeforeAll
    public void setUp() throws SQLException {
        String testDatabaseUrl = "jdbc:sqlite:src/test/resources/test_library.db";
        connection = DriverManager.getConnection(testDatabaseUrl);
        bookDAO = new BookDAOImpl(connection);
    }

    @AfterAll
    public void tearDown() throws SQLException {
        connection.close();
    }

    @AfterEach
    public void clearTable() throws SQLException {
        String clearTableQuery = "DELETE FROM books";
        try (PreparedStatement preparedStatement = connection.prepareStatement(clearTableQuery)) {
            preparedStatement.executeUpdate();
        }
    }

    @Test
    public void testAddAndGetBook() {

        Book book = new Book();
        book.setIsbn("1234");
        book.setTitle("Title");
        book.setCondition("New");

        bookDAO.addBook(book);
        Book retrievedBook = bookDAO.getBookById(book.getId());

        assertNotNull(retrievedBook);
        assertEquals(book.getIsbn(), retrievedBook.getIsbn());
        assertEquals(book.getTitle(), retrievedBook.getTitle());
        assertEquals(book.getCondition(), retrievedBook.getCondition());
    }

    @Test
    public void testGetAllBooks() {

        Book book1 = new Book();
        book1.setIsbn("1234");
        book1.setTitle("Title1");
        book1.setCondition("New");

        Book book2 = new Book();
        book2.setIsbn("1235");
        book2.setTitle("Title2");
        book2.setCondition("New");

        bookDAO.addBook(book1);
        bookDAO.addBook(book2);

        List<Book> allBooks = bookDAO.getAllBooks();

        assertEquals(2, allBooks.size());
        assertEquals(book1.getIsbn(), allBooks.get(0).getIsbn());
        assertEquals(book2.getIsbn(), allBooks.get(1).getIsbn());
    }

    @Test
    public void testUpdateBook() {

        Book book = new Book();
        book.setIsbn("1234");
        book.setTitle("Title");
        book.setCondition("New");

        bookDAO.addBook(book);

        book.setTitle("UpdatedTitle");

        bookDAO.updateBook(book);

        Book updatedBook = bookDAO.getBookById(book.getId());

        assertEquals("UpdatedTitle", updatedBook.getTitle());
    }

    @Test
    public void testDeleteAuthor() {

        Book book = new Book();
        book.setIsbn("1234");
        book.setTitle("Title");
        book.setCondition("New");

        bookDAO.addBook(book);
        bookDAO.deleteBook(book.getId());

        Book deletedBook = bookDAO.getBookById(book.getId());

        assertNull(deletedBook);
    }

    @Test
    public void testGetReservedBooks() {

        Book book1 = new Book();
        book1.setIsbn("1234");
        book1.setTitle("Title1");
        book1.setCondition("New");

        Book book2 = new Book();
        book2.setIsbn("1235");
        book2.setTitle("Title2");
        book2.setCondition("New");

        book2.setReserved(true);

        bookDAO.addBook(book1);
        bookDAO.addBook(book2);

        List<Book> reservedBooks = bookDAO.getReservedBooks();

        assertEquals(1, reservedBooks.size());
        assertEquals("1235", reservedBooks.get(0).getIsbn());
    }

    @Test
    public void testGetNotReservedBooks() {

        Book book1 = new Book();
        book1.setIsbn("1234");
        book1.setTitle("Title1");
        book1.setCondition("New");

        Book book2 = new Book();
        book2.setIsbn("1235");
        book2.setTitle("Title2");
        book2.setCondition("New");

        book2.setReserved(true);

        bookDAO.addBook(book1);
        bookDAO.addBook(book2);

        List<Book> notReservedBooks = bookDAO.getNotReservedBooks();

        assertEquals(1, notReservedBooks.size());
        assertEquals("1234", notReservedBooks.get(0).getIsbn());
    }

}

