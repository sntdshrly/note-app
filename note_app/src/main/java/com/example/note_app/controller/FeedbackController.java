package com.example.note_app.controller;

import com.example.note_app.Main;
import com.example.note_app.dao.FeedbackDaoImpl;
import com.example.note_app.entity.Feedback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ResourceBundle;

public class FeedbackController implements Initializable {


    public AnchorPane parent;
    public VBox vboxComment;
    public TextField txtComment;

    /**
     * Feedback Variables
     */
    private ObservableList<Feedback> feedbacks;
    private FeedbackDaoImpl feedbackDao;
    private MainController mainController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        feedbackDao = new FeedbackDaoImpl();
        feedbacks = FXCollections.observableArrayList();
    }

    public void load() throws IOException {
        final AnchorPane[] nodes = new AnchorPane[feedbacks.size()];
        vboxComment.getChildren().clear();

        if (feedbacks.size() == 0) {
            Label label = new Label("There is no comments");
            vboxComment.getChildren().add(label);
        } else {
            for (int i = 0; i < feedbacks.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("FeedbackView.fxml"));

                nodes[i] = loader.load();
                FeedbackItemController itemController = loader.getController();
                itemController.setItem(feedbacks.get(i).getUser().getUsername(), feedbacks.get(i).getFeedbackField(), feedbacks.get(i).getStatus());
                int temp = i;
                itemController.setButtonAction(actionEvent -> {
                    Feedback feedback = feedbacks.get(temp);
                    feedback.setStatus(!feedback.getStatus());
                    try {
                        feedbackDao.updateData(feedback);
                        load();
                    } catch (SQLException | ClassNotFoundException | IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                vboxComment.getChildren().add(nodes[i]);
            }
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setFeedbacks() {
        try {
            this.feedbacks = feedbackDao.fetchAll().filtered(f -> f.getContent().getContentId() == mainController.getSelectedContent().getContentId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addComment() throws IOException, SQLException, ClassNotFoundException {
        Feedback feedback = new Feedback();
        feedback.setFeedbackField(txtComment.getText());
        feedback.setUser(mainController.getLoggedUser());
        feedback.setContent(mainController.getSelectedContent());
        feedback.setStatus(false);

        feedbackDao.addData(feedback);

        setFeedbacks();
        load();
        txtComment.setText("");
        txtComment.requestFocus();
    }
}
