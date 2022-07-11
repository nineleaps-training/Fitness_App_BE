package com.fitness.app.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

@ApiModel(description = "Elements")
public class Elements {

    @JsonProperty("distance")
    @ApiModelProperty(name = "distance", notes = "Distance")
    private Distance distance; 
    @ApiModelProperty(name = "duration", notes = "Duration")
    private Duration duration;

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
