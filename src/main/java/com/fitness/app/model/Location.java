package com.fitness.app.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Location")
public class Location {
    @ApiModelProperty(name = "lat", notes = "Latitude")
    private double lat;
    @ApiModelProperty(name = "lng", notes = "Longitude")
    private double lng;
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLng() {
        return lng;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }

    
}
