package com.fitness.app.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Distance")
public class DistanceModel {

    @ApiModelProperty(name = "dtext", notes = "distance")
    private String dtext;
    @ApiModelProperty(name = "dvalue", notes = "distance value")
    private int dvalue;

    public String getDtext() {
        return dtext;
    }

    public void setDtext(String dtext) {
        this.dtext = dtext;
    }

    public int getDvalue() {
        return dvalue;
    }

    public void setDvalue(int dvalue) {
        this.dvalue = dvalue;
    }

}
