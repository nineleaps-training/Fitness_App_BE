package com.fitness.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Response")
public class Response {
    @JsonProperty("results")
    @ApiModelProperty(name = "result", notes = "Results Fetched")
    private ResultModel[] result;

    public ResultModel[] getResult() {
        return result;
    }

    public void setResult(ResultModel[] result) {
        this.result = result;
    }

}
