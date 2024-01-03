package com.example.demo.control;

import com.example.demo.model.ReservationsModel;
import com.example.demo.model.daoimpl.BookDAOImpl;
import com.example.demo.model.daoimpl.CurrentReservationDAOImpl;
import com.example.demo.model.daoimpl.PastReservationDAOImpl;
import com.example.demo.model.daoimpl.ReaderDAOImpl;
import com.example.demo.model.entities.Book;
import com.example.demo.model.entities.CurrentReservation;
import com.example.demo.model.entities.PastReservation;
import com.example.demo.model.entities.Reader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    @FXML
    private CheckBox strikeBox;
    @FXML
    private TextField strikeField;

    private CurrentReservationDAOImpl reservationDAO;
    private PastReservationDAOImpl pastReservationDAO;
    private ReaderDAOImpl readerDAO;
    private BookDAOImpl bookDAO;

    ObservableList<ReservationsModel> reservationsModelObservableList = FXCollections.observableArrayList();

    public void back(ActionEvent e) throws IOException {
        this.LoadScene("AdminWelcome.fxml", e, this.getLoginInfo());
    }

    public void endReservation() {

        ReservationsModel reservationsModel = tableView.getSelectionModel().getSelectedItem();
        if (reservationsModel == null) {
            return;
        }

        int reservationId = reservationsModel.getId();
        CurrentReservation reservation = reservationDAO.getCurrentReservationById(reservationId);

        Book book = reservation.getBookReserved();
        book.setReserved(false);
        bookDAO.updateBook(book);

        PastReservation pastReservation = new PastReservation();
        pastReservation.setReader(reservation.getReader());
        pastReservation.setNumOfDaysForReservation(reservation.getNumOfDaysForReservation());
        pastReservation.setBookReserved(book);
        pastReservation.setDateOfPicking(reservation.getDateOfPicking());
        pastReservation.setDateOfReturn(LocalDate.now());
        pastReservation.setBookConditionOnReturn(book.getCondition());

        if (strikeBox.isSelected()) {
            pastReservation.setStrikeIsIssued(true);
            pastReservation.setStrikeDescription(strikeField.getText());
            Reader reader = readerDAO.getReaderByLogin(reservationsModel.getUserLogin());
            reader.setNumOfStrikes(Math.min(reader.getNumOfStrikes() + 1, 3));
            readerDAO.updateReader(reader);
        } else {
            pastReservation.setStrikeIsIssued(false);
            pastReservation.setStrikeDescription(null);
        }

        reservationDAO.deleteCurrentReservation(reservationId);
        pastReservationDAO.addPastReservation(pastReservation);

        strikeBox.setSelected(false);
        strikeField.setText("");

        refreshTable();
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
            pastReservationDAO = new PastReservationDAOImpl(connection);
            bookDAO = new BookDAOImpl(connection);
            readerDAO = new ReaderDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshTable();
    }
}
