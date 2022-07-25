package com.fitness.app.components;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@EnableRetry
public class Components {

	SecureRandom secureRandom = new SecureRandom();

	@Autowired
	Environment environment;

	/**
	 * 
	 * @return 4 digit OTP generated randomly
	 */
	public String otpBuilder() {
		return String.format("%04d", secureRandom.nextInt(10000));
	}

	/**
	 * 
	 * @param message
	 * @param otp
	 * @param mobile
	 * @return Response code 200 if ok
	 */
	public int sendOtpMessage(String message, String otp, String mobile) {
		log.info("Components >> sendOtpMessage >> Message: {} OTP: {} Mobile: {}", message, otp, mobile);
		try {
			String apiKey = environment.getProperty("fast2SmsApiKey");
			String senderId = "&sender_id=" + "FSTSMS";
			message = "&message=" + URLEncoder.encode(message, java.nio.charset.StandardCharsets.UTF_8.toString());
			String variablesValues = "&variables_values=" + otp;
			String route = "&route=" + "otp";
			String numbers = "&numbers=" + mobile;

			String myUrl = "https://www.fast2sms.com/dev/bulkV2?authorization=" + apiKey + senderId + message
					+ variablesValues + route + numbers;

			URL url = new URL(myUrl);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("cache-control", "no-cache");

			int code = con.getResponseCode();

			StringBuilder responce = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				responce.append(line);
			}
			log.info("Components >> sendOtpMessage >> Response Code: {}", code, " Responce: {} ", responce);
			return code;
		} catch (Exception e) {
			log.error("Components >> sendOtpMessage >> Exception {}", e.getMessage());
			return 0;
		}
	}
}