package com.example.demo.model.daoimpl;

import com.example.demo.model.dao.CurrentReservationDAO;
import com.example.demo.model.entities.CurrentReservation ;




import java.sql.*;
import java.util.ArrayList;
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
            preparedStatement.setInt(2, currentReservation.getReader().getId());
            preparedStatement.setDate(3,java.sql.Date.valueOf( currentReservation.getDateOfPicking()));
            preparedStatement.setInt(4, currentReservation.getNumOfDaysForReservation());

            preparedStatement.executeUpdate();

            Statement statement = connection.createStatement();
            ResultSet generatedKeys = statement.executeQuery("SELECT last_insert_rowid()");
            if (generatedKeys.next()) {
                int generatedKey = generatedKeys.getInt(1);
                currentReservation.setId(generatedKey);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CurrentReservation getCurrentReservationById(int id) {

        String query = "SELECT * FROM current_reservations WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToCurrentReservation(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CurrentReservation> getAllCurrentReservations() {

        List<CurrentReservation> currentReservations = new ArrayList<>();

        String query = "SELECT * FROM current_reservations";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                currentReservations.add(mapResultSetToCurrentReservation(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentReservations;
    }

    @Override
    public void updateCurrentReservation(CurrentReservation currentReservation) {


        String query = "UPDATE current_reservations SET book_id = ?, reader_id = ?, date_of_picking = ?, num_of_days_reserved = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {


            preparedStatement.setInt(1, currentReservation.getBookReserved().getId());
            preparedStatement.setInt(2, currentReservation.getReader().getId());
            preparedStatement.setDate(3, java.sql.Date.valueOf( currentReservation.getDateOfPicking()));
            preparedStatement.setInt(4, currentReservation.getNumOfDaysForReservation());
            preparedStatement.setInt(5, currentReservation.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteCurrentReservation(int id) {

        String query = "DELETE FROM current_reservations WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private CurrentReservation mapResultSetToCurrentReservation(ResultSet resultSet) throws SQLException {
        BookDAOImpl book = new BookDAOImpl(connection);
        ReaderDAOImpl reader= new ReaderDAOImpl(connection);

        return new CurrentReservation(
                resultSet.getInt("id"),
                book.getBookById(resultSet.getInt("book_id")),
                reader.getReaderById(resultSet.getInt("reader_id")),
                resultSet.getDate("date_of_picking").toLocalDate(),
                resultSet.getInt("num_of_days_reserved")

        );
    }

}
