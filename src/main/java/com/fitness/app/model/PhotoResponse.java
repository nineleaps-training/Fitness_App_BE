package com.fitness.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoResponse {

    private String fileName;
    private String downloadURL;
    private String fileType;
    private long fileSize;

}
