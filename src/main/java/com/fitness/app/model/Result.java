package com.fitness.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Result")
public class Result {
    @JsonProperty("formatted_address")
    @ApiModelProperty(name = "address", notes = "Address")
    private String address;
    @ApiModelProperty(name = "geometry", notes = "Geometry")
    private Geo geometry;
    @JsonProperty("address_components")
    @ApiModelProperty(name = "allAddress", notes = "All the address")
    private GoogleAddress[] allAddress;

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
