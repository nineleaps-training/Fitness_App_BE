package com.fitness.app.controller;


import com.fitness.app.service.GoogleMapService;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class GoogleMapController {

    @Autowired
    private GoogleMapService googleMapService;

    @GetMapping("/address-by-lat-lng")
    public String getAddressByLatLng(@RequestParam String latlng) throws IOException, ParseException {
        return googleMapService.getAddressByLatLag(latlng);
    }
}
