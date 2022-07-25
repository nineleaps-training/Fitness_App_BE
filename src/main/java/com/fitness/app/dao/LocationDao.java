package com.fitness.app.dao;

import java.util.List;
import java.util.Map;

public interface LocationDao {

    String getDetails(String address);
    Map<String, List<String>> getAddress(String latlng);
}
