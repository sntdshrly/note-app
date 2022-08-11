package com.example.note_app.dao;

import com.example.note_app.entity.Feedback;
import com.example.note_app.entity.Feedback;
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

public class FeedbackDaoImpl implements DaoService<Feedback> {

    @Override
    public int addData(Feedback object) throws SQLException, ClassNotFoundException {
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
    public int deleteData(Feedback object) throws SQLException, ClassNotFoundException {
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
    public int updateData(Feedback object) throws SQLException, ClassNotFoundException {
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
    public ObservableList<Feedback> fetchAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtility.getSession();
        ObservableList<Feedback> feedbacks = FXCollections.observableArrayList();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Feedback> query = builder.createQuery(Feedback.class);
        query.from(Feedback.class);

        feedbacks.addAll(session.createQuery(query).getResultList());

        session.close();
        return feedbacks;
    }
}
