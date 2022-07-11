package com.fitness.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.entity.GymTime;
import com.fitness.app.model.GymClassModel;
import com.fitness.app.model.GymRepresent;
import com.fitness.app.service.FilterBySubscription;
import com.fitness.app.service.GymService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class GymControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;

    GymClassModel gymClassModel;
    List<GymClassModel> gymClassModels = new ArrayList<>();
    GymAddressClass gymAddressClass = new GymAddressClass();
    GymTime gymTime = new GymTime();
    GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass();
    List<String> workout = new ArrayList<>();

    GymClass gymClass;
    List<GymClass> gymClasses = new ArrayList<>();

    GymRepresent gymRepresent = new GymRepresent();
    List<GymRepresent> gymRepresents = new ArrayList<>();

    @MockBean
    private GymService gymService;

    @MockBean
    private FilterBySubscription filterBySubscription;

    @Autowired
    GymController gymController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(gymController).build();

        gymClassModel = new GymClassModel("priyanshi.chaturvedi@nineleaps.com", "Fitness",
                gymAddressClass, workout, gymTime, gymSubscriptionClass, 9685903290L, 100);
        gymClassModels.add(gymClassModel);

        gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);

        gymAddressClass = new GymAddressClass("1", 12345.0, 67890.1, "24cd", "Bangalore");
    }

    @Test
    void addNewGym() throws Exception {
        String content = objectMapper.writeValueAsString(gymClassModel);

        when(gymService.addNewGym(gymClassModel)).thenReturn(gymClass);

        mockMvc.perform(MockMvcRequestBuilders.put("/add/gym").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void getAllGym() throws Exception {
        when(gymService.getAllGym()).thenReturn(gymClasses);

        mockMvc.perform(MockMvcRequestBuilders.get("/gym/all").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void getAllOfVendor() throws Exception {
        when(gymService.getGymByVendorEmail(gymClass.getEmail())).thenReturn(gymRepresents);

        mockMvc.perform(MockMvcRequestBuilders.get("/gym/email/priyanshi.chaturvedi@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void editGym() throws Exception {
        String content = objectMapper.writeValueAsString(gymClassModel);

        when(gymService.editGym(gymClassModel, gymClass.getId())).thenReturn(gymClass);

        mockMvc.perform(MockMvcRequestBuilders.put("/gym/edit/1").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void getAddress() throws Exception {
        when(gymService.findTheAddress(gymClass.getId())).thenReturn(gymAddressClass);

        mockMvc.perform(MockMvcRequestBuilders.get("/gym/address/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getGymById() throws Exception {
        when(gymService.getGymByGymId(gymClass.getId())).thenReturn(gymRepresent);

        mockMvc.perform(MockMvcRequestBuilders.get("/gym/id/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void deletingEvery() throws Exception {
        when(gymService.wipingAll()).thenReturn("done");

        mockMvc.perform(MockMvcRequestBuilders.delete("/gym/delete/every").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getGymByGymName() throws Exception {
        when(gymService.getGymByGymName(gymClass.getName())).thenReturn(gymClass);

        mockMvc.perform(MockMvcRequestBuilders.get("/gym/gymName/Fitness").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getGYmByCity() throws Exception {
        when(gymService.getGymByCity(gymAddressClass.getCity())).thenReturn(gymRepresents);

        mockMvc.perform(MockMvcRequestBuilders.get("/gym/city/Bangalore").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void filterMonthly() throws Exception {
        String content = objectMapper.writeValueAsString(gymClassModels);

        when(filterBySubscription.filterByMonthly(100, gymClassModels)).thenReturn(gymClassModels);

        mockMvc.perform(MockMvcRequestBuilders.get("/filter/subscription/monthly/100").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void filterQuarterly() throws Exception {
        String content = objectMapper.writeValueAsString(gymClassModels);

        when(filterBySubscription.filterByQuarterly(300, gymClassModels)).thenReturn(gymClassModels);

        mockMvc.perform(MockMvcRequestBuilders.get("/filter/subscription/quarterly/200").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }

    @Test
    void filterHalfYearly() throws Exception {
        String content = objectMapper.writeValueAsString(gymClassModels);

        when(filterBySubscription.filterByHalfYearly(600, gymClassModels)).thenReturn(gymClassModels);

        mockMvc.perform(MockMvcRequestBuilders.get("/filter/subscription/halfYearly/500").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }

    @Test
    void filterYearly() throws Exception {
        String content = objectMapper.writeValueAsString(gymClassModels);

        when(filterBySubscription.filterByYearly(1200, gymClassModels)).thenReturn(gymClassModels);

        mockMvc.perform(MockMvcRequestBuilders.get("/filter/subscription/yearly/1000").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }

    @Test
    void filterOneWorkout() throws Exception {
        String content = objectMapper.writeValueAsString(gymClassModels);

        when(filterBySubscription.filterByOneWorkout(300, gymClassModels)).thenReturn(gymClassModels);

        mockMvc.perform(MockMvcRequestBuilders.get("/filter/subscription/oneWorkout/100").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());
    }
}