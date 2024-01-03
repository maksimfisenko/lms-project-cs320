package com.example.demo.control;

import com.example.demo.model.BookSearchModel;
import com.example.demo.model.daoimpl.BookDAOImpl;
import com.example.demo.model.entities.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AdminBookManageController extends Controller implements Initializable{

    @FXML
    private TableView<BookSearchModel> bookTableView;
    @FXML
    private TableColumn<BookSearchModel, Integer> idColumn;
    @FXML
    private TableColumn<BookSearchModel, String> titleColumn;
    @FXML
    private TableColumn<BookSearchModel, String> authorColumn;
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

    ObservableList<BookSearchModel> bookSearchModelObservableList = FXCollections.observableArrayList();


    public void back(ActionEvent e) throws IOException {
        this.LoadScene("AdminWelcome.fxml", e, this.getLoginInfo());
    }

    public void newBook(ActionEvent e) throws IOException{
        this.LoadScene("AdminAddBook.fxml", e, this.getLoginInfo());
    }

    public void deleteBook(ActionEvent e) {
        BookSearchModel bookSearchModel = bookTableView.getSelectionModel().getSelectedItem();
        if (bookSearchModel != null) {

            int id = bookSearchModel.getId();
            bookDAO.deleteBook(id);

            refreshTable();
        }
    }

    public void saveBook(ActionEvent e){
        BookSearchModel bookSearchModel = bookTableView.getSelectionModel().getSelectedItem();
        if (bookSearchModel != null){

            int id = bookSearchModel.getId();
            Book book = bookDAO.getBookById(id);
            book.setTitle(bookSearchModel.getTitle());
            book.setAuthor(bookSearchModel.getAuthor());
            book.setGenre(bookSearchModel.getGenre());
            book.setCondition(bookSearchModel.getCondition());
            book.setDescription(bookSearchModel.getDescription());

            bookDAO.updateBook(book);
            refreshTable();
        }
    }

    public void cancelButton(ActionEvent e){
        refreshTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resource){

        bookTableView.setEditable(true);
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        genreColumn.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(
                        "Classic", "Adventure", "Science", "Detective", "Study")));
        conditionColumn.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(
                "Very Good", "Good", "Acceptable", "Not Acceptable")));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());


        try {
            String databaseUrl = "jdbc:sqlite:src/main/resources/com/example/demo/library.db";
            Connection connection = DriverManager.getConnection(databaseUrl);
            bookDAO = new BookDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        refreshTable();

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

        titleColumn.setOnEditCommit(event -> {
            BookSearchModel bookSearchModel = event.getRowValue();
            bookSearchModel.setTitle(event.getNewValue());
        });

        authorColumn.setOnEditCommit(event -> {
            BookSearchModel bookSearchModel = event.getRowValue();
            bookSearchModel.setAuthor(event.getNewValue());
        });

        descriptionColumn.setOnEditCommit(event -> {
            BookSearchModel bookSearchModel = event.getRowValue();
            bookSearchModel.setDescription(event.getNewValue());
        });

        genreColumn.setOnEditCommit(event -> {
            BookSearchModel bookSearchModel = event.getRowValue();
            bookSearchModel.setGenre(event.getNewValue());
        });

        conditionColumn.setOnEditCommit(event -> {
            BookSearchModel bookSearchModel = event.getRowValue();
            bookSearchModel.setCondition(event.getNewValue());
        });

        SortedList<BookSearchModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(bookTableView.comparatorProperty());

        bookTableView.setItems(sortedData);

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
    }
}
