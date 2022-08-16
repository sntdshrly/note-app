package com.example.note_app.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class FeedbackItemController implements Initializable {
    public Label fieldTitle;
    public Label fieldFeedback;
    public ImageView iconSelect;
    public Button btnInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setItem(String fieldTitle, String fieldFeedback) {
        this.fieldTitle.setText(fieldTitle);
        this.fieldFeedback.setText(fieldFeedback);
    }

}
