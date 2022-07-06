package com.register.app.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import com.fitness.app.entity.AdminPay;
import com.fitness.app.model.AdminPayModel;
import com.fitness.app.repository.AdminPayRepo;
import com.fitness.app.service.AdminService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import com.fitness.app.repository.AdminRepo;
import com.fitness.app.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
class AdminControllerTest {
	
	private MockMvc mockMvc;
	
	
	ObjectMapper objectMapper=new ObjectMapper();
	com.fasterxml.jackson.databind.ObjectWriter objectWriter= objectMapper.writer();
     
	
	
	
	@Mock
	private AdminRepo adminRepo;
	
	@Mock
	private UserRepository userRepository;

	@Mock
	private AdminPayRepo payRepo;
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

	AdminPay VENDOR_PAY=new AdminPay("id", "orderId", "manish.kumar@nineleaps.com",
			4000, "Due","paymentID","reciept", LocalDate.now(), LocalTime.now() );


	AdminPay VENDOR_PAY_COM=new AdminPay("id", "orderId", "manish.kumar@nineleaps.com",
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


		List<UserClass> users=new ArrayList<>();
		users.add(USER2);
		users.add(USER1);
		Mockito.when(userRepository.findAll()).thenReturn(users);

		mockMvc.perform(MockMvcRequestBuilders.get("/get-all-vendors")
						.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].role", is("VENDOR")));
		
		
	}

	@Test
	 void allUserlist() throws  Exception
	{

		List<UserClass> users=new ArrayList<>();
		users.add(USER1);
		users.add(USER2);
		Mockito.when(userRepository.findAll()).thenReturn(users);

		mockMvc.perform(MockMvcRequestBuilders.get("/get-all-users")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].role", is("USER")));
	}


	



	@Test
	 void allGyms() throws  Exception
	{
		workout.add("Dance");
		workout.add("Zumba");
		workout.add("gym");
		FITNESS1.setWorkout(workout);
        List<GymClass> gyms=new ArrayList<>();
		gyms.add(FITNESS1);
		Mockito.when(gymRepository.findAll()).thenReturn(gyms);

		mockMvc.perform(MockMvcRequestBuilders.get("/get-all-gyms")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].email", is("manishsingh@gmail.com")));
	}


	//
	@Test
	 void allGymsOfVendor() throws  Exception
	{
		workout.add("Dance");
		workout.add("Zumba");
		workout.add("gym");
		FITNESS1.setWorkout(workout);
		List<GymClass> gyms=new ArrayList<>();
		gyms.add(FITNESS1);
		Mockito.when(gymRepository.findByEmail("manishsingh@gmail.com")).thenReturn(gyms);


		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/get-all-gyms-by-email/manishsingh@gmail.com ")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();


		System.out.println("data is: "+result.getResponse().getContentAsString());
	}
	
	///vendor-payment/{vendor}

	@Mock
	private AdminService adminService;



	@Test
	 void vendorPayment() throws  Exception
	{
		Mockito.when(adminService.vendorPayment("manish.kumar@nineleaps.com")).thenReturn(VENDOR_PAY);

		mockMvc.perform(MockMvcRequestBuilders.get("/vendor-payment/manish.kumar@nineleaps.com")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.status", is("Due")));
	}


	AdminPayModel VENDOR_DUE=new AdminPayModel("manish.kumar@nineleaps.com", 4000);
	///get-data-pay
	@Test
	 void amountToPay() throws Exception
	{

		Mockito.when(adminService.getDataPay(VENDOR_DUE)).thenReturn(VENDOR_PAY);

		mockMvc.perform(MockMvcRequestBuilders.get("/get-data-pay")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
//				.andExpect(jsonPath("$", notNullValue()))
//				.andExpect(jsonPath("$.amount", is(4000)));

	}

	///update-vendor-payment

	@Test
	 void updatePayment() throws Exception
	{

		HashMap<String, String> data=new HashMap<>();
		data.put("order_id", "orderId");
		data.put("payment_id", "paymentId");
		data.put("status", "Completed");

		Mockito.when(adminService.updatePayment(data)).thenReturn(VENDOR_PAY_COM);
		String requestData= objectMapper.writeValueAsString(data);

		mockMvc.perform(MockMvcRequestBuilders.put("/update-vendor-payment")
				.contentType(MediaType.APPLICATION_JSON)
						.content(requestData))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.status", is("Completed")));



	}

	///paid-history/{vendor}
	@Test
	 void paymentHistoryOfVendor()throws Exception
	{

		List<AdminPay> allHistory=new ArrayList<>();
		allHistory.add(VENDOR_PAY_COM);
       Mockito.when(adminService.paidHistroyVendor("manish.kumar@nineleaps.com")).thenReturn(allHistory);

	   mockMvc.perform(MockMvcRequestBuilders.get("/paid-history/manish.kumar@nineleaps.com")
			   .contentType(MediaType.APPLICATION_JSON))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$", notNullValue()))
			   .andExpect(jsonPath("$[0].amount", is(2000)))
	   ;
	}


	///all-numbers

	@Test
	 void getAllNumbers() throws Exception
	{

		List<UserClass> users=new ArrayList<>();
		users.add(USER1);
		users.add(USER2);
		List<GymClass> gyms=new ArrayList<>();
		gyms.add(FITNESS1);


		Mockito.when(userRepository.findAll()).thenReturn(users);
		Mockito.when(gymRepository.findAll()).thenReturn(gyms);





		String restAns="";
		MvcResult result1= mockMvc.perform(MockMvcRequestBuilders.get("/all-numbers")
				.contentType(MediaType.APPLICATION_JSON)
						.content(objectWriter.writeValueAsString(restAns)))
				.andExpect(status().isOk())
				.andReturn();

		String s=result1.getResponse().getContentAsString();
		Assertions.assertEquals('1', s.charAt(2));

	}
}
