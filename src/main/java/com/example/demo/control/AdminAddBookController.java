package com.example.demo.control;

<<<<<<< HEAD
import com.example.demo.model.daoimpl.BookDAOImpl;
import com.example.demo.model.entities.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
=======
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
>>>>>>> 86ba1a5a63533db6b94dbcb00e2f6225ac9ed040
import javafx.scene.control.TextField;

import java.io.IOException;

public class AdminAddBookController extends Controller {

    ObservableList<String> genreList = FXCollections.observableArrayList(
            "Classic", "Adventure", "Science", "Detective", "Study"
    );
    ObservableList<String> coverList = FXCollections.observableArrayList(
            "Hardcover", "Paperback"
    );
    ObservableList<String> conditionList = FXCollections.observableArrayList(
            "Very Good", "Good", "Acceptable", "Not Acceptable"
    );

    @FXML
    private TextField titleField;
    @FXML
<<<<<<< HEAD
    private TextField isbnField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField authorsField;
    @FXML
=======
>>>>>>> 86ba1a5a63533db6b94dbcb00e2f6225ac9ed040
    private ComboBox<String> genreBox;
    @FXML
    private ComboBox<String> coverBox;
    @FXML
    private ComboBox<String> conditionBox;
<<<<<<< HEAD
    @FXML
    private TextArea descriptionArea;

    private BookDAOImpl bookDAO;

    public static boolean isPositiveInteger(String s) {
        if (s != null && !s.isEmpty()) {
            try {
                int num = Integer.parseInt(s);
                return num > 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    private void throwError(String text) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Book Adding Error");
        error.setContentText(text);
        error.showAndWait();
    }



    public void back(ActionEvent e) throws IOException {
=======

    public void Back (ActionEvent e) throws IOException {
>>>>>>> 86ba1a5a63533db6b94dbcb00e2f6225ac9ed040
        this.LoadScene("AdminWelcome.fxml", e);
    }

    public void save(ActionEvent e) throws IOException {
        if (titleField.getText().isEmpty() || titleField.getText().isBlank()) {
            throwError("The name of the book cannot be empty.");
            return;
        }
        if (isbnField.getText().isEmpty() || isbnField.getText().isBlank()) {
            throwError("ISBN of the book cannot be empty.");
            return;
        }
        if (!isPositiveInteger(numberField.getText())) {
            throwError("Number of pages must be a positive integer");
            return;
        }
        // TODO: deal with authors! add authors when saving books!

        Book book = new Book();
        book.setTitle(titleField.getText());
        book.setIsbn(isbnField.getText());
        book.setNumOfPages(Integer.parseInt(numberField.getText()));
        book.setGenre(genreBox.getValue());
        book.setDescription(descriptionArea.getText());
        book.setCoverType(coverBox.getValue());
        book.setPublisher(publisherField.getText());
        book.setCondition(conditionBox.getValue());

        bookDAO.addBook(book);
    }

    @FXML
    public void initialize(){
        genreBox.setItems(genreList);
        conditionBox.setItems(conditionList);
        coverBox.setItems(coverList);
    }

}
