package com.fitness.app.image;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Service
public class ImgService {

	
	
	@Autowired
	private ImgRepo imgRepo;
	
	
	//upload Image function
	
	public Doc saveImage(MultipartFile file, String id) throws Exception
	{
		String docName=file.getOriginalFilename();
		try {
			Doc docFile=new Doc(id, docName, file.getContentType(), file.getBytes());
			return imgRepo.save(docFile);
		} catch (Exception e) {

			log.info("Exception by : {}", e.getMessage());
			throw new Exception("File Not uploaded");
		}
	}
	
	//Getting image with id
	public Doc getImage(String id) throws Exception
	{
		try {
			Optional<Doc> data=imgRepo.findById(id);
			return data.get();//imgRepo.findById(id).get();
		} catch (Exception e) {
			log.info("Exception: {}", e.getMessage());
			throw new Exception(e.getMessage());
		
		}
		
		
	}
}
