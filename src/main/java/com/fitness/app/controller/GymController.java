package com.fitness.app.controller;


import com.fitness.app.dto.requestDtos.GymClassModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.service.GymServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Gym controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fitness")
public class GymController {


    final private GymServiceImpl gymServiceImpl;


    /**
     * Add new gym api response.
     *
     * @param gymClassModel the gym class model
     * @return the api response
     */
// Adding new fitness center
    @PutMapping("/private/add-or-update-gym")
    public ApiResponse addNewGym(@RequestBody GymClassModel gymClassModel) {
        return gymServiceImpl.addNewGym(gymClassModel);
    }

    /**
     * Gets all gym.
     *
     * @return the all gym
     */
// getting list of all registered fitness center.
    //TODO :: Add pagination here.
    @GetMapping("/private/gym-all")
    public ApiResponse getAllGym() {
        return new ApiResponse(HttpStatus.OK, gymServiceImpl.getAllGym());
    }

    /**
     * Gets all of vendor.
     *
     * @param email the email
     * @return the all of vendor
     */
// Search Fitness centers by vendor email.
    //TODO :: Add pagination here.
    @GetMapping("/private/gym-by-username/{email}")
    public ApiResponse getAllOfVendor(@PathVariable String email) {
        return new ApiResponse(HttpStatus.OK, gymServiceImpl.getGymByVendorEmail(email));
    }


    /**
     * Gets address.
     *
     * @param id the id
     * @return the address
     */
//get address of fitness center by its unique id.
    @GetMapping("/private/address-by-id/{id}")
    public GymAddressClass getAddress(@PathVariable String id) {
        return gymServiceImpl.findTheAddress(id);
    }

    /**
     * Gets gym by id.
     *
     * @param id the id
     * @return the gym by id
     */
// Search Fitness center by fitness id.
    @GetMapping("/private/by-id/{id}")
    public ResponseEntity<?> getGymById(@PathVariable String id) {
        return new ResponseEntity<>(gymServiceImpl.getGymByGymId(id), HttpStatus.OK);
    }


    /**
     * Gets gym by gym name.
     *
     * @param gymName the gym name
     * @return the gym by gym name
     */
// Search gym by gymName
    @GetMapping("/private/by-gymName/{gymName}")
    public ResponseEntity<?> getGymByGymName(@PathVariable("gymName") String gymName) {
        List<GymClass> allGym = new ArrayList<GymClass>();
        allGym.add(gymServiceImpl.getGymByGymName(gymName));
        return new ResponseEntity<>(allGym, HttpStatus.OK);
    }


    /**
     * Gets g ym by city.
     *
     * @param city the city
     * @return the g ym by city
     */
    @GetMapping("/public/by-city/{city}")
    public ResponseEntity<?> getGYmByCity(@PathVariable String city) {
        return new ResponseEntity<>(gymServiceImpl.getGymByCity(city), HttpStatus.OK);
    }


}
