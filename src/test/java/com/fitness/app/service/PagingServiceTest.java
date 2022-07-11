package com.fitness.app.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class PagingServiceTest {

    @Mock
    AddGymRepository addGymRepository;

    @Mock
    UserRepository userRepository;

    PagingService pagingService;
    
    @BeforeEach
    public void initcase() {
        pagingService=new PagingService(addGymRepository,userRepository);
    }

    @Test
    void testGetallGyms() {
        Pageable pageable=PageRequest.of(0, 1);
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        long l=12345;
        GymClass gymClass=new GymClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", workoutList, l, 3.5, 20);
        List<GymClass> gymClasses= new ArrayList<>();
        gymClasses.add(gymClass);
        Page<GymClass> page = new PageImpl<>(gymClasses);
        when(addGymRepository.findAll(pageable)).thenReturn(page);
        List<GymClass> list=pagingService.getallGyms(0, 1);
        Assertions.assertEquals(gymClasses.get(0).getId(), list.get(0).getId());
    }

    @Test
    void testGetallUsers() {

        Pageable pageable=PageRequest.of(0, 1);
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        UserClass userClass=new UserClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", "workoutList", "USER", true, true, true);
        List<UserClass> userClasses= new ArrayList<>();
        userClasses.add(userClass);
        Page<UserClass> page = new PageImpl<>(userClasses);
        userClasses.add(userClass);
        when(userRepository.findAll(pageable)).thenReturn(page);
        List<UserClass> list=pagingService.getallUsers(0, 1);
        Assertions.assertEquals(userClasses.get(0).getEmail(), list.get(0).getEmail());

    }

    @Test
    void testGetallVendors() {

        Pageable pageable=PageRequest.of(0, 1);
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        UserClass userClass=new UserClass("GM1", "pankaj.jain@nineleaps.com", "Fitness", "workoutList", "VENDOR", true, true, true);
        List<UserClass> userClasses= new ArrayList<>();
        userClasses.add(userClass);
        Page<UserClass> page = new PageImpl<>(userClasses);
        userClasses.add(userClass);
        when(userRepository.findAll(pageable)).thenReturn(page);
        List<UserClass> list=pagingService.getallVendors(0, 1);
        Assertions.assertEquals(userClasses.get(0).getEmail(), list.get(0).getEmail());


    }
}
