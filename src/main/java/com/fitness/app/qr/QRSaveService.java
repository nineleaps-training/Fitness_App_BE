package com.fitness.app.qr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fitness.app.entity.UserDetails;
import com.fitness.app.repository.UserDetailsRepository;

@Service
public class QRSaveService {
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	public UserDetails saveQR(MultipartFile file, String id) throws Exception
	{
		String docName=file.getOriginalFilename();
		try {
			UserDetails docFile=new UserDetails("","","","",395007,docName,file.getContentType(),file.getBytes(),docName,file.getContentType(),file.getBytes());
			return userDetailsRepository.save(docFile);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception("File Not uploaded");
		}
	}
	
	public UserDetails getQR(String id) throws Exception
	{
		try {
			return userDetailsRepository.findByEmail("");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());		
		}
		
		
	}
}

