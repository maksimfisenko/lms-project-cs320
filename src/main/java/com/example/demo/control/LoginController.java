package com.example.demo.control;

import com.example.demo.HelloApplication;
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

public class LoginController extends Controller {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

    public void SignIn(ActionEvent e) throws IOException {
        if (loginField.getText().equals("admin") && passwordField.getText().equals("admin")){
            this.LoadScene("AdminWelcome.fxml", e);
        }
        else if (loginField.getText().equals("reader") && passwordField.getText().equals("reader")){
            this.LoadScene("UserWelcome.fxml", e);
        }
        else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Login Error");
            error.setContentText("Login or Password is incorrect!!!");
            error.showAndWait();
        }
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
