package com.fitness.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.Rating;
import com.fitness.app.entity.UserAttendance;
import com.fitness.app.entity.UserOrder;
import com.fitness.app.model.MarkUserAttModel;
import com.fitness.app.model.UserPerfomanceModel;
import com.fitness.app.repository.AttendanceRepo;
import com.fitness.app.repository.RatingRepo;

@Service
public class AttendanceService {

	
	
	@Autowired
	private AttendanceRepo attendanceRepo;
	
	@Autowired
	private RatingRepo ratingRepo;
	
	@Autowired
	private UserOrderService userOrderService;
	
	
	public String markUsersAttendance(MarkUserAttModel userAttendance)throws Exception
	{
		try {
			
			List<String> users=userAttendance.getUsers();
		
			List<UserAttendance> allUser=
					attendanceRepo.findByVendorAndGym(userAttendance.getVendor(), userAttendance.getGym());
			
		    List<String> allUsers=allUser.stream().map(p->p.getEmail()).collect(Collectors.toList());
			
			
			List<String> notUserAtt=new ArrayList<>();
			if(allUser!=null)
			{
				for(String user:allUsers)
				{
					if(!users.contains(user))
					{
						notUserAtt.add(user);
					}
				}
			}
			
			if(notUserAtt!=null)
			{
				for(String user:notUserAtt)
				{
					UserAttendance notAtt=
							attendanceRepo.findByEmailAndVendorAndGym(user, userAttendance.getVendor(), userAttendance.getGym());
					
					
					if(notAtt!=null)
					{
						List<Integer> attlist=new ArrayList<>();
								attlist=notAtt.getAttendance();
						notAtt.setRating(calculateRating(user));
						   int n=attlist.size();
				           if(n>notAtt.getBooked())
				             {
				    	       return "End of Subcription for: " +notAtt;
				             }
				           int val=attlist.get(n-1);
					       attlist.add(val-1);
					     notAtt.setAttendance(attlist);
				    	 attendanceRepo.save(notAtt);
				    	 
					}
				}
			}
			for(String user:users)
			{
			
				UserAttendance userAtt=
						attendanceRepo.findByEmailAndVendorAndGym(user, userAttendance.getVendor(), userAttendance.getGym());
				
				
				if(userAtt==null)
				{
					List<Integer> attendanceList=new ArrayList<>();
			    	attendanceList.add(1);
			    	UserAttendance userAt=new UserAttendance();
			    	userAt.setEmail(user);
			    	userAt.setGym(userAttendance.getGym());
			    	userAt.setVendor(userAttendance.getVendor());
			    	userAt.setAttendance(attendanceList);
			    	attendanceRepo.save(userAt);
			    	return "Marked";
				}
				else
				{
					List<Integer> attlist=userAtt.getAttendance();
					userAtt.setRating(calculateRating(user));
					   int n=attlist.size();
			           if(n>userAtt.getBooked())
			             {
			    	       return "End of Subcription";
			             }
			           int val=attlist.get(n-1);
				       attlist.add(val+1);
				     userAtt.setAttendance(attlist);
			    	 attendanceRepo.save(userAtt);
			    	 return "Marked";
				}
				

			}
			return "Marked";
			
		} catch (Exception e) {
			throw new Exception(e.getMessage()+"Not Marked.");
		}
	}
	
	
	
	public List<Integer> userPerfomance(String email, String gym) throws Exception
	{
		try {
			UserAttendance user=attendanceRepo.findByEmailAndGym(email, gym);
			if(user!=null)
			{	return user.getAttendance();}
			List<Integer>l=new ArrayList<>();
			return l;
		} catch (Exception e) {
			throw new Exception(e.getMessage()+"/t couse: "+ e.getCause());
		}
	}
	
	public Double calculateRating(String target)
	{
		 List<Rating> ratings = ratingRepo.findByTarget(target);
		 
	        int n=0;
	        if(ratings!=null) {
	        	n=ratings.size();
	        }
	        if(n==0)
	        {
	            return 0.0;
	        }
	        else
	        {
	            double rate=0;
	            for (Rating rating : ratings) {
	                rate+=rating.getRating();
	            }
	            rate=rate/n;
	            rate=Math.round(rate* 100) / 100.0d;
	            return rate%6;
	        }
	}
}
