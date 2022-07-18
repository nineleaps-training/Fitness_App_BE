package com.fitness.app.service;

import com.fitness.app.entity.ImageOperationDoc;
import com.fitness.app.exceptions.FileNotFoundException;
import com.fitness.app.repository.ImageOperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ImageOperationServiceImpl implements ImageOperationService {

    final private ImageOperationRepository imageOperationRepository;


    //upload Image function
    @Override
    public ImageOperationDoc saveImage(MultipartFile file, String id) throws FileNotFoundException {
        String docName = file.getOriginalFilename();
        try {
            ImageOperationDoc imageOperationDocFile = new ImageOperationDoc(id, docName, file.getContentType(), file.getBytes());
            return imageOperationRepository.save(imageOperationDocFile);
        } catch (Exception e) {
            log.error("ImageOperationServiceImpl ::-> saveImage :: Error found due to: {}", e.getMessage());
            throw new FileNotFoundException(e.getMessage());
        }
    }

    //Getting image with id
    @Override
    public ImageOperationDoc getImage(String id) throws FileNotFoundException {
        try {
            Optional<ImageOperationDoc> data = imageOperationRepository.findById(id);
            if (data.isPresent()) {
                return data.get();
            } else {
                throw new FileNotFoundException("In file data is not present");
            }
        } catch (Exception e) {
            log.error("ImageOperationServiceImpl ::-> getImage :: Error found due to: {}", e.getMessage());
            throw new FileNotFoundException(e.getMessage());

        }


    }
}
