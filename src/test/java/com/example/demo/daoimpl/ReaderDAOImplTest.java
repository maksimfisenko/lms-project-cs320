package com.example.demo.daoimpl;

import com.example.demo.model.daoimpl.ReaderDAOImpl;
import com.example.demo.model.entities.Reader;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReaderDAOImplTest {

    private ReaderDAOImpl readerDAO;
    private Connection connection;

    @BeforeAll
    public void setUp() throws SQLException {
        String testDatabaseUrl = "jdbc:sqlite:src/test/resources/test_library.db";
        connection = DriverManager.getConnection(testDatabaseUrl);
        readerDAO = new ReaderDAOImpl(connection);
    }

    @AfterAll
    public void tearDown() throws SQLException {
        connection.close();
    }

    @AfterEach
    public void clearTable() throws SQLException {
        String clearTableQuery = "DELETE FROM readers";
        try (PreparedStatement preparedStatement = connection.prepareStatement(clearTableQuery)) {
            preparedStatement.executeUpdate();
        }
    }

    @Test
    public void testAddAndGetReader() {

        Reader reader = new Reader();
        reader.setLogin("Login");
        reader.setPassword("Password");
        reader.setName("Name");
        reader.setSurname("Surname");

        readerDAO.addReader(reader);
        Reader retrievedReader = readerDAO.getReaderById(reader.getId());

        assertNotNull(retrievedReader);
        assertEquals(reader.getLogin(), retrievedReader.getLogin());
        assertEquals(reader.getName(), retrievedReader.getName());
    }

    @Test
    public void testGetAllReaders() {

        Reader reader1 = new Reader();
        reader1.setLogin("Login1");
        reader1.setPassword("Password1");
        reader1.setName("Name1");
        reader1.setSurname("Surname1");

        Reader reader2 = new Reader();
        reader2.setLogin("Login2");
        reader2.setPassword("Password2");
        reader2.setName("Name2");
        reader2.setSurname("Surname2");

        readerDAO.addReader(reader1);
        readerDAO.addReader(reader2);

        List<Reader> allReaders = readerDAO.getAllReaders();

        assertEquals(2, allReaders.size());
        assertEquals(reader1.getName(), allReaders.get(0).getName());
        assertEquals(reader2.getName(), allReaders.get(1).getName());
    }

    @Test
    public void testUpdateReader() {

        Reader reader = new Reader();
        reader.setLogin("Login");
        reader.setPassword("Password");
        reader.setName("Name");
        reader.setSurname("Surname");

        readerDAO.addReader(reader);

        reader.setName("UpdatedName");
        reader.setSurname("UpdatedSurname");

        readerDAO.updateReader(reader);

        Reader updatedReader = readerDAO.getReaderById(reader.getId());

        assertEquals("UpdatedName", updatedReader.getName());
        assertEquals("UpdatedSurname", updatedReader.getSurname());
    }

    @Test
    public void testDeleteReader() {

        Reader reader = new Reader();
        reader.setLogin("Login");
        reader.setPassword("Password");
        reader.setName("Name");
        reader.setSurname("Surname");

        readerDAO.addReader(reader);
        readerDAO.deleteReader(reader.getId());

        Reader deletedReader = readerDAO.getReaderById(reader.getId());

        assertNull(deletedReader);
    }

}
