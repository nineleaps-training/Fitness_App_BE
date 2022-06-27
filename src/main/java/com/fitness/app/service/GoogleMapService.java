package com.fitness.app.service;

import com.fitness.app.exceptions.HttpMethodCodeException;
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
        UriComponents uri= UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("maps.googleapis.com")
                .path("/maps/api/geocode/json")
                .queryParam("key", API_KEY)
                .queryParam("latlng",latlng)
                .build();

            URL url= new URL( uri.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();

            if(responsecode!=200)
            {
              throw  new HttpMethodCodeException("HttpResponseCode: "+ responsecode);
            }
            else
            {
                String inline="";
                Scanner scanner=new Scanner(url.openStream());
                while (scanner.hasNext())
                {
                    inline+=scanner.nextLine();
                }

                scanner.close();


                JSONParser parse=new JSONParser(inline);
                JSONObject data_obj=(JSONObject) parse.parse();

                JSONObject object = (JSONObject)data_obj.get("results");
                String ans=  object.toString();
                log.info("Address Components: {}", object.get("address_components"));


                return ans;

            }
    }
}
