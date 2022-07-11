package com.fitness.app.image;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@EnableRetry
public class ImgController {

	@Autowired
	private ImgService imgService;	
	
	// save file to database
   @PostMapping("/uploadFile/{id}")
   @Consumes({"image/png,image/jpg,image/jpeg"})
   @ResponseStatus(HttpStatus.CREATED)
   @Retryable(value = FileUploadException.class, maxAttempts = 2, backoff = @Backoff(delay = 60000))
   public Doc saveFile(@RequestParam MultipartFile file, @PathVariable String id) throws IOException
   {
	   return imgService.saveImage(file, id);
   }
   
   //Download file with the id
   @GetMapping("/downloadFile/{id}")
   @Produces({"image/png,image/jpg","image/jpeg"})
   @ResponseStatus(HttpStatus.OK)
   public ResponseEntity<ByteArrayResource> getImage(@PathVariable String id)
   {
	   Doc docFile=imgService.getImage(id);
	   if(docFile!=null) {
	   return  ResponseEntity.ok()
			   .contentType(MediaType.parseMediaType(docFile.getFileType()))
			   .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\""+docFile.getFileName()+"\"")
			   .body(new ByteArrayResource(docFile.getData()));
	   }
	   else
	   {
		   return ResponseEntity.ok(null);
	   }			   
   }
}
