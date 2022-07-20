package com.fitness.app.service;

import com.fitness.app.dto.GoogleAddress;
import com.fitness.app.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fitness.app.helper.ConstantsClass.API_KEY;

@Slf4j
@Service
@Transactional
public class LocationServiceImpl implements LocationService {
    @Override
    public String getDetails(String address) {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("maps.googleapis.com")
                .path("/maps/api/geocode/json")
                .queryParam("key", API_KEY)
                .queryParam("address", address)
                .build();

        log.info("URL: {}", uri.toUriString());
        ResponseEntity<Response> response = new RestTemplate().getForEntity(uri.toUriString(), Response.class);
        Response location = response.getBody();
        assert (location != null); //null pointer exception shown here.
        Double lat = location.getResult()[0].getGeometry().getLocation().getLat();
        Double lng = location.getResult()[0].getGeometry().getLocation().getLng();
        return lat.toString() + " , " + lng.toString();
    }

    @Override
    public Map<String, List<String>> getAddress(String latLan) {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("maps.googleapis.com")
                .path("/maps/api/geocode/json")
                .queryParam("key", API_KEY)
                .queryParam("latlng", latLan)
                .build();

        log.info("The build uri: {}", uri.toString());


        String city = "";
        ResponseEntity<Response> response = new RestTemplate().getForEntity(uri.toUriString(), Response.class);
        Response formatted_address = response.getBody();
        assert (formatted_address != null);
        GoogleAddress[] address = formatted_address.getResult()[1].getAllAddress();

        String complteAddress = "";
        int size = address.length;
        for (int i = 0; i < size; i++) {
            String[] type = address[i].getType();
            if (type[0].equals("locality")) {
                city = address[i].getLong_name();
            }
            complteAddress += address[i].getShort_name() + " ";
        }
        List<String> addr = new ArrayList<>();
        addr.add(complteAddress);
        addr.add(city);
        HashMap<String, List<String>> res = new HashMap<>();
        res.put("data", addr);

        return res;
    }
}
