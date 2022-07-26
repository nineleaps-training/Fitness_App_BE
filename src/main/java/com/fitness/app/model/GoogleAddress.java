package com.fitness.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GoogleAddress {

    @JsonProperty("long_name")
    private String longName;

    @JsonProperty("short_name")
    private String shortName;

    @JsonProperty("types")
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
