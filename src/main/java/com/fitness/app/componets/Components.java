package com.fitness.app.componets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Logger;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Components {

	private final Random rand;

	{
		try {
			rand = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}


	public String otpBuilder()
    {
    	return String.format("%04d",rand.nextInt(10000));
    }
	
	public int  sendOtpMessage(String message,String otp, String mobile )
	{
		try {
		
		String apiKey="vUSheoFsqykuK6T4P9YQMgEXpDrjC7NmR18Bz0OZlAGWd3tcJnjQftWidwxvqZSs1OyIuBMlkVpRgYeH";
		String senderId="&senderId="+"FSTSMS";
		message="&message="+URLEncoder.encode(message);
		String  variablesValues="&variablesValues="+otp;
		String route="&route="+"otp";
		String numbers="&numbers="+mobile;
		
		String myUrl="https://www.fast2sms.com/dev/bulkV2?authorization="+apiKey+senderId+message+variablesValues+route+numbers;
		
		URL url=new URL(myUrl);
		
		HttpURLConnection con=(HttpURLConnection)url.openConnection();
		
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("cache-control", "no-cache");
		
		int code=con.getResponseCode();
		
		StringBuilder response=new StringBuilder();
		BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		while(true)
		{
			String line=br.readLine();
			if(line==null)
			{
				break;
			}
			response.append(line);
		}
		log.info("code: {}", code, "response : {}", response);
		return code;
		}
		catch (Exception e) {
			
			log.info(e.getMessage());
			return 0;
		}
		
		
	}
}