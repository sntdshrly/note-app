package com.example.note_app.dao;

import com.example.note_app.entity.Category;
import com.example.note_app.entity.Feedback;
import com.example.note_app.util.DaoService;
import com.example.note_app.util.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FeedbackDaoImpl implements DaoService<Feedback> {

    @Override
    public int addData(Feedback object) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO feedback VALUES(?, ?, ?, ?)";
        int result = 0;
        PreparedStatement preparedStatement;

        try {
            Connection connection = MySQLConnection.createConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, object.getFeedback_id());
            preparedStatement.setString(2, object.getFeedback_field());
            preparedStatement.setInt(3, object.getUser_id());
            preparedStatement.setInt(4, object.getContent_id());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int deleteData(Feedback object) throws SQLException, ClassNotFoundException {
        String query = "delete from feedback where feedback_id = ?";
        PreparedStatement preparedStatement;
        int result = 0;

        try {
            Connection connection = MySQLConnection.createConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, object.getFeedback_id());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updateData(Feedback object) throws SQLException, ClassNotFoundException {
        String query = "UPDATE feedback SET feedback_field = ?, user_id = ?, content_id = ? WHERE feedback_id = ?";
        PreparedStatement preparedStatement;
        int result = 0;
        try {
            Connection connection = MySQLConnection.createConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, object.getFeedback_field());
            preparedStatement.setInt(2, object.getUser_id());
            preparedStatement.setInt(3, object.getContent_id());
            preparedStatement.setInt(4, object.getFeedback_id());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Feedback> fetchAll() throws SQLException, ClassNotFoundException {
        ObservableList<Feedback> feedbacks = FXCollections.observableArrayList();

        String query = "SELECT feedback.feedback_id, feedback.feedback_field, feedback.content_id, feedback.user_id \n" +
                "FROM feedback INNER JOIN content ON feedback.content_id = content.content_id \n" +
                "INNER JOIN user ON feedback.user_id = user.user_id;";
        PreparedStatement preparedStatement;

        try {
            Connection connection = MySQLConnection.createConnection();
            preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                feedbacks.add(
                        new Feedback(
                                result.getInt("feedback_id"),
                                result.getString("feedback_field"),
                                result.getInt("user_id"),
                                result.getInt("content_id")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedbacks;
    }
}
