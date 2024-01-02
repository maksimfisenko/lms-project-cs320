package com.example.demo.control;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
    private ComboBox<String> genreBox;
    @FXML
    private ComboBox<String> coverBox;
    @FXML
    private ComboBox<String> conditionBox;

    public void Back (ActionEvent e) throws IOException {
        this.LoadScene("AdminBookManage.fxml", e);
    }

    public void Save (ActionEvent e) throws IOException {

    }

    @FXML
    public void initialize(){
        genreBox.setItems(genreList);
        genreBox.setValue("Classic");
        conditionBox.setItems(conditionList);
        conditionBox.setValue("Very Good");
        coverBox.setItems(coverList);
        coverBox.setValue("Hardcover");
    }

}
