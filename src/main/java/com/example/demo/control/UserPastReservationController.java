package com.example.demo.control;

import javafx.event.ActionEvent;

import java.io.IOException;

public class UserPastReservationController extends Controller {

    public void Back (ActionEvent e) throws IOException{
        this.LoadScene("UserWelcome.fxml", e, this.getLoginInfo());
    }

}
