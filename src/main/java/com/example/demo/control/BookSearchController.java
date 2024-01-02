package com.example.demo.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class BookSearchController extends Controller {

    @FXML
    TextField searchField;
    public void Back (ActionEvent e) throws IOException {
        this.LoadScene("UserWelcome.fxml", e);
    }

    public void StartSearchBar(String search){
        searchField.setText(search);
    }
}
