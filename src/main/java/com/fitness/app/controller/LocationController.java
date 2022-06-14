package com.fitness.app.controller;

import java.net.http.HttpResponse.ResponseInfo;
import java.util.Map;

import com.fitness.app.model.DResponse;
import com.fitness.app.model.Response;
import com.fitness.app.model.Rows;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import net.bytebuddy.dynamic.scaffold.TypeInitializer.Drain;

/// get coordinate

@RestController
public class LocationController {
    private static final Object API_KEY = "AIzaSyCgHNmyruLEfUzPbSoKUJrx1I-rL_NqJ2U";
    @GetMapping("/getLocation")
    public Response getDetails(@RequestParam String address)
    {
        UriComponents uri= UriComponentsBuilder.newInstance()
        .scheme("https")
        .host("maps.googleapis.com")
        .path("/maps/api/geocode/json")
        .queryParam("key", API_KEY)
        .queryParam("address",address)
        .build();
        System.out.println(uri.toUriString());
        ResponseEntity<Response> response = new RestTemplate().getForEntity(uri.toUriString(), Response.class);
        return response.getBody();
        /*Response location=response.getBody();
        Double lat=location.getResult()[0].getGeometry().getLocation().getLat();
        Double lng=location.getResult()[0].getGeometry().getLocation().getLng();
        return lat.toString()+" , "+lng.toString();*/
    }

    @GetMapping("/getAddress")
    public String getAddress(@RequestParam String latlng)
    {
        UriComponents uri= UriComponentsBuilder.newInstance()
        .scheme("https")
        .host("maps.googleapis.com")
        .path("/maps/api/geocode/json")
        .queryParam("key", API_KEY)
        .queryParam("latlng",latlng)
        .build();
        System.out.println(uri.toUriString());
        String[] city;
        ResponseEntity<Response> response = new RestTemplate().getForEntity(uri.toUriString(), Response.class);
        Response formated_address = response.getBody();
        String address = formated_address.getResult()[6].getAddress();
        city=address.split(",");
        assert(city.length!=0);
        return city[0];
    }

    @GetMapping("/getDistance")
    public String getDistance(@RequestParam String destinations, @RequestParam String origins)
    {
        UriComponents uri= UriComponentsBuilder.newInstance()
        .scheme("https")
        .host("maps.googleapis.com")
        .path("/maps/api/distancematrix/json")
        .queryParam("key", API_KEY)
        .queryParam("destinations",destinations)
        .queryParam("origins", origins)
        .build();
        System.out.println(uri.toUriString());
        ResponseEntity<DResponse> response = new RestTemplate().getForEntity(uri.toUriString(), DResponse.class);
        DResponse matrix=response.getBody();
        String distance=matrix.getRows()[0].getElements()[0].getDistance().getText();
        String duration=matrix.getRows()[0].getElements()[0].getDuration().getText();
        String disdur=distance+" , "+duration;
        return disdur;
    }
    
}


