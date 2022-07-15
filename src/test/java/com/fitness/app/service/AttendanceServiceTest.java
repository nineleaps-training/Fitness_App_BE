package com.fitness.app.service;

import com.fitness.app.entity.Rating;
import com.fitness.app.entity.UserAttendance;
import com.fitness.app.model.MarkUserAttModel;
import com.fitness.app.model.RatingModel;
import com.fitness.app.repository.AttendanceRepo;
import com.fitness.app.repository.RatingRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AttendanceServiceTest {

    @MockBean
    AttendanceRepo attendanceRepo;

    @MockBean
    RatingRepo ratingRepo;

    @Autowired
    AttendanceService attendanceService;

    @Test
    void markUsersAttendance() {
        List<Integer> attendanceList = new ArrayList<>();
        attendanceList.add(1);
        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 2, 1, attendanceList, 4.2);
        List<UserAttendance> userAttendanceList = new ArrayList<>();
        userAttendanceList.add(userAttendance);

        List<String> users = new ArrayList<>();
        users.add("Aarohi");
        MarkUserAttModel markUserAttModel = new MarkUserAttModel("Fitness", "Priyanshi", users);

        when(attendanceRepo.findByVendorAndGym(markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendanceList);
        when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance.getEmail(), markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendance);

        int attended = 0;
        int nonAttended = 1;
        String attendance = "Marked total: " + attended + " and non attendy:  " + nonAttended;

        String actual = attendanceService.markUsersAttendance(markUserAttModel);
        assertEquals(attendance, actual);
    }

    @Test
    void markUsersAttendanceIfUserListDoesNotContainsUserAndAttendanceListIsnull() {
        List<Integer> attendanceList = null;
        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 2, 1, attendanceList, 4.2);
        List<UserAttendance> userAttendanceList = new ArrayList<>();
        userAttendanceList.add(userAttendance);

        List<String> users = new ArrayList<>();
        users.add("Aarohi");
        MarkUserAttModel markUserAttModel = new MarkUserAttModel("Fitness", "Priyanshi", users);

        when(attendanceRepo.findByVendorAndGym(markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendanceList);
        when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance.getEmail(), markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendance);

        int attended = 0;
        int nonAttended = 1;
        String attendance = "Marked total: " + attended + " and non attendy:  " + nonAttended;

        String actual = attendanceService.markUsersAttendance(markUserAttModel);
        assertEquals(attendance, actual);
    }

    @Test
    void markUsersAttendanceIfUserListContainsUser() {
        List<Integer> attendanceList = new ArrayList<>();
        attendanceList.add(1);
        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 2, 1, attendanceList, 4.2);
        List<UserAttendance> userAttendanceList = new ArrayList<>();
        userAttendanceList.add(userAttendance);

        List<String> users = new ArrayList<>();
        users.add("priyanshi.chaturvedi@nineleaps.com");
        MarkUserAttModel markUserAttModel = new MarkUserAttModel("Fitness", "Priyanshi", users);

        when(attendanceRepo.findByVendorAndGym(markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendanceList);
        when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance.getEmail(), markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendance);

        int attended = 1;
        int nonAttended = 0;
        String attendance = "Marked total: " + attended + " and non attendy:  " + nonAttended;

        String actual = attendanceService.markUsersAttendance(markUserAttModel);
        assertEquals(attendance, actual);
    }

    @Test
    void markUsersAttendanceIfAttendanceListIsNull() {
        List<Integer> attendanceList = null;
        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 2, 1, attendanceList, 4.2);
        List<UserAttendance> userAttendanceList = new ArrayList<>();
        userAttendanceList.add(userAttendance);

        List<String> users = new ArrayList<>();
        users.add("priyanshi.chaturvedi@nineleaps.com");
        MarkUserAttModel markUserAttModel = new MarkUserAttModel("Fitness", "Priyanshi", users);

        when(attendanceRepo.findByVendorAndGym(markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendanceList);
        when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance.getEmail(), markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendance);

        int attended = 1;
        int nonAttended = 0;
        String attendance = "Marked total: " + attended + " and non attendy:  " + nonAttended;

        String actual = attendanceService.markUsersAttendance(markUserAttModel);
        assertEquals(attendance, actual);
    }

    @Test
    void markUsersAttendanceThrowsException() {
        UserAttendance userAttendance = new UserAttendance();
        List<String> users = new ArrayList<>();
        MarkUserAttModel markUserAttModel = null;

        Executable executable = () -> attendanceService.markUsersAttendance(markUserAttModel);
        assertThrows(Exception.class, executable);
    }

    @Test
    void userPerformance() {
        List<Integer> attendanceList = new ArrayList<>();
        attendanceList.add(1);
        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 2, 1, attendanceList, 4.2);

        when(attendanceRepo.findByEmailAndGym(userAttendance.getEmail(), userAttendance.getGym())).thenReturn(userAttendance);

        List<Integer> actual = attendanceService.userPerformance(userAttendance.getEmail(), userAttendance.getGym());
        assertEquals(attendanceList, actual);
    }

    @Test
    void userPerformancesIfUserAttendanceIsNull() {
        List<Integer> attendanceList = new ArrayList<>();
        UserAttendance userAttendance = new UserAttendance();

        when(attendanceRepo.findByEmailAndGym(userAttendance.getEmail(), userAttendance.getGym())).thenReturn(null);

        List<Integer> actual = attendanceService.userPerformance(userAttendance.getEmail(), userAttendance.getGym());
        assertEquals(attendanceList, actual);
    }

    @Test
    void userPerformanceIfAttendanceListIsNull() {
        List<Integer> attendanceList = null;
        List<Integer> performance = new ArrayList<>();
        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 2, 1, attendanceList, 4.2);

        when(attendanceRepo.findByEmailAndGym(userAttendance.getEmail(), userAttendance.getGym())).thenReturn(userAttendance);

        List<Integer> actual = attendanceService.userPerformance(userAttendance.getEmail(), userAttendance.getGym());
        assertEquals(performance, actual);
    }

    @Test
    void userPerformanceThrowsException() {
        List<Integer> attendanceList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25));
        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 2, 1, attendanceList, 4.2);

        when(attendanceRepo.findByEmailAndGym(userAttendance.getEmail(), userAttendance.getGym())).thenReturn(userAttendance);

        Executable executable = () -> attendanceService.userPerformance(userAttendance.getEmail(), userAttendance.getGym());
        assertThrows(Exception.class, executable);

    }

    @Test
    void userPerformanceWhenAttendanceListIsMoreThan25() {
        List<Integer> attendanceList = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26));
        List<Integer> performance = new ArrayList<>(Arrays.asList(1, 0));
        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 2, 1, attendanceList, 4.2);

        when(attendanceRepo.findByEmailAndGym(userAttendance.getEmail(), userAttendance.getGym())).thenReturn(userAttendance);

        List<Integer> actual = attendanceService.userPerformance(userAttendance.getEmail(), userAttendance.getGym());
        assertEquals(performance, actual);

    }

    @Test
    void calculateRating() {
        RatingModel ratingModel = new RatingModel("1", "4", "Priyanshi", 4.2);
        List<RatingModel> ratings = new ArrayList<>();

        Rating rating = new Rating("1", "4", "Priyanshi", 4.2);
        List<Rating> ratingList = new ArrayList<>();
        ratingList.add(rating);

        when(ratingRepo.findByTarget(ratingModel.getTarget())).thenReturn(ratingList);

        Double actual = attendanceService.calculateRating(ratingModel.getTarget());
        assertEquals(ratingModel.getRate(), actual);

    }

    @Test
    void returnNullIfRatingModelIsNull() {
        RatingModel ratingModel = new RatingModel("1", "4", "Priyanshi", 4.2);
        List<RatingModel> ratings = null;

        Rating rating = new Rating("1", "4", "Priyanshi", 4.2);
        List<Rating> ratingList = null;

        when(ratingRepo.findByTarget(ratingModel.getTarget())).thenReturn(ratingList);

        Double actual = attendanceService.calculateRating(ratingModel.getTarget());
        assertEquals(0.0, actual);

    }
}