package com.fitness.app.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Duration")
public class DurationModel {

    @ApiModelProperty(name = "text", notes = "Duration")
    private String text;
    @ApiModelProperty(name = "value", notes = "Duration value")
    private int value;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
