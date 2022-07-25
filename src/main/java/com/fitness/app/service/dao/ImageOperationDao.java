package com.fitness.app.service.dao;

import com.fitness.app.entity.ImageOperationDoc;
import com.fitness.app.exceptions.FileNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

public interface ImageOperationDao {

    ImageOperationDoc saveImage(MultipartFile file, String id) throws FileNotFoundException;
    ImageOperationDoc getImage(String id) throws FileNotFoundException;
}
