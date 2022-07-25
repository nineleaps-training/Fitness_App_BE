package com.fitness.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.entity.UserAttendance;
import com.fitness.app.model.MarkUserAttModel;
import com.fitness.app.service.AttendanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class AttendanceControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;

    MarkUserAttModel markUserAttModel;
    UserAttendance userAttendance;
    List<Integer> attendanceList = new ArrayList<>();
    List<String> users = new ArrayList<>();

    @MockBean
    private AttendanceService attendanceService;

    @Autowired
    AttendanceController attendanceController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(attendanceController).build();

        users.add("Aarohi");
        markUserAttModel = new MarkUserAttModel("Fitness", "Priyanshi", users);
        attendanceList.add(1);
        userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 2, 1, attendanceList, 4.2);


    }

    @Test
    void markUserAttendance() throws Exception {
        String content = objectMapper.writeValueAsString(markUserAttModel);

        when(attendanceService.markUsersAttendance(markUserAttModel)).thenReturn(markUserAttModel.getGym());

        mockMvc.perform(MockMvcRequestBuilders.put("/attendance/mark/users").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void userPerformance() throws Exception {
        when(attendanceService.userPerformance(userAttendance.getEmail(), userAttendance.getGym())).thenReturn(attendanceList);

        mockMvc.perform(MockMvcRequestBuilders.get("/attendance/userPerformance?email=priyanshi.chaturvedi@nineleaps.com&gym=Fitness").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }
}