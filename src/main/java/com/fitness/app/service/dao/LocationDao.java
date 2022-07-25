package com.fitness.app.service.dao;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

public interface LocationDao {

    String getDetails(String address);
    Map<String, List<String>> getAddress(String latLan);
}
