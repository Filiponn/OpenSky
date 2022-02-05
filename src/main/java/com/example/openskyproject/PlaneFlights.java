package com.example.openskyproject;

public class PlaneFlights {
    private String departureAirport;
    private String arrivalAirport;
    private String departureDate;
    private String arrivalDate;
    private String optionalAirports;


    public PlaneFlights(String departureAirport, String arrivalAirport, String departureDate, String arrivalDate, String optionalAirports) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.optionalAirports = optionalAirports;

    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getOptionalAirports() {
        return optionalAirports;
    }

    @Override
    public String toString() {
        return "PlaneFlights{" +
                "departureAirport='" + departureAirport + '\'' +
                ", arrivalAirport='" + arrivalAirport + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", optionalAirports=" + optionalAirports +
                '}';
    }
}
