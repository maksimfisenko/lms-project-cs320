package com.example.demo.control;

import javafx.event.ActionEvent;

import java.io.IOException;

public class UserWelcomeController extends Controller {

    public void Back(ActionEvent e) throws IOException{
        this.LoadScene("Login.fxml", e);
    }
    public void PastReservation(ActionEvent e) throws IOException{
        this.LoadScene("UserPastReservations.fxml", e);
    }

    public void CurrentReservation(ActionEvent e) throws IOException{
        this.LoadScene("UserCurrentReservations.fxml", e);
    }
}
