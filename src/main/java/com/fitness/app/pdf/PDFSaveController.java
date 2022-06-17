package com.fitness.app.pdf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fitness.app.entity.UserDetails;

public class PDFSaveController {
    
    @Autowired
	private PDFSaveService pdfSaveService;	
	
	// save file to database
   @PutMapping("/uploadFile/{id}")
   public UserDetails saveFile(@RequestParam MultipartFile file, @PathVariable String id) throws Exception
   {
	   return pdfSaveService.savePDF(file, id);
   }
   
   //Download file with the id
   @GetMapping("/downloadFile/{id}")
   public ResponseEntity<?> getPDF(@PathVariable String id) throws Exception
   {
	   UserDetails docFile=pdfSaveService.getPDF(id);
	   if(docFile!=null) {
	   System.out.println(docFile.getPdf_fileName());
	   return  ResponseEntity.ok()
			   .contentType(MediaType.parseMediaType(docFile.getPdf_fileType()))
			   .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\""+docFile.getPdf_fileName()+"\"")
			   .body(new ByteArrayResource(docFile.getPdf_data()));
	   }
	   else
	   {
		   return ResponseEntity.ok(null);
	   }
    }
}
	   //
			 