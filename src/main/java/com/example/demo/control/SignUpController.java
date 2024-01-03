package com.example.demo.control;

import com.example.demo.HelloApplication;
import com.example.demo.model.daoimpl.AdminDAOImpl;
import com.example.demo.model.daoimpl.ReaderDAOImpl;
import com.example.demo.model.entities.Admin;
import com.example.demo.model.entities.Reader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SignUpController extends Controller {

    @FXML
    TextField firstNameField;
    @FXML
    TextField lastNameField;
    @FXML
    TextField loginField;
    @FXML
    PasswordField passwordField;
    @FXML
    PasswordField passwordCheckField;
    private ReaderDAOImpl readerDAO;
    private  AdminDAOImpl adminDAO;

    public void Back(ActionEvent e) throws IOException{
        this.LoadScene("Login.fxml", e);
    }

    private void throwError(String text) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Admin Creating Error");
        error.setContentText(text);
        error.showAndWait();
    }

    public void Save(ActionEvent e) throws IOException{

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



        // TODO: maybe add that login should be at least 8 symbols, for example
        if (!passwordField.getText().equals(passwordCheckField.getText())) {
            throwError("Passwords are not the same.");
            return;
        }
        if (passwordField.getText().isEmpty() || passwordField.getText().isBlank()) {
            throwError("Password cannot be empty.");
            return;
        }

        Reader reader = new Reader();
        reader.setLogin(loginField.getText());
        reader.setPassword(passwordField.getText());
        reader.setName(firstNameField.getText());
        reader.setSurname(lastNameField.getText());

        try {
            String databaseUrl = "jdbc:sqlite:src/main/resources/com/example/demo/library.db";
            Connection connection = DriverManager.getConnection(databaseUrl);
            readerDAO = new ReaderDAOImpl(connection);
            adminDAO = new AdminDAOImpl(connection);
        } catch (SQLException err) {
            err.printStackTrace();
        }

        if (adminDAO.loginCheck(loginField.getText()) || readerDAO.loginCheck(loginField.getText())){
            throwError("Login already exists, try another one.");
            return;
        }

        readerDAO.addReader(reader);

        this.LoadScene("Login.fxml", e);
    }

}
