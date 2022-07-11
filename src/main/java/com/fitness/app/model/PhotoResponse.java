package com.fitness.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "PhotoResponse")
public class PhotoResponse {
    @ApiModelProperty(name = "fileName", notes = "Name of the image")
    private String fileName;
    @ApiModelProperty(name = "downloadURL", notes = "URL of the image")
    private String downloadURL;
    @ApiModelProperty(name = "fileType", notes = "Type of file")
    private String fileType;
    @ApiModelProperty(name = "fileSize", notes = "Size of File")
    private long fileSize;
}
