package com.fitness.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "GoogleAddress")
public class GoogleAddressModel {

    @JsonProperty("long_name")
    @ApiModelProperty(name = "longName", notes = "Long Name of Address")
    private String longName;

    @JsonProperty("short_name")
    @ApiModelProperty(name = "shortName", notes = "Short Name of Address")
    private String shortName;

    @JsonProperty("types")
    @ApiModelProperty(name = "type", notes = "Types of Address")
    private String[] type;

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }

    public String[] getType() {
        return type;
    }
}
