package com.example.note_app.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;

import java.net.URL;
import java.util.ResourceBundle;
import java.awt.*;

public class AboutMeController implements Initializable {
    @FXML
    private Hyperlink hyperlinkFacebook;
    @FXML
    private Hyperlink hyperlinkInstagram;
    @FXML
    private Hyperlink hyperlinkWhatsapp;
    @FXML
    private Hyperlink hyperlinkTwitter;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hyperlinkFacebook.setOnAction(e->{
            openWebPage("http:www.facebook.com");
        });
        hyperlinkInstagram.setOnAction(e->{
            openWebPage("http:www.instagram.com");
        });
        hyperlinkWhatsapp.setOnAction(e->{
            openWebPage("http:www.whatsapp.com");
        });
        hyperlinkTwitter.setOnAction(e->{
            openWebPage("http:www.twitter.com");
        });
    }
    public static void openWebPage(String urlPath){
        try {
            Desktop.getDesktop().browse(new URL(urlPath).toURI());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
