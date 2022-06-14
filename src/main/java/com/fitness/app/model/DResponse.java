package com.fitness.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DResponse {
    @JsonProperty("destination_addresses")
    private String destination_addresses[];
    @JsonProperty("origin_addresses")
    private String origin_addresses[];
    @JsonProperty("rows")
    private Rows[] rows;

    public String[] getDestination_addresses() {
        return destination_addresses;
    }

    public void setDestination_addresses(String[] destination_addresses) {
        this.destination_addresses = destination_addresses;
    }

    public String[] getOrigin_addresses() {
        return origin_addresses;
    }

    public void setOrigin_addresses(String[] origin_addresses) {
        this.origin_addresses = origin_addresses;
    }

    public Rows[] getRows() {
        return rows;
    }

    public void setRows(Rows[] rows) {
        this.rows = rows;
    }
    
}
