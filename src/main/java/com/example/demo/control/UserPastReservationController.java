package com.example.demo.control;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.lang.reflect.AccessFlag;

public class UserPastReservationController extends Controller {

    public void Back (ActionEvent e) throws IOException{
        this.LoadScene("UserWelcome.fxml", e);
    }

}
