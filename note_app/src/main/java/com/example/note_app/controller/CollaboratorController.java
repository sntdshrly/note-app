package com.example.note_app.controller;

import com.example.note_app.dao.CategoryDaoImpl;
import com.example.note_app.dao.ContentDaoImpl;
import com.example.note_app.dao.UserDaoImpl;
import com.example.note_app.entity.Content;
import com.example.note_app.entity.User;
import com.example.note_app.entity.relationship.Collaborator;
import com.example.note_app.entity.relationship.UserCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import javax.persistence.NoResultException;
import java.net.URL;
import java.util.ResourceBundle;

public class CollaboratorController implements Initializable {

    public TextField addUserField;
    public ListView<User> listUser;

    private MainController mainController;

    private ObservableList<User> users;

    private UserDaoImpl userDao;
    private ContentDaoImpl contentDao;
    private CategoryDaoImpl categoryDao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        users = FXCollections.observableArrayList();
        listUser.setItems(users);

        userDao = new UserDaoImpl();
        categoryDao = new CategoryDaoImpl();
        contentDao = new ContentDaoImpl();

        ContextMenu deleteMenu = new ContextMenu();
        MenuItem delete = new MenuItem("Delete User");
        deleteMenu.getItems().add(delete);
        delete.setOnAction(actionEvent -> {
                Collaborator deleteCollaborator = contentDao.fetchCollaborator(mainController.getSelectedContent(), listUser.getSelectionModel().getSelectedItem());
                userDao.deleteCollaborator(deleteCollaborator);
                setUsers(mainController.getSelectedContent());
            }
        );
        listUser.setContextMenu(deleteMenu);
    }

    public void addUser(ActionEvent actionEvent) {
        User user = null;
        try {
            user = userDao.fetchUser(addUserField.getText());
            Collaborator newCollaborator = new Collaborator(mainController.getSelectedContent().getContentId(), user.getUserId());
            userDao.addCollaborator(newCollaborator);
            User finalUser = user;
            mainController.getSelectedContent().getCategories().forEach(category -> {
                categoryDao.addData(new UserCategory(finalUser.getUserId(), category.getCategoryId()));
            });
        } catch (NoResultException e) {
            System.out.println("No user");
        }
        setUsers(mainController.getSelectedContent());
//        contentDao.updateData() mainController.getSelectedContent().getUsers().add()
    }

    public void confirm(ActionEvent actionEvent) {
        addUserField.getScene().getWindow().hide();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setUsers(Content content) {
        users = userDao.fetchAll().filtered(user -> user.getContents().contains(content));
        listUser.setItems(users);
    }
}
