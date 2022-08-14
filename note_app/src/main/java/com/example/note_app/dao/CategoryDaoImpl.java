package com.example.note_app.dao;

import com.example.note_app.entity.Category;
import com.example.note_app.entity.User;
import com.example.note_app.entity.relationship.UserCategory;
import com.example.note_app.util.DaoService;
import com.example.note_app.util.HibernateUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CategoryDaoImpl implements DaoService<Category> {

    @Override
    public int addData(Category object) {
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
    public int deleteData(Category object) {
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

    public int deleteData(UserCategory userCategory) {
        int result;
        Session session = HibernateUtility.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(userCategory);
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
    public int updateData(Category object) {
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
    public ObservableList<Category> fetchAll() {
        Session session = HibernateUtility.getSession();
        ObservableList<Category> categories = FXCollections.observableArrayList();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Category> query = builder.createQuery(Category.class);
        query.from(Category.class);

        categories.addAll(session.createQuery(query).getResultList());

        session.close();
        return categories;
    }

    public UserCategory fetchUserCategory(Category category, User user) {
        Session session = HibernateUtility.getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserCategory> query = builder.createQuery(UserCategory.class);
        Root<UserCategory> root = query.from(UserCategory.class);

        Predicate predicate1 = builder.equal(root.get("user_id"), user.getUserId());
        Predicate predicate2 = builder.equal(root.get("category_id"), category.getCategoryId());
        Predicate predicate = builder.and(predicate1, predicate2);
        query.where(predicate);

        UserCategory loginUser = session.createQuery(query).getSingleResult();

        session.close();
        return loginUser;
    }
}
