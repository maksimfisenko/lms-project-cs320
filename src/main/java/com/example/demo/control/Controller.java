package com.example.demo.control;

import com.example.demo.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    Parent root;
    private String loginInfo;
        public void LoadScene(String title, ActionEvent e) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(title));
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            stage.setTitle("Welcome!");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }

    public void LoadScene(String title, ActionEvent e, String login) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(title));
        root = fxmlLoader.load();
        Controller controller = (Controller) fxmlLoader.getController();
        controller.setLoginInfo(login);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 640, 480);
        stage.setTitle("Welcome!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
//        System.out.println(login);
    }

    public void setLoginInfo(String login){
        this.loginInfo = login;
    }

    public String getLoginInfo(){
            return this.loginInfo;
    }
}
