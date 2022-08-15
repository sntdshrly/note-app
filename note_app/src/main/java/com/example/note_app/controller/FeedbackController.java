package com.example.note_app.controller;

import com.example.note_app.dao.FeedbackDaoImpl;
import com.example.note_app.entity.Feedback;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class FeedbackController implements Initializable {

    @FXML
    private VBox vBoxItem;
    @FXML
    private Button btnInfo;
    @FXML
    private ImageView iconSelect;
    @FXML
    private Label fieldFeedback;
    @FXML
    private Label fieldTitle;

    /**
     * Feedback Variables
     */
    private ObservableList<Feedback> feedbacks;
    private FeedbackDaoImpl feedbackDao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setTask(Feedback feedback) {
        fieldFeedback.setText(feedback.getFeedbackField());
        if (feedback.getStatus()) {
            btnInfo.setText("Complete");
            iconSelect.setImage(new Image(getClass().getResourceAsStream("img/icon-checklist.png")));
        } else {
            btnInfo.setText("In Progress");
        }

    }
}
