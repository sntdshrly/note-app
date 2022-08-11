package com.example.note_app.dao;

import com.example.note_app.entity.Feedback;
import com.example.note_app.util.DaoService;
import com.example.note_app.util.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FeedbackDaoImpl implements DaoService<Feedback> {

    @Override
    public int addData(Feedback object) throws SQLException, ClassNotFoundException {
        return 1;
    }

    @Override
    public int deleteData(Feedback object) throws SQLException, ClassNotFoundException {
        return 1;
    }

    @Override
    public int updateData(Feedback object) throws SQLException, ClassNotFoundException {
        return 1;
    }

    @Override
    public ObservableList<Feedback> fetchAll() throws SQLException, ClassNotFoundException {
        ObservableList<Feedback> feedbacks = FXCollections.observableArrayList();

        return feedbacks;
    }
}
