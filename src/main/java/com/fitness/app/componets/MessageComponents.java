package com.fitness.app.componets;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserOrderClass;
import com.fitness.app.model.BookedGymModel;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.GymAddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MessageComponents {

    private Random random = SecureRandom.getInstanceStrong();

    @Autowired
    private AddGymRepository gymRepo;


    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private GymAddressRepository gymAddressRepository;


    public MessageComponents() throws NoSuchAlgorithmException {
        log.info("There is error in Algorithm to generate random set of integers.");

    }

    public String otpBuilder() {
        int randomInt = this.random.nextInt(10000);
        return String.format("%04d", randomInt);
    }


    public int sendMail(String toEmail, String body, String subject) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("fitness.app.nineleaps@gmail.com");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(message);
            return 200;

        } catch (Exception e) {
            log.error("MessageComponents >> sendmail >> Error: {}", e.getMessage());
        }
        return 300;

    }

    @Value("${apiKey}")
    private String apikey;

    public int sendOtpMessage(String otp, String mobile) {
        try {

            log.info("Creating URL and combining data:");
            String senderId = "&senderId=" + "FSTSMS";
            String message = "Hello : " + URLEncoder.encode("Hello");
            String variablesValues = "&variablesValues=" + otp;
            String route = "&route=" + "otp";
            String numbers = "&numbers=" + mobile;

            log.info("Making Request for send message:  ");
            String myUrl = "https://www.fast2sms.com/dev/bulkV2?authorization=" + apikey + senderId + message + variablesValues + route + numbers;
            URL url = new URL(myUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("cache-control", "no-cache");
            int code = con.getResponseCode();

            log.info("Reading the message api response and Making response decision :  ");
            StringBuilder response = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                response.append(line);
            }
            log.info("MessageComponents >> sendOTPMessage >> Done the message process:  ");
            return code;
        } catch (Exception e) {

            log.error("MessageComponents >> sendOTPMessage >> Error found due to:  {}", e.getMessage());

            return 0;
        }


    }


    public BookedGymModel gymModelByStatus(List<UserOrderClass> orders, String filter) {
        List<UserOrderClass> activeOrder = orders.stream().filter(o -> o.getBooked().equals(filter)).collect(Collectors.toList());

        BookedGymModel gymModel = new BookedGymModel();
        GymClass localGym = new GymClass();
        GymAddressClass addressGym = new GymAddressClass();

        for (UserOrderClass order : activeOrder) {
            Optional<GymClass> localGym1 = gymRepo.findById(order.getGym());
            if (localGym1.isPresent()) {
                localGym = localGym1.get();
            }
            Optional<GymAddressClass> addressGym1 = gymAddressRepository.findById(order.getGym());
            if (addressGym1.isPresent()) {
                addressGym = addressGym1.get();
            }
            LocalDate endDate = LocalDate.now();
            endDate = endDate.plusDays(calculateTotalTime(order.getSubscription()));
            gymModel.setId(order.getGym());
            gymModel.setGymName(localGym.getName());
            gymModel.setService(order.getServices());
            gymModel.setSlot(order.getSlot());
            gymModel.setRating(localGym.getRating());
            gymModel.setEndDate(endDate);
            gymModel.setAddress(addressGym);
            gymModel.setContact(localGym.getContact());
            gymModel.setRating(localGym.getRating());
        }


        return gymModel;
    }


    public int calculateTotalTime(String subs) {
        int time = 0;
        switch (subs) {
            case "Monthly":
                time += 25;
                break;
            case "Quaterly":
                time += 75;
                break;
            case "Half":
                time += 150;
                break;
            case "Yearly":
                time += 300;
                break;
            default:
                time = 0;
                break;
        }
        return time;
    }


}