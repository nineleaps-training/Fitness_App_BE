package com.fitness.app.qr;

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
import com.fitness.app.pdf.PDFSaveService;

public class QRSaveController {
    
    @Autowired
	private QRSaveService qrSaveService;	
	
	// save file to database
   @PutMapping("/uploadFile/{id}")
   public UserDetails saveFile(@RequestParam MultipartFile file, @PathVariable String id) throws Exception
   {
	   return qrSaveService.saveQR(file, id);
   }
   
   //Download file with the id
   @GetMapping("/downloadFile/{id}")
   public ResponseEntity<?> getQR(@PathVariable String id) throws Exception
   {
	   UserDetails docFile=qrSaveService.getQR(id);
	   if(docFile!=null) {
	   System.out.println(docFile.getQr_fileName());
	   return  ResponseEntity.ok()
			   .contentType(MediaType.parseMediaType(docFile.getQr_fileType()))
			   .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\""+docFile.getQr_fileName()+"\"")
			   .body(new ByteArrayResource(docFile.getQr_data()));
	   }
	   else
	   {
		   return ResponseEntity.ok(null);
	   }
    }
}
	   //
			 