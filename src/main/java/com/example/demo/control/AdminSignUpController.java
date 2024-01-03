package com.example.demo.control;

import com.example.demo.model.daoimpl.AdminDAOImpl;
import com.example.demo.model.daoimpl.BookDAOImpl;
import com.example.demo.model.entities.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AdminSignUpController extends Controller {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordCheckField;

    private AdminDAOImpl adminDAO;

    private void throwError(String text) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Admin Creating Error");
        error.setContentText(text);
        error.showAndWait();
    }

    public void back(ActionEvent e) throws IOException{
        this.LoadScene("AdminWelcome.fxml", e, this.getLoginInfo());
    }

    public void save(ActionEvent e) throws IOException {

        if (firstNameField.getText().isEmpty() || firstNameField.getText().isBlank()) {
            throwError("First Name cannot be empty.");
            return;
        }
        if (lastNameField.getText().isEmpty() || lastNameField.getText().isBlank()) {
            throwError("Last Name cannot be empty.");
            return;
        }
        if (loginField.getText().isEmpty() || loginField.getText().isBlank()) {
            throwError("Login cannot be empty.");
            return;
        }
        // TODO: check if there are readers and admins with this login already
        // TODO: maybe add that login should be at least 8 symbols, for example
        if (!passwordField.getText().equals(passwordCheckField.getText())) {
            throwError("Passwords are not the same.");
            return;
        }
        if (passwordField.getText().isEmpty() || passwordField.getText().isBlank()) {
            throwError("Password cannot be empty.");
            return;
        }

        Admin admin = new Admin();
        admin.setLogin(loginField.getText());
        admin.setPassword(passwordField.getText());
        admin.setName(firstNameField.getText());
        admin.setSurname(lastNameField.getText());

        try {
            String databaseUrl = "jdbc:sqlite:src/main/resources/com/example/demo/library.db";
            Connection connection = DriverManager.getConnection(databaseUrl);
            adminDAO = new AdminDAOImpl(connection);
        } catch (SQLException err) {
            err.printStackTrace();
        }

        adminDAO.addAdmin(admin);

        firstNameField.setText("");
        lastNameField.setText("");
        loginField.setText("");
        passwordField.setText("");
        passwordCheckField.setText("");
    }

}
