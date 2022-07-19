package com.fitness.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @InjectMocks
    LocationService locationService;

    @Test
    void testGetAddress() {

        String address="Nineleaps Pasta Street";
        String latlng = "12.930779 , 77.633151";
        String latlng1 = locationService.getDetails(address);
        Assertions.assertEquals(latlng, latlng1);

    }

    @Test
    void testGetAddressException() {

        String address="N";
        try
        {
            locationService.getDetails(address);
        }
        catch(Exception e)
        {
            Assertions.assertEquals("Coordinates not found", e.getMessage());
        }

    }

    @Test
    void testGetDetails() {

        List<String> list = new ArrayList<>();
        list.add("");
        list.add("Bengaluru");
        Map<String,List<String>> address = new HashMap<>();
        address.put("data",list);
        String latlng = "12.930779,77.633151";
        Map<String,List<String>> address1 = locationService.getAddress(latlng);
        Assertions.assertEquals(address, address1);

    }

    @Test
    void testGetDetailsException() {

        List<String> list = new ArrayList<>();
        list.add("");
        list.add("Bengaluru");
        Map<String,List<String>> address = new HashMap<>();
        address.put("data",list);
        String latlng = "0,0";
        try
        {
            locationService.getAddress(latlng);
        }
        catch(Exception e)
        {
            Assertions.assertEquals("Address Not Found", e.getMessage());
        }

    }
}
