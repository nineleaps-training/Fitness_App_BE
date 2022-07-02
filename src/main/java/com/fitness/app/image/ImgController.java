package com.fitness.app.image;




import com.fitness.app.exceptions.FileNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@Slf4j
@RestController
public class ImgController {

	
	@Autowired
	private ImgService imgService;
	
	
	
	
	// save file to database
   @PutMapping("/uploadFile/{id}")
   public Doc saveFile(@RequestParam MultipartFile file, @PathVariable String id) throws FileNotFoundException
   {
	   return imgService.saveImage(file, id);
   }
   
   //Download file with the id
   @GetMapping("/downloadFile/{id}")
   public ResponseEntity<ByteArrayResource> getImage(@PathVariable String id) throws FileNotFoundException
   {
	   try
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
			   throw new FileNotFoundException("File not found at location");
			   
		   }
		   
		 
	   }catch (FileNotFoundException e) {
		log.info("Image File not found: {}", e.getMessage());
		throw new FileNotFoundException(e.getMessage());
	}
	   
	   

			   
   }
   
  
}

