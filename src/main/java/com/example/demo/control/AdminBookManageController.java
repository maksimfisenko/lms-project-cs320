package com.example.demo.control;

import javafx.event.ActionEvent;

import java.io.IOException;

public class AdminBookManageController  extends Controller {

    public void Back (ActionEvent e) throws IOException {
        this.LoadScene("AdminWelcome.fxml", e);
    }

    public void NewBook(ActionEvent e) throws IOException{
        this.LoadScene("AdminAddBook.fxml", e);
    }
}
