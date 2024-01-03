package com.example.demo.control;

import javafx.event.ActionEvent;

import java.io.IOException;

public class AdminWelcomeController extends Controller {

    public void Back (ActionEvent e) throws IOException{
        this.LoadScene("Login.fxml", e);
    }

    public void BookManage (ActionEvent e) throws IOException{
        this.LoadScene("AdminBookManage.fxml", e, this.getLoginInfo());
    }

    public void ReaderManage (ActionEvent e) throws IOException{
        this.LoadScene("AdminReaderManage.fxml", e, this.getLoginInfo());
    }

    public void ReservationManage (ActionEvent e) throws IOException{
        this.LoadScene("AdminReservationManage.fxml", e, this.getLoginInfo());
    }

    public void SignUp (ActionEvent e) throws IOException{
        this.LoadScene("AdminSignUp.fxml", e, this.getLoginInfo());
    }

    public void NewReservationManage (ActionEvent e) throws IOException{
        this.LoadScene("AdminNewReservationManage.fxml", e, this.getLoginInfo());
    }

}
