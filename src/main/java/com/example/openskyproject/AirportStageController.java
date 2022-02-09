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

public class AirportStageController {
    ObservableList<Flight> arrivals;
    ObservableList<Flight> departures;


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
    private TableColumn<Flight, String> arrivalsAirportColumn;

    @FXML
    private TableColumn<Flight, String> arrivalsDateColumn;

    @FXML
    private TableColumn<Flight, String> arrivalsPlaneColumn;

    @FXML
    private TableView<Flight> arrivalsTable;

    @FXML
    private TableColumn<Flight, String> departuresAirportColumn;

    @FXML
    private TableColumn<Flight, String> departuresDateColumn;

    @FXML
    private TableColumn<Flight, String> departuresPlaneColumn;

    @FXML
    private TableView<Flight> departuresTable;

    @FXML
    private Button saveArrivalBtn;

    @FXML
    private Button saveDepartBtn;

    @FXML
    void showFlightsOnAction(ActionEvent event) {
        String icao = airportCodeTF.getText().toUpperCase();
        String dateFrom = airportDateFromTF.getText();
        String dateTo = airportDateToTF.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime date1 = LocalDateTime.parse(dateFrom, formatter);
        LocalDateTime date2 = LocalDateTime.parse(dateTo, formatter);
        ZoneId zone = ZoneId.of("Europe/Warsaw");
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(date1, zone);
        ZonedDateTime zonedDateTime2 = ZonedDateTime.of(date2, zone);

        long unixTime1 = zonedDateTime1.toEpochSecond();
        long unixTime2 = zonedDateTime2.toEpochSecond();

        StringBuffer arrivalsResponse = Connector.getResponse("https://maciek2000:projektZPO@opensky-network.org/api/flights/arrival?airport=" + icao + "&begin=" + unixTime1 + "&end=" + unixTime2);
        StringBuffer departuresResponse = Connector.getResponse("https://maciek2000:projektZPO@opensky-network.org/api/flights/departure?airport=" + icao + "&begin=" + unixTime1 + "&end=" + unixTime2);
//        StringBuffer arrivalsResponse = Connector.getResponse("https://opensky-network.org/api/flights/arrival?airport=EDDF&begin=1643112000&end=1643373900");
//        StringBuffer departuresResponse = Connector.getResponse("https://opensky-network.org/api/flights/departure?airport=EDDF&begin=1643112000&end=1643373900");

        ArrayList<Flight> arrivalsArrayList = Connector.getAirportFlights(arrivalsResponse, Direction.ARRIVALS);
        ArrayList<Flight> departuresArrayList = Connector.getAirportFlights(departuresResponse, Direction.DEPARTURES);
        arrivals = FXCollections.observableArrayList();
        arrivals.addAll(arrivalsArrayList);

        departures = FXCollections.observableArrayList();
        departures.addAll(departuresArrayList);

        arrivalsPlaneColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("icao24"));
        arrivalsAirportColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("airport"));
        arrivalsDateColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("date"));
        departuresPlaneColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("icao24"));
        departuresAirportColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("airport"));
        departuresDateColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("date"));

        saveDepartBtn.setDisable(false);
        saveArrivalBtn.setDisable(false);

        arrivalsTable.getItems().clear();
        departuresTable.getItems().clear();
        arrivalsTable.setItems(arrivals);
        departuresTable.setItems(departures);
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
        assert saveArrivalBtn != null : "fx:id=\"menuBtn1\" was not injected: check your FXML file 'airportStage.fxml'.";
        assert saveDepartBtn != null : "fx:id=\"menuBtn1\" was not injected: check your FXML file 'airportStage.fxml'.";

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

    public void saveDepartBtnOnAction(ActionEvent event) {
        Stage secondaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save data to file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        if (departures.isEmpty()) {
            Alert emptyTable = new Alert(Alert.AlertType.ERROR, "EMPTY DEPARTURES TABLE", ButtonType.OK);
            if (emptyTable.getResult() == ButtonType.OK) {
                emptyTable.close();
            }
        } else {
            File file = fileChooser.showSaveDialog(secondaryStage);
            if (file != null) {

                saveToFile(departuresTable.getItems(), file);
            }
        }
    }

    public void saveArrivalBtnOnAction(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save data to file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        if (arrivals.isEmpty()) {
            Alert emptyTable = new Alert(Alert.AlertType.ERROR, "EMPTY ARRIVALS TABLE", ButtonType.OK);
            if (emptyTable.getResult() == ButtonType.OK) {
                emptyTable.close();
            }
        } else {
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                saveToFile(arrivalsTable.getItems(), file);
            }
        }
    }

    public void saveToFile(ObservableList<Flight> observableList, File file) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(file));

            for (Flight flights : observableList) {
                bufferedWriter.write(flights.getIcao24()+ ",\t" + flights.getAirport()
                        + ",\t" + flights.getDate());
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
