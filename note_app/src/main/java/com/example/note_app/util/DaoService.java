package com.example.note_app.util;

import java.sql.SQLException;
import java.util.List;

public interface DaoService<T> {
    int addData (T object) throws SQLException,ClassNotFoundException;
    int deleteData (T object) throws SQLException,ClassNotFoundException;
    int updateData (T object) throws SQLException,ClassNotFoundException;
    List<T> fetchAll() throws SQLException,ClassNotFoundException;
}
