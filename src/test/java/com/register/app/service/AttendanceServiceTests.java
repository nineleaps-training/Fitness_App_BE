package com.register.app.service;

import com.fitness.app.entity.Rating;
import com.fitness.app.entity.UserAttendance;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.model.MarkUserAttModel;
import com.fitness.app.repository.AttendanceRepo;
import com.fitness.app.repository.RatingRepo;
import com.fitness.app.service.AttendanceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class AttendanceServiceTests {


    @InjectMocks
    private AttendanceService attendanceService;


    @Mock
    private AttendanceRepo attendanceRepo;

    @Mock
    private RatingRepo ratingRepo;

    List<String> users=new ArrayList<>(Arrays.asList("Rahul"));
    MarkUserAttModel attModel=new MarkUserAttModel(
            "GM1",
            "Manish",
            users
    );

    Rating rating=new Rating(
            "rId",
            "Rahul",
            "Manish",
            4.0
    );

    Rating rating1=new Rating(
            "rId",
            "Ranjit",
            "Manish",
            4.0
    );


    List<Integer> attendance=new ArrayList<>(Arrays.asList(1,1,1,1,1,0,0,1,0,0,1,0,1,0,1,0
            ,1,0,1,0,1,0,1,0,1,0,1,0,1,1,0,1,0,1,1,1,0,1,0,1,0,1,0,0,1,0,1,0,1,1,1,1,0,1,0,
            1,0));
    UserAttendance userAttendance=new UserAttendance(
            "Rahul",
            "GM1",
            "Manish",
            30,
            60,
            null,
            4.0
    );
    UserAttendance userAttendance2=new UserAttendance(
            "Ranjit",
            "GM1",
            "Manish",
            30,
            60,
            null,
            4.0
    );


    @Test
     void markUserAttendance()
    {
        List<UserAttendance> allusers=new ArrayList<>();
        allusers.add(userAttendance);

        Mockito.when(attendanceRepo.findByVendorAndGym(userAttendance.getVendor(), userAttendance.getGym())).thenReturn(allusers);
        Mockito.when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance.getEmail(), userAttendance.getVendor(), userAttendance.getGym())).thenReturn(userAttendance);

        String ans=attendanceService.markUsersAttendance(attModel);

        Assertions.assertNotNull(ans);
    }

    @Test
    @DisplayName("adding non attending too")
     void markUserAttendanceForNonAtt()
    {
        List<UserAttendance> allusers=new ArrayList<>();
        allusers.add(userAttendance);
        allusers.add(userAttendance2);

        Mockito.when(attendanceRepo.findByVendorAndGym(userAttendance.getVendor(), userAttendance.getGym())).thenReturn(allusers);
        Mockito.when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance.getEmail(), userAttendance.getVendor(), userAttendance.getGym())).thenReturn(userAttendance);
        Mockito.when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance2.getEmail(), userAttendance2.getVendor(), userAttendance2.getGym())).thenReturn(userAttendance2);

        String ans=attendanceService.markUsersAttendance(attModel);

        Assertions.assertNotNull(ans);
    }


    @Test
    @DisplayName("With Exception for marking attendance")
     void markAttForException()
    {
        Mockito.when(attendanceRepo.findByVendorAndGym(userAttendance.getVendor(), userAttendance.getGym())).thenReturn(null);
        Assertions.assertThrows(DataNotFoundException.class, ()->{

            String ans=attendanceService.markUsersAttendance(attModel);
        }, "No data Found Exception");
    }


    @Test
     void  userPerformance()
    {
        List<Integer> attendance=new ArrayList<>(Arrays.asList(1,1,1,1,1,0,0,1,0,0,1,0,1,0,1,0
                ,1,0,1,0,1,0,1,0,1,0,1,0,1,1,0,1,0,1,1,1,0,1,0,1,0,1,0,0,1,0,1,0,1,1,1,1,0,1,0,
                1,0));

        UserAttendance userAttendance=new UserAttendance(
                "Rahul",
                "GM1",
                "Manish",
                30,
                60,
                attendance,
                4.0
        );
        Mockito.when(attendanceRepo.findByEmailAndGym(userAttendance.getEmail(), userAttendance.getGym())).thenReturn(userAttendance);
        List<Integer> attendance1= attendanceService.userPerfomance(userAttendance.getEmail(), userAttendance.getGym());

        Assertions.assertNotNull(attendance1);
        Assertions.assertTrue(attendance1.size()>0);
        Assertions.assertEquals(14,attendance1.get(0) );
    }

    @Test
    @DisplayName("Exception handle")
    void  userPerformanceForException()
    {
        Mockito.when(attendanceRepo.findByEmailAndGym(userAttendance.getEmail(), userAttendance.getGym())).thenReturn(null);
        String email= userAttendance.getEmail(), gym= userAttendance.getGym();
        Assertions.assertThrows(DataNotFoundException.class, ()->{

            List<Integer> attendance1= attendanceService.userPerfomance(email, gym);
        },"Data not found any: ");
    }




    @Test
     void  calculateRating()
    {
       List<Rating> ratingsList=new ArrayList<>();
       ratingsList.add(rating);
       ratingsList.add(rating);


       Mockito.when(ratingRepo.findByTarget(rating.getTarget())).thenReturn(ratingsList);
       Double returnedRate= attendanceService.calculateRating(rating.getTarget());
       Assertions.assertNotNull(returnedRate);
       Assertions.assertEquals(4.0, returnedRate);

    }



    @Test
    void  calculateRatingWithNullRating()
    {

        Mockito.when(ratingRepo.findByTarget(rating.getTarget())).thenReturn(null);
        Double returnedRate= attendanceService.calculateRating(rating.getTarget());
        Assertions.assertNotNull(returnedRate);
        Assertions.assertEquals(0.0, returnedRate);

    }

}
