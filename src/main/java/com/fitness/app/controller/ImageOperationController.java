package com.fitness.app.controller;


import com.fitness.app.entity.ImageOperationDoc;
import com.fitness.app.exceptions.FileNotFoundException;
import com.fitness.app.service.ImageOperationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class ImageOperationController {


    @Autowired
    private ImageOperationServiceImpl imageOperationServiceImpl;

    // save file to database
    @PutMapping("/uploadFile/{id}")
    public ImageOperationDoc saveFile(@RequestParam MultipartFile file, @PathVariable String id) throws FileNotFoundException {
        return imageOperationServiceImpl.saveImage(file, id);
    }

    //Download file with the id
    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable String id) throws FileNotFoundException {
        try {
            ImageOperationDoc imageOperationDocFile = imageOperationServiceImpl.getImage(id);
            if (imageOperationDocFile != null) {

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(imageOperationDocFile.getFileType()))
                        .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + imageOperationDocFile.getFileName() + "\"")
                        .body(new ByteArrayResource(imageOperationDocFile.getData()));
            } else {
                throw new FileNotFoundException("File not found at location");

            }


        } catch (FileNotFoundException e) {
            log.info("Image File not found: {}", e.getMessage());
            throw new FileNotFoundException(e.getMessage());
        }


    }


}
