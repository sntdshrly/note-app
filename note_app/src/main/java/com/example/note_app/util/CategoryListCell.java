package com.example.note_app.util;

import com.example.note_app.entity.Category;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CategoryListCell extends ListCell<Category> {
    private final TextField textField = new TextField();

    public CategoryListCell() {
        textField.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
            if (e.getCode() == KeyCode.ENTER) {
                commitEdit(getItem());
            }
        });
        textField.setOnAction(e -> {
            getItem().setCategory_name(textField.getText());
            setText(textField.getText());
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        });
        setGraphic(textField);
    }

    @Override
    protected void updateItem(Category category, boolean b) {
        super.updateItem(category, b);
        if (isEditing()) {
            textField.setText(category.getCategory_name());
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } else {
            setContentDisplay(ContentDisplay.TEXT_ONLY);
            if (b) {
                setText(null);
            } else {
                setText(category.getCategory_name());
            }
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        textField.setText(getItem().getCategory_name());
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        textField.requestFocus();
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().getCategory_name());
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void commitEdit(Category category) {
        super.commitEdit(category);
        textField.setText(getItem().getCategory_name());
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
}
