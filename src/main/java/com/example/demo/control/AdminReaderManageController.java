package com.example.demo.control;

import com.example.demo.model.BookSearchModel;
import com.example.demo.model.UserSearchModel;
import com.example.demo.model.daoimpl.BookDAOImpl;
import com.example.demo.model.daoimpl.ReaderDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminReaderManageController  extends Controller implements Initializable {

    @FXML
    private TableView<UserSearchModel> userSearchTable;
    @FXML
    private TableColumn<UserSearchModel, String> firstNameColumn;
    @FXML
    private TableColumn<UserSearchModel, String> lastNameColumn;
    @FXML
    private TableColumn<UserSearchModel, String> loginColumn;
    @FXML
    private TableColumn<UserSearchModel, String> passwordColumn;
    @FXML
    private TableColumn<UserSearchModel, Integer> strikesColumn;
    @FXML
    private TableColumn<UserSearchModel, Boolean> banColumn;
    @FXML
    private TextField searchField;

    private ReaderDAOImpl readerDAO;
    ObservableList<UserSearchModel> userSearchModelObservableList = FXCollections.observableArrayList();



    public void Back (ActionEvent e) throws IOException {
        this.LoadScene("AdminWelcome.fxml", e, this.getLoginInfo());
    }

    @Override
    public void initialize(URL url, ResourceBundle resource) {

        try {
            String databaseUrl = "jdbc:sqlite:src/main/resources/com/example/demo/library.db";
            Connection connection = DriverManager.getConnection(databaseUrl);
            readerDAO = new ReaderDAOImpl(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
