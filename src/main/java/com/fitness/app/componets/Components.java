package com.fitness.app.componets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Components {

    @Autowired
    Environment environment;
    private final SecureRandom rand = new SecureRandom();

    public String otpBuilder() {

        return String.format("%04d", rand.nextInt(10000));
    }

    public int sendOtpMessage(String message, String otp, String mobile) {
        try {

            String apiKey = environment.getProperty("apiKey");
            String senderId = "&senderId=" + "FSTSMS";
            message = "&message=" + URLEncoder.encode(message, java.nio.charset.StandardCharsets.UTF_8.toString());
            String variablesValues = "&variablesValues=" + otp;
            String route = "&route=" + "otp";
            String numbers = "&numbers=" + mobile;

            String myUrl = "https://www.fast2sms.com/dev/bulkV2?authorization=" + apiKey + senderId + message + variablesValues + route + numbers;

            URL url = new URL(myUrl);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("cache-control", "no-cache");

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
            log.info("code: {}", code, "response : {}", response);
            return code;
        } catch (Exception e) {

            log.info(e.getMessage());
            return 0;
        }

    }
}