package com.example.demo.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AdminAddBookController extends Controller {

    @FXML
    private TextField titleField;
    public void Back (ActionEvent e) throws IOException {
        this.LoadScene("AdminWelcome.fxml", e);
    }

    public void Save (ActionEvent e) throws IOException {

    }

}
