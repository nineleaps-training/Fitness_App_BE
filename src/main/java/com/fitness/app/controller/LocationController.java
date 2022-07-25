package com.fitness.app.controller;

import java.util.*;

import com.fitness.app.dao.LocationDao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/// get coordinate

@Slf4j
@RestController
public class LocationController {

    @Autowired
    LocationDao locationDao;

    @GetMapping("/getLocation")
    public String getDetails(@NotNull @NotBlank @RequestParam String address) {

        return locationDao.getDetails(address);
    }

    @GetMapping("/get-fitness-center-by-location")
    public Map<String, List<String>> getAddress(@NotNull @NotBlank @RequestParam String latlng) {

        return locationDao.getAddress(latlng);
    }


}


