package com.example.openskyproject;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StartApplicationController {
    private double lamin = 49;
    private double lamax = 54.45;
    private double lomin = 14.11;
    private double lomax = 24.12;

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
    private Text textPlanesAmount;

    @FXML
    private Button refreshBtn;


    @FXML
    void airportBtnOnAction(ActionEvent event) {
        Stage stage = (Stage) airportBtn.getScene().getWindow();
        stage.close();
        createAirportStage();
    }

    @FXML
    void planeBtnOnAction(ActionEvent event) {
        Stage stage = (Stage) planeBtn.getScene().getWindow();
        stage.close();
        createPlaneStage();

    }

    @FXML
    void refreshBtnOnAction(ActionEvent event) {
        StringBuffer response = Connector.getResponse("https://opensky-network.org/api/states/all?lamin=" + lamin + "&lomin=" + lomin + "&lamax=" + lamax + "&lomax=" + lomax);
        int planesAmount = Connector.getPlanesNumber(response);
        textPlanesAmount.setText(String.valueOf(planesAmount));

    }


    @FXML
    void initialize() {
        assert airportBtn != null : "fx:id=\"airportBtn\" was not injected: check your FXML file 'startApplication.fxml'.";
        assert planeBtn != null : "fx:id=\"planeBtn\" was not injected: check your FXML file 'startApplication.fxml'.";

        StringBuffer response = Connector.getResponse("https://opensky-network.org/api/states/all?lamin=" + lamin + "&lomin=" + lomin + "&lamax=" + lamax + "&lomax=" + lomax);
        int planesAmount = Connector.getPlanesNumber(response);
        textPlanesAmount.setText(String.valueOf(planesAmount));
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


