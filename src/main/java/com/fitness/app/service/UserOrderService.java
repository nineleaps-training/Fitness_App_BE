package com.fitness.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserAttendance;
import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.UserOrder;
import com.fitness.app.entity.VendorPayment;
import com.fitness.app.model.BookedGymModel;
import com.fitness.app.model.GymRepresnt;
import com.fitness.app.model.UserPerfomanceModel;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.AttendanceRepo;
import com.fitness.app.repository.GymAddressRepo;
import com.fitness.app.repository.UserOrderRepo;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.repository.VendorPayRepo;

@Service
public class UserOrderService {

	@Autowired
	private UserOrderRepo userOrderRepo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AttendanceRepo attendanceRepo;
	
	@Autowired
	private AddGymRepository gymRepo;
	
	
	@Autowired
	private VendorPayRepo vendorOrderRepo;
	
	//creating order
	public void orderNow(UserOrder userOrder)
	{	
		 userOrderRepo.save(userOrder);
	}
	
	
	//updating order
	
	public UserOrder updateOrder(Map<String, String> data)
	{
		 LocalDate date=LocalDate.now();
		 LocalTime time=LocalTime.now();
	     UserOrder order=userOrderRepo.findById(data.get("order_id")).get();
	     order.setPaymentId(data.get("payment_id"));
	     order.setStatus(data.get("status"));
	     order.setDate(date);
	     order.setTime(time);    
	     
	     
	     
	     //update booked...
	     int booked=30;
	     if(order.getSubscription().equals("monthly"))
	     {
	    	 booked=30;
	     }
	     else if(order.getSubscription().equals("quaterly"))
	     {
	    	 booked=90;
	     }
	     else if(order.getSubscription().equals("half"))
	     {
	    	 booked=180;
	     }
	     else
	     {
	    	 booked=360;
	     }
	     
	     UserAttendance attendance=new UserAttendance();
	     String vendor= gymRepo.findById(order.getGym()).get().getEmail();
	     
	     attendance.setEmail(order.getEmail());
	     attendance.setGym(order.getGym());
	     attendance.setVendor(vendor);
	     attendance.setBooked(booked);
	     attendance.setAttended(0);
	     attendance.setAttendance(null);
	     attendance.setRating(0.0);
	     attendanceRepo.save(attendance);
	     
	     
	     VendorPayment vendorOrder=new VendorPayment();
	     vendorOrder.setVendor(vendor);
	     vendorOrder.setUser(order.getEmail());
	     vendorOrder.setGym(order.getGym());
	     vendorOrder.setAmount(order.getAmount());
	     vendorOrder.setStatus("Due");
	     vendorOrder.setDate(date);
	     vendorOrder.setTime(time);
	     
	     vendorOrderRepo.save(vendorOrder);
	     
	     return userOrderRepo.save(order);
	}
	
	//pending order list of user
	
	
	public List<UserOrder> pendingListOrder(String email)
	{
		List<UserOrder> orders=userOrderRepo.findByEmail(email);
		orders=orders.stream().filter(o->o.getStatus().equals("created")).collect(Collectors.toList());
		for(UserOrder eachOrder:orders)
		{
			LocalDate date= eachOrder.getDate();
			date=date.plusDays(5);
			LocalDate currenDate=LocalDate.now();
			int ans=currenDate.compareTo(date);
			if(ans<0)
			{
				userOrderRepo.delete(eachOrder);
			}
		}
		return orders;
	}
	public List<UserOrder> OrderListOrder(String email)
	{
		List<UserOrder> orders=userOrderRepo.findByEmail(email);
		orders=orders.stream().filter(o->o.getStatus().equals("Completed")).collect(Collectors.toList());
		return orders;
	}
	
	public Set<UserPerfomanceModel> allMyUser(String gymId)
	{
		List<UserOrder> orders=userOrderRepo.findByGym(gymId);
		orders=orders.stream().filter(o->o.getStatus().equals("Completed")).collect(Collectors.toList());
		Set<UserPerfomanceModel> users=new HashSet<>();
		String vendor=gymRepo.findById(gymId).get().getEmail();
		for(UserOrder order:orders)
		{
			UserAttendance newAtt=attendanceRepo.findByEmailAndVendor(order.getEmail(), vendor);
			UserPerfomanceModel user=new UserPerfomanceModel();
			user.setName(userRepository.findByEmail(order.getEmail()).getFullName());
			user.setEmail(order.getEmail());
			user.setGym(gymId);
			user.setVendor(vendor);
			user.setAttendance(newAtt.getAttendance());
			user.setRating(newAtt.getRating());
		   users.add(user);
		}
		return users;
	}
	
	@Autowired
	private GymAddressRepo gymAddressRepo;
	
	public  Set<BookedGymModel> bookedGym(String email)
	{
	  List<UserOrder> orders=userOrderRepo.findByEmail(email);
	  orders=orders.stream().filter(o->o.getStatus().equals("Completed")).collect(Collectors.toList());
	  
	 Set<BookedGymModel> gyms=new HashSet<>();
	 for(UserOrder order: orders)
	 {
		 GymClass gym=gymRepo.findById(order.getGym()).get();
		 BookedGymModel bookedGym=new BookedGymModel();
		 bookedGym.setId(gym.getId());
		 bookedGym.setGymName(gym.getName());
		 bookedGym.setVendor(gym.getEmail());
		 bookedGym.setService(order.getServices());
		 bookedGym.setEndDate(order.getDate().plusDays(30));
		 bookedGym.setAddress(gymAddressRepo.findById(gym.getId()).get());
		 bookedGym.setContact(gym.getContact().toString());
		 bookedGym.setRating(gym.getRating());
		 
		 gyms.add(bookedGym);
	 }
	 
	 return gyms;
	}
	
	
	
}
