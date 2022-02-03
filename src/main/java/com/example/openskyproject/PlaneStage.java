package com.example.openskyproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaneStage {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField airportCodeTF;

    @FXML
    private Button menuBtn;

    @FXML
    void menuBtnOnAction(ActionEvent event) {
        Stage stage = (Stage) menuBtn.getScene().getWindow();
        stage.close();
        createStartApplicationStage();
    }

    @FXML
    void initialize() {
        assert airportCodeTF != null : "fx:id=\"airportCodeTF\" was not injected: check your FXML file 'airportStage.fxml'.";
        assert menuBtn != null : "fx:id=\"menuBtn\" was not injected: check your FXML file 'airportStage.fxml'.";

    }
    void createStartApplicationStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("startApplication.fxml"));
            Stage registerStage = new Stage();
            registerStage.setScene(new Scene(root, 600, 400));
            registerStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
