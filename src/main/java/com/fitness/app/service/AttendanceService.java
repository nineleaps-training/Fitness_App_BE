package com.fitness.app.service;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fitness.app.entity.UserAttendance;
import com.fitness.app.exception.DataNotFoundException;
import com.fitness.app.model.MarkUserAttModel;
import com.fitness.app.model.RatingRequestModel;
import com.fitness.app.repository.AttendanceRepo;
import com.fitness.app.repository.RatingRepo;

@Service
public class AttendanceService {

	@Autowired
	private AttendanceRepo attendanceRepo;
	
	@Autowired
	private RatingRepo ratingRepo;
	
	public AttendanceService(RatingRepo ratingRepo2, AttendanceRepo attendanceRepo2) {
		this.ratingRepo=ratingRepo2;
		this.attendanceRepo=attendanceRepo2;
	}

	public String markUsersAttendance(MarkUserAttModel userAttendance)
	{
		try
		{
			List<String> users=userAttendance.getUsers();
			
			List<UserAttendance> allUser = attendanceRepo.findByVendorAndGym(userAttendance.getVendor(), userAttendance.getGym());
			
			List<String> allUsers=allUser.stream().map(UserAttendance::getEmail).collect(Collectors.toList());

			int att=0;
			int nonatt=0;
			
			for(String user:allUsers)
			{
				UserAttendance userAtt=attendanceRepo.findByEmailAndVendorAndGym(user, userAttendance.getVendor(), userAttendance.getGym());
				userAtt.setRating(calculateRating(user));
				List<Integer> attendanceList= userAtt.getAttendance();
				if(users.contains(user))
				{
					
					if(attendanceList==null) 
					{
						attendanceList =new ArrayList<>(); 
						attendanceList.add(1);
					}
					else 
					{
						attendanceList.add(1);
					}
					att++;
				}
				else
				{
					
					if(attendanceList==null) 
					{
						attendanceList =new ArrayList<>(); 
						attendanceList.add(0);
					}
					else 
					{
						attendanceList.add(0);
					}
					nonatt++;
				}
			
				userAtt.setAttendance(attendanceList);
				attendanceRepo.save(userAtt);
				
			}
			return "Marked total: " + att+ " and non attendy:  "+ nonatt;
		}
		catch(Exception e)
		{
			throw new DataNotFoundException("Data Not Found");
		}
	}

	public List<Integer> userPerfomance(String email, String gym)
	{
		try
		{
			UserAttendance user=attendanceRepo.findByEmailAndGym(email, gym);
			List<Integer> perfomance=new ArrayList<>();
			List<Integer> attendenceList=user.getAttendance();
			int s=attendenceList.size();
			int div=s/25;
			int count=0;
			int i=0;
			int j=25;
			for(int k=0 ;k<div;k++)
			{
				count=0;
				while(i<=j)
				{
					if(attendenceList.get(i)==1) {count++;}
					i++;
				}
				perfomance.add(count);
				j+=25;
				}
				count=0;
				while(i<s)
				{
			    	if(attendenceList.get(i)==1) {count++;}
					i++;
				}
				perfomance.add(count);			
				return perfomance;
		}
		catch(Exception e)
		{
			throw new DataNotFoundException("Data Not Found");
		}
	}
	
	public Double calculateRating(String target)
	{
		 List<RatingRequestModel> ratings = ratingRepo.findByTarget(target);
		 
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
	            for (RatingRequestModel rating : ratings) {
	                rate+=rating.getRating();
	            }
	            rate=rate/n;
	            rate=Math.round(rate* 100) / 100.0d;
	            return rate%6;
	        }
	}
}

