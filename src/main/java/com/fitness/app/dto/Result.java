package com.fitness.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    @JsonProperty("formatted_address")
    private String address;
    private Geo geometry;

    @JsonProperty("address_components")
    private GoogleAddress allAddress[];

    public GoogleAddress[] getAllAddress() {
        return allAddress;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Geo getGeometry() {
        return geometry;
    }
    public void setGeometry(Geo geometry) {
        this.geometry = geometry;
    }
}
