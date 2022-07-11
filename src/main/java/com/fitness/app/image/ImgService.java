package com.fitness.app.image;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.Optional;

@Slf4j
@Service
public class ImgService {

    @Autowired
    private ImgRepo imgRepo;

    //upload Image function
    public Doc saveImage(MultipartFile file, String id) throws FileUploadException {
        String docName = file.getOriginalFilename();
        try {
            Doc docFile = new Doc(id, docName, file.getContentType(), file.getBytes());
            return imgRepo.save(docFile);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new FileUploadException("File Not uploaded");
        }
    }

    //Getting image with id
    public Doc getImage(String id) throws FileNotFoundException {
        try {
            Optional<Doc> optional = imgRepo.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new FileNotFoundException(e.getMessage());

        }

    }
}
