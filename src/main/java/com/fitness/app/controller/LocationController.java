package com.fitness.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.AuthenticationException;
import com.fitness.app.model.GoogleAddress;
import com.fitness.app.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

@Slf4j
@RestController
@EnableRetry
public class LocationController {

    private static final Object API_KEY = System.getenv("GOOGLE_API_KEY");

    @ApiOperation(value = "Get Lat Lng", notes = "Fetching the latitude and longitude from address")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Location Fetched", response = String.class),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
    @GetMapping(value = "/v1/getLocation", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Retryable(value = ResponseStatusException.class, maxAttempts = 2, backoff = @Backoff(delay = 60000))
    @SuppressWarnings("java:S2259")
    public String getDetails (@NotBlank @NotNull @RequestParam String address)
    {
        log.info("address:{}",address);
        UriComponents uri= UriComponentsBuilder.newInstance()
        .scheme("https")
        .host("maps.googleapis.com")
        .path("/maps/api/geocode/json")
        .queryParam("key", API_KEY)
        .queryParam("address",address)
        .build();  
        ResponseEntity<Response> response = new RestTemplate().getForEntity(uri.toUriString(), Response.class);
        Response location=response.getBody();
        Double lat=location.getResult()[0].getGeometry().getLocation().getLat();
        Double lng=location.getResult()[0].getGeometry().getLocation().getLng();
        log.info("lat: {} lng:{}",lat,lng);
        return lat.toString()+" , "+lng.toString();
    }

    @ApiOperation(value = "Get Address", notes = "Fetching Gym Address from latitude and longitude")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Address Fetched", response = HashMap.class),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
    @GetMapping(value = "/v1/get-fitness-center-by-location", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @SuppressWarnings("java:S2259")
    public Map<String, List<String>> getAddress(@NotBlank @NotNull @RequestParam String latlng)
    {
        log.info("LatLng:{}",latlng);
        UriComponents uri= UriComponentsBuilder.newInstance()
        .scheme("https")
        .host("maps.googleapis.com")
        .path("/maps/api/geocode/json")
        .queryParam("key", API_KEY)
        .queryParam("latlng",latlng)
        .build();
        String city="";
        ResponseEntity<Response> response = new RestTemplate().getForEntity(uri.toUriString(), Response.class);
        Response formatedAddress = response.getBody();
        GoogleAddress[] address = formatedAddress.getResult()[1].getAllAddress();
        String complteAddress="";
        int size=address.length;
        for(int i=0;i<size;i++)
        {
            String[] type=address[i].getType();
            if(type[0].equals("locality"))
            {
                city=address[i].getLongName();
            }
            StringBuilder completeAddress = new StringBuilder();
            completeAddress.append(address[i].getShortName()+" ");
        }
        List<String> addr=new ArrayList<>();
        addr.add(complteAddress);
        addr.add(city);
        HashMap<String,List<String>> res=new HashMap<>();
        res.put("data", addr);
        log.info("Response: {}",res);
        return res;
        }  
}   