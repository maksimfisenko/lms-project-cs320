package com.example.demo.daoimpl;

import com.example.demo.model.daoimpl.AuthorDAOImpl;
import com.example.demo.model.entities.Author;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.*;

import java.sql.*;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
public class AuthorDAOImplTest {

    private AuthorDAOImpl authorDAO;
    private Connection connection;

    @BeforeAll
    public void setUp() throws SQLException {
        String testDatabaseUrl = "jdbc:sqlite:src/test/resources/test_library.db";
        connection = DriverManager.getConnection(testDatabaseUrl);
        authorDAO = new AuthorDAOImpl(connection);
    }

    @AfterAll
    public void tearDown() throws SQLException {
        connection.close();
    }

    @AfterEach
    public void clearTable() throws SQLException {
        String clearTableQuery = "DELETE FROM authors";
        try (PreparedStatement preparedStatement = connection.prepareStatement(clearTableQuery)) {
            preparedStatement.executeUpdate();
        }
    }

    @Test
    public void testAddAndGetAuthor() {

        Author author = new Author();
        author.setName("Name");
        author.setSurname("Surname");
        author.setYearOfBirth(1900);

        authorDAO.addAuthor(author);
        Author retrievedAuthor = authorDAO.getAuthorById(author.getId());

        assertNotNull(retrievedAuthor);
        assertEquals(author.getName(), retrievedAuthor.getName());
        assertEquals(author.getSurname(), retrievedAuthor.getSurname());
        assertEquals(author.getYearOfBirth(), retrievedAuthor.getYearOfBirth());
        assertEquals(author.getYearOfDeath(), retrievedAuthor.getYearOfDeath());
    }

    @Test
    public void testGetAllAuthors() {

        Author author1 = new Author();
        author1.setName("Name1");
        author1.setSurname("Surname1");
        author1.setYearOfBirth(1900);

        Author author2 = new Author();
        author2.setName("Name2");
        author2.setSurname("Surname2");
        author2.setYearOfBirth(1900);

        authorDAO.addAuthor(author1);
        authorDAO.addAuthor(author2);

        List<Author> allAuthors = authorDAO.getAllAuthors();

        assertEquals(2, allAuthors.size());
        assertEquals(author1.getName(), allAuthors.get(0).getName());
        assertEquals(author2.getName(), allAuthors.get(1).getName());
    }

    @Test
    public void testUpdateAuthor() {

        Author author = new Author();
        author.setName("Name");
        author.setSurname("Surname");
        author.setYearOfBirth(1900);

        authorDAO.addAuthor(author);

        author.setName("UpdatedName");
        author.setSurname("UpdatedSurname");

        authorDAO.updateAuthor(author);

        Author updatedAuthor = authorDAO.getAuthorById(author.getId());

        // Assert
        assertEquals("UpdatedName", updatedAuthor.getName());
        assertEquals("UpdatedSurname", updatedAuthor.getSurname());
    }

    @Test
    public void testDeleteAuthor() {

        Author author = new Author();
        author.setName("Name");
        author.setSurname("Surname");
        author.setYearOfBirth(1900);

        authorDAO.addAuthor(author);
        authorDAO.deleteAuthor(author.getId());

        Author deletedAuthor = authorDAO.getAuthorById(author.getId());

        assertNull(deletedAuthor);
    }

}
