package com.example.note_app.controller;

import com.example.note_app.Main;
import com.example.note_app.dao.CategoryDaoImpl;
import com.example.note_app.dao.ContentDaoImpl;
import com.example.note_app.entity.Category;
import com.example.note_app.entity.Content;
import com.example.note_app.entity.User;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

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
     * User hasil login
     */
    private User loggedUser;

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
        showLogin();

        // Content
        contentDao = new ContentDaoImpl();
        contents = FXCollections.observableArrayList();
        contents.addAll(loggedUser.getContents());
        listContent.setItems(contents);
        initContent();

        // Category
        categoryDao = new CategoryDaoImpl();
        categories = FXCollections.observableArrayList();
        refreshCategory();
        initCategory();
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
    private void initContent() {
        listCategory.getSelectionModel().selectedItemProperty().addListener((observableValue, category, t1) -> {
            Category selectedCategory = listCategory.getSelectionModel().getSelectedItem();
            contents = FXCollections.observableArrayList(loggedUser.getContents());
            contents = contents.filtered(content -> content.getCategories().contains(selectedCategory));
            listContent.setItems(contents);
        });
    }
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
    private void initCategory() {
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
                    categories.remove(listCategory.getSelectionModel().getSelectedItem());
                }
        );

        listCategory.setContextMenu(deleteMenu);
        txtCategory.setOnKeyPressed(keyEvent -> { if (keyEvent.getCode() == KeyCode.ENTER) { saveCategory(null); }});
    }

    public void editCommit(ListView.EditEvent<Category> editEvent) {
        categoryDao.updateData(editEvent.getNewValue());
        refreshCategory();
    }

    public void saveCategory(ActionEvent actionEvent) {
        Category newCategory = new Category();
        newCategory.setCategoryName(txtCategory.getText());
        newCategory.setCategoryDescription(txtCategory.getText());

        categoryDao.addData(newCategory);

        txtCategory.setText("");
        refreshCategory();
    }

    private void refreshCategory() {
        Set<Category> categorySet = new HashSet<>();
        for (Content content: contents) {
            categorySet.addAll(content.getCategories());
        }
        categories.clear();
        categories.addAll(categorySet);
        listCategory.setItems(categories);
    }

    /**
     * Login method
     */
    private void showLogin() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginView.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(scene);

        LoginController loginController = fxmlLoader.getController();
        loginController.setMainController(this);

        stage.showAndWait();
    }

    public void setLoggedUser(User user) {
        loggedUser = user;
    }
}