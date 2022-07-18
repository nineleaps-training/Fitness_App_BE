package com.fitness.app.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Geo")
public class GeoModel {

    @ApiModelProperty(name = "location", notes = "Location of User or Gym")
    private LocationModel location;

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

}
