package com.fitness.app.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.entity.UserClass;
import com.fitness.app.repository.AddGymRepo;
import com.fitness.app.repository.UserBankDetailsRepo;
import com.fitness.app.repository.UserRepo;

@ExtendWith(MockitoExtension.class)
class PagingServiceTest {

    @Mock
    AddGymRepo addGymRepository;

    @Mock
    UserRepo userRepository;

    @Mock
    UserBankDetailsRepo userBankDetailsRepo;

    PagingService pagingService;

    @BeforeEach
    public void initcase() {
        pagingService = new PagingService(addGymRepository, userRepository, userBankDetailsRepo);
    }

    @Test
    @DisplayName("Testing of fetching all the registered gyms")
    void testGetallGyms() {
        Pageable pageable = PageRequest.of(0, 1);
        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        long l = 12345;
        GymClass gymClass = new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, l, 3.5, 20);
        List<GymClass> gymClasses = new ArrayList<>();
        gymClasses.add(gymClass);
        Page<GymClass> page = new PageImpl<>(gymClasses);
        when(addGymRepository.findAll(pageable)).thenReturn(page);
        List<GymClass> list = pagingService.getallGyms(0, 1);
        Assertions.assertEquals(gymClasses.get(0).getId(), list.get(0).getId());
    }

    @Test
    @DisplayName("Testing of fetching all the registered users")
    void testGetallUsers() {

        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        UserClass userClass = new UserClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", "workoutList", "USER", true,
                true, true);
        List<UserClass> userClasses = new ArrayList<>();
        userClasses.add(userClass);
        userClasses.add(userClass);
        when(userRepository.findAll()).thenReturn(userClasses);
        List<UserClass> list = pagingService.getallUsers(0, 1);
        Assertions.assertEquals(userClasses.get(0).getEmail(), list.get(0).getEmail());

    }

    @Test
    @DisplayName("Testing of fetching all the users when exception is thrown")
    void testGetallUsersException() {

        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        UserClass userClass = new UserClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", "workoutList", "VENDOR",
                true, true, true);
        List<UserClass> userClasses = new ArrayList<>();
        userClasses.add(userClass);
        userClasses.add(userClass);
        when(userRepository.findAll()).thenReturn(userClasses);
        try {
            pagingService.getallUsers(0, 1);
        } catch (Exception e) {
            Assertions.assertEquals("No Users are registered", e.getMessage());
        }

    }

    @Test
    @DisplayName("Testing of fetching all the registered vendors")
    void testGetallVendors() {

        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        UserClass userClass = new UserClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", "workoutList", "VENDOR",
                true, true, true);
        List<UserClass> userClasses = new ArrayList<>();
        userClasses.add(userClass);
        userClasses.add(userClass);
        when(userRepository.findAll()).thenReturn(userClasses);
        List<UserClass> list = pagingService.getallVendors(0, 1);
        Assertions.assertEquals(userClasses.get(0).getEmail(), list.get(0).getEmail());

    }

    @Test
    @DisplayName("Testing of fetching all the vendors when exception is thrown")
    void testGetallVendorsException() {

        List<String> workoutList = new ArrayList<>();
        workoutList.add("zumba");
        UserClass userClass = new UserClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", "workoutList", "USER", true,
                true, true);
        List<UserClass> userClasses = new ArrayList<>();
        userClasses.add(userClass);
        userClasses.add(userClass);
        when(userRepository.findAll()).thenReturn(userClasses);
        try {
            pagingService.getallVendors(0, 1);
        } catch (Exception e) {
            Assertions.assertEquals("No Vendors are registered", e.getMessage());
        }
    }

    @Test
    @DisplayName("Testing of fetching all the bank details")
    void testGetallDetails() {
        long l = 1234;
        Pageable pageable = PageRequest.of(0, 1);
        UserBankDetails uBankDetails = new UserBankDetails("pankaj.jain@nineleaps.com", "Pankaj Jain", "ICICI",
                "Bhatar", l, "ICICI20002");
        List<UserBankDetails> userBankDetails = new ArrayList<>();
        userBankDetails.add(uBankDetails);
        Page<UserBankDetails> page = new PageImpl<>(userBankDetails);
        when(userBankDetailsRepo.findAll(pageable)).thenReturn(page);
        List<UserBankDetails> list = pagingService.getallDetails(0, 1);
        Assertions.assertEquals(userBankDetails.get(0).getUEmail(), list.get(0).getUEmail());

    }
}
