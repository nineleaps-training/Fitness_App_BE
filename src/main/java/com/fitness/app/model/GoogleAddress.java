package com.fitness.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleAddress {

    @JsonProperty("long_name")
    private String long_name;

    @JsonProperty("short_name")
    private String short_name;

    @JsonProperty("types")
    private String[] type;

    public String getLong_name() {
        return long_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public String[] getType() {
        return type;
    }
}
