package com.example.openskyproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.*;

public class Connector {
    public static StringBuffer getResponse(String url){

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

    public static void main(String[] args) {
        StringBuffer response = getResponse("https://opensky-network.org/api/states/all?lamin=45.8389&lomin=5.9962&lamax=47.8229&lomax=10.5226");

        JsonElement jsonTree = JsonParser.parseString(String.valueOf(response));

        if(jsonTree.isJsonObject()){
            System.out.println("test");
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonElement time = jsonObject.get("time");
            JsonElement states = jsonObject.get("states");
            System.out.println(time.getAsInt());
            JsonArray array = states.getAsJsonArray();
            for(int i = 0; i<array.size(); i++){
                System.out.println(array.get(i));
            }
        }
    }
}
