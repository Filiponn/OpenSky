package com.example.openskyproject;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StartApplicationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button airportBtn;

    @FXML
    private Button planeBtn;

    @FXML
    private ImageView image;



    @FXML
    void airportBtnOnAction(ActionEvent event) {
        Stage stage = (Stage) airportBtn.getScene().getWindow();
        stage.close();
        createAirportStage();
    }

    @FXML
    void planeBtnOnAction(ActionEvent event) {
        Stage stage = (Stage) airportBtn.getScene().getWindow();
        stage.close();
        createPlaneStage();

    }


    @FXML
    void initialize() {
        assert airportBtn != null : "fx:id=\"airportBtn\" was not injected: check your FXML file 'startApplication.fxml'.";
        assert planeBtn != null : "fx:id=\"planeBtn\" was not injected: check your FXML file 'startApplication.fxml'.";

    }

    public void createAirportStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("airportStage.fxml"));
            Stage registerStage = new Stage();
            registerStage.setScene(new Scene(root, 800, 700));
            registerStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createPlaneStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("planeStage.fxml"));
            Stage registerStage = new Stage();
            registerStage.setScene(new Scene(root, 800, 700));
            registerStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}


