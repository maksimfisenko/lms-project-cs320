package com.example.demo.model.daoimpl;

import com.example.demo.model.dao.PastReservationDAO;
import com.example.demo.model.entities.PastReservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PastReservationDAOImpl implements PastReservationDAO {

    private final Connection connection ;

    public PastReservationDAOImpl (Connection connection) {
        this.connection = connection;
    }


    @Override
    public void addPastReservation(PastReservation pastReservation) {

        String query = "INSERT INTO past_reservations (book_id, reader_id, date_of_picking, num_of_days_reserved, date_of_return ,strike_is_issued , strike_description ,book_condition_on_return) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, pastReservation.getBookReserved().getId());
            preparedStatement.setInt(2, pastReservation.getReader().getId());
            preparedStatement.setDate(3,java.sql.Date.valueOf( pastReservation.getDateOfPicking()));
            preparedStatement.setInt(4, pastReservation.getNumOfDaysForReservation());
            preparedStatement.setDate(5, java.sql.Date.valueOf(pastReservation.getDateOfReturn()));
            preparedStatement.setBoolean(6, pastReservation.isStrikeIsIssued());
            preparedStatement.setString(7, pastReservation.getStrikeDescription());
            preparedStatement.setString(8, pastReservation.getBookConditionOnReturn());

            preparedStatement.executeUpdate();

            Statement statement = connection.createStatement();
            ResultSet generatedKeys = statement.executeQuery("SELECT last_insert_rowid()");
            if (generatedKeys.next()) {
                int generatedKey = generatedKeys.getInt(1);
                pastReservation.setId(generatedKey);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public PastReservation getPastReservationById(int id) {
        String query = "SELECT * FROM past_reservations WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToPastReservation(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PastReservation> getAllPastReservations() {
        List<PastReservation> pastReservations = new ArrayList<>();

        String query = "SELECT * FROM past_reservations";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                pastReservations.add(mapResultSetToPastReservation(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pastReservations;
    }

    @Override
    public void updatePastReservation(PastReservation pastReservation) {

        String query = "UPDATE past_reservations SET book_id = ?, reader_id = ?, date_of_picking = ?, num_of_days_reserved = ?, date_of_return = ?, strike_is_issued = ?, strike_description = ?, book_condition_on_return = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {


            preparedStatement.setInt(1, pastReservation.getBookReserved().getId());
            preparedStatement.setInt(2, pastReservation.getReader().getId());
            preparedStatement.setDate(3,java.sql.Date.valueOf( pastReservation.getDateOfPicking()));
            preparedStatement.setInt(4, pastReservation.getNumOfDaysForReservation());
            preparedStatement.setDate(5, java.sql.Date.valueOf(pastReservation.getDateOfReturn()));
            preparedStatement.setBoolean(6, pastReservation.isStrikeIsIssued());
            preparedStatement.setString(7, pastReservation.getStrikeDescription());
            preparedStatement.setString(8, pastReservation.getBookConditionOnReturn());
            preparedStatement.setInt(9,pastReservation.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deletePastReservation(int id) {

        String query = "DELETE FROM past_reservations WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private PastReservation mapResultSetToPastReservation(ResultSet resultSet) throws SQLException {

        BookDAOImpl book = new BookDAOImpl(connection);
        ReaderDAOImpl reader= new ReaderDAOImpl(connection);

        return new PastReservation(
                resultSet.getInt("id"),
                book.getBookById(resultSet.getInt("book_id")),
                reader.getReaderById(resultSet.getInt("reader_id")),
                resultSet.getDate("date_of_picking").toLocalDate(),
                resultSet.getInt("num_of_days_reserved"),
                resultSet.getDate("date_of_return").toLocalDate(),
                resultSet.getBoolean("strike_is_issued"),
                resultSet.getString("strike_description"),
                resultSet.getString("book_condition_on_return")

        );
    }
}
