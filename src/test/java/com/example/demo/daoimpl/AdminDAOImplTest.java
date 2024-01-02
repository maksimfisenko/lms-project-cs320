package com.example.demo.daoimpl;

import com.example.demo.model.daoimpl.AdminDAOImpl;
import com.example.demo.model.entities.Admin;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.*;

import java.sql.*;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
public class AdminDAOImplTest {

    private AdminDAOImpl adminDAO;
    private Connection connection;

    @BeforeAll
    public void setUp() throws SQLException {
        String testDatabaseUrl = "jdbc:sqlite:src/test/resources/test_library.db";
        connection = DriverManager.getConnection(testDatabaseUrl);
        adminDAO = new AdminDAOImpl(connection);
    }

    @AfterAll
    public void tearDown() throws SQLException {
        connection.close();
    }

    @AfterEach
    public void clearTable() throws SQLException {
        String clearTableQuery = "DELETE FROM admins";
        try (PreparedStatement preparedStatement = connection.prepareStatement(clearTableQuery)) {
            preparedStatement.executeUpdate();
        }
    }

    @Test
    public void testAddAndGetAdmin() {

        Admin admin = new Admin();
        admin.setLogin("Login");
        admin.setPassword("Password");
        admin.setName("Name");
        admin.setSurname("Surname");

        adminDAO.addAdmin(admin);
        Admin retrievedAdmin = adminDAO.getAdminById(admin.getId());

        assertNotNull(retrievedAdmin);
        assertEquals(admin.getLogin(), retrievedAdmin.getLogin());
        assertEquals(admin.getName(), retrievedAdmin.getName());
    }

    @Test
    public void testGetAllAdmins() {

        Admin admin1 = new Admin();
        admin1.setLogin("Login1");
        admin1.setPassword("Password1");
        admin1.setName("Name1");
        admin1.setSurname("Surname1");

        Admin admin2 = new Admin();
        admin2.setLogin("Login2");
        admin2.setPassword("Password2");
        admin2.setName("Name2");
        admin2.setSurname("Surname2");

        adminDAO.addAdmin(admin1);
        adminDAO.addAdmin(admin2);

        List<Admin> allAdmins = adminDAO.getAllAdmins();

        assertEquals(2, allAdmins.size());
        assertEquals(admin1.getName(), allAdmins.get(0).getName());
        assertEquals(admin2.getName(), allAdmins.get(1).getName());
    }

    @Test
    public void testUpdateAdmin() {

        Admin admin = new Admin();
        admin.setLogin("Login");
        admin.setPassword("Password");
        admin.setName("Name");
        admin.setSurname("Surname");

        adminDAO.addAdmin(admin);

        admin.setName("UpdatedName");
        admin.setSurname("UpdatedSurname");

        adminDAO.updateAdmin(admin);

        Admin updatedAdmin = adminDAO.getAdminById(admin.getId());

        assertEquals("UpdatedName", updatedAdmin.getName());
        assertEquals("UpdatedSurname", updatedAdmin.getSurname());
    }

    @Test
    public void testDeleteAdmin() {

        Admin admin = new Admin();
        admin.setLogin("Login");
        admin.setPassword("Password");
        admin.setName("Name");
        admin.setSurname("Surname");

        adminDAO.addAdmin(admin);
        adminDAO.deleteAdmin(admin.getId());

        Admin deletedAdmin = adminDAO.getAdminById(admin.getId());

        assertNull(deletedAdmin);
    }

}
