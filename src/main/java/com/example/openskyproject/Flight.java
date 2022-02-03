package com.example.openskyproject;

public class Flight {
    private String icao24;
    private String airport;
    private String date;

    public Flight(String icao24, String airport, String date) {
        this.icao24 = icao24;
        this.airport = airport;
        this.date = date;
    }

    public String getIcao24() {
        return icao24;
    }

    public String getDepartueAirport() {
        return airport;
    }

    public String getAirport() {
        return airport;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "icao24='" + icao24 + '\'' +
                ", airport='" + airport + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
