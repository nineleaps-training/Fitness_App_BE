package com.fitness.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
<<<<<<< HEAD
import java.util.concurrent.Future;

import com.fitness.app.model.GoogleAddress;
import com.fitness.app.model.GymRepresnt;
import com.fitness.app.model.Response;

import com.fitness.app.service.GymService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
=======

import com.fitness.app.model.GoogleAddress;
import com.fitness.app.model.Response;

import com.fitness.app.service.GymServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/// get coordinate
@Slf4j
@RestController
public class LocationController {
    private static final Object API_KEY = "AIzaSyCgHNmyruLEfUzPbSoKUJrx1I-rL_NqJ2U";

    @Autowired
<<<<<<< HEAD
    public GymService gymService;
=======
    public GymServiceImpl gymServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
    @GetMapping("/getLocation")
    public String getDetails(@RequestParam String address)
    {
        UriComponents uri= UriComponentsBuilder.newInstance()
        .scheme("https")
        .host("maps.googleapis.com")
        .path("/maps/api/geocode/json")
        .queryParam("key", API_KEY)
        .queryParam("address",address)
        .build();

        log.info("URL: {}", uri.toUriString());
        ResponseEntity<Response> response = new RestTemplate().getForEntity(uri.toUriString(), Response.class);
        Response location=response.getBody();
        assert (location!=null); //null pointer exception shown here.
        Double lat=location.getResult()[0].getGeometry().getLocation().getLat();
        Double lng=location.getResult()[0].getGeometry().getLocation().getLng();
        return lat.toString()+" , "+lng.toString();
    }

    @GetMapping("/get-fitness-center-by-location")
    public Map<String, List<String>> getAddress(@RequestParam String latlng)
    {
        UriComponents uri= UriComponentsBuilder.newInstance()
        .scheme("https")
        .host("maps.googleapis.com")
        .path("/maps/api/geocode/json")
        .queryParam("key", API_KEY)
        .queryParam("latlng",latlng)
        .build();

        log.info("The build uri: {}", uri.toString());


        String city="";
        ResponseEntity<Response> response = new RestTemplate().getForEntity(uri.toUriString(), Response.class);
        Response formated_address = response.getBody();
        assert (formated_address!=null);
        GoogleAddress[] address = formated_address.getResult()[1].getAllAddress();

        String complteAddress="";
        int size=address.length;
       for(int i=0;i<size;i++)
       {
           String[] type=address[i].getType();
           if(type[0].equals("locality"))
           {
               city=address[i].getLong_name();
           }
           complteAddress+=address[i].getShort_name()+" ";
       }
       List<String> addr=new ArrayList<>();
       addr.add(complteAddress);
       addr.add(city);
       HashMap<String,List<String>> res=new HashMap<>();
       res.put("data", addr);

       return res;
    }




   
    
}


