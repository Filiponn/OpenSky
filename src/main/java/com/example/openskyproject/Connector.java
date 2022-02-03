package com.example.openskyproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.google.gson.*;

public class Connector {
    public static StringBuffer getResponse(String url) {

        StringBuffer response = new StringBuffer();

        try {
            URL obj = new URL(url);
            HttpURLConnection connection =
                    (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(connection.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }

    public static int getPlanesNumber(StringBuffer response){
        int planesAmount = 0;
        JsonElement jsonTree = JsonParser.parseString(String.valueOf(response));
        if(jsonTree.isJsonObject()){
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonElement states = jsonObject.get("states");
            JsonArray array = states.getAsJsonArray();
            planesAmount = array.size();
        }
        return planesAmount;
    }



    public static ArrayList<Flight> getAirportFlights(StringBuffer response, Direction direction) {
        ArrayList<Flight> flights = new ArrayList<>();
        JsonElement jsonTree = JsonParser.parseString(String.valueOf(response));
        JsonArray array = jsonTree.getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject line = array.get(i).getAsJsonObject();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Map map = gson.fromJson(line.toString(), Map.class);

            String icao24 = map.get("icao24").toString();

            String airport;
            if (direction.equals(Direction.ARRIVALS)) {
                try {
                    airport = map.get("estDepartureAirport").toString();
                } catch (NullPointerException e) {
                    airport = "Null";
                }
            } else {
                try {
                    airport = map.get("estArrivalAirport").toString();
                } catch (NullPointerException e) {
                    airport = "Null";
                }

            }


            String dateString = map.get("lastSeen").toString();

            dateString = dateString.replace(".", "");
            dateString = dateString.replace("E9", "");

            long dateLong = Long.parseLong(dateString);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String date = sdf.format(new Date(dateLong * 1000));

            flights.add(new Flight(icao24, airport, date));

        }


        return flights;
    }

    public static void main(String[] args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String data = "2022-01-02 13:00";
        LocalDateTime dateTime = LocalDateTime.parse(data, formatter);
        ZoneId zone = ZoneId.of("Europe/Warsaw");
        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, zone);


        long unixTime = zonedDateTime.toEpochSecond();
        System.out.println(unixTime);


    }
}
