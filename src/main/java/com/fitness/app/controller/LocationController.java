package com.fitness.app.controller;

import com.fitness.app.service.LocationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Location controller.
 */
/// get coordinate
@Slf4j
@RestController
@RequestMapping("/api/v1/map")
@RequiredArgsConstructor
public class LocationController {


    private final LocationServiceImpl locationServiceImpl;


    /**
     * Gets details.
     *
     * @param address the address
     * @return the details
     */
    @GetMapping("/public/getLocation")
    public ResponseEntity<?> getDetails(@RequestParam String address) {
        return new ResponseEntity<>(locationServiceImpl.getDetails(address), HttpStatus.OK);
    }

    /**
     * Gets address.
     *
     * @param latlng the latlng
     * @return the address
     */
    @GetMapping("/public/get-fitness-center-by-location")
    public ResponseEntity<?> getAddress(@RequestParam String latlng) {
        return new ResponseEntity<>(locationServiceImpl.getAddress(latlng), HttpStatus.OK);
    }


}


