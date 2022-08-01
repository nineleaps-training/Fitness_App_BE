package com.fitness.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;

import com.fitness.app.dao.LocationDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

@RestController
@EnableRetry
public class LocationController {

    @Autowired
    private LocationDAO locationDAO;

    /**
     * This controller is used to fetch the location by the provided address
     * 
     * @param address - Address of the fitness center
     * @return - Location(Latitude, Longitude)
     * @throws - ArrayIndexOutOfBoundsException
     */
    @ApiOperation(value = "Get Lat Lng", notes = "Fetching the latitude and longitude from address")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Location Fetched", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @GetMapping(value = "/v1/location/getLocation", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getDetails(@NotBlank @NotNull @RequestParam String address) {

        return locationDAO.getDetails(address);
    }

    /**
     * This controller is used to fetch the address from the given latitude and
     * longitude
     * 
     * @param latlng - Latitude, Longitude
     * @return - Address of the gym
     */
    @ApiOperation(value = "Get Address", notes = "Fetching Gym Address from latitude and longitude")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Address Fetched", response = HashMap.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @GetMapping(value = "/v1/location/getFitnessCenterByLocation", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<String>> getAddress(@NotBlank @NotNull @RequestParam String latlng) {

        return locationDAO.getAddress(latlng);

    }
}