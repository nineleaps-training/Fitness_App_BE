package com.fitness.app.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
			System.out.println(e.getMessage());
			throw new Exception("File Not uploaded");
		}
	}
	
	//Getting image with id
	public Doc getImage(String id) throws Exception
	{
		try {
			return imgRepo.findById(id).get();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		
		}
		
		
	}
}
