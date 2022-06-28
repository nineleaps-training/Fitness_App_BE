package com.register.app.service;

import com.fitness.app.entity.UserAttendance;
import com.fitness.app.model.MarkUserAttModel;
import com.fitness.app.repository.AttendanceRepo;
import com.fitness.app.repository.RatingRepo;
import com.fitness.app.service.AttendanceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class AttendanceServiceTests {


    @InjectMocks
    private AttendanceService attendanceService;


    @Mock
    private AttendanceRepo attendanceRepo;

    @Mock
    private RatingRepo ratingRepo;

    List<String> users=new ArrayList<>(Arrays.asList("Manish", "Rahul", "Ranjit"));
    MarkUserAttModel attModel=new MarkUserAttModel(
            "GM1",
            "Manish",
            users
    );

    List<Integer> attendance=new ArrayList<>(Arrays.asList(1,1,1,1,1,0,0,1,0));
    UserAttendance userAttendance=new UserAttendance(
            "Rahul",
            "GM1",
            "Manish",
            30,
            60,
            attendance,
            4.0
    );


    @Test
    public void markUserAttendance() throws  Exception
    {
        List<UserAttendance> allusers=new ArrayList<>();
        allusers.add(userAttendance);

        Mockito.when(attendanceRepo.findByVendorAndGym(userAttendance.getVendor(), userAttendance.getGym())).thenReturn(allusers);
        Mockito.when(attendanceRepo.findByEmailAndVendorAndGym(userAttendance.getEmail(), userAttendance.getVendor(), userAttendance.getGym())).thenReturn(userAttendance);

        String ans=attendanceService.markUsersAttendance(attModel);

        Assertions.assertNotNull(ans);
    }




}
