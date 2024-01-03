package com.example.demo.control;

import com.example.demo.model.BookSearchModel;
import com.example.demo.model.UserReservationsModel;
import com.example.demo.model.daoimpl.BookDAOImpl;
import com.example.demo.model.daoimpl.CurrentReservationDAOImpl;
import com.example.demo.model.daoimpl.ReaderDAOImpl;
import com.example.demo.model.entities.Book;
import com.example.demo.model.entities.CurrentReservation;
import com.example.demo.model.entities.Reader;
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

public class UserCurrentReservationController extends Controller {

    @FXML
    private TableView<UserReservationsModel> tableView;
    @FXML
    private TableColumn<UserReservationsModel, Integer> id;
    @FXML
    private TableColumn<UserReservationsModel, String> bookTitle;
    @FXML
    private TableColumn<UserReservationsModel, String> bookCondition;
    @FXML
    private TableColumn<UserReservationsModel, LocalDate> datePicked;
    @FXML
    private TableColumn<UserReservationsModel, LocalDate> deadline;

    private CurrentReservationDAOImpl currentReservationDAO;
    private ReaderDAOImpl readerDAO;
    ObservableList<UserReservationsModel> list = FXCollections.observableArrayList();


    public void Back(ActionEvent e) throws IOException{
        this.LoadScene("UserWelcome.fxml", e, this.getLoginInfo());
    }

    public void test() {
        try {
            String databaseUrl = "jdbc:sqlite:src/main/resources/com/example/demo/library.db";
            Connection connection = DriverManager.getConnection(databaseUrl);
            currentReservationDAO = new CurrentReservationDAOImpl(connection);
            readerDAO = new ReaderDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshTable();
    }

    public void refreshTable() {
        Reader reader = readerDAO.getReaderByLogin(this.getLoginInfo());
        int readerId = reader.getId();
        List<CurrentReservation> reservations = currentReservationDAO.getCurrentReservationsByReaderIdNotNull(readerId);
        list.clear();
        for (CurrentReservation reservation : reservations) {
            list.add(new UserReservationsModel(
                    reservation.getId(),
                    reservation.getBookReserved().getTitle(),
                    reservation.getBookReserved().getCondition(),
                    reservation.getDateOfPicking(),
                    reservation.getDateOfPicking().plusDays(14)));
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        bookCondition.setCellValueFactory(new PropertyValueFactory<>("bookCondition"));
        datePicked.setCellValueFactory(new PropertyValueFactory<>("datePicked"));
        deadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        tableView.setItems(list);
    }

}
