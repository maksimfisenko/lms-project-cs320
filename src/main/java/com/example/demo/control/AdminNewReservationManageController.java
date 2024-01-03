package com.example.demo.control;

import com.example.demo.model.daoimpl.BookDAOImpl;
import com.example.demo.model.daoimpl.CurrentReservationDAOImpl;
import com.example.demo.model.daoimpl.ReaderDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminNewReservationManageController extends Controller implements Initializable {

    public void back(ActionEvent e) throws IOException {
        this.LoadScene("AdminWelcome.fxml", e, this.getLoginInfo());
    }

    @Override
    public void initialize(URL url, ResourceBundle resource){

        try {
            String databaseUrl = "jdbc:sqlite:src/main/resources/com/example/demo/library.db";
            Connection connection = DriverManager.getConnection(databaseUrl);
//            bookDAO = new BookDAOImpl(connection);
//            reservationDAO = new CurrentReservationDAOImpl(connection);
//            readerDAO = new ReaderDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        refreshTable();
    }
}
