package com.fitness.app.image;

import com.fitness.app.exceptions.FileNotFoundException;
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
	
	public Doc saveImage(MultipartFile file, String id) throws FileNotFoundException
	{
		String docName=file.getOriginalFilename();
		try {
			Doc docFile=new Doc(id, docName, file.getContentType(), file.getBytes());
			return imgRepo.save(docFile);
		} catch (Exception e) {

			log.info("Exception by : {}", e.getMessage());
			throw new FileNotFoundException(e.getMessage());
		}
	}
	
	//Getting image with id
	public Doc getImage(String id) throws FileNotFoundException
	{
		try {
			Optional<Doc> data=imgRepo.findById(id);
			if(data.isPresent())
			{
				return data.get();
			}
			else
			{
				throw new FileNotFoundException("In file data is not present");
			}
		} catch (Exception e) {
			log.info("Exception: {}", e.getMessage());
			throw new FileNotFoundException(e.getMessage());
		
		}
		
		
	}
}
