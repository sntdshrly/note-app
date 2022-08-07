package com.example.note_app.dao;

import com.example.note_app.entity.Content;
import com.example.note_app.util.DaoService;
import com.example.note_app.util.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContentDaoImpl implements DaoService<Content> {
    @Override
    public int addData(Content object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "INSERT INTO Content(content_title,content_field) VALUES(?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,object.getContent_title());
        ps.setString(2,object.getContent_field());
        if (ps.executeUpdate() != 0){
            connection.commit();
            result = 1;
        }
        else {
            connection.rollback();
        }
        ps.close();
        connection.close();
        return result;
    }

    @Override
    public int deleteData(Content object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "DELETE FROM Content WHERE content_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,object.getContent_id());
        if (ps.executeUpdate() != 0){
            connection.commit();
            result = 1;
        }
        else {
            connection.rollback();
        }
        ps.close();
        connection.close();
        return result;
    }

    @Override
    public int updateData(Content object) throws SQLException, ClassNotFoundException {
        int result = 0;
        Connection connection = MySQLConnection.createConnection();
        String query = "UPDATE Content SET content_title = ?, content_field = ?, timestamp = ?, timestamp_update = DEFAULT WHERE content_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,object.getContent_title());
        ps.setString(2,object.getContent_field());
        ps.setString(3,object.getTimestamp());
        ps.setInt(4,object.getContent_id());
        if (ps.executeUpdate() != 0){
            connection.commit();
            result = 1;
        }
        else {
            connection.rollback();
        }
        ps.close();
        connection.close();
        return result;
    }

    @Override
    public ObservableList<Content> fetchAll() throws SQLException, ClassNotFoundException {
        ObservableList<Content> contents = FXCollections.observableArrayList();
        Connection connection = MySQLConnection.createConnection();
        String query = "SELECT * FROM Content";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Content content = new Content();
            content.setContent_id(rs.getInt("content_id"));
            content.setContent_title(rs.getString("content_title"));
            content.setContent_field(rs.getString("content_field"));
            content.setTimestamp(rs.getString("timestamp"));
            contents.add(content);
        }
        rs.close();
        ps.close();
        connection.close();
        return contents;
    }
}
