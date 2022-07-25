package com.fitness.app.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LocationServiceTest {

    @Autowired
    LocationService locationService;

    @Test
    void getDetails() {
        String latlng = "12.9351929 , 77.62448069999999";

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

        Map<String, List<String>> actual = locationService.getAddress("12.9988, 77.5921 ");

        assertEquals(address, actual);
    }
}