package com.fitness.app.service;

import com.fitness.app.entity.UserAttendance;
import com.fitness.app.model.MarkUserAttModel;
import com.fitness.app.model.RatingModel;
import com.fitness.app.repository.AttendanceRepo;
import com.fitness.app.repository.RatingRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
        List<UserAttendance> userAttendanceList= new ArrayList<>();
        userAttendanceList.add(userAttendance);

        List<String> users = new ArrayList<>();
        users.add("Geet");
        MarkUserAttModel markUserAttModel = new MarkUserAttModel("Fitness", "Priyanshi", users);

        when(attendanceRepo.findByVendorAndGym(markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendanceList);
        when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance.getEmail(), markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendance);

        int attended = 0;
        int nonAttended = 1;
        String attendance = "Marked total: " + attended + " and non attendy:  "+ nonAttended;

        assertEquals(attendance, attendanceService.markUsersAttendance(markUserAttModel));
    }

    @Test
    void markUsersAttendanceIfUserListDoesNotContainsUserAndAttendanceListIsnull() {
        List<Integer> attendanceList = null;
        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 2, 1, attendanceList, 4.2);
        List<UserAttendance> userAttendanceList= new ArrayList<>();
        userAttendanceList.add(userAttendance);

        List<String> users = new ArrayList<>();
        users.add("Geet");
        MarkUserAttModel markUserAttModel = new MarkUserAttModel("Fitness", "Priyanshi", users);

        when(attendanceRepo.findByVendorAndGym(markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendanceList);
        when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance.getEmail(), markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendance);

        int attended = 0;
        int nonAttended = 1;
        String attendance = "Marked total: " + attended + " and non attendy:  "+ nonAttended;

        assertEquals(attendance, attendanceService.markUsersAttendance(markUserAttModel));
    }

    @Test
    void markUsersAttendanceIfUserListContainsUser() {
        List<Integer> attendanceList = new ArrayList<>();
        attendanceList.add(1);
        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 2, 1, attendanceList, 4.2);
        List<UserAttendance> userAttendanceList= new ArrayList<>();
        userAttendanceList.add(userAttendance);

        List<String> users = new ArrayList<>();
        users.add("priyanshi.chaturvedi@nineleaps.com");
        MarkUserAttModel markUserAttModel = new MarkUserAttModel("Fitness", "Priyanshi", users);

        when(attendanceRepo.findByVendorAndGym(markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendanceList);
        when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance.getEmail(), markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendance);

        int attended = 1;
        int nonAttended = 0;
        String attendance = "Marked total: " + attended + " and non attendy:  "+ nonAttended;

        assertEquals(attendance, attendanceService.markUsersAttendance(markUserAttModel));
    }

    @Test
    void markUsersAttendanceIfAttendanceListIsNull() {
        List<Integer> attendanceList = null;
//        attendanceList.add(1);
        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 2, 1, attendanceList, 4.2);
        List<UserAttendance> userAttendanceList= new ArrayList<>();
        userAttendanceList.add(userAttendance);

        List<String> users = new ArrayList<>();
        users.add("priyanshi.chaturvedi@nineleaps.com");
        MarkUserAttModel markUserAttModel = new MarkUserAttModel("Fitness", "Priyanshi", users);

        when(attendanceRepo.findByVendorAndGym(markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendanceList);
        when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance.getEmail(), markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendance);

        int attended = 1;
        int nonAttended = 0;
        String attendance = "Marked total: " + attended + " and non attendy:  "+ nonAttended;

        assertEquals(attendance, attendanceService.markUsersAttendance(markUserAttModel));
    }

    @Test
    void markUsersAttendanceThrowsException() {
        List<Integer> attendanceList = new ArrayList<>();
        attendanceList.add(1);
        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 2, 1, attendanceList, 4.2);
        List<UserAttendance> userAttendanceList= null;
//        userAttendanceList.add(userAttendance);

        List<String> users = null;
//        users.add("Geet");
        MarkUserAttModel markUserAttModel = null;

//        when(attendanceRepo.findByVendorAndGym(markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendanceList);
//        when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance.getEmail(), markUserAttModel.getVendor(), markUserAttModel.getGym())).thenReturn(userAttendance);

        int attended = 0;
        int nonAttended = 1;
        String attendance = "Marked total: " + attended + " and non attendy:  "+ nonAttended;

//        assertEquals(attendance, attendanceService.markUsersAttendance(markUserAttModel));
        assertThrows(Exception.class, () -> attendanceService.markUsersAttendance(markUserAttModel));
    }

    @Test
    void userPerformance() {
        List<Integer> attendanceList = new ArrayList<>();
        attendanceList.add(1);
        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 2, 1, attendanceList, 4.2);

        when(attendanceRepo.findByEmailAndGym(userAttendance.getEmail(), userAttendance.getGym())).thenReturn(userAttendance);

        assertEquals(attendanceList, attendanceService.userPerformance(userAttendance.getEmail(), userAttendance.getGym()));
    }

    @Test
    void userPerformancesIfUserAttendanceIsNull() {
        List<Integer> attendanceList = new ArrayList<>();
        UserAttendance userAttendance = new UserAttendance();

        when(attendanceRepo.findByEmailAndGym(userAttendance.getEmail(), userAttendance.getGym())).thenReturn(null);

        assertEquals(attendanceList, attendanceService.userPerformance(userAttendance.getEmail(), userAttendance.getGym()));
    }

    @Test
    void userPerformanceIfAttendanceListIsNull() {
        List<Integer> attendanceList = null;
        List<Integer> performance = new ArrayList<>();
        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 2, 1, attendanceList, 4.2);

        when(attendanceRepo.findByEmailAndGym(userAttendance.getEmail(), userAttendance.getGym())).thenReturn(userAttendance);

        assertEquals(performance, attendanceService.userPerformance(userAttendance.getEmail(), userAttendance.getGym()));
    }

    @Test
    void userPerformanceThrowsException() {
        List<Integer> attendanceList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25));

        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 2, 1, attendanceList, 4.2);

        when(attendanceRepo.findByEmailAndGym(userAttendance.getEmail(), userAttendance.getGym())).thenReturn(userAttendance);

        assertThrows(Exception.class, ()-> attendanceService.userPerformance(userAttendance.getEmail(), userAttendance.getGym()));

    }

    @Test
    void userPerformanceWhenAttendanceListIsMoreThan25() {
        List<Integer> attendanceList = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26));

        List<Integer> performance = new ArrayList<>(Arrays.asList(1, 0));

        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 2, 1, attendanceList, 4.2);

        when(attendanceRepo.findByEmailAndGym(userAttendance.getEmail(), userAttendance.getGym())).thenReturn(userAttendance);

        assertEquals(performance, attendanceService.userPerformance(userAttendance.getEmail(), userAttendance.getGym()));

    }

    @Test
    void calculateRating() {
        RatingModel ratingModel = new RatingModel("1", "4", "Priyanshi", 4.2);
        List<RatingModel> ratings = new ArrayList<>();
        ratings.add(ratingModel);

        when(ratingRepo.findByTarget(ratingModel.getTarget())).thenReturn(ratings);

        assertEquals(ratingModel.getRate(), attendanceService.calculateRating(ratingModel.getTarget()));

    }

    @Test
    void returnNullIfRatingModelIsNull() {
        RatingModel ratingModel = new RatingModel("1", "4", "Priyanshi", 4.2);
        List<RatingModel> ratings = null;

        when(ratingRepo.findByTarget(ratingModel.getTarget())).thenReturn(ratings);

        assertEquals(0.0, attendanceService.calculateRating(ratingModel.getTarget()));

    }
}