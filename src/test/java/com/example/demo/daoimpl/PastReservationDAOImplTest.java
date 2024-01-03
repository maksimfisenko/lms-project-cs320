package com.example.demo.daoimpl;

import com.example.demo.model.daoimpl.BookDAOImpl;
import com.example.demo.model.daoimpl.PastReservationDAOImpl;
import com.example.demo.model.daoimpl.ReaderDAOImpl;
import com.example.demo.model.entities.Book;
import com.example.demo.model.entities.PastReservation;
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
public class PastReservationDAOImplTest {

    private PastReservationDAOImpl pastReservationDAO;
    private BookDAOImpl bookDAO;
    private ReaderDAOImpl readerDAO;
    private Connection connection;

    @BeforeAll
    public void setUp() throws SQLException {
        String testDatabaseUrl = "jdbc:sqlite:src/test/resources/test_library.db";
        connection = DriverManager.getConnection(testDatabaseUrl);
        pastReservationDAO = new PastReservationDAOImpl(connection);
        bookDAO = new BookDAOImpl(connection);
        readerDAO = new ReaderDAOImpl(connection);
    }

    @AfterAll
    public void tearDown() throws SQLException {
        connection.close();
    }

    @AfterEach
    public void clearTable() throws SQLException {
        String clearTableQuery = "DELETE FROM past_reservations";
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
    public void testAddAndGetPastReservation() {

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
        book.setAuthor("Author");
        bookDAO.addBook(book);

        PastReservation pastReservation = new PastReservation();
        pastReservation.setBookReserved(book);
        pastReservation.setReader(reader);
        pastReservation.setNumOfDaysForReservation(10);
        pastReservation.setDateOfPicking(LocalDate.now().minusDays(10));
        pastReservation.setDateOfReturn(LocalDate.now());
        pastReservation.setStrikeIsIssued(false);
        pastReservation.setBookConditionOnReturn("Acceptable");

        pastReservationDAO.addPastReservation(pastReservation);
        PastReservation retrievedPastReservation =
                pastReservationDAO.getPastReservationById(pastReservation.getId());

        assertNotNull(retrievedPastReservation);
        assertEquals(pastReservation.getBookReserved(), retrievedPastReservation.getBookReserved());
        assertEquals(pastReservation.getReader(), retrievedPastReservation.getReader());
    }

    @Test
    public void testGetAllPastReservations() {

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
        book1.setAuthor("Author1");
        bookDAO.addBook(book1);

        Book book2 = new Book();
        book2.setIsbn("1235");
        book2.setTitle("Title2");
        book2.setCondition("New2");
        book2.setAuthor("Author2");
        bookDAO.addBook(book2);

        PastReservation pastReservation1 = new PastReservation();
        pastReservation1.setBookReserved(book1);
        pastReservation1.setReader(reader);
        pastReservation1.setNumOfDaysForReservation(10);
        pastReservation1.setDateOfPicking(LocalDate.now().minusDays(10));
        pastReservation1.setDateOfReturn(LocalDate.now());
        pastReservation1.setStrikeIsIssued(false);
        pastReservation1.setBookConditionOnReturn("Acceptable");

        PastReservation pastReservation2 = new PastReservation();
        pastReservation2.setBookReserved(book2);
        pastReservation2.setReader(reader);
        pastReservation2.setNumOfDaysForReservation(5);
        pastReservation2.setDateOfPicking(LocalDate.now().minusDays(10));
        pastReservation2.setDateOfReturn(LocalDate.now());
        pastReservation2.setStrikeIsIssued(false);
        pastReservation2.setBookConditionOnReturn("Goof");

        pastReservationDAO.addPastReservation(pastReservation1);
        pastReservationDAO.addPastReservation(pastReservation2);

        List<PastReservation> allReservations = pastReservationDAO.getAllPastReservations();

        assertEquals(2, allReservations.size());
        assertEquals(book1.getTitle(), allReservations.get(0).getBookReserved().getTitle());
        assertEquals(book2.getTitle(), allReservations.get(1).getBookReserved().getTitle());
    }

    @Test
    public void testGetPastReservationsByReaderId() {

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
        book1.setAuthor("Author1");
        bookDAO.addBook(book1);

        Book book2 = new Book();
        book2.setIsbn("1235");
        book2.setTitle("Title2");
        book2.setCondition("New2");
        book2.setAuthor("Author2");
        bookDAO.addBook(book2);

        PastReservation pastReservation1 = new PastReservation();
        pastReservation1.setBookReserved(book1);
        pastReservation1.setReader(reader1);
        pastReservation1.setNumOfDaysForReservation(10);
        pastReservation1.setDateOfPicking(LocalDate.now().minusDays(10));
        pastReservation1.setDateOfReturn(LocalDate.now());
        pastReservation1.setStrikeIsIssued(false);
        pastReservation1.setBookConditionOnReturn("Acceptable");

        PastReservation pastReservation2 = new PastReservation();
        pastReservation2.setBookReserved(book1);
        pastReservation2.setReader(reader2);
        pastReservation2.setNumOfDaysForReservation(5);
        pastReservation2.setDateOfPicking(LocalDate.now().minusDays(10));
        pastReservation2.setDateOfReturn(LocalDate.now());
        pastReservation2.setStrikeIsIssued(false);
        pastReservation2.setBookConditionOnReturn("Goof");

        pastReservationDAO.addPastReservation(pastReservation1);
        pastReservationDAO.addPastReservation(pastReservation2);

        List<PastReservation> reservations = pastReservationDAO.getPastReservationsByReaderId(reader1.getId());

        assertEquals(1, reservations.size());
    }


    @Test
    public void testUpdatePastReservation() {

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
        book.setAuthor("Author");
        bookDAO.addBook(book);

        PastReservation pastReservation = new PastReservation();
        pastReservation.setBookReserved(book);
        pastReservation.setReader(reader);
        pastReservation.setNumOfDaysForReservation(10);
        pastReservation.setDateOfPicking(LocalDate.now().minusDays(10));
        pastReservation.setDateOfReturn(LocalDate.now());
        pastReservation.setStrikeIsIssued(false);
        pastReservation.setBookConditionOnReturn("Acceptable");

        pastReservationDAO.addPastReservation(pastReservation);

        pastReservation.setDateOfReturn(LocalDate.now().minusDays(1));

        pastReservationDAO.updatePastReservation(pastReservation);

        PastReservation updatedReservation =
                pastReservationDAO.getPastReservationById(pastReservation.getId());

        assertEquals(LocalDate.now().minusDays(1), updatedReservation.getDateOfReturn());
    }

    @Test
    public void testDeletePastReservation() {

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
        book.setAuthor("Author");
        bookDAO.addBook(book);

        PastReservation pastReservation = new PastReservation();
        pastReservation.setBookReserved(book);
        pastReservation.setReader(reader);
        pastReservation.setNumOfDaysForReservation(10);
        pastReservation.setDateOfPicking(LocalDate.now().minusDays(10));
        pastReservation.setDateOfReturn(LocalDate.now());
        pastReservation.setStrikeIsIssued(false);
        pastReservation.setBookConditionOnReturn("Acceptable");

        pastReservationDAO.addPastReservation(pastReservation);
        pastReservationDAO.deletePastReservation(pastReservation.getId());

        PastReservation deletedReservation =
                pastReservationDAO.getPastReservationById(pastReservation.getId());

        assertNull(deletedReservation);
    }

}
