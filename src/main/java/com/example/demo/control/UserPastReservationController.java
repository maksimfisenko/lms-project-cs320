package com.example.demo.control;

import com.example.demo.model.UserPastReservationsModel;
import com.example.demo.model.UserReservationsModel;
import com.example.demo.model.daoimpl.CurrentReservationDAOImpl;
import com.example.demo.model.daoimpl.PastReservationDAOImpl;
import com.example.demo.model.daoimpl.ReaderDAOImpl;
import com.example.demo.model.entities.CurrentReservation;
import com.example.demo.model.entities.PastReservation;
import com.example.demo.model.entities.Reader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class UserPastReservationController extends Controller {
    @FXML
    private TableView<UserPastReservationsModel> tableView;
    @FXML
    private TableColumn<UserPastReservationsModel, Integer> id;
    @FXML
    private TableColumn<UserPastReservationsModel, LocalDate> datePicked;
    @FXML
    private TableColumn<UserPastReservationsModel, LocalDate> dateReturned;
    @FXML
    private TableColumn<UserPastReservationsModel, String> bookTitle;
    @FXML
    private TableColumn<UserPastReservationsModel, Boolean> strikeIssued;
    @FXML
    private TableColumn<UserPastReservationsModel, String> strikeDescription;

    private PastReservationDAOImpl pastReservationDAO;
    private ReaderDAOImpl readerDAO;

    ObservableList<UserPastReservationsModel> list = FXCollections.observableArrayList();

    public void Back(ActionEvent e) throws IOException{
        this.LoadScene("UserWelcome.fxml", e, this.getLoginInfo());
    }

    public void test() {
        try {
            String databaseUrl = "jdbc:sqlite:src/main/resources/com/example/demo/library.db";
            Connection connection = DriverManager.getConnection(databaseUrl);
            pastReservationDAO = new PastReservationDAOImpl(connection);
            readerDAO = new ReaderDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshTable();
    }

    public void refreshTable() {
        Reader reader = readerDAO.getReaderByLogin(this.getLoginInfo());
        int readerId = reader.getId();
        List<PastReservation> reservations = pastReservationDAO.getPastReservationsByReaderId(readerId);
        list.clear();
        for (PastReservation reservation : reservations) {
            list.add(new UserPastReservationsModel(
                    reservation.getId(),
                    reservation.getDateOfPicking(),
                    reservation.getDateOfReturn(),
                    reservation.getBookReserved().getTitle(),
                    reservation.isStrikeIsIssued(),
                    reservation.getStrikeDescription()));
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        datePicked.setCellValueFactory(new PropertyValueFactory<>("datePicked"));
        dateReturned.setCellValueFactory(new PropertyValueFactory<>("dateReturned"));
        bookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        strikeIssued.setCellValueFactory(new PropertyValueFactory<>("strikeIssued"));
        strikeDescription.setCellValueFactory(new PropertyValueFactory<>("strikeDescription"));

        tableView.setItems(list);
    }
}
