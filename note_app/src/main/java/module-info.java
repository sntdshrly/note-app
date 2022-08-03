module com.example.note_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;


    opens com.example.note_app to javafx.fxml;
    exports com.example.note_app;
    exports com.example.note_app.controller;
    opens com.example.note_app.controller to javafx.fxml;
    exports com.example.note_app.dao;
    opens com.example.note_app.dao to javafx.fxml;
    exports com.example.note_app.entity;
    opens com.example.note_app.entity to javafx.fxml;
    exports com.example.note_app.util;
    opens com.example.note_app.util to javafx.fxml;
}