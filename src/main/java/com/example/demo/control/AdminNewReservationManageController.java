package com.example.demo.control;

import com.example.demo.model.NewReservationModel;
import com.example.demo.model.daoimpl.BookDAOImpl;
import com.example.demo.model.daoimpl.CurrentReservationDAOImpl;
import com.example.demo.model.entities.Book;
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

public class AdminNewReservationManageController extends Controller implements Initializable {

    @FXML
    private TableView<NewReservationModel> tableView;
    @FXML
    private TableColumn<NewReservationModel, Integer> idColumn;
    @FXML
    private TableColumn<NewReservationModel, String> userLoginColumn;
    @FXML
    private TableColumn<NewReservationModel, Integer> strikesColumn;
    @FXML
    private TableColumn<NewReservationModel, String> bookTitleColumn;
    @FXML
    private TableColumn<NewReservationModel, String> conditionColumn;

    private CurrentReservationDAOImpl reservationDAO;
    private BookDAOImpl bookDAO;

    ObservableList<NewReservationModel> newReservationModelObservableList = FXCollections.observableArrayList();

    public void back(ActionEvent e) throws IOException {
        this.LoadScene("AdminWelcome.fxml", e, this.getLoginInfo());
    }

    public void approve(ActionEvent e) {

        NewReservationModel newReservationModel = tableView.getSelectionModel().getSelectedItem();
        if (newReservationModel == null) {
            return;
        }

        int reservationId = newReservationModel.getId();
        CurrentReservation reservation = reservationDAO.getCurrentReservationById(reservationId);

        reservation.setDateOfPicking(LocalDate.now());
        reservationDAO.updateCurrentReservation(reservation);

        refreshTable();
    }

    public void deny(ActionEvent e) {

        NewReservationModel newReservationModel = tableView.getSelectionModel().getSelectedItem();
        if (newReservationModel == null) {
            return;
        }

        int reservationId = newReservationModel.getId();
        CurrentReservation reservation = reservationDAO.getCurrentReservationById(reservationId);
        Book book = reservation.getBookReserved();

        book.setReserved(false);
        bookDAO.updateBook(book);

        reservationDAO.deleteCurrentReservation(reservationId);

        refreshTable();
    }

    public void refreshTable() {
        List<CurrentReservation> newReservations = reservationDAO.getReservationsWithNullDate();
        newReservationModelObservableList.clear();
        for (CurrentReservation reservation : newReservations) {
            newReservationModelObservableList.add(new NewReservationModel(
                    reservation.getId(),
                    reservation.getReader().getLogin(),
                    reservation.getReader().getNumOfStrikes(),
                    reservation.getBookReserved().getTitle(),
                    reservation.getBookReserved().getCondition()));
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userLoginColumn.setCellValueFactory(new PropertyValueFactory<>("userLogin"));
        strikesColumn.setCellValueFactory(new PropertyValueFactory<>("numOfStrikes"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("bookCondition"));

        tableView.setItems(newReservationModelObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resource){

        try {
            String databaseUrl = "jdbc:sqlite:src/main/resources/com/example/demo/library.db";
            Connection connection = DriverManager.getConnection(databaseUrl);
            reservationDAO = new CurrentReservationDAOImpl(connection);
            bookDAO = new BookDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshTable();
    }
}
