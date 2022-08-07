package com.example.note_app.dao;

import com.example.note_app.entity.Category;
import com.example.note_app.util.DaoService;
import com.example.note_app.util.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements DaoService<Category> {

    @Override
    public int addData(Category object) {
        String query = "INSERT INTO category VALUES(DEFAULT, ?, ?)";
        int result = 0;
        PreparedStatement preparedStatement;

        try {
            Connection connection = MySQLConnection.createConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, object.getCategory_id());
            preparedStatement.setString(2, object.getCategory_name());
            result = preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int deleteData(Category object) {
        String query = "delete from category where id = ?";
        PreparedStatement preparedStatement;
        int result = 0;

        try {
            Connection connection = MySQLConnection.createConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, object.getCategory_id());
            result = preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updateData(Category object) {
        String query = "UPDATE category " +
                "SET category_name = ? , category_description = ? " +
                "WHERE id = ?";
        PreparedStatement preparedStatement;
        int result = 0;

        try {
            Connection connection = MySQLConnection.createConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, object.getCategory_name());
            preparedStatement.setString(2, object.getCategory_description());
            preparedStatement.setInt(3, object.getCategory_id());
            result = preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ObservableList<Category> fetchAll() {
        ObservableList<Category> categories = FXCollections.observableArrayList();

        String query = "SELECT * FROM category";
        PreparedStatement preparedStatement;

        try {
            Connection connection = MySQLConnection.createConnection();
            preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                categories.add(
                        new Category(
                                result.getInt("category_id"),
                                result.getString("category_name"),
                                result.getString("category_description")
                        )
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return categories;
    }
}
