package com.fitness.app.image;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImgService {
	
	@Autowired
	private ImgRepo imgRepo;

	Doc doc;

	//upload Image function
	
	public ImgService(ImgRepo imgRepo2) {
		this.imgRepo=imgRepo2;
	}

	public Doc saveImage(MultipartFile file, String id) throws IOException
	{
		if("image/png".equals(file.getContentType()) || "image/jpg".equals(file.getContentType()) || "image/jpeg".equals(file.getContentType()))
		{
			String docName=file.getOriginalFilename();
			Doc docFile=new Doc(id, docName, file.getContentType(), file.getBytes());
			imgRepo.save(docFile);
			return docFile;		
		}
		else
		{
			return doc;
		}
		
	}
	
	//Getting image with id
	public Doc getImage(String id)
	{
		Optional<Doc> optional=imgRepo.findById(id);
		if(optional.isPresent())
		{
			doc=optional.get();
			return doc;
		}
		else
		{
			return null;
		}
	}
}
