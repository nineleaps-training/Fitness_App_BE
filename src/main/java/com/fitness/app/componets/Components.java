package com.fitness.app.componets;

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

import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.GymAddressRepo;
import com.fitness.app.service.UserOrderService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.logging.Log_$logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserOrder;
import com.fitness.app.model.BookedGymModel;

@Component
@Slf4j
public class Components {

    private Random random= SecureRandom.getInstanceStrong();

	@Autowired
	private AddGymRepository gymRepo;

	@Autowired
	private GymAddressRepo gymAddressRepo;


	public Components() throws NoSuchAlgorithmException {
		log.info("There is error in Algorithm to generate random set of integers.");

	}

	public String otpBuilder()
    {
		int randomInt=this.random.nextInt(10000);

    	return String.format("%04d",randomInt);
    }
	
	public int  sendOtpMessage(String otp, String mobile )
	{
		try {

		String apiKey="vUSheoFsqykuK6T4P9YQMgEXpDrjC7NmR18Bz0OZlAGWd3tcJnjQftWidwxvqZSs1OyIuBMlkVpRgYeH";
		String senderId="&senderId="+"FSTSMS";
		String message="Hello : "+URLEncoder.encode("Hello");
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


		StringBuilder responce=new StringBuilder();
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


		log.info("Code: {}", code, " responce: {} ",responce);
		return code;
		}
		catch (Exception e) {

			log.info("Exception found in {}", e.getMessage());

			return 0;
		}

		
	}


	public BookedGymModel gymModelByStatus(List<UserOrder> orders, String filter)
	{
		List<UserOrder> activeOrder=orders.stream().filter(o->o.getBooked().equals(filter)).collect(Collectors.toList());

		BookedGymModel gymModel = new BookedGymModel();
		GymClass localGym=new GymClass();
		GymAddressClass addressGym=new GymAddressClass();

		for(UserOrder order:activeOrder)
		{
			Optional<GymClass> local_gym=gymRepo.findById(order.getGym());
			if(local_gym.isPresent())
			{
				localGym=local_gym.get();
			}
			Optional<GymAddressClass> address_Gym= gymAddressRepo.findById(order.getGym());
			if(address_Gym.isPresent())
			{
				addressGym=address_Gym.get();
			}
			LocalDate endDate=LocalDate.now();
			endDate=endDate.plusDays(calculateTotalTime(order.getSubscription()));
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


	 public int calculateTotalTime(String subs)
	{
		int time=0;
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
				time=0;
				break;
		}
		return time;
	}
	
	
	
	
}