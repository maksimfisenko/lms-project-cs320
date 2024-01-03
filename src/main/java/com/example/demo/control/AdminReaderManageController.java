package com.example.demo.control;

import com.example.demo.model.UserSearchModel;
import com.example.demo.model.daoimpl.ReaderDAOImpl;
import com.example.demo.model.entities.Reader;
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
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
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

    public void banReader(ActionEvent e) {
        UserSearchModel userSearchModel = userSearchTable.getSelectionModel().getSelectedItem();
        if (userSearchModel != null) {
            String login = userSearchModel.getLogin();
            Reader reader = readerDAO.getReaderByLogin(login);
            reader.setNumOfStrikes(3);
            readerDAO.updateReader(reader);
            refreshTable();
        }
    }

    public void unban(ActionEvent e) {
        UserSearchModel userSearchModel = userSearchTable.getSelectionModel().getSelectedItem();
        if (userSearchModel != null) {
            String login = userSearchModel.getLogin();
            Reader reader = readerDAO.getReaderByLogin(login);
            reader.setNumOfStrikes(0);
            readerDAO.updateReader(reader);
            refreshTable();
        }
    }
    public void giveStrike(ActionEvent e) {
        UserSearchModel userSearchModel = userSearchTable.getSelectionModel().getSelectedItem();
        if (userSearchModel != null) {
            String login = userSearchModel.getLogin();
            Reader reader = readerDAO.getReaderByLogin(login);
            reader.setNumOfStrikes(Math.min(reader.getNumOfStrikes() + 1, 3));
            readerDAO.updateReader(reader);
            refreshTable();
        }
    }

    public void refreshTable() {
        List<Reader> readers = readerDAO.getAllReaders();
        userSearchModelObservableList.clear();
        for (Reader reader : readers) {

            userSearchModelObservableList.add(new UserSearchModel(
                    reader.getName(),
                    reader.getSurname(),
                    reader.getLogin(),
                    reader.getPassword(),
                    reader.getNumOfStrikes()
            ));

            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
            passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
            strikesColumn.setCellValueFactory(new PropertyValueFactory<>("numOfStrikes"));
            banColumn.setCellValueFactory(new PropertyValueFactory<>("banStatus"));

            userSearchTable.setItems(userSearchModelObservableList);

            FilteredList<UserSearchModel> filteredList = new FilteredList<>(userSearchModelObservableList, b -> true);
            searchField.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(userSearchModel -> {

                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if (userSearchModel.getFirstName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (userSearchModel.getLastName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else return userSearchModel.getLogin().toLowerCase().contains(searchKeyword);
            }));

            SortedList<UserSearchModel> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(userSearchTable.comparatorProperty());

            userSearchTable.setItems(sortedList);
        }
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

        refreshTable();
    }
}
