package com.register.app.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import com.fitness.app.entity.AdminPayClass;
import com.fitness.app.dto.request.AdminPayModel;

import com.fitness.app.repository.AdminPayRepository;
import com.fitness.app.service.AdminDaoImpl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.controller.AdminController;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.repository.AddGymRepository;

import com.fitness.app.repository.AdminRepository;

import com.fitness.app.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
class AdminControllerTest {
	
	private MockMvc mockMvc;
	
	
	ObjectMapper objectMapper=new ObjectMapper();
	com.fasterxml.jackson.databind.ObjectWriter objectWriter= objectMapper.writer();
     
	
	
	
	@Mock

	private AdminRepository adminRepository;

	@Mock
	private UserRepository userRepository;

	@Mock

	private AdminPayRepository payRepo;

	@Mock
	private AddGymRepository gymRepository;
	
	@InjectMocks
	private AdminController adminController;
	
	
	
	@BeforeEach
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		this.mockMvc=MockMvcBuilders.standaloneSetup(adminController).build();



	}
	
	
	UserClass user=new UserClass();

	UserClass USER1=new UserClass("rahul01@nineleaps.com", "Rahul Kumar",
			"7651977515","Rahul@123","USER", true, true, true );
	UserClass USER2=new UserClass("manish.kumar@nineleaps.com", "Manish Kumar",
			"7651977515","Manish@123","VENDOR", true, true, true );

	AdminPayClass VENDOR_PAY=new AdminPayClass("id", "orderId", "manish.kumar@nineleaps.com",
			4000, "Due","paymentID","reciept", LocalDate.now(), LocalTime.now() );


	AdminPayClass VENDOR_PAY_COM=new AdminPayClass("id", "orderId", "manish.kumar@nineleaps.com",
			2000, "Completed","paymentID","reciept", LocalDate.now(), LocalTime.now() );


	List<String> workout=new ArrayList<>();


	long contact=7651977515L;
	GymClass FITNESS1=new GymClass(
			"GM1",
			"manishsingh@gmail.com",
			"Fitness Center",
			workout,
			contact,
			4.0,
			100
	);

	@Test
	 void getAllVendors() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1//admin/private/get-all-vendors/0/2")
						.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	 void allUserlist() throws  Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/private/get-all-users/0/2")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	 void allGyms() throws  Exception
	{

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/private/get-all-gyms/0/2")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}


	//
	@Test
	 void allGymsOfVendor() throws  Exception
	{

		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/private/get-all-gyms-by-email/manishsingh@gmail.com ")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

	}
	
	///vendor-payment/{vendor}

	@Mock

	private AdminDaoImpl adminServiceImpl;


	@Test
	 void vendorPayment() throws  Exception
	{

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/private/vendor-payment/manish.kumar@nineleaps.com")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}


	AdminPayModel VENDOR_DUE=new AdminPayModel("manish.kumar@nineleaps.com", 4000);
	///get-data-pay
	@Test
	 void amountToPay() throws Exception
	{

		String requestData= objectMapper.writeValueAsString(VENDOR_DUE);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/private/get-data-pay")
				.contentType(MediaType.APPLICATION_JSON).content(requestData))
				.andExpect(status().isOk());
	}

	///update-vendor-payment

	@Test
	 void updatePayment() throws Exception
	{

		HashMap<String, String> data=new HashMap<>();
		data.put("order_id", "orderId");
		data.put("payment_id", "paymentId");
		data.put("status", "Completed");
		String requestData= objectMapper.writeValueAsString(data);
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/admin/private/update-vendor-payment")
				.contentType(MediaType.APPLICATION_JSON)
						.content(requestData))
				.andExpect(status().isOk());




	}

	///paid-history/{vendor}
	@Test
	 void paymentHistoryOfVendor()throws Exception
	{


	   mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/private/paid-history/manish.kumar@nineleaps.com")
			   .contentType(MediaType.APPLICATION_JSON))
			   .andExpect(status().isOk())
	   ;
	}


	///all-numbers

	@Test
	 void getAllNumbers() throws Exception
	{
		MvcResult result1= mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/public/all-numbers")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();


	}
}
