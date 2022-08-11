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
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    /**
     * FXML Component
     * **/
    @FXML
    private ListView<Category> listCategory;
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
    private ListView<Content> listContent;
    @FXML
    private TextField txtTitle;
    @FXML
    private Label labelKeterangan;
    @FXML
    private TextArea txtArea;
    @FXML
    private TextField txtCategory;


    /**
     * Content Variables
     * **/
    private ObservableList<Content> contents;
    private ContentDaoImpl contentDao;
    private Content selectedContent;


    /**
     * Category Variables
     */
    private ObservableList<Category> categories;
    private CategoryDaoImpl categoryDao;


    /**
     * Class method
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Content
        contentDao = new ContentDaoImpl();
        contents = FXCollections.observableArrayList();
        contents.addAll(contentDao.fetchAll());
        listContent.setItems(contents);

        // Category
        categoryDao = new CategoryDaoImpl();
        categories = FXCollections.observableArrayList();
        categories.addAll(categoryDao.fetchAll());
        listCategory.setItems(categories);
        listCategory.setEditable(true);
        listCategory.setCellFactory(TextFieldListCell.forListView(new StringConverter<Category>() {
            @Override
            public String toString(Category category) {
                return category.getCategoryName();
            }

            @Override
            public Category fromString(String s) {
                Category category1 = new Category();
                category1.setCategoryName(s);
                category1.setCategoryId(listCategory.getSelectionModel().getSelectedItem().getCategoryId());
                category1.setCategoryDescription(listCategory.getSelectionModel().getSelectedItem().getCategoryDescription());
                return category1;
            }
        }));

        ContextMenu deleteMenu = new ContextMenu();
        MenuItem delete = new MenuItem("Delete Category");
        deleteMenu.getItems().add(delete);
        delete.setOnAction(actionEvent -> {
                    categoryDao.deleteData(listCategory.getSelectionModel().getSelectedItem());
                    categories = categoryDao.fetchAll();
                    listCategory.setItems(categories);
                }
        );

        listCategory.setContextMenu(deleteMenu);
        txtCategory.setOnKeyPressed(keyEvent -> { if (keyEvent.getCode() == KeyCode.ENTER) { saveCategory(null); }});
    }
    private void reset() {
        txtTitle.clear();
        txtArea.clear();
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        labelKeterangan.setText("");
    }


    /**
     * Content method
     * **/
    @FXML
    protected void onActionSaveContent(ActionEvent actionEvent) {
        Content content = new Content();
        content.setContentTitle(txtTitle.getText().trim());
        content.setContentField(txtArea.getText().trim());
        if (contentDao.addData(content) == 1) {
            labelStatus.setText("Note Saved!");
            contents.clear();
            contents.addAll(contentDao.fetchAll());
        }
    }

    @FXML
    protected void onActionEditContent(ActionEvent actionEvent) {
        if (txtTitle.getText().trim().isEmpty() || txtTitle.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in the blank!");
            alert.show();
        }
        else {
            selectedContent.setContentTitle(txtTitle.getText().trim());
            selectedContent.setContentField(txtArea.getText().trim());
            if (contentDao.updateData(selectedContent) == 1) {
                labelStatus.setText("Note Edited!");
                contents.clear();
                contents.addAll(contentDao.fetchAll());
                reset();
            }
        }
    }

    public void contentClicked(MouseEvent mouseEvent) {
        selectedContent = listContent.getSelectionModel().getSelectedItem();
        if (selectedContent != null) {
            labelKeterangan.setText("Created in : "+selectedContent.getCreatedAt()+"\t Updated in : "+selectedContent.getUpdatedAt());
            txtTitle.setText(selectedContent.getContentTitle());
            txtArea.setText(selectedContent.getContentField());
            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    @FXML
    protected void onActionDeleteContent(ActionEvent actionEvent) {
//        ContentDaoImpl contentDao = new ContentDaoImpl();
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setContentText("Are you sure want to delete?");
//        alert.showAndWait();
//        if (alert.getResult() == ButtonType.OK) {
//            try {
//                if (contentDao.deleteData(selectedContent) == 1) {
//                    labelStatus.setText("Note Deleted!");
//                    contents.clear();
//                    contents.addAll(contentDao.fetchAll());
//                    reset();
//                }
//            } catch (SQLException | ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }


    /**
     * Category method
     */
    public void editCommit(ListView.EditEvent<Category> editEvent) {
        categoryDao.updateData(editEvent.getNewValue());
        categories = categoryDao.fetchAll();
        listCategory.setItems(categories);
    }

    public void saveCategory(ActionEvent actionEvent) {
        Category newCategory = new Category();
        newCategory.setCategoryName(txtCategory.getText());
        newCategory.setCategoryDescription(txtCategory.getText());

        categoryDao.addData(newCategory);

        txtCategory.setText("");
        categories = categoryDao.fetchAll();
        listCategory.setItems(categories);
    }
}