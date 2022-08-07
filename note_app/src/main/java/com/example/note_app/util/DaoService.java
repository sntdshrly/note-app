package com.example.note_app.util;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface DaoService<T> {
    int addData (T object) throws SQLException,ClassNotFoundException;
    int deleteData (T object) throws SQLException,ClassNotFoundException;
    int updateData (T object) throws SQLException,ClassNotFoundException;
    ObservableList<T> fetchAll() throws SQLException,ClassNotFoundException;
}
