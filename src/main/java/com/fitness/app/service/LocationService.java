package com.fitness.app.service;

import com.fitness.app.dao.LocationDao;
import com.fitness.app.model.GoogleAddress;
import com.fitness.app.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Component
@Slf4j
public class LocationService implements LocationDao {


    private static final Object GOOGLE_API_KEY = "AIzaSyCgHNmyruLEfUzPbSoKUJrx1I-rL_NqJ2U";

    public String getDetails(String address) {
        log.info("LocationService >> getDetails >> Initiated");
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("maps.googleapis.com")
                .path("/maps/api/geocode/json")
                .queryParam("key", GOOGLE_API_KEY)
                .queryParam("address", address)
                .build();
        log.info(uri.toUriString());
        ResponseEntity<Response> response = new RestTemplate().getForEntity(uri.toUriString(), Response.class);
        Response location = response.getBody();
        Double lat = Objects.requireNonNull(location).getResult()[0].getGeometry().getLocation().getLat();
        Double lng = location.getResult()[0].getGeometry().getLocation().getLng();
        log.info("LocationService >> getDetails >> Ends");
        return lat.toString() + " , " + lng.toString();
    }

    public Map<String, List<String>> getAddress(String latlng) {
        log.info("LocationService >> getAddress >> Initiated");
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("maps.googleapis.com")
                .path("/maps/api/geocode/json")
                .queryParam("key", GOOGLE_API_KEY)
                .queryParam("latlng", latlng)
                .build();
        log.info(uri.toUriString());


        String city = "";
        ResponseEntity<Response> response = new RestTemplate().getForEntity(uri.toUriString(), Response.class);
        Response formattedAddress = response.getBody();
        GoogleAddress[] address = Objects.requireNonNull(formattedAddress).getResult()[1].getAllAddress();

        StringBuilder completeAddress = new StringBuilder();
        for (GoogleAddress googleAddress : address) {
            String[] type = googleAddress.getType();
            if (type[0].equals("locality")) {
                city = googleAddress.getLongName();
            }
            completeAddress.append(googleAddress.getShortName()).append(" ");
        }
        List<String> addr = new ArrayList<>();
        addr.add(completeAddress.toString());
        addr.add(city);
        HashMap<String, List<String>> res = new HashMap<>();
        res.put("data", addr);
        log.info("LocationService >> getAddress >> Ends");
        return res;
    }
}
