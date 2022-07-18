package com.fitness.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "DResponse")
public class DResponseModel {

    @JsonProperty("destination_addresses")
    @ApiModelProperty(name = "destinationAddresses", notes = "Address of destination")
    private String[] destinationAddresses;
    @JsonProperty("origin_addresses")
    @ApiModelProperty(name = "originAddresses", notes = "Address of origin")
    private String[] originAddresses;
    @JsonProperty("rows")
    @ApiModelProperty(name = "rows", notes = "List of rows")
    private Rows[] rows;

    public String[] getDestinationAddresses() {
        return destinationAddresses;
    }

    public void setDestinationAddresses(String[] destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }

    public String[] getOriginAddresses() {
        return originAddresses;
    }

    public void setOriginAddresses(String[] originAddresses) {
        this.originAddresses = originAddresses;
    }

    public Rows[] getRows() {
        return rows;
    }

    public void setRows(Rows[] rows) {
        this.rows = rows;
    }

}
