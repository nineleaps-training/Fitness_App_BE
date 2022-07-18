package com.fitness.app.image;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fitness.app.exception.DataNotFoundException;

@Service
public class ImgService {

	@Autowired
	private ImgRepo imgRepo;

	Doc doc;

	// Upload Image function
	/**
	 * 
	 * @param imgRepo2
	 */
	public ImgService(ImgRepo imgRepo2) {
		this.imgRepo = imgRepo2;
	}

	public Doc saveImage(MultipartFile file, String id) throws IOException {

		String docName = file.getOriginalFilename();
		Doc docFile = new Doc(id, docName, file.getContentType(), file.getBytes());
		imgRepo.save(docFile);
		return docFile;

	}

	// Fetching image with id
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Doc getImage(String id) {
		Optional<Doc> optional = imgRepo.findById(id);
		if (optional.isPresent()) {
			doc = optional.get();
			return doc;
		} else {
			throw new DataNotFoundException("File Not Found");
		}
	}
}
