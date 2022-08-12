package com.example.note_app.controller;

import com.example.note_app.dao.UserDaoImpl;
import com.example.note_app.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import javax.persistence.NoResultException;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    private UserDaoImpl userDao;
    private MainController mainController;

    public void initialize() {
        userDao = new UserDaoImpl();
        password.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                signIn(null);
            }
        });
    }

    public void signIn(ActionEvent actionEvent) {
        if (checkForm()) {
            try {
                User user = userDao.fetchUser(username.getText(), password.getText());
                mainController.setLoggedUser(user);
                username.getScene().getWindow().hide();
            } catch (NoResultException e) {
                showAlert("Username or Password wrong!", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Please fill in blank space", Alert.AlertType.ERROR);
        }
    }

    public void signUp(ActionEvent actionEvent) {
//        User user = new User();
//        user.setUsername(username.getText());
//        user.setPassword(password.getText());
//        userDao.addData(user);
//        showAlert("Login Successfully", Alert.AlertType.INFORMATION);
//        username.getScene().getWindow().hide();
    }

    private boolean checkForm() {
        return !username.getText().isBlank() && !password.getText().isBlank();
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(msg);
        alert.show();
    }

    public void setMainController(MainController controller) {
        mainController = controller;
    }
}
