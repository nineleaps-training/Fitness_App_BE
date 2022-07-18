package com.fitness.app.controller;


import com.fitness.app.service.GoogleMapServiceImpl;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class GoogleMapController {

    @Autowired
    private GoogleMapServiceImpl googleMapServiceImpl;


    @GetMapping("/address-by-lat-lng")
    public String getAddressByLatLng(@RequestParam String latlng) throws IOException, ParseException {
        return googleMapServiceImpl.getAddressByLatLag(latlng);
    }
}
