package com.example.note_app.dao;

import com.example.note_app.entity.Category;
import com.example.note_app.entity.Content;
import com.example.note_app.entity.User;
import com.example.note_app.util.DaoService;
import com.example.note_app.util.HibernateUtility;
import com.example.note_app.util.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ContentDaoImpl implements DaoService<Content> {
    @Override
    public int addData(Content object) {
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
    public int deleteData(Content object) {
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
    public int updateData(Content object) {
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
    public ObservableList<Content> fetchAll() {
        ObservableList<Content> contents = FXCollections.observableArrayList();
        Session session = HibernateUtility.getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Content> query = builder.createQuery(Content.class);
        query.from(Content.class);

        contents.addAll(session.createQuery(query).getResultList());

        session.close();
        return contents;
    }

    public List<Content> filterData(Category categorySelected) {
        Session session = HibernateUtility.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Content> criteriaQuery = criteriaBuilder.createQuery(Content.class);
        Root<Content> contentRoot = criteriaQuery.from(Content.class);
        Predicate predicateSelectedCategory = criteriaBuilder.equal(contentRoot.get("categories"), categorySelected);
        criteriaQuery.where(predicateSelectedCategory);
        List<Content> contents = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return contents;
    }

    public ObservableList<Content> fetchByUser(User user) {
        ObservableList<Content> contents = FXCollections.observableArrayList();
        Session session = HibernateUtility.getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Content> query = builder.createQuery(Content.class);
        Root<Content> root = query.from(Content.class);

        Predicate predicate = builder.equal(root.get(""), user.getUserId());
        query.where(predicate);

        contents.addAll(session.createQuery(query).getResultList());

        session.close();
        return contents;
    }
}
