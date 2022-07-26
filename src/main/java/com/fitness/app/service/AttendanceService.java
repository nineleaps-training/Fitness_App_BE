package com.fitness.app.service;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import com.fitness.app.dao.AttendanceDao;
import com.fitness.app.entity.Rating;
import com.fitness.app.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import com.fitness.app.entity.UserAttendance;

import com.fitness.app.model.MarkUserAttModel;

import com.fitness.app.repository.AttendanceRepository;
import com.fitness.app.repository.RatingRepository;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AttendanceService implements AttendanceDao {

    private AttendanceRepository attendanceRepository;
    private RatingRepository ratingRepository;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository, RatingRepository ratingRepository) {
        this.attendanceRepository = attendanceRepository;
        this.ratingRepository = ratingRepository;
    }

    public String markUsersAttendance(MarkUserAttModel userAttendance) {
        log.info("AttendanceService >> markUsersAttendance >> Initiated");
        try {
            log.info("AttendanceService >> markUsersAttendance >> Try Block");
            List<String> users = userAttendance.getUsers();

            List<UserAttendance> allUser =
                    attendanceRepository.findByVendorAndGym(userAttendance.getVendor(), userAttendance.getGym());

            List<String> allUsers = allUser.stream().map(p -> p.getEmail()).collect(Collectors.toList());

            int att = 0;
            int nonatt = 0;

            for (String user : allUsers) {
                UserAttendance userAtt =
                        attendanceRepository.findByEmailAndVendorAndGym(user, userAttendance.getVendor(), userAttendance.getGym());
                userAtt.setRating(calculateRating(user));
                List<Integer> attendanceList = userAtt.getAttendance();
                if (users.contains(user)) {

                    if (attendanceList == null) {
                        log.warn("AttendanceService >> markUsersAttendance >> AttendanceList is null");
                        attendanceList = new ArrayList<>();
                        attendanceList.add(1);
                    } else {
                        attendanceList.add(1);
                    }
                    att++;
                } else {

                    if (attendanceList == null) {
                        log.warn("AttendanceService >> markUsersAttendance >> UserList is null");
                        attendanceList = new ArrayList<>();
                        attendanceList.add(0);
                    } else {
                        attendanceList.add(0);
                    }
                    nonatt++;
                }

                userAtt.setAttendance(attendanceList);
                attendanceRepository.save(userAtt);

            }
            log.info("AttendanceService >> markUsersAttendance >> Ends");
            return "Marked total: " + att + " and non attendy:  " + nonatt;

        } catch (Exception e) {
            log.error("AttendanceService >> markUsersAttendance >> Exception Thrown");
            throw new DataNotFoundException("Data Not Found!");
        }


    }


    public List<Integer> userPerformance(String email, String gym) {
        log.info("AttendanceService >> userPerformance >> Initiated");

        try {
            log.info("AttendanceService >> userPerformance >> Try Block");
            UserAttendance user = attendanceRepository.findByEmailAndGym(email, gym);

            if (user != null) {
                log.info("AttendanceService >> userPerformance >> User is not null");
                List<Integer> performance = new ArrayList<>();
                List<Integer> attendanceList = user.getAttendance();

                if (attendanceList != null) {
                    int size = attendanceList.size();
                    int divide = size / 25;
                    int count = 0;
                    int i = 0;
                    int j = 25;
                    for (int k = 0; k < divide; k++) {
                        count = 0;

                        while (i <= j) {
                            if (attendanceList.get(i) == 1) {
                                count++;
                            }
                            i++;
                        }
                        performance.add(count);
                        j += 25;
                    }
                    count = 0;
                    while (i < size) {
                        if (attendanceList.get(i) == 1) {
                            count++;
                        }
                        i++;
                    }
                    performance.add(count);
                }
                log.warn("AttendanceService >> userPerformance >> AttendanceList is null");
                return performance;
            }
            log.info("AttendanceService >> userPerformance >> Ends");
            return new ArrayList<>();
        } catch (Exception e) {
            log.error("AttendanceService >> userPerformance >> Exception Thrown");
                throw new DataNotFoundException("Data Not Found!");
        }
    }


    public Double calculateRating(String target) {
        log.info("AttendanceService >> calculateRating >> Initiated");

        List<Rating> ratings = ratingRepository.findByTarget(target);

        int n = 0;
        if (ratings != null) {
            n = ratings.size();
        }
        if (n == 0) {
            log.warn("AttendanceService >> calculateRating >> returns 0");
            return 0.0;
        } else {
            double rate = 0;
            for (Rating rating : ratings) {
                rate += rating.getRate();
            }
            rate = rate / n;
            rate = Math.round(rate * 100) / 100.0d;
            log.info("AttendanceService >> calculateRating >> Ends");
            return rate % 6;
        }
    }
}

