package com.example.note_app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {
    public Label labelStatus;
    public Button btnCreate;
    public Button btnSave;
    public Button btnDelete;
    public Button btnSettings;
    public Button btnClose;
    public ListView list1;
    public TextField txtTitle;
    public Label labelKeterangan;
    public TextArea txtArea;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}