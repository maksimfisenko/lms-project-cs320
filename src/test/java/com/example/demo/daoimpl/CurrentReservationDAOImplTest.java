package com.example.demo.daoimpl;

import com.example.demo.model.daoimpl.BookDAOImpl;
import com.example.demo.model.daoimpl.CurrentReservationDAOImpl;
import com.example.demo.model.daoimpl.ReaderDAOImpl;
import com.example.demo.model.entities.Book;
import com.example.demo.model.entities.CurrentReservation;
import com.example.demo.model.entities.Reader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
public class CurrentReservationDAOImplTest {

    private CurrentReservationDAOImpl currentReservationDAO;
    private BookDAOImpl bookDAO;
    private ReaderDAOImpl readerDAO;
    private Connection connection;

    @BeforeAll
    public void setUp() throws SQLException {
        String testDatabaseUrl = "jdbc:sqlite:src/test/resources/test_library.db";
        connection = DriverManager.getConnection(testDatabaseUrl);
        currentReservationDAO = new CurrentReservationDAOImpl(connection);
        bookDAO = new BookDAOImpl(connection);
        readerDAO = new ReaderDAOImpl(connection);
    }

    @AfterAll
    public void tearDown() throws SQLException {
        connection.close();
    }

    @AfterEach
    public void clearTable() throws SQLException {
        String clearTableQuery = "DELETE FROM current_reservations";
        try (PreparedStatement preparedStatement = connection.prepareStatement(clearTableQuery)) {
            preparedStatement.executeUpdate();
        }
        clearTableQuery = "DELETE FROM readers";
        try (PreparedStatement preparedStatement = connection.prepareStatement(clearTableQuery)) {
            preparedStatement.executeUpdate();
        }
        clearTableQuery = "DELETE FROM books";
        try (PreparedStatement preparedStatement = connection.prepareStatement(clearTableQuery)) {
            preparedStatement.executeUpdate();
        }
    }

    @Test
    public void testAddAndGetCurrentReservation() {

        Reader reader = new Reader();
        reader.setLogin("Login");
        reader.setPassword("Password");
        reader.setName("Name");
        reader.setSurname("Surname");
        readerDAO.addReader(reader);

        Book book = new Book();
        book.setIsbn("1234");
        book.setTitle("Title");
        book.setCondition("New");
        bookDAO.addBook(book);

        CurrentReservation currentReservation = new CurrentReservation();
        currentReservation.setBookReserved(book);
        currentReservation.setReader(reader);
        currentReservation.setNumOfDaysForReservation(10);
        currentReservation.setDateOfPicking(LocalDate.now());

        currentReservationDAO.addCurrentReservation(currentReservation);
        CurrentReservation retrievedCurReservation =
                currentReservationDAO.getCurrentReservationById(currentReservation.getId());

        assertNotNull(retrievedCurReservation);
        assertEquals(currentReservation.getBookReserved(), retrievedCurReservation.getBookReserved());
        assertEquals(currentReservation.getReader(), retrievedCurReservation.getReader());
    }

    @Test
    public void testGetAllCurrentReservations() {

        Reader reader = new Reader();
        reader.setLogin("Login");
        reader.setPassword("Password");
        reader.setName("Name");
        reader.setSurname("Surname");
        readerDAO.addReader(reader);

        Book book1 = new Book();
        book1.setIsbn("1234");
        book1.setTitle("Title1");
        book1.setCondition("New1");
        bookDAO.addBook(book1);

        Book book2 = new Book();
        book2.setIsbn("1235");
        book2.setTitle("Title2");
        book2.setCondition("New2");
        bookDAO.addBook(book2);

        CurrentReservation currentReservation1 = new CurrentReservation();
        currentReservation1.setBookReserved(book1);
        currentReservation1.setReader(reader);
        currentReservation1.setNumOfDaysForReservation(10);
        currentReservation1.setDateOfPicking(LocalDate.now());

        CurrentReservation currentReservation2 = new CurrentReservation();
        currentReservation2.setBookReserved(book2);
        currentReservation2.setReader(reader);
        currentReservation2.setNumOfDaysForReservation(5);
        currentReservation2.setDateOfPicking(LocalDate.now());

        currentReservationDAO.addCurrentReservation(currentReservation1);
        currentReservationDAO.addCurrentReservation(currentReservation2);

        List<CurrentReservation> allReservations = currentReservationDAO.getAllCurrentReservations();

        assertEquals(2, allReservations.size());
        assertEquals(book1.getTitle(), allReservations.get(0).getBookReserved().getTitle());
        assertEquals(book2.getTitle(), allReservations.get(1).getBookReserved().getTitle());
    }

    @Test
    public void testGetCurrentReservationsByReaderId() {

        Reader reader1 = new Reader();
        reader1.setLogin("Login1");
        reader1.setPassword("Password1");
        reader1.setName("Name1");
        reader1.setSurname("Surname1");
        readerDAO.addReader(reader1);

        Reader reader2 = new Reader();
        reader2.setLogin("Login2");
        reader2.setPassword("Password2");
        reader2.setName("Name2");
        reader2.setSurname("Surname2");
        readerDAO.addReader(reader2);

        Book book1 = new Book();
        book1.setIsbn("1234");
        book1.setTitle("Title1");
        book1.setCondition("New1");
        bookDAO.addBook(book1);

        Book book2 = new Book();
        book2.setIsbn("1235");
        book2.setTitle("Title2");
        book2.setCondition("New2");
        bookDAO.addBook(book2);

        CurrentReservation currentReservation1 = new CurrentReservation();
        currentReservation1.setBookReserved(book1);
        currentReservation1.setReader(reader1);
        currentReservation1.setNumOfDaysForReservation(10);
        currentReservation1.setDateOfPicking(LocalDate.now());

        CurrentReservation currentReservation2 = new CurrentReservation();
        currentReservation2.setBookReserved(book2);
        currentReservation2.setReader(reader2);
        currentReservation2.setNumOfDaysForReservation(10);
        currentReservation2.setDateOfPicking(LocalDate.now());

        currentReservationDAO.addCurrentReservation(currentReservation1);
        currentReservationDAO.addCurrentReservation(currentReservation2);

        List<CurrentReservation> reservations = currentReservationDAO.getCurrentReservationsByReaderId(reader1.getId());

        assertEquals(1, reservations.size());
    }


    @Test
    public void testUpdateCurrentReservation() {

        Reader reader = new Reader();
        reader.setLogin("Login");
        reader.setPassword("Password");
        reader.setName("Name");
        reader.setSurname("Surname");
        readerDAO.addReader(reader);

        Book book = new Book();
        book.setIsbn("1234");
        book.setTitle("Title");
        book.setCondition("New");
        bookDAO.addBook(book);

        CurrentReservation currentReservation = new CurrentReservation();
        currentReservation.setBookReserved(book);
        currentReservation.setReader(reader);
        currentReservation.setNumOfDaysForReservation(10);
        currentReservation.setDateOfPicking(LocalDate.now());

        currentReservationDAO.addCurrentReservation(currentReservation);

        currentReservation.setNumOfDaysForReservation(20);

        currentReservationDAO.updateCurrentReservation(currentReservation);

        CurrentReservation updatedReservation =
                currentReservationDAO.getCurrentReservationById(currentReservation.getId());

        assertEquals(20, updatedReservation.getNumOfDaysForReservation());
    }

    @Test
    public void testDeleteCurrentReservation() {

        Reader reader = new Reader();
        reader.setLogin("Login");
        reader.setPassword("Password");
        reader.setName("Name");
        reader.setSurname("Surname");
        readerDAO.addReader(reader);

        Book book = new Book();
        book.setIsbn("1234");
        book.setTitle("Title");
        book.setCondition("New");
        bookDAO.addBook(book);

        CurrentReservation currentReservation = new CurrentReservation();
        currentReservation.setBookReserved(book);
        currentReservation.setReader(reader);
        currentReservation.setNumOfDaysForReservation(10);
        currentReservation.setDateOfPicking(LocalDate.now());

        currentReservationDAO.addCurrentReservation(currentReservation);
        currentReservationDAO.deleteCurrentReservation(currentReservation.getId());

        CurrentReservation deletedReservation =
                currentReservationDAO.getCurrentReservationById(currentReservation.getId());

        assertNull(deletedReservation);
    }

}
