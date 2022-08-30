module com.example.note_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires com.google.gson;
    requires com.jfoenix;
    requires org.apache.commons.codec;
    requires java.desktop;
    requires AnimateFX;
    requires javafx.web;
    requires org.json;
    requires prettytime;
    requires commons.validator;

    opens com.example.note_app to javafx.fxml, com.google.gson;
    exports com.example.note_app;
    exports com.example.note_app.controller;
    opens com.example.note_app.controller to javafx.fxml;
    exports com.example.note_app.dao;
    opens com.example.note_app.dao to javafx.fxml;
    exports com.example.note_app.entity;
    opens com.example.note_app.entity;
    opens com.example.note_app.entity.relationship;
    exports com.example.note_app.entity.relationship;
    exports com.example.note_app.util;
    opens com.example.note_app.util to javafx.fxml;
}