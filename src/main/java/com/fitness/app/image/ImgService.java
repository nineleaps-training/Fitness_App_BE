package com.fitness.app.image;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fitness.app.exception.DataNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImgService implements ImgDAO {

	private ImgRepo imgRepo;

	/**
	 * 
	 * @param imgRepo2
	 */

	public ImgService(ImgRepo imgRepo2) {
		this.imgRepo = imgRepo2;
	}

	/**
	 * This
	 * 
	 * @param multipart - Actual file
	 * @param id        - Gym id
	 * @return - Doc
	 */
	public Doc saveImage(MultipartFile multipart, String id) throws IOException {
		log.info("ImgService >> saveImage >> started"); // Upload Image function
		String docName = multipart.getOriginalFilename();
		Doc doc = new Doc(id, docName, multipart.getContentType(), multipart.getBytes());
		imgRepo.save(doc);
		return doc;

	}

	// Fetching image with id
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Doc getImage(String id) {
		log.info("ImgService >> getImage >> started");
		Optional<Doc> optional = imgRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			log.error("ImgService >> saveImage >> Exception thrown");
			throw new DataNotFoundException("File Not Found");
		}
	}
}
