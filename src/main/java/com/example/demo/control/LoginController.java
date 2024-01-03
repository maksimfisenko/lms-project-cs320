package com.example.demo.control;

import com.example.demo.HelloApplication;
import com.example.demo.model.daoimpl.AdminDAOImpl;
import com.example.demo.model.daoimpl.ReaderDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.Console;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginController extends Controller {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    private AdminDAOImpl adminDAO;
    private ReaderDAOImpl readerDAO;

    private void throwError(String text) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Admin Creating Error");
        error.setContentText(text);
        error.showAndWait();
    }

    public void SignIn(ActionEvent e) throws IOException {
        if (loginField.getText().isEmpty() || loginField.getText().isBlank()) {
            throwError("Login cannot be empty.");
            return;
        }
        if (passwordField.getText().isEmpty() || passwordField.getText().isBlank()) {
            throwError("Password cannot be empty.");
            return;
        }

        try {
            String databaseUrl = "jdbc:sqlite:src/main/resources/com/example/demo/library.db";
            Connection connection = DriverManager.getConnection(databaseUrl);
            readerDAO = new ReaderDAOImpl(connection);
            adminDAO = new AdminDAOImpl(connection);
        } catch (SQLException err) {
            err.printStackTrace();
        }

        if (adminDAO.adminExists(loginField.getText(), passwordField.getText())) {
            this.LoadScene("AdminWelcome.fxml", e, loginField.getText());
            return;
        }
        if (readerDAO.readerExists(loginField.getText(), passwordField.getText())) {
            this.LoadScene("UserWelcome.fxml", e, loginField.getText());
            return;
        }

        throwError("No user with such login and password.");


        loginField.setText("");
        passwordField.setText("");
    }

    @FXML
    public void onEnter(ActionEvent e) throws IOException {
        this.SignIn(e);
    }

    public void SignUp(ActionEvent e) throws IOException {
        this.LoadScene("SignUp.fxml", e);
    }
}
