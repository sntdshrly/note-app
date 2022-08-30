package com.example.note_app.controller;

import com.example.note_app.Main;
import com.example.note_app.dao.UserDaoImpl;
import com.example.note_app.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.validator.routines.EmailValidator;

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
    private TextField newPass_text;
    @FXML
    private CheckBox pass_toggle;
    @FXML
    private CheckBox newPass_toggle;
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnSignUp;
    @FXML
    private Button btnSignIn2;
    @FXML
    private JFXToggleButton btnMode;
    @FXML
    private JFXToggleButton newBtnMode;
    @FXML
    private AnchorPane parent;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField newUsername;
    @FXML
    private TextField newEmail;
    @FXML
    private PasswordField newPassword;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblPassword;
    @FXML
    private Label newLblUsername;
    @FXML
    private Label newLblEmail;
    @FXML
    private Label newLblPassword;
    @FXML
    private BorderPane loginView;
    @FXML
    private BorderPane signUpView;

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
//        btnSignUp.setCursor(Cursor.HAND);
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
                new animatefx.animation.Shake(password).play();
                username.setStyle("-fx-text-box-border: red;");
                password.setStyle("-fx-text-box-border: red;");
                showAlert("Username or Password is incorrect!", Alert.AlertType.ERROR);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            if (username.getText().trim() == "" && password.getText().trim() == "") {
                new animatefx.animation.Shake(username).play();
                new animatefx.animation.Shake(password).play();
                username.setStyle("-fx-text-box-border: red;");
                password.setStyle("-fx-text-box-border: red;");
                lblUsername.setText("Please fill username field!");
                lblPassword.setText("Please fill password field!");
                showAlert("Please fill username and password field!", Alert.AlertType.ERROR);
            } else if (username.getText().trim() == "") {
                new animatefx.animation.Shake(username).play();
                username.setStyle("-fx-text-box-border: red;");
                lblUsername.setText("Please fill username field!");
                showAlert("Please fill username field!", Alert.AlertType.ERROR);
            } else if (password.getText().trim() == "") {
                new animatefx.animation.Shake(password).play();
                password.setStyle("-fx-text-box-border: red;");
                lblPassword.setText("Please fill password field!");
                showAlert("Please fill password field!", Alert.AlertType.ERROR);
            }
        }
    }

    public void registerNewUser(ActionEvent actionEvent) throws NoResultException {
        User user = new User();
        user.setUsername(newUsername.getText());
        /*
         * Hashing password
         * */
        String enc_password = DigestUtils.sha1Hex((newPassword.getText().trim()));
        user.setPassword(enc_password);
        user.setEmail(newEmail.getText());
//        userDao.addData(user);
//        username.getScene().getWindow().hide();

        boolean valid = EmailValidator.getInstance().isValid(newEmail.getText());
        if (valid) {
            if (!newUsername.getText().isBlank() && !newPassword.getText().isBlank() && !newEmail.getText().isBlank()) {
                try {
                    userDao.addData(user);
                    username.getScene().getWindow().hide();
                    user = userDao.fetchUser(newUsername.getText(), enc_password);
                    Files.write(pathDefaultUser, gson.toJson(user).getBytes());
                    mainController.setLoggedUser(user);
                    newUsername.getScene().getWindow().hide();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (newUsername.getText().isBlank() || newPassword.getText().isBlank() || newEmail.getText().isBlank()) {
            if (newUsername.getText().isBlank()) {
                new animatefx.animation.Shake(newUsername).play();
                newLblUsername.setText("Please fill username field!");
                newUsername.setStyle("-fx-text-box-border: red;");
            }
            if (newPassword.getText().isBlank()) {
                new animatefx.animation.Shake(newPassword).play();
                newLblPassword.setText("Please fill password field!");
                newPassword.setStyle("-fx-text-box-border: red;");
            }
            if (newEmail.getText().isBlank()) {
                new animatefx.animation.Shake(newEmail).play();
                newLblEmail.setText("Please fill email field!");
                newEmail.setStyle("-fx-text-box-border: red;");
            }
            showAlert("Please fill all the field!", Alert.AlertType.ERROR);
        }
        else if(!valid){
            new animatefx.animation.Shake(newEmail).play();
            newLblEmail.setText("Please use an valid email address!");
            newEmail.setStyle("-fx-text-box-border: red;");
            showAlert("Please use an valid email address!", Alert.AlertType.ERROR);
        }
    }

    public void signUp(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnSignUp) {
            signUpView.setVisible(true);
            loginView.setVisible(false);
        }
        if (actionEvent.getSource() == btnSignIn2) {
            signUpView.setVisible(false);
            loginView.setVisible(true);
        }
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
        btnMode.setText("DAY");
        btnMode.setTextFill(Color.BLACK);
        newBtnMode.setText("DAY");
        newBtnMode.setTextFill(Color.BLACK);
//        Image image = new Image(String.valueOf(Main.class.getResource("img/dark.png")));
//        imgMode.setImage(image);
    }

    private void setDarkMode() {
        parent.getStylesheets().remove(0);
        parent.getStylesheets().add(Main.class.getResource("style/dark.css").toExternalForm());
        btnMode.setText("NIGHT");
        btnMode.setTextFill(Color.WHITE);
        newBtnMode.setText("NIGHT");
        newBtnMode.setTextFill(Color.WHITE);
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
        /*
         * For Login Pages
         * */
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
    public void onActionToggle2Visible(ActionEvent event) {
        /*
         * For Sign Up Pages
         * */
        if (newPass_toggle.isSelected()) {
            newPass_text.setText(newPassword.getText());
            newPass_text.setVisible(true);
            newPassword.setVisible(false);
            return;
        }
        newPassword.setText(newPass_text.getText());
        newPassword.setVisible(true);
        newPass_text.setVisible(false);
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

    public void onClickedNewUsername(MouseEvent mouseEvent) {
        newUsername.setStyle("-fx-text-box-border: #d9d9d9;");
        newLblUsername.setText("");
    }

    public void onClickedNewPassword(MouseEvent mouseEvent) {
        newPassword.setStyle("-fx-text-box-border: #d9d9d9;");
        newLblPassword.setText("");
    }

    public void onClickedNewEmail(MouseEvent mouseEvent) {
        newEmail.setStyle("-fx-text-box-border: #d9d9d9;");
        newLblEmail.setText("");
    }
}
