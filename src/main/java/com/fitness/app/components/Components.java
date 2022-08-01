package com.fitness.app.components;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Component;

import static com.fitness.app.components.Constants.*;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@EnableRetry
public class Components {

	SecureRandom secureRandom = new SecureRandom();

	/**
	 * 
	 * This function is used to generate a 4-digit OTP
	 * 
	 * @return - 4 digit OTP generated randomly
	 */
	public String otpBuilder() {
		return String.format("%04d", secureRandom.nextInt(10000));
	}

	/**
	 * This function is used to send OTP message to the user
	 * 
	 * @param message - Message for the user
	 * @param otp     - OTP generated for user
	 * @param mobile  - Mobile number of the user
	 * @return Response code 200 if ok
	 */
	@Cacheable(value = "code")
	public int sendOtpMessage(String message, String otp, String mobile) {
		log.info("Components >> sendOtpMessage >> Message: {} OTP: {} Mobile: {}", message, otp, mobile);
		try {
			String apiKey = System.getenv(FAST2SMS_API_KEY);
			String senderId = SENDER_ID + FSTSMS;
			message = MESSAGE + URLEncoder.encode(message, java.nio.charset.StandardCharsets.UTF_8.toString());
			String variablesValues = VARIABLES_VALUES + otp;
			String route = ROUTE + OTP;
			String numbers = NUMBERS + mobile;

			String myUrl = FAST2SMS_URL + apiKey + senderId + message
					+ variablesValues + route + numbers;

			URL url = new URL(myUrl);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod(GET);
			con.setRequestProperty(USER_AGENT, MOZILLA);
			con.setRequestProperty(CACHE_CONTROL, NO_CACHE);

			int code = con.getResponseCode();

			StringBuilder response = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				response.append(line);
			}
			log.info("Components >> sendOtpMessage >> Response Code: {}", code, " Response: {} ", response);
			return code;
		} catch (Exception e) {
			log.error("Components >> sendOtpMessage >> Exception {}", e.getMessage());
			return 0;
		}
	}
}