package com.fitness.app.image;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface ImgDAO {

    public Doc saveImage(MultipartFile file, String id) throws IOException;

    public Doc getImage(String id);
    
}
