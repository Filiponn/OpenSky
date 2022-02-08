package com.example.openskyproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PlaneStage {
    private ObservableList<PlaneFlights> flights;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField planeCodeTF;

    @FXML
    private TableColumn<PlaneFlights, String> arrivalAirportCol;

    @FXML
    private TableColumn<PlaneFlights, String> arrivalDateCol;

    @FXML
    private TableColumn<PlaneFlights, String> departureAirportCol;

    @FXML
    private TableColumn<PlaneFlights, String> departureDateCol;

    @FXML
    private Button menuBtn;

    @FXML
    private TableColumn<PlaneFlights, String> optionalAirportsCol;

    @FXML
    private Button planeBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField planeDateFromTF;

    @FXML
    private TextField planeDateToTF;

    @FXML
    private TableView<PlaneFlights> planeTable;

    @FXML
    void showPlanesFlightsOnAction(ActionEvent event) {
        String icao24 = planeCodeTF.getText().toUpperCase();
        String dateFrom = planeDateFromTF.getText();
        String dateTo = planeDateToTF.getText();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime date1 = LocalDateTime.parse(dateFrom, formatter);
        LocalDateTime date2 = LocalDateTime.parse(dateTo, formatter);
        ZoneId zone = ZoneId.of("Europe/Warsaw");
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(date1, zone);
        ZonedDateTime zonedDateTime2 = ZonedDateTime.of(date2, zone);

        long unixTime1 = zonedDateTime1.toEpochSecond();
        long unixTime2 = zonedDateTime2.toEpochSecond();

        StringBuffer response = Connector.getResponse("https://maciek2000:projektZPO@opensky-network.org/api/flights/aircraft?icao24=" + icao24 + "&begin=" + unixTime1 + "&end=" + unixTime2);
        //StringBuffer response = Connector.getResponse("https://opensky-network.org/api/flights/aircraft?icao24=4b180a&begin=1609455600&end=1610233200");

        ArrayList<PlaneFlights> flightList = Connector.getPlanesFlights(response);
        flights = FXCollections.observableArrayList();
        flights.addAll(flightList);

        departureAirportCol.setCellValueFactory(new PropertyValueFactory<PlaneFlights, String>("departureAirport"));
        arrivalAirportCol.setCellValueFactory(new PropertyValueFactory<PlaneFlights, String>("arrivalAirport"));
        departureDateCol.setCellValueFactory(new PropertyValueFactory<PlaneFlights, String>("departureDate"));
        arrivalDateCol.setCellValueFactory(new PropertyValueFactory<PlaneFlights, String>("arrivalDate"));
        optionalAirportsCol.setCellValueFactory(new PropertyValueFactory<PlaneFlights, String>("optionalAirports"));

        planeTable.getItems().clear();
        planeTable.setItems(flights);
        saveBtn.setDisable(false);


    }

    @FXML
    void initialize() {
        assert planeCodeTF != null : "fx:id=\"airportCodeTF\" was not injected: check your FXML file 'planeStage.fxml'.";
        assert arrivalAirportCol != null : "fx:id=\"arrivalAirportCol\" was not injected: check your FXML file 'planeStage.fxml'.";
        assert arrivalDateCol != null : "fx:id=\"arrivalDateCol\" was not injected: check your FXML file 'planeStage.fxml'.";
        assert departureAirportCol != null : "fx:id=\"departureAirportCol\" was not injected: check your FXML file 'planeStage.fxml'.";
        assert departureDateCol != null : "fx:id=\"departureDateCol\" was not injected: check your FXML file 'planeStage.fxml'.";
        assert menuBtn != null : "fx:id=\"menuBtn\" was not injected: check your FXML file 'planeStage.fxml'.";
        assert optionalAirportsCol != null : "fx:id=\"optionalAirportsCol\" was not injected: check your FXML file 'planeStage.fxml'.";
        assert planeBtn != null : "fx:id=\"planeBtn\" was not injected: check your FXML file 'planeStage.fxml'.";
        assert planeDateFromTF != null : "fx:id=\"planeDateFromTF\" was not injected: check your FXML file 'planeStage.fxml'.";
        assert planeDateToTF != null : "fx:id=\"planeDateToTF\" was not injected: check your FXML file 'planeStage.fxml'.";
        assert planeTable != null : "fx:id=\"planeTable\" was not injected: check your FXML file 'planeStage.fxml'.";

    }

    @FXML
    void menuBtnOnAction(ActionEvent event) {
        Stage stage = (Stage) menuBtn.getScene().getWindow();
        stage.close();
        createStartApplicationStage();
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

    public void saveBtnOnAction(ActionEvent event) {
        Stage secondaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save data to file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        if (flights.isEmpty()) {
            Alert emptyTable = new Alert(Alert.AlertType.ERROR, "EMPTY DEPARTURES TABLE", ButtonType.OK);
            if (emptyTable.getResult() == ButtonType.OK) {
                emptyTable.close();
            }
        } else {
            File file = fileChooser.showSaveDialog(secondaryStage);
            if (file != null) {
                saveToFile(planeTable.getItems(), file);
            }
        }
    }

    public void saveToFile(ObservableList<PlaneFlights> observableList, File file) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(file));

            for (PlaneFlights flights : observableList) {
                bufferedWriter.write(flights.getArrivalAirport() + "\t" + flights.getArrivalDate()
                        + "\t" + flights.getDepartureAirport() + "\t" + flights.getDepartureDate()+
                        "\t" + flights.getOptionalAirports());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            Alert ioAlert = new Alert(Alert.AlertType.ERROR, "Error!", ButtonType.OK);
            ioAlert.setContentText("Sorry, an error has occurred.");
            ioAlert.showAndWait();
            if (ioAlert.getResult() == ButtonType.OK) {
                ioAlert.close();
            }
        }
    }
}
