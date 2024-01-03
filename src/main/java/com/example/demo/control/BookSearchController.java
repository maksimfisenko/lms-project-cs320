package com.example.demo.control;

import com.example.demo.model.BookSearchModel;
import com.example.demo.model.daoimpl.BookDAOImpl;
import com.example.demo.model.daoimpl.CurrentReservationDAOImpl;
import com.example.demo.model.daoimpl.ReaderDAOImpl;
import com.example.demo.model.entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class BookSearchController extends Controller implements Initializable{

    @FXML
    private TableView<BookSearchModel> bookTableView;
    @FXML
    private TableColumn<BookSearchModel, Integer> idColumn;
    @FXML
    private TableColumn<BookSearchModel, String> titleColumn;
    @FXML
    private TableColumn<BookSearchModel, List<Author>> authorColumn;
    @FXML
    private TableColumn<BookSearchModel, String> genreColumn;
    @FXML
    private TableColumn<BookSearchModel, String> isbnColumn;
    @FXML
    private TableColumn<BookSearchModel, String> publisherColumn;
    @FXML
    private TableColumn<BookSearchModel, String> conditionColumn;
    @FXML
    private TableColumn<BookSearchModel, String> descriptionColumn;
    @FXML
    private TextField searchField;

    private BookDAOImpl bookDAO;
    private ReaderDAOImpl readerDAO;
    private CurrentReservationDAOImpl reservationDAO;

    ObservableList<BookSearchModel> bookSearchModelObservableList = FXCollections.observableArrayList();

    public void Back (ActionEvent e) throws IOException {
        this.LoadScene("UserWelcome.fxml", e, this.getLoginInfo());
    }

    public void StartSearchBar(String search){
        searchField.setText(search);
    }

    private void throwError(String text) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Book Reserving Error");
        error.setContentText(text);
        error.showAndWait();
    }

    private void throwNotification() {
        Alert error = new Alert(Alert.AlertType.INFORMATION);
        error.setHeaderText("Book Reserving");
        error.setContentText("The request has been sent to admin.");
        error.showAndWait();
    }

    public void reserveBook(ActionEvent e) {

        BookSearchModel bookSearchModel = bookTableView.getSelectionModel().getSelectedItem();
        if (bookSearchModel == null) {
            return;
        }

        int bookId = bookSearchModel.getId();
        String readerLogin = this.getLoginInfo();

        Book book = bookDAO.getBookById(bookId);
        Reader reader = readerDAO.getReaderByLogin(readerLogin);

        if (reader.getNumOfStrikes() > 2) {
            this.throwError("You are banned as you have more than 2 strikes.");
            return;
        }

        CurrentReservation reservation = new CurrentReservation();
        reservation.setReader(reader);
        reservation.setBookReserved(book);
        reservation.setNumOfDaysForReservation(14);

        reservationDAO.addCurrentReservation(reservation);

        book.setReserved(true);
        bookDAO.updateBook(book);

        this.throwNotification();

        refreshTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resource){
        try {
            String databaseUrl = "jdbc:sqlite:src/main/resources/com/example/demo/library.db";
            Connection connection = DriverManager.getConnection(databaseUrl);
            bookDAO = new BookDAOImpl(connection);
            reservationDAO = new CurrentReservationDAOImpl(connection);
            readerDAO = new ReaderDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshTable();
    }

    public void refreshTable() {

        List<Book> notReservedBooks = bookDAO.getNotReservedBooks();
        bookSearchModelObservableList.clear();
        for (Book book : notReservedBooks) {

            bookSearchModelObservableList.add(new BookSearchModel(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getGenre(),
                    book.getIsbn(),
                    book.getPublisher(),
                    book.getCondition(),
                    book.getDescription()));
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        bookTableView.setItems(bookSearchModelObservableList);

        FilteredList<BookSearchModel> filteredData = new FilteredList<>(bookSearchModelObservableList, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(bookSearchModel -> {
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                return bookSearchModel.getTitle().toLowerCase().contains(searchKeyword);

            });
        });

        SortedList<BookSearchModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(bookTableView.comparatorProperty());

        bookTableView.setItems(sortedData);
    }
}
