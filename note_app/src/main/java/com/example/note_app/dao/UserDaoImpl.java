package com.example.note_app.dao;

import com.example.note_app.entity.Category;
import com.example.note_app.entity.User;
import com.example.note_app.util.DaoService;
import com.example.note_app.util.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements DaoService<User> {


    @Override
    public int addData(User object) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO user VALUES(?, ?, ?, ?)";
        int result = 0;
        PreparedStatement preparedStatement;

        try {
            Connection connection = MySQLConnection.createConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, object.getUser_id());
            preparedStatement.setString(2, object.getUsername());
            preparedStatement.setString(3, object.getEmail());
            preparedStatement.setString(4, object.getPassword());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int deleteData(User object) throws SQLException, ClassNotFoundException {
        String query = "delete from user where user_id = ?";
        PreparedStatement preparedStatement;
        int result = 0;

        try {
            Connection connection = MySQLConnection.createConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, object.getUser_id());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updateData(User object) throws SQLException, ClassNotFoundException {
        String query = "UPDATE user SET username = ?, email = ?, password = ? WHERE user_id = ?";
        PreparedStatement preparedStatement;
        int result = 0;
        try {
            Connection connection = MySQLConnection.createConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, object.getUsername());
            preparedStatement.setString(2, object.getEmail());
            preparedStatement.setString(3, object.getPassword());
            preparedStatement.setInt(4, object.getUser_id());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<User> fetchAll() throws SQLException, ClassNotFoundException {
        ObservableList<User> users = FXCollections.observableArrayList();

        String query = "SELECT * FROM user";
        PreparedStatement preparedStatement;

        try {
            Connection connection = MySQLConnection.createConnection();
            preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                users.add(
                        new User(
                                result.getInt("user_id"),
                                result.getString("username"),
                                result.getString("email"),
                                result.getString("password")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
