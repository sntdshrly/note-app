package com.example.note_app.controller;

import com.example.note_app.Main;
import com.example.note_app.dao.UserDaoImpl;
import com.example.note_app.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.NoResultException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoginController {
    @FXML
    private TextField pass_text;
    @FXML
    private CheckBox pass_toggle;
    @FXML
    private Label lblForgotPass;
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnSignUp;
    @FXML
    private JFXToggleButton btnMode;
    @FXML
    private AnchorPane parent;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblPassword;

    private UserDaoImpl userDao;
    private MainController mainController;
    private Gson gson;
    private final Path pathDefaultUser = Paths.get("data/user.json");

    public void initialize() {
        /**
         * Setting cursor
         */
        btnMode.setCursor(Cursor.HAND);
        btnSignIn.setCursor(Cursor.HAND);
        btnSignUp.setCursor(Cursor.HAND);
        lblForgotPass.setCursor(Cursor.HAND);
        pass_toggle.setCursor(Cursor.HAND);

        gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        userDao = new UserDaoImpl();
        password.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                signIn(null);
            }
        });
        this.onActionToggleVisible(null);
    }

    public void signIn(ActionEvent actionEvent) {
        if (checkForm()) {
            try {
                /*
                 * Hashing password
                 * */
                String enc_password = DigestUtils.sha1Hex((password.getText().trim()));
                User user = userDao.fetchUser(username.getText(), enc_password);
                Files.write(pathDefaultUser, gson.toJson(user).getBytes());
                mainController.setLoggedUser(user);
                username.getScene().getWindow().hide();
            } catch (NoResultException e) {
                new animatefx.animation.Shake(username).play();
                showAlert("Username or Password is incorrect!", Alert.AlertType.ERROR);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            if (username.getText().trim()=="" && password.getText().trim()=="") {
                new animatefx.animation.Shake(username).play();
                new animatefx.animation.Shake(password).play();
                username.setStyle("-fx-text-box-border: red;");
                password.setStyle("-fx-text-box-border: red;");
                lblUsername.setText("Please fill username field!");
                lblPassword.setText("Please fill password field!");
                showAlert("Please fill username and password field!", Alert.AlertType.ERROR);
            }
            else if(username.getText().trim()==""){
                new animatefx.animation.Shake(username).play();
                username.setStyle("-fx-text-box-border: red;");
                lblUsername.setText("Please fill username field!");
                showAlert("Please fill username field!", Alert.AlertType.ERROR);
            }
            else if(password.getText().trim()==""){
                new animatefx.animation.Shake(password).play();
                password.setStyle("-fx-text-box-border: red;");
                lblPassword.setText("Please fill password field!");
                showAlert("Please fill password field!", Alert.AlertType.ERROR);
            }
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

    private boolean isLightMode = true;

    public void onActionMode(ActionEvent actionEvent) {
        isLightMode = !isLightMode;
        if (isLightMode) {
            setLightMode();
        } else {
            setDarkMode();
        }
    }

    private void setLightMode() {
        parent.getStylesheets().remove(0);
        parent.getStylesheets().add(Main.class.getResource("style/light.css").toExternalForm());
        btnMode.setText("OFF");
        btnMode.setTextFill(Color.BLACK);
//        Image image = new Image(String.valueOf(Main.class.getResource("img/dark.png")));
//        imgMode.setImage(image);
    }

    private void setDarkMode() {
        parent.getStylesheets().remove(0);
        parent.getStylesheets().add(Main.class.getResource("style/dark.css").toExternalForm());
        btnMode.setText("ON");
        btnMode.setTextFill(Color.WHITE);
    }

    public boolean checkLoginInfo() {
        if (new File(pathDefaultUser.toUri()).exists()) {
            // Load file
            User savedUser = null;
            try {
                savedUser = gson.fromJson(Files.readString(pathDefaultUser), User.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Login
            if (savedUser != null) {
                User user = userDao.fetchUser(savedUser.getUsername(), savedUser.getPassword());
                mainController.setLoggedUser(user);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @FXML
    public void onActionToggleVisible(ActionEvent event) {
        if (pass_toggle.isSelected()) {
            pass_text.setText(password.getText());
            pass_text.setVisible(true);
            password.setVisible(false);
            return;
        }
        password.setText(pass_text.getText());
        password.setVisible(true);
        pass_text.setVisible(false);
    }

    @FXML
    public void onClickedUsername(MouseEvent mouseEvent) {
        username.setStyle("-fx-text-box-border: #d9d9d9;");
        lblUsername.setText("");
    }
    @FXML
    public void onClickedPassword(MouseEvent mouseEvent) {
        password.setStyle("-fx-text-box-border: #d9d9d9;");
        lblPassword.setText("");
    }
}
