package com.fitness.app.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Slf4j
@Service
public class GoogleMapService {


    //API Key for accessing google location.
    private static final Object API_KEY = "AIzaSyCgHNmyruLEfUzPbSoKUJrx1I-rL_NqJ2U";

    //function to get full address with city.
    public String getAddressByLatLag(String latlng) throws IOException, ParseException {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("maps.googleapis.com")
                .path("/maps/api/geocode/json")
                .queryParam("key", API_KEY)
                .queryParam("latlng", latlng)
                .build();

        URL url = new URL(uri.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new ParseException("HttpResponseCode:" + responseCode);
        } else {
            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }

            scanner.close();


            JSONParser parse = new JSONParser(inline.toString());
            JSONObject dataObj = (JSONObject) parse.parse();

            JSONObject object = (JSONObject) dataObj.get("results");
            String ans = object.toString();

            log.info(String.valueOf(object.get("address_components")));
            return ans;

        }
    }
}
