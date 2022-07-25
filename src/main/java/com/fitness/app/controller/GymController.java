package com.fitness.app.controller;


import com.fitness.app.dto.request.GymClassModel;
import com.fitness.app.dto.response.ApiResponse;
import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.service.dao.GymDao;
import com.fitness.app.utils.InputStringValidate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * The type Gym controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fitness")
public class GymController {


    final private GymDao gymDao;


    /**
     * Add new gym api response.
     *
     * @param gymClassModel the gym class model
     * @return the api response
     */
// Adding new fitness center
    @PutMapping("/add/update/gym")
    @Validated
    @ApiOperation(value = "Vendor can add or update fitness center", notes = "vendor  can update fitness center or add new fitness center")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Successful", response = ApiResponse.class),
    })
    public ApiResponse addNewGym(@Valid @RequestBody GymClassModel gymClassModel) {
        return gymDao.addNewGym(gymClassModel);
    }

    /**
     * Gets all gym.
     *
     * @return the all gym
     */
// getting list of all registered fitness center.
    @GetMapping("/gym/all")
    public ApiResponse getAllGym() {
        return new ApiResponse(HttpStatus.OK, gymDao.getAllGym());
    }

    /**
     * Gets all of vendor.
     *
     * @param email the email
     * @return the all of vendor
     */
// Search Fitness centers by vendor email.
    @Validated
    @ApiOperation(value = "fitness centers of Vendor", notes = "Get all Fitness Center of vendor")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "list of all fitness center in a page", response = ApiResponse.class),
    })
    @GetMapping("/private/gym/by/username/{email}")
    public ApiResponse getAllOfVendor(@PathVariable @Valid String email, int offSet, int pageSize) {
        return new ApiResponse(HttpStatus.OK, gymDao.getGymByVendorEmail(email,offSet, pageSize));
    }


    /**
     * Gets address.
     *
     * @param id the id
     * @return the address
     */
//get address of fitness center by its unique id.
    @GetMapping("/address/by/id/{id}")
    @Validated
    @ApiOperation(value = "Get Complete address of fitness Center", notes = "Complete address of a fitness center")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Address of Fitness center", response = GymAddressClass.class),
    })
    public GymAddressClass getAddress(@PathVariable @Valid String id) {
        return gymDao.findTheAddress(id);
    }

    /**
     * Gets gym by id.
     *
     * @param id the id
     * @return the gym by id
     */
// Search Fitness center by fitness id.
    @GetMapping("/by/id/{id}")
    @Validated
    @ApiOperation(value = "Find fitness center", notes = "Vendor can get a fitness center by Center Id.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Successful", response = ApiResponse.class),
    })
    public ResponseEntity<?> getGymById(@PathVariable @Valid String id) {
        return new ResponseEntity<>(gymDao.getGymByGymId(id), HttpStatus.OK);
    }


    /**
     * Gets gym by gym name.
     *
     * @param gymName the gym name
     * @return the gym by gym name
     */
// Search gym by gymName
    @GetMapping("/by/gymName/{gymName}")
    @ApiOperation(value = "Find fitness center", notes = "Vendor can get a fitness center by Center Name.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Fitness Center", response =GymClass.class),
    })
    @InputStringValidate
    public ResponseEntity<?> getGymByGymName(@Valid @PathVariable("gymName") String gymName) {
        return new ResponseEntity<>(gymDao.getGymByGymName(gymName), HttpStatus.OK);
    }


    /**
     * Gets g ym by city.
     *
     * @param city the city
     * @return the g ym by city
     */
    @GetMapping("/public/by/city/{city}")
    @Validated
    @ApiOperation(value = "Find fitness center", notes = "Vendor can get a fitness center by Center Name.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Fitness Center", response =GymClass.class),
    })
    @InputStringValidate
    public ResponseEntity<?> getGYmByCity(@Valid @PathVariable String city, int offSet, int pageSize) {
        return new ResponseEntity<>(gymDao.getGymByCity(city, offSet, pageSize), HttpStatus.OK);
    }


}
