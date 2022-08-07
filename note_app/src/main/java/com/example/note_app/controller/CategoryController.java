package com.example.note_app.controller;

import com.example.note_app.dao.CategoryDaoImpl;
import com.example.note_app.entity.Category;
import com.example.note_app.util.CategoryListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class CategoryController {
    public ListView<Category> listCategory;
    public TextField category;

    private ObservableList<Category> categories;
    private CategoryDaoImpl categoryDao;

    public void initialize() {
        categoryDao = new CategoryDaoImpl();

        categories = FXCollections.observableArrayList(categoryDao.fetchAll());

        listCategory.setItems(categories);
        listCategory.setEditable(true);
        listCategory.setCellFactory(stringListView -> new CategoryListCell());

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
    }

    public void editCancel(ListView.EditEvent<Category> editEvent) {
        System.out.println("EDIT CANCEL");
    }

    public void editCommit(ListView.EditEvent<Category> editEvent) {
        System.out.println("EDIT COMMIT");
        categoryDao.updateData(editEvent.getNewValue());
        categories = categoryDao.fetchAll();
        listCategory.setItems(categories);
    }

    public void editStart(ListView.EditEvent<Category> editEvent) {
        System.out.println("EDIT START");
    }

    public void saveCategory(ActionEvent actionEvent) {
        categoryDao.addData(new Category(
                category.getText(),
                ""
        ));
        categories = categoryDao.fetchAll();
        listCategory.setItems(categories);
    }
}
