package com.example.demo.model.daoimpl;

import com.example.demo.model.dao.CurrentReservationDAO;
import com.example.demo.model.entities.CurrentReservation ;




import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CurrentReservationDAODAOImpl implements CurrentReservationDAO {
    private final Connection connection ;


    public CurrentReservationDAODAOImpl(Connection connection) {
       this.connection = connection;
    }

    @Override
    public void addCurrentReservation(CurrentReservation currentReservation) {
        String query = "INSERT INTO current_reservations (book_id, reader_id, date_of_picking, num_of_days_reserved) VALUES (?, ?, ?, ?)";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, currentReservation.getBookReserved().getId());
//            preparedStatement.setInt(2, currentReservation.getSurname());
//            preparedStatement.setInt(3, author.getYearOfBirth());
//            preparedStatement.setInt(4, author.getYearOfDeath());

            preparedStatement.executeUpdate();

            Statement statement = connection.createStatement();
            ResultSet generatedKeys = statement.executeQuery("SELECT last_insert_rowid()");
            if (generatedKeys.next()) {
                int generatedKey = generatedKeys.getInt(1);
//                author.setId(generatedKey);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CurrentReservation getCurrentReservationById(int id) {
        return null;
    }

    @Override
    public List<CurrentReservation> getAllCurrentReservations() {
        return null;
    }

    @Override
    public void updateCurrentReservation(CurrentReservation currentReservation) {

    }

    @Override
    public void deleteCurrentReservation(int id) {

    }

}
