package com.example.demo.control;

import com.example.demo.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Controller {

    Parent root;
    public void LoadScene(String title, ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(title));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Welcome!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
