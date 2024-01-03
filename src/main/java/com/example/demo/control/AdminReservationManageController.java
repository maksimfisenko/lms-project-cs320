package com.example.demo.control;

import com.example.demo.model.ReservationsModel;
import com.example.demo.model.daoimpl.CurrentReservationDAOImpl;
import com.example.demo.model.entities.CurrentReservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AdminReservationManageController extends Controller implements Initializable {

    @FXML
    private TableView<ReservationsModel> tableView;
    @FXML
    private TableColumn<ReservationsModel, Integer> idColumn;
    @FXML
    private TableColumn<ReservationsModel, String> userLoginColumn;
    @FXML
    private TableColumn<ReservationsModel, LocalDate> dateColumn;
    @FXML
    private TableColumn<ReservationsModel, String> bookTitleColumn;
    @FXML
    private TableColumn<ReservationsModel, String> conditionColumn;
    @FXML
    private TableColumn<ReservationsModel, LocalDate> deadlineColumn;

    private CurrentReservationDAOImpl reservationDAO;

    ObservableList<ReservationsModel> reservationsModelObservableList = FXCollections.observableArrayList();

    public void Back (ActionEvent e) throws IOException {
        this.LoadScene("AdminWelcome.fxml", e, this.getLoginInfo());
    }

    public void refreshTable() {
        List<CurrentReservation> reservations = reservationDAO.getReservationsWithDate();
        reservationsModelObservableList.clear();
        for (CurrentReservation reservation : reservations) {
            reservationsModelObservableList.add(new ReservationsModel(
                    reservation.getId(),
                    reservation.getReader().getLogin(),
                    reservation.getDateOfPicking(),
                    reservation.getBookReserved().getTitle(),
                    reservation.getBookReserved().getCondition()));
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userLoginColumn.setCellValueFactory(new PropertyValueFactory<>("userLogin"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("datePicked"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("bookCondition"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        tableView.setItems(reservationsModelObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resource){

        try {
            String databaseUrl = "jdbc:sqlite:src/main/resources/com/example/demo/library.db";
            Connection connection = DriverManager.getConnection(databaseUrl);
            reservationDAO = new CurrentReservationDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshTable();
    }
}
