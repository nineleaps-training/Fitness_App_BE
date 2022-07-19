package com.fitness.app.service;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.entity.GymTime;
import com.fitness.app.model.GymClassModel;
import com.fitness.app.model.GymRepresentModel;
import com.fitness.app.repository.AddGymRepo;
import com.fitness.app.repository.GymAddressRepo;
import com.fitness.app.repository.GymSubscriptionRepo;
import com.fitness.app.repository.GymTimeRepo;

@ExtendWith(MockitoExtension.class)
class GymServiceTest {

    @Mock
    AddGymRepo addGymRepository;

    @Mock
    GymTimeRepo gymTimeRepo;

    @Mock
    GymSubscriptionRepo gymSubscriptionRepo;

    @Mock
    GymAddressRepo gymAddressRepo;

    @Mock
    RatingService ratingService;

    @InjectMocks
    GymService gymService;

    GymClassModel gymClassModel;
    GymTime gymTime;
    GymAddressClass gymAddressClass;
    GymSubscriptionClass gymSubscriptionClass;

    @Test
    @DisplayName("Testing for adding new gym")
    void testAddNewGym() {
        gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        long l = 12345;
        gymTime = new GymTime("GM1", "yes", "yes", "yes");
        gymSubscriptionClass = new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1);
        gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime,
                gymSubscriptionClass, l, 20);
        GymClass gymClass = new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, l, 3.5, 20);
        when(addGymRepository.findByName(gymClassModel.getMGymname())).thenReturn(gymClass);
        when(addGymRepository.count()).thenReturn(anyLong());
        GymClass gymClass1 = gymService.addNewGym(gymClassModel);
        Assertions.assertEquals(gymClass.getId(), gymClass1.getId());

    }

    @Test
    @DisplayName("Testing of adding new gym when returned null")
    void testAddNewGymwithNull() {
        gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        long l = 12345;
        gymTime = new GymTime("GM1", "yes", "yes", "yes");
        gymSubscriptionClass = new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1);
        gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime,
                gymSubscriptionClass, l, 20);
        GymClass gymClass2 = new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, 12345L, null, 20);
        when(addGymRepository.findByName(gymClassModel.getMGymname())).thenReturn(null);
        GymClass gymClass3 = gymService.addNewGym(gymClassModel);
        Assertions.assertEquals(gymClass2, gymClass3);
    }

    @Test
    @DisplayName("Testing of editing the gym details")
    void testEditGym() {

        List<String> workout = new ArrayList<>();
        workout.add("zumba");
        long l = 1234;
        List<String> services = new ArrayList<>();
        services.add("zumba");
        List<Integer> attendance = new ArrayList<>();
        attendance.add(3);
        GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
        GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1);
        gymAddressClass = new GymAddressClass("GM1", 27.13214, 24.14241, "Bhatar", "Surat");
        gymTime = new GymTime("GM1", "yes", "yes", "yes");
        gymSubscriptionClass = new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1);
        gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime,
                gymSubscriptionClass, l, 20);
        GymClass gymClass = new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, l, 3.5, 20);
        GymRepresentModel gymRepresnt = new GymRepresentModel("GM1", "pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        Optional<GymClass> optional = Optional
                .of(new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, l, 3.5, 20));
        List<GymAddressClass> list = new ArrayList<>();
        list.add(gymAddressClass);
        List<GymRepresentModel> list3 = new ArrayList<>();
        list3.add(gymRepresnt);
        List<GymClass> list4 = new ArrayList<>();
        list4.add(gymClass);

        when(addGymRepository.findById(gymClass.getId())).thenReturn(optional);
        GymClass gymClass2 = gymService.editGym(gymClassModel, gymClass.getId());
        Assertions.assertEquals(gymClass, gymClass2);
    }

    @Test
    @DisplayName("Testing of adding editing new gym when returned null")
    void testEditGymwithNull() {

        List<String> workout = new ArrayList<>();
        workout.add("zumba");
        long l = 1234;
        List<String> services = new ArrayList<>();
        services.add("zumba");
        List<Integer> attendance = new ArrayList<>();
        attendance.add(3);
        GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
        GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1);
        gymAddressClass = new GymAddressClass("GM1", 27.13214, 24.14241, "Bhatar", "Surat");
        gymTime = new GymTime("GM1", "yes", "yes", "yes");
        gymSubscriptionClass = new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1);
        gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime,
                gymSubscriptionClass, l, 20);
        GymClass gymClass = new GymClass();
        GymRepresentModel gymRepresnt = new GymRepresentModel("GM1", "pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        Optional<GymClass> optional = Optional.empty();
        List<GymAddressClass> list = new ArrayList<>();
        list.add(gymAddressClass);
        List<GymRepresentModel> list3 = new ArrayList<>();
        list3.add(gymRepresnt);
        List<GymClass> list4 = new ArrayList<>();
        list4.add(gymClass);
        GymClassModel gymClassModel = new GymClassModel();
        when(addGymRepository.findById(gymClass.getId())).thenReturn(optional);
        GymClass gymClass2 = gymService.editGym(gymClassModel, gymClass.getId());
        Assertions.assertEquals(gymClass, gymClass2);
    }

    @Test
    @DisplayName("Testing for fetching the gym address")
    void testFindTheAddress() {

        List<String> workout = new ArrayList<>();
        workout.add("zumba");
        long l = 1234;
        List<String> services = new ArrayList<>();
        services.add("zumba");
        List<Integer> attendance = new ArrayList<>();
        attendance.add(3);
        GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
        GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1);
        gymAddressClass = new GymAddressClass("GM1", 27.13214, 24.14241, "Bhatar", "Surat");
        gymTime = new GymTime("GM1", "yes", "yes", "yes");
        gymSubscriptionClass = new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1);
        gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime,
                gymSubscriptionClass, l, 20);
        GymClass gymClass = new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, l, 3.5, 20);
        GymRepresentModel gymRepresnt = new GymRepresentModel("GM1", "pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        Optional<GymAddressClass> optional2 = Optional
                .of(new GymAddressClass("GM1", 27.13214, 24.14241, "Bhatar", "Surat"));
        List<GymAddressClass> list = new ArrayList<>();
        list.add(gymAddressClass);
        List<GymRepresentModel> list3 = new ArrayList<>();
        list3.add(gymRepresnt);
        List<GymClass> list4 = new ArrayList<>();
        list4.add(gymClass);

        when(gymAddressRepo.findById(gymAddressClass.getId())).thenReturn(optional2);

        GymAddressClass gymAddressClass2 = gymService.findTheAddress(gymAddressClass.getId());
        Assertions.assertEquals(gymAddressClass, gymAddressClass2);

    }

    @Test
    @DisplayName("Testing for fetching the gym address when returned null")
    void testFindTheAddresswithNull() {

        GymAddressClass gymAddressClass = new GymAddressClass();
        Optional<GymAddressClass> optional2 = Optional.empty();
        when(gymAddressRepo.findById(gymAddressClass.getId())).thenReturn(optional2);
        GymAddressClass gymAddressClass2 = gymService.findTheAddress(gymAddressClass.getId());
        Assertions.assertEquals(gymAddressClass, gymAddressClass2);

    }

    @Test
    @DisplayName("Testing to fetch all the registered gyms from the database")
    void testGetAllGym() {

        List<String> workout = new ArrayList<>();
        workout.add("zumba");
        long l = 1234;
        List<String> services = new ArrayList<>();
        services.add("zumba");
        List<Integer> attendance = new ArrayList<>();
        attendance.add(3);
        GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
        GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1);
        gymAddressClass = new GymAddressClass("GM1", 27.13214, 24.14241, "Bhatar", "Surat");
        gymTime = new GymTime("GM1", "yes", "yes", "yes");
        gymSubscriptionClass = new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1);
        gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime,
                gymSubscriptionClass, l, 20);
        GymClass gymClass = new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, l, 3.5, 20);
        GymRepresentModel gymRepresnt = new GymRepresentModel("GM1", "pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymAddressClass> list = new ArrayList<>();
        list.add(gymAddressClass);
        List<GymRepresentModel> list3 = new ArrayList<>();
        list3.add(gymRepresnt);
        List<GymClass> list4 = new ArrayList<>();
        list4.add(gymClass);
        when(addGymRepository.findAll()).thenReturn(list4);
        List<GymClass> list2 = gymService.getAllGym();
        Assertions.assertEquals(list4, list2);
    }

    @Test
    @DisplayName("Testing to fetch the registered gyms by their cities")
    void testGetGymByCity() {

        List<String> workout = new ArrayList<>();
        workout.add("zumba");
        long l = 1234;
        List<String> services = new ArrayList<>();
        services.add("zumba");
        List<Integer> attendance = new ArrayList<>();
        attendance.add(3);
        GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
        GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1);
        gymAddressClass = new GymAddressClass("GM1", 27.13214, 24.14241, "Bhatar", "Surat");
        gymTime = new GymTime("GM1", "yes", "yes", "yes");
        gymSubscriptionClass = new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1);
        gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime,
                gymSubscriptionClass, l, 20);
        GymClass gymClass = new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, l, 3.5, 20);
        GymRepresentModel gymRepresnt = new GymRepresentModel("GM1", "pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        Optional<GymClass> optional = Optional
                .of(new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, l, 3.5, 20));
        Optional<GymTime> optional3 = Optional.of(new GymTime("GM1", "yes", "yes", "yes"));
        Optional<GymSubscriptionClass> optional4 = Optional.of(new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1));
        List<GymAddressClass> list = new ArrayList<>();
        list.add(gymAddressClass);
        List<GymRepresentModel> list3 = new ArrayList<>();
        list3.add(gymRepresnt);
        when(gymAddressRepo.findByCity(gymAddressClass.getCity())).thenReturn(list);
        when(addGymRepository.findById(gymClass.getId())).thenReturn(optional);
        when(gymTimeRepo.findById(gymClass.getId())).thenReturn(optional3);
        when(gymSubscriptionRepo.findById(gymClass.getId())).thenReturn(optional4);
        List<GymRepresentModel> list2 = gymService.getGymByCity(gymAddressClass.getCity());
        Assertions.assertEquals(list3, list2);

    }

    @Test
    @DisplayName("Testing to fetch the registered gyms by their cities when null is returned")
    void testGetGymByCityAddresswithNull() {
        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        long l = 1234;
        GymAddressClass gymAddressClass = new GymAddressClass("GM1", 27.13214, 24.14241, "address", "city");
        List<GymAddressClass> gymAddressClasses = new ArrayList<>();
        gymAddressClasses.add(gymAddressClass);
        GymClass gymClass = new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, l, 3.5, 20);
        GymRepresentModel gymRepresnt = new GymRepresentModel("GM1", "pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresentModel> list = new ArrayList<>();
        list.add(gymRepresnt);
        Optional<GymTime> optional7 = Optional.empty();
        Optional<GymSubscriptionClass> optional8 = Optional.empty();
        Optional<GymClass> optional = Optional
                .of(new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, l, 3.5, 20));
        when(addGymRepository.findById(gymClass.getId())).thenReturn(optional);
        when(gymAddressRepo.findByCity(gymAddressClass.getCity())).thenReturn(gymAddressClasses);
        when(gymTimeRepo.findById(gymClass.getId())).thenReturn(optional7);
        when(gymSubscriptionRepo.findById(gymClass.getId())).thenReturn(optional8);
        List<GymRepresentModel> list2 = gymService.getGymByCity(gymAddressClass.getCity());
        Assertions.assertEquals(list, list2);
    }

    @Test
    @DisplayName("Testing to fetch the registered gyms by their cities when returned null")
    void testGetGymByCitywithNull() {
        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        GymAddressClass gymAddressClass = new GymAddressClass();
        List<GymAddressClass> gymAddressClasses = new ArrayList<>();
        gymAddressClasses.add(gymAddressClass);
        GymClass gymClass = new GymClass();
        GymRepresentModel gymRepresnt = new GymRepresentModel();
        List<GymRepresentModel> list = new ArrayList<>();
        list.add(gymRepresnt);
        Optional<GymClass> optional = Optional.empty();
        when(addGymRepository.findById(gymClass.getId())).thenReturn(optional);
        when(gymAddressRepo.findByCity(gymAddressClass.getCity())).thenReturn(gymAddressClasses);
        List<GymRepresentModel> list2 = gymService.getGymByCity(gymAddressClass.getCity());
        Assertions.assertEquals(list, list2);
    }

    @Test
    @DisplayName("Testing to fetch the gym from it's id")
    void testGetGymByGymId() {

        List<String> workout = new ArrayList<>();
        workout.add("zumba");
        long l = 1234;
        List<String> services = new ArrayList<>();
        services.add("zumba");
        List<Integer> attendance = new ArrayList<>();
        attendance.add(3);
        GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
        GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
        gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
        gymTime = new GymTime("GM1", "yes", "yes", "yes");
        gymSubscriptionClass = new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1);
        gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime,
                gymSubscriptionClass, l, 20);
        GymClass gymClass = new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, l, 3.5, 20);
        GymRepresentModel gymRepresnt = new GymRepresentModel("GM1", "pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        Optional<GymClass> optional = Optional
                .of(new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, l, 3.5, 20));
        Optional<GymAddressClass> optional2 = Optional
                .of(new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat"));
        Optional<GymTime> optional3 = Optional.of(new GymTime("GM1", "yes", "yes", "yes"));
        Optional<GymSubscriptionClass> optional4 = Optional.of(new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1));
        when(addGymRepository.findById(gymClass.getId())).thenReturn(optional);
        when(gymAddressRepo.findById(gymClass.getId())).thenReturn(optional2);
        when(gymTimeRepo.findById(gymClass.getId())).thenReturn(optional3);
        when(gymSubscriptionRepo.findById(gymClass.getId())).thenReturn(optional4);
        GymRepresentModel gymRepresnt2 = gymService.getGymByGymId(gymClass.getId());
        Assertions.assertEquals(gymRepresnt, gymRepresnt2);
    }

    @Test
    @DisplayName("Testing to fetch the gym from it's id when returned null")
    void testGetGymByGymIdwithNull() {
        GymRepresentModel gymRepresnt = null;
        GymClass gyclass = new GymClass();
        Optional<GymClass> optional5 = Optional.empty();
        when(addGymRepository.findById(gyclass.getId())).thenReturn(optional5);
        GymRepresentModel gymRepresnt1 = gymService.getGymByGymId(gyclass.getId());
        Assertions.assertEquals(gymRepresnt, gymRepresnt1);

    }

    @Test
    @DisplayName("Testing to fetch the gym from it's address after null is returned")
    void testGymAddresswithNull() {
        List<String> workout = new ArrayList<>();
        workout.add("zumba");
        long l = 1234;
        List<String> services = new ArrayList<>();
        services.add("zumba");
        List<Integer> attendance = new ArrayList<>();
        attendance.add(3);
        GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
        GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
        gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
        gymTime = new GymTime("GM1", "yes", "yes", "yes");
        gymSubscriptionClass = new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1);
        gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime,
                gymSubscriptionClass, l, 20);
        GymClass gymClass = new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, l, 3.5, 20);
        GymRepresentModel gymRepresnt = new GymRepresentModel("GM1", "pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        Optional<GymAddressClass> optional6 = Optional.empty();
        Optional<GymTime> optional7 = Optional.empty();
        Optional<GymSubscriptionClass> optional8 = Optional.empty();
        Optional<GymClass> optional = Optional
                .of(new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, l, 3.5, 20));
        when(addGymRepository.findById(gymClass.getId())).thenReturn(optional);
        when(gymAddressRepo.findById(gymClass.getId())).thenReturn(optional6);
        when(gymTimeRepo.findById(gymClass.getId())).thenReturn(optional7);
        when(gymSubscriptionRepo.findById(gymClass.getId())).thenReturn(optional8);
        GymRepresentModel gymRepresnt2 = gymService.getGymByGymId(gymClass.getId());
        Assertions.assertEquals(gymRepresnt.getId(), gymRepresnt2.getId());

    }

    @Test
    @DisplayName("Testing to fecth the registered gyms by their names")
    void testGetGymByGymName() {
        List<String> workout = new ArrayList<>();
        workout.add("zumba");
        long l = 1234;
        List<String> services = new ArrayList<>();
        services.add("zumba");
        List<Integer> attendance = new ArrayList<>();
        attendance.add(3);
        GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
        GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
        gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
        gymTime = new GymTime("GM1", "yes", "yes", "yes");
        gymSubscriptionClass = new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1);
        gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime,
                gymSubscriptionClass, l, 20);
        GymClass gymClass = new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, l, 3.5, 20);
        when(addGymRepository.findByName(gymClass.getName())).thenReturn(gymClass);
        GymClass gymClass2 = gymService.getGymByGymName(gymClass.getName());
        Assertions.assertEquals(gymClass, gymClass2);

    }

    @Test
    @DisplayName("Testing to fetch all the registered gyms of the vendor")
    void testGetGymByVendorEmail() {

        List<String> workout = new ArrayList<>();
        workout.add("zumba");
        long l = 1234;
        List<String> services = new ArrayList<>();
        services.add("zumba");
        List<Integer> attendance = new ArrayList<>();
        attendance.add(3);
        GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
        GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
        gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
        gymTime = new GymTime("GM1", "yes", "yes", "yes");
        gymSubscriptionClass = new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1);
        gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass, workoutList, gymTime,
                gymSubscriptionClass, l, 20);
        GymClass gymClass = new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, l, 3.5, 20);
        GymRepresentModel gymRepresnt = new GymRepresentModel("GM1", "pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        Optional<GymAddressClass> optional2 = Optional
                .of(new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat"));
        Optional<GymTime> optional3 = Optional.of(new GymTime("GM1", "yes", "yes", "yes"));
        Optional<GymSubscriptionClass> optional4 = Optional.of(new GymSubscriptionClass("GM1", 1, 1, 1, 1, 1, 1));
        List<GymRepresentModel> list = new ArrayList<>();
        list.add(gymRepresnt);
        List<GymClass> list2 = new ArrayList<>();
        list2.add(gymClass);
        when(addGymRepository.findByEmail(gymClass.getEmail())).thenReturn(list2);
        when(gymAddressRepo.findById(gymClass.getId())).thenReturn(optional2);
        when(gymSubscriptionRepo.findById(gymClass.getId())).thenReturn(optional4);
        when(gymTimeRepo.findById(gymClass.getId())).thenReturn(optional3);
        List<GymRepresentModel> list3 = gymService.getGymByVendorEmail(gymClass.getEmail());
        Assertions.assertEquals(list.size(), list3.size());

    }

    @Test
    @DisplayName("Testing to fetch all the registered gyms of the vendor when returned null")
    void testGetGymByVendorEmailwithNull() {
        GymClass gymClass = new GymClass();
        GymRepresentModel gymRepresnt = new GymRepresentModel();
        Optional<GymAddressClass> optional2 = Optional.empty();
        Optional<GymTime> optional3 = Optional.empty();
        Optional<GymSubscriptionClass> optional4 = Optional.empty();
        List<GymRepresentModel> list = new ArrayList<>();
        list.add(gymRepresnt);
        List<GymClass> list2 = new ArrayList<>();
        list2.add(gymClass);
        when(addGymRepository.findByEmail(gymClass.getEmail())).thenReturn(list2);
        when(gymAddressRepo.findById(gymClass.getId())).thenReturn(optional2);
        when(gymSubscriptionRepo.findById(gymClass.getId())).thenReturn(optional4);
        when(gymTimeRepo.findById(gymClass.getId())).thenReturn(optional3);
        List<GymRepresentModel> list3 = gymService.getGymByVendorEmail(gymClass.getEmail());
        Assertions.assertEquals(list.size(), list3.size());

    }

    @Test
    @DisplayName("Testing of wiping all the gyms from the database")
    void testWipingAll() {

        String d = "done";
        String d1 = gymService.wipingAll();
        Assertions.assertEquals(d, d1);

    }
}
