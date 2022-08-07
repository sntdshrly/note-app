package com.example.note_app.controller;

import com.example.note_app.dao.CategoryDaoImpl;
import com.example.note_app.dao.ContentDaoImpl;
import com.example.note_app.entity.Category;
import com.example.note_app.entity.Content;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private ListView list2;
    @FXML
    private Label labelStatus;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnClose;
    @FXML
    private ListView list1;
    @FXML
    private TextField txtTitle;
    @FXML
    private Label labelKeterangan;
    @FXML
    private TextArea txtArea;

    private ObservableList<Content> contents;
    private ObservableList<Category> categories;
    private Content selectedContent;

    @FXML
    protected void onActionSaveContent(ActionEvent actionEvent) {
        Content content = new Content();
        ContentDaoImpl contentDao = new ContentDaoImpl();
        content.setContent_title(txtTitle.getText().trim());
        content.setContent_field(txtArea.getText().trim());
        try {
            if (contentDao.addData(content) == 1) {
                labelStatus.setText("Note Saved!");
                contents.clear();
                contents.addAll(contentDao.fetchAll());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void onActionEditContent(ActionEvent actionEvent) {
        ContentDaoImpl contentDao = new ContentDaoImpl();
        if (txtTitle.getText().trim().isEmpty() || txtTitle.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in the blank!");
            alert.showAndWait();
        }
        else {
            selectedContent.setContent_title(txtTitle.getText().trim());
            selectedContent.setContent_field(txtArea.getText().trim());
            try {
                if (contentDao.updateData(selectedContent) == 1) {
                    labelStatus.setText("Note Edited!");
                    contents.clear();
                    contents.addAll(contentDao.fetchAll());
                    reset();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void reset() {
        txtTitle.clear();
        txtArea.clear();
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        labelKeterangan.setText("");
    }

    public void contentClicked(MouseEvent mouseEvent) {
        selectedContent = (Content) list1.getSelectionModel().getSelectedItem();
        if (selectedContent != null) {
            labelKeterangan.setText("Created in : "+selectedContent.getTimestamp()+"\t Updated in : "+selectedContent.getTimestamp_update());
            txtTitle.setText(selectedContent.getContent_title());
            txtArea.setText(selectedContent.getContent_field());
            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    @FXML
    protected void onActionDeleteContent(ActionEvent actionEvent) {
        ContentDaoImpl contentDao = new ContentDaoImpl();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure want to delete?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                if (contentDao.deleteData(selectedContent) == 1) {
                    labelStatus.setText("Note Deleted!");
                    contents.clear();
                    contents.addAll(contentDao.fetchAll());
                    reset();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContentDaoImpl contentDao = new ContentDaoImpl();
        contents = FXCollections.observableArrayList();
        CategoryDaoImpl categoryDao = new CategoryDaoImpl();
        categories = FXCollections.observableArrayList();
        try {
            contents.addAll(contentDao.fetchAll());
            categories.addAll(categoryDao.fetchAll());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        list1.setItems(contents);
        list2.setItems(categories);
    }

}