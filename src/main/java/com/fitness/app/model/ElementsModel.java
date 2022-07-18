package com.fitness.app.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

@ApiModel(description = "Elements")
public class ElementsModel {

    @JsonProperty("distance")
    @ApiModelProperty(name = "distance", notes = "Distance")
    private DistanceModel distance;
    @ApiModelProperty(name = "duration", notes = "Duration")
    private DurationModel duration;

    public DistanceModel getDistance() {
        return distance;
    }

    public void setDistance(DistanceModel distance) {
        this.distance = distance;
    }

    public DurationModel getDuration() {
        return duration;
    }

    public void setDuration(DurationModel duration) {
        this.duration = duration;
    }
}
