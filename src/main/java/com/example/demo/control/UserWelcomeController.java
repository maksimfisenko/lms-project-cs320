package com.example.demo.control;

import com.example.demo.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UserWelcomeController extends Controller {

    @FXML
    TextField searchField;
    public void Back(ActionEvent e) throws IOException{
        this.LoadScene("Login.fxml", e);
    }
    public void PastReservation(ActionEvent e) throws IOException{
        this.LoadScene("UserPastReservations.fxml", e, this.getLoginInfo());
    }

    public void CurrentReservation(ActionEvent e) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("UserCurrentReservations.fxml"));
        Parent root1 = fxmlLoader.load();
        UserCurrentReservationController controller = fxmlLoader.getController();
        controller.setLoginInfo(this.getLoginInfo());
        controller.test();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root1, 640, 480);
        stage.setTitle("Welcome!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void onEnter(ActionEvent e) throws IOException {
        if (!searchField.getText().isEmpty() && !searchField.getText().isBlank()) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("BookSearch.fxml"));
            Parent root = fxmlLoader.load();
            BookSearchController bookSearchController = fxmlLoader.getController();
            bookSearchController.StartSearchBar(searchField.getText());
            bookSearchController.setLoginInfo(this.getLoginInfo());

            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 640, 480);

            stage.setTitle("Welcome!");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Search Error");
            error.setContentText("Empty search is not allowed!");
            error.showAndWait();
        }
    }
}
