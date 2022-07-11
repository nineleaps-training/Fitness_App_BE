package com.fitness.app.service;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import com.fitness.app.model.RatingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.app.entity.UserAttendance;

import com.fitness.app.model.MarkUserAttModel;

import com.fitness.app.repository.AttendanceRepo;
import com.fitness.app.repository.RatingRepo;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepo attendanceRepo;

    @Autowired
    private RatingRepo ratingRepo;

    public String markUsersAttendance(MarkUserAttModel userAttendance) throws NullPointerException {
        try {
            List<String> users = userAttendance.getUsers();

            List<UserAttendance> allUser =
                    attendanceRepo.findByVendorAndGym(userAttendance.getVendor(), userAttendance.getGym());

            List<String> allUsers = allUser.stream().map(p -> p.getEmail()).collect(Collectors.toList());

            int att = 0;
            int nonatt = 0;

            for (String user : allUsers) {
                UserAttendance userAtt =
                        attendanceRepo.findByEmailAndVendorAndGym(user, userAttendance.getVendor(), userAttendance.getGym());
                userAtt.setRating(calculateRating(user));
                List<Integer> attendanceList = userAtt.getAttendance();
                if (users.contains(user)) {

                    if (attendanceList == null) {
                        attendanceList = new ArrayList<>();
                        attendanceList.add(1);
                    } else {
                        attendanceList.add(1);
                    }
                    att++;
                } else {

                    if (attendanceList == null) {
                        attendanceList = new ArrayList<>();
                        attendanceList.add(0);
                    } else {
                        attendanceList.add(0);
                    }
                    nonatt++;
                }

                userAtt.setAttendance(attendanceList);
                attendanceRepo.save(userAtt);

            }

            return "Marked total: " + att + " and non attendy:  " + nonatt;

        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }


    }


    public List<Integer> userPerformance(String email, String gym) throws IndexOutOfBoundsException {
        try {
            UserAttendance user = attendanceRepo.findByEmailAndGym(email, gym);

            if (user != null) {
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
                return performance;
            }
            return new ArrayList<>();
        } catch (Exception e) {
            throw new IndexOutOfBoundsException(e.getMessage() + "/t cause: " + e.getCause());
        }
    }


    public Double calculateRating(String target) {
        List<RatingModel> ratings = ratingRepo.findByTarget(target);

        int n = 0;
        if (ratings != null) {
            n = ratings.size();
        }
        if (n == 0) {
            return 0.0;
        } else {
            double rate = 0;
            for (RatingModel rating : ratings) {
                rate += rating.getRate();
            }
            rate = rate / n;
            rate = Math.round(rate * 100) / 100.0d;
            return rate % 6;
        }
    }
}

