package com.fitness.app.service;

import com.fitness.app.dto.request.MarkUserAttModel;
import com.fitness.app.entity.RatingClass;
import com.fitness.app.entity.UserAttendanceClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.repository.AttendanceRepository;
import com.fitness.app.repository.RatingRepository;
import com.fitness.app.service.dao.AttendanceDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Component
public class AttendanceDaoImpl implements AttendanceDao {

    final private AttendanceRepository attendanceRepository;
    final private RatingRepository ratingRepository;

    @Override
    public String markUsersAttendance(MarkUserAttModel userAttendance) throws DataNotFoundException {
        try {
            List<String> users = userAttendance.getUsers();

            List<UserAttendanceClass> allUser =
                    attendanceRepository.findByVendorAndGym(userAttendance.getVendor(), userAttendance.getGym());
            if (allUser == null || allUser.size() < 0) {
                throw new DataNotFoundException("No User is present in list: ");
            }
            List<String> allUsers = allUser.stream().map(p -> p.getEmail()).collect(Collectors.toList());
            int att = 0, nonAtt = 0;
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
                    nonAtt++;
                }

                userAtt.setAttendance(attendanceList);
                attendanceRepository.save(userAtt);

            }
            return "Marked total: " + att + " and non attendy:  " + nonAtt;
        } catch (DataNotFoundException e) {
            log.error("AttendanceService ::-> markUserAttendance :: Error found due to: {} ", e.getMessage());
            throw new DataNotFoundException("Data not found for most of users.");
        }
    }

    @Override
    public List<Integer> userPerformance(String email, String gym) throws DataNotFoundException {
        try {
            UserAttendanceClass user = attendanceRepository.findByEmailAndGym(email, gym);
            if (user != null) {
                List<Integer> attendanceList = user.getAttendance();
                List<Integer> performance = new ArrayList<>();
                if (attendanceList != null) {
                    int s = attendanceList.size();
                    int div = s / 25;
                    int count = 0;
                    int i = 0;
                    int j = 25;
                    for (int k = 0; k < div; k++) {
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
                    while (i < s) {
                        if (attendanceList.get(i) == 1) {
                            count++;
                        }
                        i++;
                    }
                    performance.add(count);
                }
                return performance;
            } else {
                log.info("No more user exist with this user name : ");
                throw new DataNotFoundException("User Not found with this username:");
            }
        } catch (DataNotFoundException e) {
            log.error("AttendanceService ::-> userPerformance ::  Error found due to : {}", e.getMessage());
            throw new DataNotFoundException("Error: Data is not available for this user");
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

