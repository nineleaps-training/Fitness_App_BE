package com.fitness.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fitness.app.dao.LocationDAO;
import com.fitness.app.model.GoogleAddressModel;
import com.fitness.app.model.Response;

@Slf4j
@Service
public class LocationService implements LocationDAO {

    private static final Object API_KEY = System.getenv("GOOGLE_API_KEY");

    public String getDetails(@NotBlank @NotNull @RequestParam String address)
    {

        log.info("LocationService >> getDetails >> address:{}", address);
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("maps.googleapis.com")
                .path("/maps/api/geocode/json")
                .queryParam("key", API_KEY)
                .queryParam("address", address)
                .build();
        ResponseEntity<Response> response = new RestTemplate().getForEntity(uri.toUriString(), Response.class);
        try {
            Response location = response.getBody(); // Get Location(Latitude,Longitude) from Address
            Double lat = Objects.requireNonNull(location).getResult()[0].getGeometry().getLocation().getLat();
            Double lng = location.getResult()[0].getGeometry().getLocation().getLng();
            log.info("lat: {} lng:{}", lat, lng);
            return lat.toString() + " , " + lng.toString();
        } catch (Exception e) {
            log.error("LocationService >> getDetails >> Exception thrown");
            throw new ArrayIndexOutOfBoundsException("Coordinates not found");
        }
    }

    public Map<String, List<String>> getAddress(@NotBlank @NotNull @RequestParam String latlng)
    {
        log.info("LocationService >> getAddress >> LatLng:{}", latlng);
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("maps.googleapis.com")
                .path("/maps/api/geocode/json")
                .queryParam("key", API_KEY)
                .queryParam("latlng", latlng)
                .build();
        String city = "";
        ResponseEntity<Response> response = new RestTemplate().getForEntity(uri.toUriString(), Response.class);
        try {
            Response formatedAddress = response.getBody(); // Get Address from Location(Latitude,Longitude)
            GoogleAddressModel[] address = Objects.requireNonNull(formatedAddress).getResult()[1].getAllAddress();
            String complteAddress = "";
            int size = address.length;
            for (int i = 0; i < size; i++) {
                String[] type = address[i].getType();
                if (type[0].equals("locality")) {
                    city = address[i].getLongName();
                }
                StringBuilder completeAddress = new StringBuilder();
                completeAddress.append(address[i].getShortName() + " ");
            }
            List<String> addr = new ArrayList<>();
            addr.add(complteAddress);
            addr.add(city);
            HashMap<String, List<String>> res = new HashMap<>();
            res.put("data", addr);
            log.info("Response: {}", res);
            return res;
        } catch (Exception e) {
            log.error("LocationService >> getAddress >> Exception thrown");
            throw new ArrayIndexOutOfBoundsException("Address Not Found");
        }
    }
    
}
