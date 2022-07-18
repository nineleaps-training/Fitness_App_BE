package com.fitness.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.entity.UserAttendance;
import com.fitness.app.model.MarkUserAttModel;
import com.fitness.app.repository.AttendanceRepo;
import com.fitness.app.service.AttendanceService;

@ExtendWith(MockitoExtension.class)
class AttendanceControllerTest {

    @InjectMocks
    AttendanceController attendanceController;

    @Mock
    AttendanceService attendanceService;

    @Mock
    AttendanceRepo attendanceRepo;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(attendanceController).build();
    }

    @Test
    @DisplayName("Testing to mark the user attendace")
    void testMarkUserAttendance() throws Exception {
        int att = 0;
        int nonatt = 0;
        String attendance = "Marked total: " + att + " and non attendy:  " + nonatt;
        List<String> list = new ArrayList<>();
        list.add("string");
        MarkUserAttModel markUserAttModel = new MarkUserAttModel("gym", "vendor", list);
        List<Integer> aList = new ArrayList<>();
        aList.add(1);
        UserAttendance userAttendance = new UserAttendance("email", "gym", "vendor", 1, 1, aList, 3.5);
        List<UserAttendance> userAttendances = new ArrayList<>();
        userAttendances.add(userAttendance);
        Mockito.when(attendanceService.markUsersAttendance(markUserAttModel)).thenReturn(attendance);
        String content = objectMapper.writeValueAsString(markUserAttModel);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/mark/users/attendance").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Testing to fetch the user performance")
    void testUserPerformance() throws Exception {

        List<String> list = new ArrayList<>();
        list.add("string");
        List<Integer> aList = new ArrayList<>();
        aList.add(1);
        UserAttendance userAttendance = new UserAttendance("email", "gym", "vendor", 1, 1, aList, 3.5);
        List<UserAttendance> userAttendances = new ArrayList<>();
        userAttendances.add(userAttendance);
        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        Mockito.when(attendanceService.userPerfomance(userAttendance.getEmail(), userAttendance.getGym()))
                .thenReturn(list2);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/user-performance?email=email&gym=gym")).andExpect(status().isOk());
    }
}
