package com.fitness.app.service;

import com.fitness.app.entity.RatingClass;
import com.fitness.app.entity.UserAttendanceClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.model.MarkUserAttModel;
import com.fitness.app.repository.AttendanceRepository;
import com.fitness.app.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    final private AttendanceRepository attendanceRepository;
    final private RatingRepository ratingRepository;
    @Override
    public String markUsersAttendance(MarkUserAttModel userAttendance) throws DataNotFoundException {
        try {
            List<String> users = userAttendance.getUsers();

            List<UserAttendanceClass> allUser =
                    attendanceRepository.findByVendorAndGym(userAttendance.getVendor(), userAttendance.getGym());
            if (allUser == null) {
                throw new DataNotFoundException("No User is present: ");
            }
            List<String> allUsers = allUser.stream().map(p -> p.getEmail()).collect(Collectors.toList());
            int att = 0, nonatt = 0;
            for (String user : allUsers) {
                UserAttendanceClass userAtt =
                        attendanceRepository.findByEmailAndVendorAndGym(user, userAttendance.getVendor(), userAttendance.getGym());
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
                attendanceRepository.save(userAtt);

            }
            return "Marked total: " + att + " and non attendy:  " + nonatt;
        } catch (DataNotFoundException e) {
            log.error("AttendanceService ::-> markUserAttendance :: Error found due to: {} ", e.getMessage());
            throw new DataNotFoundException("Error: " + e.getMessage());
        }
    }

    @Override
    public List<Integer> userPerformance(String email, String gym) throws DataNotFoundException {
        try {
            UserAttendanceClass user = attendanceRepository.findByEmailAndGym(email, gym);
            if (user != null) {
                List<Integer> attendenceList = user.getAttendance();
                List<Integer> perfomance = new ArrayList<>();
                if (attendenceList != null) {
                    int s = attendenceList.size();
                    int div = s / 25;
                    int count = 0;
                    int i = 0;
                    int j = 25;
                    for (int k = 0; k < div; k++) {
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
                    perfomance.add(count);
                }
                return perfomance;
            } else {
                throw new DataNotFoundException("No attendance Found");
            }
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException("Error: " + e.getMessage());
        }
    }

    @Override
    public Double calculateRating(String target) {
        List<RatingClass> ratingClasses = ratingRepository.findByTarget(target);
        int n = 0;
        if (ratingClasses != null) {
            n = ratingClasses.size();
        }
        if (n == 0) {
            return 0.0;
        } else {
            double rate = 0;
            for (RatingClass ratingClass : ratingClasses) {
                rate += ratingClass.getRate();
            }
            rate = rate / n;
            rate = Math.round(rate * 100) / 100.0d;
            return rate % 6;
        }
    }
}

