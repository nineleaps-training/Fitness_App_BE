package com.fitness.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.entity.UserOrder;
import com.fitness.app.model.GymRepresent;
import com.fitness.app.model.UserOrderModel;
import com.fitness.app.model.UserPerformanceModel;
import com.fitness.app.service.UserOrderService;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserOrderControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;

    UserOrder userOrder;
    List<UserOrder> userOrders = new ArrayList<>();
    List<String> services = new ArrayList<>();
    UserOrderModel userOrderModel;
    Map<String, String> data = new HashMap<>();
    Set<UserPerformanceModel> userPerformanceModels = new HashSet<>();
    List<GymRepresent> gymRepresents = new ArrayList<>();


    @MockBean
    private UserOrderService userOrderService;

    @Autowired
    UserOrderController userOrderController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userOrderController).build();

        userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com",
                "Fitness", services, "monthly", "Evening", 500, "Expired",
                "created", "123abc", "112233", LocalDate.now(), LocalTime.now());
        userOrders.add(userOrder);

        userOrderModel = new UserOrderModel("priyanshi.chaturvedi@nineleaps.com", "Fitness", services, "monthly", 500, "Evening");
    }

    @Test
    void checkUserCanOrder() throws Exception {
        when(userOrderService.canOrder(userOrder.getEmail())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/check-user-order/priyanshi.chaturvedi@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void orderNow() throws Exception {
        String content = objectMapper.writeValueAsString(userOrderModel);

        when(userOrderService.orderNow(userOrderModel)).thenReturn(anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/order/now").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void updatingOrder() throws Exception {
        String content = objectMapper.writeValueAsString(data);

        when(userOrderService.updateOrder(data)).thenReturn(userOrder);

        mockMvc.perform(MockMvcRequestBuilders.put("/update/order").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void pendingOrderList() throws Exception {
        when(userOrderService.pendingListOrder(userOrder.getEmail())).thenReturn(userOrders);

        mockMvc.perform(MockMvcRequestBuilders.get("/pending/order/priyanshi.chaturvedi@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void orderHistory() throws Exception {
        when(userOrderService.orderListOrder(userOrder.getEmail())).thenReturn(userOrders);

        mockMvc.perform(MockMvcRequestBuilders.get("/order/history/priyanshi.chaturvedi@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void allMyUsers() throws Exception {
        when(userOrderService.allMyUser(userOrder.getId())).thenReturn(userPerformanceModels);

        mockMvc.perform(MockMvcRequestBuilders.get("/my/users/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void bookedGym() throws Exception {
        when(userOrderService.bookedGym(userOrder.getEmail())).thenReturn(gymRepresents);

        mockMvc.perform(MockMvcRequestBuilders.get("/booked/gyms/priyanshi.chaturvedi@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}