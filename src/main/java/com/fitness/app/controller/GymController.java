package com.fitness.app.controller;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.model.GymClassModel;
import com.fitness.app.model.GymRepresnt;
import com.fitness.app.service.GymServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GymController {

    @Autowired
    private GymServiceImpl gymServiceImpl;


    // Adding new fitness center
    @PutMapping("/add/gym")
    public GymClass addNewGym(@RequestBody GymClassModel gymClassModel) {
        return gymServiceImpl.addNewGym(gymClassModel);
    }

    // getting list of all registered fitness center.
    @GetMapping("/gym/all")
    public List<GymClass> getAllGym() {
        return gymServiceImpl.getAllGym();
    }

    // Search Fitness centers by vendor email.
    @GetMapping("/gym/email/{email}")
    public List<GymRepresnt> getAllOfVendor(@PathVariable String email) {
        return gymServiceImpl.getGymByVendorEmail(email);
    }


    //get address of fitness center by its unique id.
    @GetMapping("/gym/address/{id}")
    public GymAddressClass getAddress(@PathVariable String id) {
        return gymServiceImpl.findTheAddress(id);
    }

    // Search Fitness center by fitness id.
    @GetMapping("/gym/id/{id}")
    public GymRepresnt getGymById(@PathVariable String id) {
        return gymServiceImpl.getGymByGymId(id);
    }


    // Search gym by gymName
    @GetMapping("/gym/gymName/{gymName}")
    public List<GymClass> getGymByGymName(@PathVariable("gymName") String gymName) {
        List<GymClass> allGym = new ArrayList<GymClass>();
        allGym.add(gymServiceImpl.getGymByGymName(gymName));
        return allGym;
    }


    @GetMapping("/gym/city/{city}")
    public List<GymRepresnt> getGYmByCity(@PathVariable String city) {
        return gymServiceImpl.getGymByCity(city);
    }


}
