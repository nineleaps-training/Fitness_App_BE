package com.fitness.app.componets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Components {



    public String otpBuilder()
    {
    	Random rand = new Random();
    	return String.format("%04d",rand.nextInt(10000));
    }
	
	public int  sendOtpMessage(String message,String otp, String mobile )
	{
		try {
		
		String api_key="vUSheoFsqykuK6T4P9YQMgEXpDrjC7NmR18Bz0OZlAGWd3tcJnjQftWidwxvqZSs1OyIuBMlkVpRgYeH";
		String sender_id="&sender_id="+"FSTSMS";
		message="&message="+URLEncoder.encode(message);
		String  variables_values="&variables_values="+otp;	
		String route="&route="+"otp";
		String numbers="&numbers="+mobile;
		
		String myUrl="https://www.fast2sms.com/dev/bulkV2?authorization="+api_key+sender_id+message+variables_values+route+numbers;
		
		URL url=new URL(myUrl);
		
		HttpURLConnection con=(HttpURLConnection)url.openConnection();
		
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("cache-control", "no-cache");
		
		int code=con.getResponseCode();
		
		StringBuffer responce=new StringBuffer();
		BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		while(true)
		{
			String line=br.readLine();
			if(line==null)
			{
				break;
			}
			responce.append(line);
		}
		
		System.out.println(code +"responce: "+ responce);
		return code;
		}
		catch (Exception e) {
			
			System.out.println(e.getMessage());
			return 0;
		}
		
		
	}
}