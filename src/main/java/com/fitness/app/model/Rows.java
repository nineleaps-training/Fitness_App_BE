package com.fitness.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Rows")
public class Rows {

    @JsonProperty("elements")
    @ApiModelProperty(name = "elements", notes = "All Elements")
    private ElementsModel[] elements;

    public ElementsModel[] getElements() {
        return elements;
    }

    public void setElements(ElementsModel[] elements) {
        this.elements = elements;
    }

}
