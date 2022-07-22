package com.fitness.app.service;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.app.dao.AttendanceDAO;
import com.fitness.app.entity.Rating;
import com.fitness.app.entity.UserAttendance;
import com.fitness.app.exception.DataNotFoundException;
import com.fitness.app.model.MarkUserAttModel;
import com.fitness.app.repository.AttendanceRepo;
import com.fitness.app.repository.RatingRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AttendanceService implements AttendanceDAO {

	private AttendanceRepo attendanceRepo;
	
	private RatingRepo ratingRepo;

	@Autowired
	public AttendanceService(RatingRepo ratingRepo2, AttendanceRepo attendanceRepo2) {
		this.ratingRepo = ratingRepo2;
		this.attendanceRepo = attendanceRepo2;
	}

	/**
	 * This function is used to mark user's attendance
	 * 
	 * @param userAttendance - Attendance details of the user
	 * @return - Attendance marked
	 * @throws DataNotFoundException
	 */
	public String markUsersAttendance(MarkUserAttModel userAttendance) {
		log.info("Attendance Service >> markUsersAttendance >> Initiated");
		try {
			List<String> users = userAttendance.getUsers();

			List<UserAttendance> allUser = attendanceRepo.findByVendorAndGym(userAttendance.getVendor(),
					userAttendance.getGym());

			List<String> allUsers = allUser.stream().map(UserAttendance::getEmail).collect(Collectors.toList());

			int att = 0;
			int nonatt = 0;

			for (String user : allUsers) {
				UserAttendance userAtt = attendanceRepo.findByEmailAndVendorAndGym(user, userAttendance.getVendor(),
						userAttendance.getGym());
				userAtt.setRating(calculateRating(user));
				List<Integer> attendanceList = userAtt.getAttendance();
				if (users.contains(user)) {

					if (attendanceList == null) {
						attendanceList = new ArrayList<>();
						attendanceList.add(1);
					} else {
						log.warn("Attendance Service >> markUsersAttendance >> List is null");
						attendanceList.add(1);
					}
					att++;
				} else {

					if (attendanceList == null) {
						attendanceList = new ArrayList<>();
						attendanceList.add(0);
					} else {
						log.warn("Attendance Service >> markUsersAttendance >> List is null");
						attendanceList.add(0);
					}
					nonatt++;
				}

				userAtt.setAttendance(attendanceList); // Marking Users Attendance
				attendanceRepo.save(userAtt);

			}
			log.info("Attendance Service >> markUsersAttendance >> ends");
			return "Marked total: " + att + " and non attendy:  " + nonatt;
		} catch (Exception e) {
			log.error("Attendance Service >> markUsersAttendance >> Exception thrown");
			throw new DataNotFoundException("Data Not Found");
		}
	}

	/**
	 * This function is used to fetch the user's performance
	 * 
	 * @param email - Email id of the user
	 * @param gym   - Gym id of the fitness center
	 * @return - List of integers of user performances
	 * @throws DataNotFoundException
	 */
	public List<Integer> userPerfomance(String email, String gym) {
		try {
			log.info("Attendance Service >> userPerfomance >> Initiated");
			UserAttendance user = attendanceRepo.findByEmailAndGym(email, gym);
			List<Integer> perfomance = new ArrayList<>();
			List<Integer> attendenceList = user.getAttendance();
			int s = attendenceList.size();
			int div = s / 25;
			int count = 0;
			int i = 0;
			int j = 25;
			for (int k = 0; k < div; k++) { // Calculating User Performance
				count = 0;
				while (i <= j) {
					if (attendenceList.get(i) == 1) {
						count++;
					}
					i++;
				}
				perfomance.add(count);
				j += 25;
			}
			count = 0;
			while (i < s) {
				if (attendenceList.get(i) == 1) {
					count++;
				}
				i++;
			}
			perfomance.add(count); // Adding User Performance
			log.info("Attendance Service >> userPerfomance >> Terminated");
			return perfomance;
		} catch (Exception e) {
			log.error("Attendance Service >> userPerfomance >> Exception thrown");
			throw new DataNotFoundException("Data Not Found");
		}
	}

	/**
	 * This function is used to calculate the rating of enthusiast,vendor or gym
	 * 
	 * @param target - vendor email, enthusiast email or gym id
	 * @return - Rating
	 */
	public Double calculateRating(String target) {
		log.info("Attendance Service >> calculateRating >> Initiated");
		List<Rating> ratings = ratingRepo.findByTarget(target);

		int n = 0;
		if (ratings != null) {
			n = ratings.size();
		}
		if (n == 0) {
			log.warn("Attendance Service >> calculateRating >> returning 0");
			return 0.0;
		} else {
			double rate = 0; // Calculating Rating of User
			for (Rating rating : ratings) {
				rate += rating.getRate();
			}
			rate = rate / n;
			rate = Math.round(rate * 100) / 100.0d;
			log.info("Attendance Service >> calculateRating >> Terminated");
			return rate % 6;
		}
	}
}
