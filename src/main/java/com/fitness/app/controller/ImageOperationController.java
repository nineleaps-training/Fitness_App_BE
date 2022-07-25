package com.fitness.app.controller;


import com.fitness.app.dto.response.ApiResponse;
import com.fitness.app.entity.ImageOperationDoc;
import com.fitness.app.exceptions.FileNotFoundException;
import com.fitness.app.service.ImageOperationDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * The type Image operation controller.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/image/")
public class ImageOperationController {


    @Autowired
    private ImageOperationDaoImpl imageOperationServiceImpl;

    /**
     * Save file api response.
     *
     * @param file the file
     * @param id   the id
     * @return the api response
     * @throws FileNotFoundException the file not found exception
     */
// save file to database
    @PutMapping("/uploadFile/{id}")
    public ApiResponse saveFile(@RequestParam MultipartFile file, @PathVariable String id) throws FileNotFoundException {
        return new ApiResponse(HttpStatus.OK, imageOperationServiceImpl.saveImage(file, id));
    }

    /**
     * Gets image.
     *
     * @param id the id
     * @return the image
     * @throws FileNotFoundException the file not found exception
     */
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

