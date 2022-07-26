package com.fitness.app.service;

import com.fitness.app.model.*;
import com.mongodb.client.model.geojson.Geometry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    Environment environment;

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    LocationService locationService;

    @Test
    void getDetails() {
        String latlng = "12.9351929 , 77.62448069999999";

        Result[] results = new Result[1];

        Result result = new Result();
        Geo geo = new Geo();
        result.setGeometry(geo);
        Location location = new Location(12.9351929, 77.62448069999999);
        geo.setLocation(location);
        results[0] = result;

        Response response = new Response();
        response.setResult(results);
        ResponseEntity responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

        when(environment.getProperty("googleApiKey")).thenReturn("123abc");
        when(restTemplate.getForEntity(anyString(), any(), (Object) any())).thenReturn(responseEntity);

        String actual = locationService.getDetails("Koramangala, Bangalore");
        assertEquals(latlng, actual);

    }

    @Test
    void getAddress() {
        List<String> addressList = new ArrayList<>();
        addressList.add("Unnamed Road Jayamahal Bengaluru Bangalore Urban KA IN 560006 ");
        addressList.add("Bengaluru");

        Map<String,List<String>> address = new HashMap<>();
        address.put("data", addressList);

        String[] strings = new String[1];
        strings[0] = "locality";

        GoogleAddress googleAddress = new GoogleAddress();
        GoogleAddress[] googleAddresses = new GoogleAddress[1];
        googleAddresses[0] = googleAddress;
        googleAddress.setType(strings);
        googleAddress.setShortName("Unnamed Road Jayamahal Bengaluru Bangalore Urban KA IN 560006");
        googleAddress.setLongName("Bengaluru");

        Result[] results = new Result[2];
        Result result = new Result();
        result.setAddress("Unnamed Road Jayamahal Bengaluru Bangalore Urban KA IN 560006");
        result.setAllAddress(googleAddresses);
        results[1] = result;

        Response response = new Response();
        response.setResult(results);

        ResponseEntity responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

        when(environment.getProperty("googleApiKey")).thenReturn("123abc");
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(responseEntity);


        Map<String, List<String>> actual = locationService.getAddress("12.9988, 77.5921 ");
        assertEquals(address, actual);
    }
}