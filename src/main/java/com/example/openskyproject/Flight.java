package com.example.openskyproject;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    public static void main(String[] args) {
        LocalDateTime date1 = LocalDateTime.of(2020,10,10,10,30);
        LocalDateTime date2 = LocalDateTime.of(2020,11,10,10,30);
        long roznica = ChronoUnit.DAYS.between(date1, date2);
        System.out.println(roznica);

    }
}
