package com.example.demo.model.daoimpl;
import com.example.demo.model.dao.AdminDAO ;
import com.example.demo.model.entities.Admin;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class AdminDAOImpl implements AdminDAO {

    private final Connection connection ;

    public AdminDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addAdmin(Admin admin) {

        String query = "INSERT INTO admins (login, password, name, surname ) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, admin.getLogin());
            preparedStatement.setString(2, admin.getPassword());
            preparedStatement.setString(3, admin.getName());
            preparedStatement.setString(4, admin.getSurname());

            preparedStatement.executeUpdate();

            Statement statement = connection.createStatement();
            ResultSet generatedKeys = statement.executeQuery("SELECT last_insert_rowid()");
            if (generatedKeys.next()) {
                int generatedKey = generatedKeys.getInt(1);
                admin.setId(generatedKey);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Admin getAdminById(int id) {
        String query = "SELECT * FROM admins WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToAdmin(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Admin> getAllAdmins() {

        List<Admin> admins = new ArrayList<>();

        String query = "SELECT * FROM admins";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                admins.add(mapResultSetToAdmin(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;

    }

    @Override
    public void updateAdmin(Admin admin) {

        String query = "UPDATE admins SET login = ?, password = ?, name = ?, surname = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, admin.getLogin());
            preparedStatement.setString(2, admin.getPassword());
            preparedStatement.setString(3, admin.getName());
            preparedStatement.setString(4, admin.getSurname());
            preparedStatement.setInt(5, admin.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteAdmin(int id) {

        String query = "DELETE FROM admins WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean adminExists(String login, String password) {
        String query = "SELECT * FROM admins WHERE login = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Admin mapResultSetToAdmin(ResultSet resultSet) throws SQLException {

        return new Admin(
                resultSet.getInt("id"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("surname")
        );
    }
}
