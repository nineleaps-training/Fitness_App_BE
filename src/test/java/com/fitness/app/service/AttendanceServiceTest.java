package com.fitness.app.service;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fitness.app.entity.UserAttendance;
import com.fitness.app.exception.DataNotFoundException;
import com.fitness.app.model.MarkUserAttModel;
import com.fitness.app.model.RatingRequestModel;
import com.fitness.app.repository.AttendanceRepo;
import com.fitness.app.repository.RatingRepo;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceTest {

    String user;
    String target;

    MarkUserAttModel markUserAttModel;

    UserAttendance userAttendance;

    RatingRequestModel ratingModel;

    List<UserAttendance> allUser= new ArrayList<>();

    List<RatingRequestModel> ratings;

    List<Integer> attendance1 = new ArrayList<>();

    List<String> users = new ArrayList<>();

    @Mock
    AttendanceRepo attendanceRepo;

    @Mock
    RatingRepo ratingRepo;

    AttendanceService attendanceService;

    @BeforeEach
    public void initcase() {
        attendanceService=new AttendanceService(ratingRepo,attendanceRepo);
    }

    @Test
    void testCalculateRating() {
        
        List<Integer> attendance=new ArrayList<>();
        attendance.add(1);
        UserAttendance userAttendance = new UserAttendance("abc@gmail.com","Fitness","ABC",2,1,attendance,4.2);
        List<String> userList=new ArrayList<>();
        userList.add("A");
        RatingRequestModel ratingRequestModel=new RatingRequestModel("id", "4", "user", 4.2);
        List<UserAttendance> userAttendances=new ArrayList<>();
        userAttendances.add(userAttendance);
        ratings = new ArrayList<>();
        ratings.add(ratingRequestModel);
        when(ratingRepo.findByTarget(ratingRequestModel.getTarget())).thenReturn(ratings);
        Double rating=attendanceService.calculateRating(ratingRequestModel.getTarget());
        Assertions.assertEquals(ratingRequestModel.getRating(), rating);
    }

    @Test
    void testCalculateRatingwithNull() {
        
        List<Integer> attendance=new ArrayList<>();
        attendance.add(1);
        UserAttendance userAttendance = new UserAttendance("abc@gmail.com","Fitness","ABC",2,1,attendance,0.0);
        List<String> userList=new ArrayList<>();
        userList.add("A");
        RatingRequestModel ratingRequestModel=new RatingRequestModel("id", "4", "user", 0.0);
        List<UserAttendance> userAttendances=new ArrayList<>();
        userAttendances.add(userAttendance);
        ratings = null;
        when(ratingRepo.findByTarget(ratingRequestModel.getTarget())).thenReturn(ratings);
        Double rating=attendanceService.calculateRating(ratingRequestModel.getTarget());
        Assertions.assertEquals(ratingRequestModel.getRating(), rating);
    }

    @Test
    void testMarkUsersAttendance() {
        List<Integer> attendance=new ArrayList<>();
        attendance.add(1);
        UserAttendance userAttendance = new UserAttendance("abc@gmail.com","Fitness","ABC",2,1,attendance,4.2);
        List<String> userList=new ArrayList<>();
        userList.add("A");
        MarkUserAttModel markUserAttModel=new MarkUserAttModel("Fitness", "ABC", userList);
        RatingRequestModel ratingRequestModel=new RatingRequestModel("id", "4", "user", 4.2);
        List<UserAttendance> userAttendances=new ArrayList<>();
        userAttendances.add(userAttendance);
        ratings = new ArrayList<>();
        ratings.add(ratingRequestModel);
        when(attendanceRepo.findByVendorAndGym(userAttendance.getVendor(), userAttendance.getGym())).thenReturn(userAttendances);
        when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance.getEmail(), userAttendance.getVendor(), userAttendance.getGym())).thenReturn(userAttendance);
        String response=attendanceService.markUsersAttendance(markUserAttModel);
        int att=0;
        int nonatt=1;
        String attendance1 = "Marked total: " + att+ " and non attendy:  "+ nonatt;
        Assertions.assertEquals(attendance1, response);
    }

    @Test
    void testMarkUsersAttendanceException() {
        List<Integer> attendance=new ArrayList<>();
        attendance.add(1);
        UserAttendance userAttendance = new UserAttendance();
        List<String> userList=new ArrayList<>();
        userList.add("A");
        MarkUserAttModel markUserAttModel=new MarkUserAttModel("Fitness", "ABC", userList);
        RatingRequestModel ratingRequestModel=new RatingRequestModel("id", "4", "user", 4.2);
        List<UserAttendance> userAttendances=new ArrayList<>();
        userAttendances.add(userAttendance);
        ratings = new ArrayList<>();
        ratings.add(ratingRequestModel);
        when(attendanceRepo.findByVendorAndGym(userAttendance.getVendor(), userAttendance.getGym())).thenReturn(userAttendances);
        when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance.getEmail(), userAttendance.getVendor(), userAttendance.getGym())).thenReturn(userAttendance);
        try
        {
            attendanceService.markUsersAttendance(markUserAttModel);
        }
        catch(DataNotFoundException d)
        {
            Assertions.assertEquals("Data Not Found", d.getMessage());
        }
    }

    @Test
    void testMarkUsersAttendanceUserContainsUser() {

        List<Integer> attendance=new ArrayList<>();
        attendance.add(1);
        UserAttendance userAttendance = new UserAttendance("abc@gmail.com","Fitness","ABC",2,1,attendance,4.2);
        List<String> userList=new ArrayList<>();
        userList.add("abc@gmail.com");
        MarkUserAttModel markUserAttModel=new MarkUserAttModel("Fitness", "ABC", userList);
        RatingRequestModel ratingRequestModel=new RatingRequestModel("id", "4", "user", 4.2);
        List<UserAttendance> userAttendances=new ArrayList<>();
        userAttendances.add(userAttendance);
        ratings = new ArrayList<>();
        ratings.add(ratingRequestModel);
        when(attendanceRepo.findByVendorAndGym(userAttendance.getVendor(), userAttendance.getGym())).thenReturn(userAttendances);
        when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance.getEmail(), userAttendance.getVendor(), userAttendance.getGym())).thenReturn(userAttendance);
        String response=attendanceService.markUsersAttendance(markUserAttModel);
        int att=1;
        int nonatt=0;
        String attendance1 = "Marked total: " + att+ " and non attendy:  "+ nonatt;
        Assertions.assertEquals(attendance1, response);
    }

    @Test
    void testMarkUsersAttendanceUserContainsUserwithNull() {

        List<Integer> attendance=null;
        UserAttendance userAttendance = new UserAttendance("abc@gmail.com","Fitness","ABC",2,1,attendance,4.2);
        List<String> userList=new ArrayList<>();
        userList.add("abc@gmail.com");
        MarkUserAttModel markUserAttModel=new MarkUserAttModel("Fitness", "ABC", userList);
        RatingRequestModel ratingRequestModel=new RatingRequestModel("id", "4", "user", 4.2);
        List<UserAttendance> userAttendances=new ArrayList<>();
        userAttendances.add(userAttendance);
        ratings = new ArrayList<>();
        ratings.add(ratingRequestModel);
        when(attendanceRepo.findByVendorAndGym(userAttendance.getVendor(), userAttendance.getGym())).thenReturn(userAttendances);
        when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance.getEmail(), userAttendance.getVendor(), userAttendance.getGym())).thenReturn(userAttendance);
        String response=attendanceService.markUsersAttendance(markUserAttModel);
        int att=1;
        int nonatt=0;
        String attendance1 = "Marked total: " + att+ " and non attendy:  "+ nonatt;
        Assertions.assertEquals(attendance1, response);
    }

    @Test
    void testMarkUsersAttendanceElse() {
        List<Integer> attendance=null;
        UserAttendance userAttendance = new UserAttendance("abc@gmail.com","Fitness","ABC",2,1,attendance,4.2);
        List<String> userList=new ArrayList<>();
        userList.add("A");
        MarkUserAttModel markUserAttModel=new MarkUserAttModel("Fitness", "ABC", userList);
        RatingRequestModel ratingRequestModel=new RatingRequestModel("id", "4", "user", 4.2);
        List<UserAttendance> userAttendances=new ArrayList<>();
        userAttendances.add(userAttendance);
        ratings = new ArrayList<>();
        ratings.add(ratingRequestModel);
        when(attendanceRepo.findByVendorAndGym(userAttendance.getVendor(), userAttendance.getGym())).thenReturn(userAttendances);
        when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance.getEmail(), userAttendance.getVendor(), userAttendance.getGym())).thenReturn(userAttendance);
        String response=attendanceService.markUsersAttendance(markUserAttModel);
        int att=0;
        int nonatt=1;
        String attendance1 = "Marked total: " + att+ " and non attendy:  "+ nonatt;
        Assertions.assertEquals(attendance1, response);
    }



    @Test
    void testUserPerfomance() {

        List<Integer> attendance=new ArrayList<>();
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        UserAttendance userAttendance = new UserAttendance("abc@gmail.com","Fitness","ABC",2,1,attendance,4.2);
        List<String> userList=new ArrayList<>();
        userList.add("A");
        RatingRequestModel ratingRequestModel=new RatingRequestModel("id", "4", "user", 4.2);
        List<UserAttendance> userAttendances=new ArrayList<>();
        userAttendances.add(userAttendance);
        ratings = new ArrayList<>();
        ratings.add(ratingRequestModel);
        when(ratingRepo.findByTarget(ratingRequestModel.getTarget())).thenReturn(ratings);
        Double rating=attendanceService.calculateRating(ratingRequestModel.getTarget());
        Assertions.assertEquals(ratingRequestModel.getRating(), rating);
        when(attendanceRepo.findByEmailAndGym(userAttendance.getEmail(), userAttendance.getGym())).thenReturn(userAttendance);
        List<Integer> list2=attendanceService.userPerfomance(userAttendance.getEmail(), userAttendance.getGym());
        List<Integer> list = new ArrayList<>();
        list.add(26);
        list.add(4);
        Assertions.assertEquals(list, list2);

    }

    @Test
    void testUserPerfomance1() {

        List<Integer> attendance=new ArrayList<>();
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(0);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(1);
        attendance.add(0);
        attendance.add(1);
        UserAttendance userAttendance = new UserAttendance("abc@gmail.com","Fitness","ABC",2,1,attendance,4.2);
        List<String> userList=new ArrayList<>();
        userList.add("A");
        RatingRequestModel ratingRequestModel=new RatingRequestModel("id", "4", "user", 4.2);
        List<UserAttendance> userAttendances=new ArrayList<>();
        userAttendances.add(userAttendance);
        ratings = new ArrayList<>();
        ratings.add(ratingRequestModel);
        when(ratingRepo.findByTarget(ratingRequestModel.getTarget())).thenReturn(ratings);
        Double rating=attendanceService.calculateRating(ratingRequestModel.getTarget());
        Assertions.assertEquals(ratingRequestModel.getRating(), rating);
        when(attendanceRepo.findByEmailAndGym(userAttendance.getEmail(), userAttendance.getGym())).thenReturn(userAttendance);
        List<Integer> list2=attendanceService.userPerfomance(userAttendance.getEmail(), userAttendance.getGym());
        List<Integer> list = new ArrayList<>();
        list.add(25);
        list.add(25);
        list.add(8);
        Assertions.assertEquals(list, list2);

    }

    @Test
    void testUserPerfomanceException() {

        List<Integer> attendance=null;
        UserAttendance userAttendance = new UserAttendance("abc@gmail.com","Fitness","ABC",2,1,attendance,4.2);
        List<String> userList=new ArrayList<>();
        userList.add("A");
        RatingRequestModel ratingRequestModel=new RatingRequestModel("id", "4", "user", 4.2);
        List<UserAttendance> userAttendances=new ArrayList<>();
        userAttendances.add(userAttendance);
        ratings = new ArrayList<>();
        ratings.add(ratingRequestModel);
        when(ratingRepo.findByTarget(ratingRequestModel.getTarget())).thenReturn(ratings);
        Double rating=attendanceService.calculateRating(ratingRequestModel.getTarget());
        Assertions.assertEquals(ratingRequestModel.getRating(), rating);
        when(attendanceRepo.findByEmailAndGym(userAttendance.getEmail(), userAttendance.getGym())).thenReturn(userAttendance);
        try
        {
            attendanceService.userPerfomance(userAttendance.getEmail(), userAttendance.getGym());
        }
        catch(DataNotFoundException d)
        {
            Assertions.assertEquals("Data Not Found", d.getMessage());
        }
    }
}
