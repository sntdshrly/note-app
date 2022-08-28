package com.example.note_app.dao;

import com.example.note_app.entity.User;
import com.example.note_app.entity.User;
import com.example.note_app.entity.relationship.Collaborator;
import com.example.note_app.util.DaoService;
import com.example.note_app.util.HibernateUtility;
import com.example.note_app.util.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements DaoService<User> {
    @Override
    public int addData(User object) {
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
    public int deleteData(User object) {
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
    public int updateData(User object) {
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
    public ObservableList<User> fetchAll() {
        Session session = HibernateUtility.getSession();
        ObservableList<User> users = FXCollections.observableArrayList();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        query.from(User.class);

        users.addAll(session.createQuery(query).getResultList());

        session.close();
        return users;
    }

    public User fetchUser(String username, String password) {
        Session session = HibernateUtility.getSession();
        ObservableList<User> users = FXCollections.observableArrayList();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        Predicate usernameCheck = builder.equal(root.get("username"), username);
        Predicate passwordCheck = builder.equal(root.get("password"), password);
        Predicate predicate = builder.and(usernameCheck, passwordCheck);
        query.where(predicate);

        User loginUser = session.createQuery(query).getSingleResult();

        session.close();
        return loginUser;
    }

    public void addCollaborator(Collaborator collaborator) {
        Session session = HibernateUtility.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(collaborator);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }

        session.close();
    }

    public User fetchUser(String user) {
        Session session = HibernateUtility.getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        Predicate usernameCheck = builder.equal(root.get("username"), user);
        Predicate emailCheck = builder.equal(root.get("email"), user);
        Predicate predicate = builder.or(usernameCheck, emailCheck);
        query.where(predicate);

        User fetchedUser = session.createQuery(query).getSingleResult();

        session.close();
        return fetchedUser;
    }

    public void deleteCollaborator(Collaborator collaborator) {
        Session session = HibernateUtility.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            System.out.println(collaborator);
            session.delete(collaborator);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }

        session.close();
    }
}
