package com.fitness.app.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.entity.GymTime;
import com.fitness.app.entity.UserOrder;
import com.fitness.app.model.GymRepresnt;
import com.fitness.app.model.UserPerfomanceModel;
import com.fitness.app.service.UserOrderService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserOrderControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    UserOrderController userOrderController;

    @Mock
    UserOrderService userOrderService;

    ObjectMapper objectMapper = 
    new ObjectMapper().registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    
    @BeforeEach
    public void setup()
    {
        this.mockMvc=MockMvcBuilders.standaloneSetup(userOrderController).build();
    }
    @Test
    void testAllMyUsers() throws Exception {
        List<Integer> list=new ArrayList<>();
        list.add(4);
        UserPerfomanceModel userPerfomanceModel=new UserPerfomanceModel("Pankaj Jain", "pankaj.jain@nineleaps.com", "GM6", "vendor", list, 3.5);
        Set<UserPerfomanceModel> set=new HashSet<>();
        set.add(userPerfomanceModel);
        Mockito.when(userOrderService.allMyUser(userPerfomanceModel.getGym())).thenReturn(set);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/my/users/GM6")).andExpect(status().isOk());

    }

    @Test
    void testBookedGym() throws Exception {
        GymAddressClass gymAddressClass=new GymAddressClass("GM6",27.13214,24.14241,"Bhatar","Surat");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        long l=12345;
        GymTime gymTime=new GymTime("GM6","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("GM6", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 2.5, 20);
        List<GymRepresnt> list=new ArrayList<>();
        list.add(gymRepresnt);

        Mockito.when(userOrderService.bookedGym(gymRepresnt.getEmail())).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/booked/gyms/email")).andExpect(status().isOk());

    }

    @Test
    void testCheckUserCanOrder() throws Exception {
        LocalDate localDate=LocalDate.of(2022, 6, 24);
        LocalTime localTime=LocalTime.of(12, 32, 4);
        List<String> services=new ArrayList<>();
        services.add("zumba");

        UserOrder userOrder=new UserOrder("GM6", "email", "gym", services, "subscription", "slot", 1234, "booked", "status", "paymentId", "receipt", localDate, localTime);
    
        Mockito.when(userOrderService.canOrder(userOrder.getEmail())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/check-user-order/email")).andExpect(status().isOk());

    }

    @Test
    void testOrderHistory() throws Exception {

        LocalDate localDate=LocalDate.of(2022, 6, 24);
        LocalTime localTime=LocalTime.of(12, 32, 4);
        List<String> services=new ArrayList<>();
        services.add("zumba");
        UserOrder userOrder=new UserOrder("GM6", "email", "gym", services, "subscription", "slot", 1234, "booked", "status", "paymentId", "receipt", localDate, localTime);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        Mockito.when(userOrderService.orderListOrder(userOrder.getEmail())).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/order/history/email")).andExpect(status().isOk());

    }

    @Test
    void testOrderNow() throws Exception {

        LocalDate localDate=LocalDate.of(2022, 6, 24);
        LocalTime localTime=LocalTime.of(12, 32, 4);
        List<String> services=new ArrayList<>();
        services.add("zumba");
        UserOrder userOrder=new UserOrder("GM6", "pankaj.jain@nineleaps.com", "gym", services, "subscription", "slot", 1234, "booked", "status", "paymentId", "receipt", localDate, localTime);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        String content=objectMapper.writeValueAsString(userOrder);
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/order/now").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isCreated());

    }

    @Test
    void testPedingOrerList() throws Exception {
        
        LocalDate localDate=LocalDate.of(2022, 6, 24);
        LocalTime localTime=LocalTime.of(12, 32, 4);
        List<String> services=new ArrayList<>();
        services.add("zumba");

        UserOrder userOrder=new UserOrder("GM6", "email", "gym", services, "subscription", "slot", 1234, "booked", "status", "paymentId", "receipt", localDate, localTime);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        Mockito.when(userOrderService.pendingListOrder(userOrder.getEmail())).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/pending/order/email")).andExpect(status().isOk());
    }

    @Test
    void testUpdatingOrder() throws Exception {
        
        LocalDate localDate=LocalDate.of(2022, 6, 24);
        LocalTime localTime=LocalTime.of(12, 32, 4);
        List<String> services=new ArrayList<>();
        services.add("zumba");
        UserOrder userOrder=new UserOrder("GM6", "email", "gym", services, "subscription", "slot", 1234, "booked", "status", "paymentId", "receipt", localDate, localTime);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        Map<String,String> data=new HashMap<>();
        data.put("order_id", "id");
        data.put("paymentId","paymentId");
        data.put("status","status");
        String content=objectMapper.writeValueAsString(data);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/update/order").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }
}
