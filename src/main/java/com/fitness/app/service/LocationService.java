package com.fitness.app.service;

import java.util.List;
import java.util.Map;

public interface LocationService {

    String getDetails(String address);
    Map<String, List<String>> getAddress(String latLan);
}
