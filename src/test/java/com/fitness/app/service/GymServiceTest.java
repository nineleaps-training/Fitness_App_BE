package com.fitness.app.service;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.entity.GymTime;
import com.fitness.app.model.GymClassModel;
import com.fitness.app.model.GymRepresent;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.GymAddressRepo;
import com.fitness.app.repository.GymSubscriptionRepo;
import com.fitness.app.repository.GymTimeRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GymServiceTest {

    GymAddressClass gymAddressClass = new GymAddressClass();
    GymTime gymTime = new GymTime();
    GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass();
    List<String> workout = new ArrayList<>();

    @MockBean
    private AddGymRepository gymRepository;

    @MockBean
    private GymAddressRepo gymAddressRepo;

    @MockBean
    private GymTimeRepo gymTimeRepo;

    @MockBean
    private GymSubscriptionRepo gymSubscriptionRepo;

    @Autowired
    GymService gymService;


    @Test
    void returnExistingGymIfGymClassIsNotNull() {
        GymClassModel gymClassModel = new GymClassModel("priyanshi.chaturvedi@nineleaps.com", "Fitness",
                gymAddressClass, workout, gymTime, gymSubscriptionClass, 9685903290L, 100);

        GymClass gymClass = new GymClass("GM1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);

        when(gymRepository.findByName(gymClassModel.getGymName())).thenReturn(gymClass);

        String actual = gymService.addNewGym(gymClassModel).getEmail();
        assertEquals(gymClass.getEmail(), actual);
    }

    @Test
    void returnNewGymIfGymClassIsNull() {
        GymClassModel gymClassModel = new GymClassModel("priyanshi.chaturvedi@nineleaps.com", "Fitness",
                gymAddressClass, workout, gymTime, gymSubscriptionClass, 9685903290L, 100);

        GymClass gymClass = new GymClass();
        gymClass.setRate(4.2);
        GymClass newGym = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);

        when(gymRepository.findByName(gymClassModel.getGymName())).thenReturn(null);

        String actual = gymService.addNewGym(gymClassModel).getEmail();
        assertEquals(newGym.getEmail(), actual);
    }

    @Test
    void getGymByGymId() {
        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);
        GymAddressClass gymAddressClass = new GymAddressClass("1", 123456.0, 654321.0, "Koramangala", "Bangalore");
        GymRepresent gymRepresent = new GymRepresent("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", gymAddressClass, workout, gymTime, gymSubscriptionClass, 9685903290L, 4.2, 100);

        when(gymRepository.findById(gymClass.getId())).thenReturn(Optional.of(gymClass));
        when(gymAddressRepo.findById(gymAddressClass.getId())).thenReturn(Optional.of(gymAddressClass));
        when(gymTimeRepo.findById(gymTime.getId())).thenReturn(Optional.of(gymTime));
        when(gymSubscriptionRepo.findById(gymSubscriptionClass.getId())).thenReturn(Optional.of(gymSubscriptionClass));

        String actual = gymService.getGymByGymId(gymRepresent.getId()).getId();
        assertEquals(gymRepresent.getId(), actual);
    }

    @Test
    void getGymByGymIdIfGymClassOptionalIsNotPresent() {
        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);
        GymAddressClass gymAddressClass = new GymAddressClass("Add1", 123456.0, 654321.0, "Koramangala", "Bangalore");
        GymRepresent gymRepresent = new GymRepresent("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", gymAddressClass, workout, gymTime, gymSubscriptionClass, 9685903290L, 4.2, 100);

        Optional<GymClass> gymClassOptional = Optional.empty();
        Optional<GymAddressClass> gymAddressClassOptional = Optional.empty();
        Optional<GymTime> gymTimeOptional = Optional.empty();
        Optional<GymSubscriptionClass> gymSubscriptionClassOptional = Optional.empty();
        GymRepresent gymRepresent1 = new GymRepresent();

        when(gymRepository.findById(gymClass.getId())).thenReturn(gymClassOptional);
        when(gymAddressRepo.findById(gymAddressClass.getId())).thenReturn(gymAddressClassOptional);
        when(gymTimeRepo.findById(gymTime.getId())).thenReturn(gymTimeOptional);
        when(gymSubscriptionRepo.findById(gymSubscriptionClass.getId())).thenReturn(gymSubscriptionClassOptional);

        GymRepresent actual = gymService.getGymByGymId(gymRepresent.getId());
        assertEquals(gymRepresent1, actual);
    }

    @Test
    void getGymByGymIdIfGymAddressClassOptionalIsNotPresent() {
        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);
        GymAddressClass gymAddressClass = new GymAddressClass("Add1", 123456.0, 654321.0, "Koramangala", "Bangalore");
        GymRepresent gymRepresent = new GymRepresent("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", gymAddressClass, workout, gymTime, gymSubscriptionClass, 9685903290L, 4.2, 100);

        Optional<GymAddressClass> gymAddressClassOptional = Optional.empty();
        Optional<GymTime> gymTimeOptional = Optional.empty();
        Optional<GymSubscriptionClass> gymSubscriptionClassOptional = Optional.empty();
        GymRepresent gymRepresent1 = new GymRepresent();

        when(gymRepository.findById(gymClass.getId())).thenReturn(Optional.of(gymClass));
        when(gymAddressRepo.findById(gymAddressClass.getId())).thenReturn(gymAddressClassOptional);
        when(gymTimeRepo.findById(gymTime.getId())).thenReturn(gymTimeOptional);
        when(gymSubscriptionRepo.findById(gymSubscriptionClass.getId())).thenReturn(gymSubscriptionClassOptional);

        String actual = gymService.getGymByGymId(gymRepresent.getId()).getId();
        assertEquals(gymRepresent.getId(), actual);
    }

    @Test
    void getGymByGymIdIfGymTimeOptionalIsNotPresent() {
        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);
        GymAddressClass gymAddressClass = new GymAddressClass("Add1", 123456.0, 654321.0, "Koramangala", "Bangalore");
        GymRepresent gymRepresent = new GymRepresent("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", gymAddressClass, workout, gymTime, gymSubscriptionClass, 9685903290L, 4.2, 100);

        Optional<GymTime> gymTimeOptional = Optional.empty();
        Optional<GymSubscriptionClass> gymSubscriptionClassOptional = Optional.empty();

        when(gymRepository.findById(gymClass.getId())).thenReturn(Optional.of(gymClass));
        when(gymAddressRepo.findById(gymAddressClass.getId())).thenReturn(Optional.of(gymAddressClass));
        when(gymTimeRepo.findById(gymTime.getId())).thenReturn(gymTimeOptional);
        when(gymSubscriptionRepo.findById(gymSubscriptionClass.getId())).thenReturn(gymSubscriptionClassOptional);

        String actual = gymService.getGymByGymId(gymRepresent.getId()).getId();
        assertEquals(gymRepresent.getId(), actual);
    }

    @Test
    void getGymByGymIdIfGymSubscriptionOptionalIsNotPresent() {
        GymAddressClass gymAddressClass = new GymAddressClass();
        GymTime gymTime = new GymTime();
        GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass();
        List<String> workout = new ArrayList<>();

        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);
        GymRepresent gymRepresent = new GymRepresent("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", gymAddressClass, workout, gymTime, gymSubscriptionClass, 9685903290L, 4.2, 100);

        Optional<GymSubscriptionClass> gymSubscriptionClassOptional = Optional.empty();

        when(gymRepository.findById(gymClass.getId())).thenReturn(Optional.of(gymClass));
        when(gymAddressRepo.findById(gymAddressClass.getId())).thenReturn(Optional.of(gymAddressClass));
        when(gymTimeRepo.findById(gymTime.getId())).thenReturn(Optional.of(gymTime));
        when(gymSubscriptionRepo.findById(gymSubscriptionClass.getId())).thenReturn(gymSubscriptionClassOptional);

        String actual = gymService.getGymByGymId(gymRepresent.getId()).getId();
        assertEquals(gymRepresent.getId(), actual);
    }

    @Test
    void getGymByVendorEmail() {
        GymAddressClass gymAddressClass = new GymAddressClass("1", 123456.0, 654321.0, "Koramangala", "Bangalore");
        Optional<GymAddressClass> gymAddressClassOptional;
        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);
        List<GymClass> gymClasses = new ArrayList<>();
        gymClasses.add(gymClass);
        GymRepresent gymRepresent = new GymRepresent("1", "priyanshi.chaturvedi@nineleaps.com",
                "Fitness", gymAddressClass, workout, gymTime, gymSubscriptionClass, 9685903290L,
                4.2, 100);
        List<GymRepresent> gymRepresents = new ArrayList<>();
        gymRepresents.add(gymRepresent);

        when(gymRepository.findByEmail(gymClass.getEmail())).thenReturn(gymClasses);
        when(gymAddressRepo.findById(gymAddressClass.getId())).thenReturn(Optional.of(gymAddressClass));
        when(gymTimeRepo.findById(gymTime.getId())).thenReturn(Optional.ofNullable(gymTime));
        when(gymSubscriptionRepo.findById(gymSubscriptionClass.getId())).thenReturn(Optional.ofNullable(gymSubscriptionClass));

        String actual = gymService.getGymByVendorEmail(gymClass.getEmail()).get(0).getId();
        assertEquals(gymRepresents.get(0).getId(), actual);

    }

    @Test
    void getGymByVendorEmailIfOptionalIsNotPresent() {
        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);
        List<GymClass> gymClasses = new ArrayList<>();
        gymClasses.add(gymClass);
        GymRepresent gymRepresent = new GymRepresent("1", "priyanshi.chaturvedi@nineleaps.com",
                "Fitness", gymAddressClass, workout, gymTime, gymSubscriptionClass, 9685903290L,
                4.2, 100);
        List<GymRepresent> gymRepresents = new ArrayList<>();
        gymRepresents.add(gymRepresent);

        Optional<GymAddressClass> gymAddressClassOptional = Optional.empty();
        Optional<GymTime> gymTimeOptional = Optional.empty();
        Optional<GymSubscriptionClass> gymSubscriptionClassOptional = Optional.empty();

        when(gymRepository.findByEmail(gymClass.getEmail())).thenReturn(gymClasses);
        when(gymAddressRepo.findById(gymAddressClass.getId())).thenReturn(gymAddressClassOptional);
        when(gymTimeRepo.findById(gymTime.getId())).thenReturn(gymTimeOptional);
        when(gymSubscriptionRepo.findById(gymSubscriptionClass.getId())).thenReturn(gymSubscriptionClassOptional);

        String actual = gymService.getGymByVendorEmail(gymClass.getEmail()).get(0).getId();
        assertEquals(gymRepresents.get(0).getId(), actual);

    }

    @Test
    void findTheAddress() {
        GymAddressClass gymAddressClass = new GymAddressClass("Add1", 123456.0, 654321.0, "Koramangala", "Bangalore");

        when(gymAddressRepo.findById(gymAddressClass.getId())).thenReturn(Optional.of(gymAddressClass));

        GymAddressClass actual = gymService.findTheAddress(gymAddressClass.getId());
        assertEquals(gymAddressClass, actual);
    }

    @Test
    void returnNullIfGymAddressClassIsNull() {
        GymAddressClass gymAddressClass = new GymAddressClass();

        GymAddressClass actual = gymService.findTheAddress(gymAddressClass.getId());
        assertNull(actual);
    }

    @Test
    void getAllGym() {
        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);
        List<GymClass> gymClasses = new ArrayList<>();
        gymClasses.add(gymClass);

        when(gymRepository.findAll()).thenReturn(gymClasses);

        List<GymClass> actual = gymService.getAllGym();
        assertEquals(gymClasses, actual);
    }

    @Test
    void editGym() {
        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);
        GymAddressClass gymAddressClass = new GymAddressClass();
        GymClassModel gymClassModel = new GymClassModel("priyanshi.chaturvedi@nineleaps.com", "Fitness", gymAddressClass, workout, gymTime, gymSubscriptionClass, 9685903290L, 100);

        when(gymRepository.findById(gymClass.getId())).thenReturn(Optional.of(gymClass));

        GymClass actual = gymService.editGym(gymClassModel, gymClass.getId());
        assertEquals(gymClass, actual);
    }

    @Test
    void editGymIfOptionalIsNotPresent() {
        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, null, 100);
        Optional<GymClass> gymClassOptional = Optional.empty();
        GymAddressClass gymAddressClass = new GymAddressClass();
        GymClassModel gymClassModel = new GymClassModel("priyanshi.chaturvedi@nineleaps.com", "Fitness", gymAddressClass, workout, gymTime, gymSubscriptionClass, 9685903290L, 100);

        when(gymRepository.findById(gymClass.getId())).thenReturn(gymClassOptional);

        GymClass actual = gymService.editGym(gymClassModel, gymClass.getId());
        assertEquals(gymClass, actual);
    }

    @Test
    void wipingAll() {
        String actual = gymService.wipingAll();
        assertEquals("done", actual);
    }

    @Test
    void getGymByGymName() {
        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);

        when(gymRepository.findByName(gymClass.getName())).thenReturn(gymClass);

        GymClass actual = gymService.getGymByGymName(gymClass.getName());
        assertEquals(gymClass, actual);
    }

    @Test
    void getGymByCity() {
        GymAddressClass gymAddressClass = new GymAddressClass("1", 123456.0, 654321.0, "Koramangala", "Bangalore");
        List<GymAddressClass> gymAddressClasses = new ArrayList<>();
        gymAddressClasses.add(gymAddressClass);

        List<String> workout = new ArrayList<>();
        GymTime gymTime = new GymTime("1", "6 AM", "7 PM", "Sunday");
        GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("1", 100, 300, 900, 1800, 3600, 10);
        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);

        GymRepresent gymRepresent = new GymRepresent("1", "priyanshi.chaturvedi@nineleaps.com",
                "Fitness", gymAddressClass, workout, gymTime, gymSubscriptionClass, 9685903290L,
                4.2, 100);
        List<GymRepresent> gymRepresents = new ArrayList<>();
        gymRepresents.add(gymRepresent);

        when(gymAddressRepo.findByCity(gymAddressClass.getCity())).thenReturn(gymAddressClasses);
        when(gymRepository.findById(gymClass.getId())).thenReturn(Optional.of(gymClass));
        when(gymSubscriptionRepo.findById(gymSubscriptionClass.getId())).thenReturn(Optional.ofNullable(gymSubscriptionClass));
        when(gymTimeRepo.findById(gymTime.getId())).thenReturn(Optional.of(gymTime));

        List<GymRepresent> actual = gymService.getGymByCity(gymAddressClass.getCity());
        assertEquals(gymRepresents, actual);
    }

    @Test
    void getGymByCityIfOptionalIsNotPresent() {
        GymAddressClass gymAddressClass = new GymAddressClass("1", 123456.0, 654321.0, "Koramangala", "Bangalore");
        List<GymAddressClass> gymAddressClasses = new ArrayList<>();
        gymAddressClasses.add(gymAddressClass);
        List<String> workout = new ArrayList<>();
        GymTime gymTime = new GymTime();

        GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass();

        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);

        GymRepresent gymRepresent = new GymRepresent("1", "priyanshi.chaturvedi@nineleaps.com",
                "Fitness", gymAddressClass, workout, gymTime, gymSubscriptionClass, 9685903290L,
                4.2, 100);
        List<GymRepresent> gymRepresents = new ArrayList<>();
        gymRepresents.add(gymRepresent);

        Optional<GymClass> gymClassOptional = Optional.empty();
        Optional<GymTime> gymTimeOptional = Optional.empty();
        Optional<GymSubscriptionClass> gymSubscriptionClassOptional = Optional.empty();

        when(gymAddressRepo.findByCity(gymAddressClass.getCity())).thenReturn(gymAddressClasses);
        when(gymRepository.findById(gymClass.getId())).thenReturn(gymClassOptional);
        when(gymSubscriptionRepo.findById(gymSubscriptionClass.getId())).thenReturn(gymSubscriptionClassOptional);
        when(gymTimeRepo.findById(gymTime.getId())).thenReturn(gymTimeOptional);

        GymAddressClass actual = gymService.getGymByCity(gymAddressClass.getCity()).get(0).getGymAddress();
        assertEquals(gymRepresents.get(0).getGymAddress(), actual);
    }
}