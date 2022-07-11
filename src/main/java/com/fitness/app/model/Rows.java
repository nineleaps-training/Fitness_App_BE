package com.fitness.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Rows")
public class Rows {

    @JsonProperty("elements")
    @ApiModelProperty(name = "elements", notes = "All Elements")
    private Elements[] elements;

    public Elements[] getElements() {
        return elements;
    }

    public void setElements(Elements[] elements) {
        this.elements = elements;
    }

    
}
