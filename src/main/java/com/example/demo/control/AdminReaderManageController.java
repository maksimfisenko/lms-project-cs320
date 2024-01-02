package com.example.demo.control;

import javafx.event.ActionEvent;

import java.io.IOException;

public class AdminReaderManageController  extends Controller {

    public void Back (ActionEvent e) throws IOException {
        this.LoadScene("AdminWelcome.fxml", e);
    }
}
