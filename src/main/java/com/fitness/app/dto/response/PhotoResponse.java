package com.fitness.app.dto.response;

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
