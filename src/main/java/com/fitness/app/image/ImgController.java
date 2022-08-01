package com.fitness.app.image;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.fitness.app.exception.IncorrectFileUploadException;

import static com.fitness.app.components.Constants.ALLOWED_FILE_TYPE;

@RestController
public class ImgController {

	@Autowired
	private ImgService imgService;

	// Saving image to database
	/**
	 * 
	 * @param file
	 * @param id
	 * @return
	 * @throws IOException
	 * @throws MaxUploadSizeExceededException
	 */
	@PostMapping("/v1/image/uploadFile/{id}")
	@Consumes({ "image/png,image/jpg,image/jpeg" })
	@ResponseStatus(HttpStatus.CREATED)
	public Doc saveFile(@RequestParam MultipartFile file, @PathVariable String id)
			throws IOException, MaxUploadSizeExceededException {
		if ("image/png".equals(file.getContentType()) || "image/jpg".equals(file.getContentType())
				|| "image/jpeg".equals(file.getContentType())) {
			return imgService.saveImage(file, id);
		} else {
			throw new IncorrectFileUploadException(ALLOWED_FILE_TYPE);
		}
	}

	// Downloading image with the id
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/v1/image/downloadFile/{id}")
	@Produces({ "image/png,image/jpg", "image/jpeg" })
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ByteArrayResource> getImage(@PathVariable String id) {
		Doc docFile = imgService.getImage(id);
		if (docFile != null) {
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(docFile.getFileType()))
					.header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
							"attachment:filename=\"" + docFile.getFileName() + "\"")
					.body(new ByteArrayResource(docFile.getData()));
		} else {
			return ResponseEntity.ok(null);
		}
	}
}
