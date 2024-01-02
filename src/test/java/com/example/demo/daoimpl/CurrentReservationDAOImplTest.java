package com.example.demo.daoimpl;

import com.example.demo.model.dao.CurrentReservationDAO;
import com.example.demo.model.daoimpl.AdminDAOImpl;
import com.example.demo.model.daoimpl.BookDAOImpl;
import com.example.demo.model.daoimpl.CurrentReservationDAOImpl;
import com.example.demo.model.entities.Admin;
import com.example.demo.model.entities.Book;
import com.example.demo.model.entities.CurrentReservation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
public class CurrentReservationDAOImplTest {

    private CurrentReservationDAOImpl currentReservationDAO;
    private BookDAOImpl bookDAO;
    private Connection connection;

    @BeforeAll
    public void setUp() throws SQLException {
        String testDatabaseUrl = "jdbc:sqlite:src/test/resources/test_library.db";
        connection = DriverManager.getConnection(testDatabaseUrl);
        currentReservationDAO = new CurrentReservationDAOImpl(connection);
        bookDAO = new BookDAOImpl(connection);
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
    }

    @Test
    public void testAddAndGetCurrentReservation() {

        // TODO: finish when reader class is ready

//        Book book = new Book();
//        book.setIsbn("1234");
//        book.setTitle("Title");
//        book.setCondition("New");
//
//        bookDAO.addBook(book);
//
//        CurrentReservation currentReservation = new CurrentReservation();
//        admin.setLogin("Login");
//        admin.setPassword("Password");
//        admin.setName("Name");
//        admin.setSurname("Surname");
//
//        adminDAO.addAdmin(admin);
//        Admin retrievedAdmin = adminDAO.getAdminById(admin.getId());
//
//        assertNotNull(retrievedAdmin);
//        assertEquals(admin.getLogin(), retrievedAdmin.getLogin());
//        assertEquals(admin.getName(), retrievedAdmin.getName());
    }

    @Test
    public void testGetAllCurrentReservations() {

//        Admin admin1 = new Admin();
//        admin1.setLogin("Login1");
//        admin1.setPassword("Password1");
//        admin1.setName("Name1");
//        admin1.setSurname("Surname1");
//
//        Admin admin2 = new Admin();
//        admin2.setLogin("Login2");
//        admin2.setPassword("Password2");
//        admin2.setName("Name2");
//        admin2.setSurname("Surname2");
//
//        adminDAO.addAdmin(admin1);
//        adminDAO.addAdmin(admin2);
//
//        List<Admin> allAdmins = adminDAO.getAllAdmins();
//
//        assertEquals(2, allAdmins.size());
//        assertEquals(admin1.getName(), allAdmins.get(0).getName());
//        assertEquals(admin2.getName(), allAdmins.get(1).getName());
    }

    @Test
    public void testGetCurrentReservationsById() {
     // do
    }
}

    @Test
    public void testUpdateCurrentReservation() {

//        Admin admin = new Admin();
//        admin.setLogin("Login");
//        admin.setPassword("Password");
//        admin.setName("Name");
//        admin.setSurname("Surname");
//
//        adminDAO.addAdmin(admin);
//
//        admin.setName("UpdatedName");
//        admin.setSurname("UpdatedSurname");
//
//        adminDAO.updateAdmin(admin);
//
//        Admin updatedAdmin = adminDAO.getAdminById(admin.getId());
//
//        assertEquals("UpdatedName", updatedAdmin.getName());
//        assertEquals("UpdatedSurname", updatedAdmin.getSurname());
    }

    @Test
    public void testDeleteCurrentReservation() {

//        Admin admin = new Admin();
//        admin.setLogin("Login");
//        admin.setPassword("Password");
//        admin.setName("Name");
//        admin.setSurname("Surname");
//
//        adminDAO.addAdmin(admin);
//        adminDAO.deleteAdmin(admin.getId());
//
//        Admin deletedAdmin = adminDAO.getAdminById(admin.getId());
//
//        assertNull(deletedAdmin);
//    }

}
