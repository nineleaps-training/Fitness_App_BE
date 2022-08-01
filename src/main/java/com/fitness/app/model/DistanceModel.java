package com.fitness.app.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Distance")
@Data
public class DistanceModel {

    @ApiModelProperty(name = "dText", notes = "distance")
    private String dText;
    @ApiModelProperty(name = "dValue", notes = "distance value")
    private int dValue;

}
