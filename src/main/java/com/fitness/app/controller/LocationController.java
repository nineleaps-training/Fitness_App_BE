package com.fitness.app.controller;

import java.util.*;

import com.fitness.app.model.GoogleAddress;
import com.fitness.app.model.Response;

import com.fitness.app.service.GymService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/// get coordinate

@Slf4j
@RestController
public class LocationController {
    private static final Object API_KEY = "AIzaSyCgHNmyruLEfUzPbSoKUJrx1I-rL_NqJ2U";

    @Autowired
    public GymService gymService;

    @GetMapping("/getLocation")
    public String getDetails(@RequestParam String address) {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("maps.googleapis.com")
                .path("/maps/api/geocode/json")
                .queryParam("key", API_KEY)
                .queryParam("address", address)
                .build();
        log.info(uri.toUriString());
        ResponseEntity<Response> response = new RestTemplate().getForEntity(uri.toUriString(), Response.class);
        Response location = response.getBody();
        Double lat = Objects.requireNonNull(location).getResult()[0].getGeometry().getLocation().getLat();
        Double lng = location.getResult()[0].getGeometry().getLocation().getLng();
        return lat.toString() + " , " + lng.toString();
    }

    @GetMapping("/get-fitness-center-by-location")
    public Map<String, List<String>> getAddress(@RequestParam String latlng) {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("maps.googleapis.com")
                .path("/maps/api/geocode/json")
                .queryParam("key", API_KEY)
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

        return res;
    }


}


