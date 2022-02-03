package com.example.openskyproject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AirportStage {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField airportCodeTF;

    @FXML
    private TextField airportDateFromTF;

    @FXML
    private TextField airportDateToTF;

    @FXML
    private Button flightsBtn;

    @FXML
    private Button menuBtn;

    @FXML
    void showFlightsOnAction(ActionEvent event) {

    }


    @FXML
    void menuBtnOnAction(ActionEvent event) {
        Stage stage = (Stage) menuBtn.getScene().getWindow();
        stage.close();
        createStartApplicationStage();
    }

    @FXML
    void initialize() {
        assert airportCodeTF != null : "fx:id=\"airportCodeTF\" was not injected: check your FXML file 'airportStage.fxml'.";
        assert airportDateFromTF != null : "fx:id=\"airportDateFromTF\" was not injected: check your FXML file 'airportStage.fxml'.";
        assert airportDateToTF != null : "fx:id=\"airportDateToTF\" was not injected: check your FXML file 'airportStage.fxml'.";
        assert flightsBtn != null : "fx:id=\"flightsBtn\" was not injected: check your FXML file 'airportStage.fxml'.";
        assert menuBtn != null : "fx:id=\"menuBtn1\" was not injected: check your FXML file 'airportStage.fxml'.";

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
