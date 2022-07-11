package com.fitness.app.controller;

import java.util.ArrayList;
import java.util.List;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.entity.GymTime;
import com.fitness.app.model.GymClassModel;
import com.fitness.app.model.GymRepresnt;
import com.fitness.app.service.FilterBySubscription;
import com.fitness.app.service.GymService;

@ExtendWith(MockitoExtension.class)
class GymControllerTest {

    List<String> aList=new ArrayList<>(List.of("Hello"));
    ObjectMapper objectMapper=new ObjectMapper();
    

    private MockMvc mockMvc;

    @InjectMocks
    GymController gymController;

    @Mock
    GymService gymService;

    @Mock
    FilterBySubscription filterBySubscription;

    List<GymRepresnt> gymRepresnts=new ArrayList<>();

    GymRepresnt gymRepresnt;

    GymAddressClass gymAddressClass;

    long l=123;

    GymClass gymClass=new GymClass("GM6","pankaj.jain@nineleaps.com","Pankaj Jain",aList,l,2.3,15);
    @BeforeEach
    public void setup()
    {
        this.mockMvc=MockMvcBuilders.standaloneSetup(gymController).build();
    }
    
    @Test
    void testAddNewGym() throws Exception {

        GymAddressClass gymAddressClass=new GymAddressClass("GM6",27.13214,24.14241,"Bhatar","Surat");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        long l=12345;
        GymTime gymTime=new GymTime("GM6","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,2,3,4,5,6);
        GymClassModel gymClassModel=new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 20);
        String content=objectMapper.writeValueAsString(gymClassModel);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/add/gym").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void testDeletingEvery() throws Exception {
 
        Mockito.when(gymService.wipingAll()).thenReturn("Deleted Sucessfully");
        mockMvc.perform(MockMvcRequestBuilders
        .delete("/v1/gym/delete/every").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void testEditGym() throws Exception {

        GymAddressClass gymAddressClass=new GymAddressClass("GM6",27.13214,24.14241,"Bhatar","Surat");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        long l=12345;
        GymTime gymTime=new GymTime("GM6","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,2,3,4,5,6);
        GymClassModel gymClassModel=new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 20);
        String content=objectMapper.writeValueAsString(gymClassModel);
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/gym/edit/GM6").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void testFilterHalfYearly() throws Exception {
        
        GymAddressClass gymAddressClass=new GymAddressClass("GM6",27.13214,24.14241,"Bhatar","Surat");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        long l=12345;
        GymTime gymTime=new GymTime("GM6","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,2,3,4,5,6);
        GymClassModel gymClassModel=new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 20);
        List<GymClassModel> list=new ArrayList<>();
        list.add(gymClassModel);
        String content=objectMapper.writeValueAsString(list);
        Mockito.when(filterBySubscription.filterByHalfYearly(gymClassModel.getMSubscription().getHalf(),list)).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/filter/subscription/halfYearly/4").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());

    }

    @Test
    void testFilterMonthly() throws Exception {

        GymAddressClass gymAddressClass=new GymAddressClass("GM6",27.13214,24.14241,"Bhatar","Surat");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        long l=12345;
        GymTime gymTime=new GymTime("GM6","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,2,3,4,5,6);
        GymClassModel gymClassModel=new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 20);
        List<GymClassModel> list=new ArrayList<>();
        list.add(gymClassModel);
        String content=objectMapper.writeValueAsString(list);
        Mockito.when(filterBySubscription.filterByMonthly(gymClassModel.getMSubscription().getMonthly(),list)).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/filter/subscription/monthly/2").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());


    }

    @Test
    void testFilterOneWorkout() throws Exception {

        GymAddressClass gymAddressClass=new GymAddressClass("GM6",27.13214,24.14241,"Bhatar","Surat");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        long l=12345;
        GymTime gymTime=new GymTime("GM6","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,2,3,4,5,6);
        GymClassModel gymClassModel=new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 20);
        List<GymClassModel> list=new ArrayList<>();
        list.add(gymClassModel);
        String content=objectMapper.writeValueAsString(list);
        Mockito.when(filterBySubscription.filterByOneWorkout(gymClassModel.getMSubscription().getOneWorkout(),list)).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/filter/subscription/oneWorkout/1").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());


    }

    @Test
    void testFilterQuarterly() throws Exception {

        GymAddressClass gymAddressClass=new GymAddressClass("GM6",27.13214,24.14241,"Bhatar","Surat");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        long l=12345;
        GymTime gymTime=new GymTime("GM6","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,2,3,4,5,6);
        GymClassModel gymClassModel=new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 20);
        List<GymClassModel> list=new ArrayList<>();
        list.add(gymClassModel);
        String content=objectMapper.writeValueAsString(list);
        Mockito.when(filterBySubscription.filterByQuarterly(gymClassModel.getMSubscription().getQuaterly(),list)).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/filter/subscription/quarterly/3").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());


    }

    @Test
    void testFilterYearly() throws Exception {
        
        GymAddressClass gymAddressClass=new GymAddressClass("GM6",27.13214,24.14241,"Bhatar","Surat");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        long l=12345;
        GymTime gymTime=new GymTime("GM6","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,2,3,4,5,6);
        GymClassModel gymClassModel=new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 20);
        List<GymClassModel> list=new ArrayList<>();
        list.add(gymClassModel);
        String content=objectMapper.writeValueAsString(list);
        Mockito.when(filterBySubscription.filterByYearly(gymClassModel.getMSubscription().getYearly(),list)).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/filter/subscription/yearly/5").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk());


    }

    @Test
    void testGetAddress() throws Exception {

        Mockito.when(gymService.findTheAddress("GM6")).thenReturn(gymAddressClass);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/gym/address/GM6").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


    }

    @Test
    void testGetAllGym() throws Exception {

        List<GymClass> gymClasses=new ArrayList<>();
        gymClasses.add(gymClass);
        Mockito.when(gymService.getAllGym()).thenReturn(gymClasses);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/gym/all").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void testGetAllOfVendor() throws Exception {
    
        Mockito.when(gymService.getGymByVendorEmail("pankaj.jain@nineleaps.com")).thenReturn(gymRepresnts);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/gym/email/pankaj.jain@nineleaps.com").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void testGetGYmByCity() throws Exception {

        GymAddressClass gymAddressClass=new GymAddressClass("GM6",27.13214,24.14241,"Bhatar","Surat");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        long l=12345;
        GymTime gymTime=new GymTime("GM6","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("GM6", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 2.5, 20);
        gymRepresnts.add(gymRepresnt);
        Mockito.when(gymService.getGymByCity(gymRepresnt.getGymAddress().getCity())).thenReturn(gymRepresnts);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/gym/city/Surat").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    void testGetGymByGymName() throws Exception {

        Mockito.when(gymService.getGymByGymName(gymClass.getName())).thenReturn(gymClass);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/gym/gymName/Pankaj Jain").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


    }

    @Test
    void testGetGymById() throws Exception {
        
        Mockito.when(gymService.getGymByGymId("GM6")).thenReturn(gymRepresnt);
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/gym/id/GM6").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


    }
}
