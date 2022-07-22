package com.register.app.service;

import com.fitness.app.entity.RatingClass;
import com.fitness.app.entity.UserAttendanceClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.dto.request.MarkUserAttModel;
import com.fitness.app.repository.AttendanceRepository;
import com.fitness.app.repository.RatingRepository;
import com.fitness.app.service.AttendanceDaoImpl;
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
class AttendanceDaoImplTests {


    @InjectMocks
    private AttendanceDaoImpl attendanceServiceImpl;


    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private RatingRepository ratingRepository;

    List<String> users=new ArrayList<>(Arrays.asList("Rahul"));
    MarkUserAttModel attModel=new MarkUserAttModel(
            "GM1",
            "Manish",
            users
    );

    RatingClass ratingClass =new RatingClass(
            "rId",
            "Rahul",
            "Manish",
            4.0
    );

    RatingClass ratingClass1 =new RatingClass(
            "rId",
            "Ranjit",
            "Manish",
            4.0
    );


    List<Integer> attendance=new ArrayList<>(Arrays.asList(1,1,1,1,1,0,0,1,0,0,1,0,1,0,1,0
            ,1,0,1,0,1,0,1,0,1,0,1,0,1,1,0,1,0,1,1,1,0,1,0,1,0,1,0,0,1,0,1,0,1,1,1,1,0,1,0,
            1,0));
    UserAttendanceClass userAttendanceClass =new UserAttendanceClass(
            "Rahul",
            "GM1",
            "Manish",
            30,
            60,
            null,
            4.0
    );
    UserAttendanceClass userAttendanceClass2 =new UserAttendanceClass(
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
        List<UserAttendanceClass> allusers=new ArrayList<>();
        allusers.add(userAttendanceClass);

        Mockito.when(attendanceRepository.findByVendorAndGym(userAttendanceClass.getVendor(), userAttendanceClass.getGym())).thenReturn(allusers);
        Mockito.when(attendanceRepository.findByEmailAndVendorAndGym(userAttendanceClass.getEmail(), userAttendanceClass.getVendor(), userAttendanceClass.getGym())).thenReturn(userAttendanceClass);

        String ans= attendanceServiceImpl.markUsersAttendance(attModel);

        Assertions.assertNotNull(ans);
    }

    @Test
    @DisplayName("adding non attending too")
     void markUserAttendanceForNonAtt()
    {
        List<UserAttendanceClass> allusers=new ArrayList<>();
        allusers.add(userAttendanceClass);
        allusers.add(userAttendanceClass2);

        Mockito.when(attendanceRepository.findByVendorAndGym(userAttendanceClass.getVendor(), userAttendanceClass.getGym())).thenReturn(allusers);
        Mockito.when(attendanceRepository.findByEmailAndVendorAndGym(userAttendanceClass.getEmail(), userAttendanceClass.getVendor(), userAttendanceClass.getGym())).thenReturn(userAttendanceClass);
        Mockito.when(attendanceRepository.findByEmailAndVendorAndGym(userAttendanceClass2.getEmail(), userAttendanceClass2.getVendor(), userAttendanceClass2.getGym())).thenReturn(userAttendanceClass2);

        String ans= attendanceServiceImpl.markUsersAttendance(attModel);

        Assertions.assertNotNull(ans);
    }



    @Test
    @DisplayName("Mark attendance service for having attendance list;")
    void markAllUserAttendance()
    {
        List<String> users=new ArrayList<>(Arrays.asList("Rahul"));
        UserAttendanceClass userAttendanceClass =new UserAttendanceClass(
                "Rahul",
                "GM1",
                "Manish",
                30,
                60,
                attendance,
                4.0
        );
        UserAttendanceClass userAttendanceClass2 =new UserAttendanceClass(
                "Ranjit",
                "GM1",
                "Manish",
                30,
                60,
                attendance,
                4.0
        );
        List<UserAttendanceClass> allusers=new ArrayList<>();
        allusers.add(userAttendanceClass);
        allusers.add(userAttendanceClass2);

        Mockito.when(attendanceRepository.findByVendorAndGym(userAttendanceClass.getVendor(), userAttendanceClass.getGym())).thenReturn(allusers);
        Mockito.when(attendanceRepository.findByEmailAndVendorAndGym(userAttendanceClass.getEmail(), userAttendanceClass.getVendor(), userAttendanceClass.getGym())).thenReturn(userAttendanceClass);
        Mockito.when(attendanceRepository.findByEmailAndVendorAndGym(userAttendanceClass2.getEmail(), userAttendanceClass2.getVendor(), userAttendanceClass2.getGym())).thenReturn(userAttendanceClass2);
        String ans= attendanceServiceImpl.markUsersAttendance(attModel);
        System.out.println(ans);
        Assertions.assertNotNull(ans);

    }

    @Test
    @DisplayName("With Exception for marking attendance")
     void markAttForException()
    {
        Mockito.when(attendanceRepository.findByVendorAndGym(userAttendanceClass.getVendor(), userAttendanceClass.getGym())).thenReturn(null);
        Assertions.assertThrows(DataNotFoundException.class, ()->{
            String ans= attendanceServiceImpl.markUsersAttendance(attModel);
        }, "No data Found Exception");
    }


    @Test
     void  userPerformance()
    {
        List<Integer> attendance=new ArrayList<>(Arrays.asList(1,1,1,1,1,0,0,1,0,0,1,0,1,0,1,0
                ,1,0,1,0,1,0,1,0,1,0,1,0,1,1,0,1,0,1,1,1,0,1,0,1,0,1,0,0,1,0,1,0,1,1,1,1,0,1,0,
                1,0));

        UserAttendanceClass userAttendanceClass =new UserAttendanceClass(
                "Rahul",
                "GM1",
                "Manish",
                30,
                60,
                attendance,
                4.0
        );
        Mockito.when(attendanceRepository.findByEmailAndGym(userAttendanceClass.getEmail(), userAttendanceClass.getGym())).thenReturn(userAttendanceClass);
        List<Integer> attendance1= attendanceServiceImpl.userPerformance(userAttendanceClass.getEmail(), userAttendanceClass.getGym());

        Assertions.assertNotNull(attendance1);
        Assertions.assertTrue(attendance1.size()>0);
        Assertions.assertEquals(14,attendance1.get(0) );
    }

    @Test
    @DisplayName("Exception handle")
    void  userPerformanceForException()
    {
        Mockito.when(attendanceRepository.findByEmailAndGym(userAttendanceClass.getEmail(), userAttendanceClass.getGym())).thenReturn(null);
        String email= userAttendanceClass.getEmail(), gym= userAttendanceClass.getGym();
        Assertions.assertThrows(DataNotFoundException.class, ()->{

            List<Integer> attendance1= attendanceServiceImpl.userPerformance(email, gym);
        },"Data not found any: ");
    }




    @Test
     void  calculateRating()
    {
       List<RatingClass> ratingsList=new ArrayList<>();
       ratingsList.add(ratingClass);
       ratingsList.add(ratingClass);


       Mockito.when(ratingRepository.findByTarget(ratingClass.getTarget())).thenReturn(ratingsList);
       Double returnedRate= attendanceServiceImpl.calculateRating(ratingClass.getTarget());
       Assertions.assertNotNull(returnedRate);
       Assertions.assertEquals(4.0, returnedRate);

    }



    @Test
    void  calculateRatingWithNullRating()
    {

        Mockito.when(ratingRepository.findByTarget(ratingClass.getTarget())).thenReturn(null);
        Double returnedRate= attendanceServiceImpl.calculateRating(ratingClass.getTarget());
        Assertions.assertNotNull(returnedRate);
        Assertions.assertEquals(0.0, returnedRate);

    }

}
