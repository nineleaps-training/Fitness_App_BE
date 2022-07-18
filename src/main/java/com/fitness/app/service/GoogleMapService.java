package com.fitness.app.service;

import java.io.IOException;
import java.text.ParseException;

public interface GoogleMapService {


    String getAddressByLatLag(String latLan) throws IOException, ParseException, org.apache.tomcat.util.json.ParseException;

}
