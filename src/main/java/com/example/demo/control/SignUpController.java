package com.example.demo.control;

import com.example.demo.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController extends Controller {

    public void Back(ActionEvent e) throws IOException{
        this.LoadScene("Login.fxml", e);
    }

    public void Save(ActionEvent e) throws IOException{
        //Save new info to DB
        this.LoadScene("Login.fxml", e);
    }

}
