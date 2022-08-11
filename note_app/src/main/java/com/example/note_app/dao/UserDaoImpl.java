package com.example.note_app.dao;

import com.example.note_app.entity.User;
import com.example.note_app.entity.User;
import com.example.note_app.util.DaoService;
import com.example.note_app.util.HibernateUtility;
import com.example.note_app.util.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements DaoService<User> {
    @Override
    public int addData(User object) throws SQLException, ClassNotFoundException {
        int result;
        Session session = HibernateUtility.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(object);
            transaction.commit();
            result = 1;
        } catch (Exception e) {
            transaction.rollback();
            result = -1;
        }

        session.close();
        return result;
    }

    @Override
    public int deleteData(User object) throws SQLException, ClassNotFoundException {
        int result;
        Session session = HibernateUtility.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(object);
            transaction.commit();
            result = 1;
        } catch (Exception e) {
            transaction.rollback();
            result = -1;
        }

        session.close();
        return result;
    }

    @Override
    public int updateData(User object) throws SQLException, ClassNotFoundException {
        int result;
        Session session = HibernateUtility.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.update(object);
            transaction.commit();
            result = 1;
        } catch (Exception e) {
            transaction.rollback();
            result = -1;
        }

        session.close();
        return result;
    }

    @Override
    public ObservableList<User> fetchAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtility.getSession();
        ObservableList<User> users = FXCollections.observableArrayList();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        query.from(User.class);

        users.addAll(session.createQuery(query).getResultList());

        session.close();
        return users;
    }
}
