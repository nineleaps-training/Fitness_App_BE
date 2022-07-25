package com.fitness.app.controller;

import java.util.ArrayList;
import java.util.List;

import com.fitness.app.componets.StringValidate;
import com.fitness.app.dao.FilterBySubscriptionDao;
import com.fitness.app.dao.GymDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.model.GymClassModel;
import com.fitness.app.model.GymRepresent;
import com.fitness.app.service.FilterBySubscription;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class GymController {

    private GymDao gymDao;
    private FilterBySubscriptionDao filterSubscriptionDao;

    @Autowired
    public GymController(GymDao gymDao, FilterBySubscription filterSubscriptionDao) {
        this.gymDao = gymDao;
        this.filterSubscriptionDao = filterSubscriptionDao;
    }

    // Adding new fitness center
    @PutMapping("/gym/add")
    @Validated
    public GymClass addNewGym(@Valid @RequestBody GymClassModel gymClassModel) {
        return gymDao.addNewGym(gymClassModel);
    }

    // getting list of all registered fitness center.
    @GetMapping("/gym/all")
    public List<GymClass> getAllGym() {
        return gymDao.getAllGym();
    }

    // Search Fitness centers by vendor email.
    @GetMapping("/gym/email/{email}")
    public List<GymRepresent> getAllOfVendor(@NotBlank @NotEmpty @NotNull @Email @PathVariable String email) {
        return gymDao.getGymByVendorEmail(email);
    }

    // Update details and other in the fitness center.
    @PutMapping("/gym/edit/{id}")
    public GymClass editGym(@Valid @RequestBody GymClassModel newGym, @NotBlank @NotEmpty @NotNull @PathVariable String id) {
        return gymDao.editGym(newGym, id);
    }

    //get address of fitness center by its unique id.
    @GetMapping("/gym/address/{id}")
    public GymAddressClass getAddress(@NotBlank @NotEmpty @NotNull @PathVariable String id) {
        return gymDao.findTheAddress(id);
    }

    // Search Fitness center by fitness id.
    @GetMapping("/gym/id/{id}")
    public GymRepresent getGymById(@NotBlank @NotEmpty @NotNull @PathVariable String id) {
        return gymDao.getGymByGymId(id);
    }

    //Remove all Fitness centers.
    @DeleteMapping("/gym/delete/every")
    public String deletingEvery() {
        return gymDao.wipingAll();
    }

    // Search gym by gymName
    @GetMapping("/gym/gymName/{gymName}")
    @StringValidate
    public List<GymClass> getGymByGymName(@Valid @PathVariable("gymName") String gymName) {
        List<GymClass> allGym = new ArrayList<>();
        allGym.add(gymDao.getGymByGymName(gymName));
        return allGym;
    }

    // Search gym by City
    @GetMapping("/gym/city/{city}")
    @StringValidate
    public List<GymRepresent> getGYmByCity(@Valid @PathVariable String city) {
        return gymDao.getGymByCity(city);
    }


    //Get Fitness by Monthly price limit.
    @GetMapping("/filter/subscription/monthly/{price}")
    public List<GymClassModel> filterMonthly(@NotNull @PathVariable int price, @RequestBody List<GymClassModel> listGym) {
        return filterSubscriptionDao.filterByMonthly(price, listGym);
    }

    //Get Fitness by quarterly price limit.
    @GetMapping("/filter/subscription/quarterly/{price}")
    public List<GymClassModel> filterQuarterly(@NotNull @PathVariable int price, @RequestBody List<GymClassModel> listGym) {

        return filterSubscriptionDao.filterByQuarterly(price, listGym);
    }

    //Get Fitness by halfYearly price limit.
    @GetMapping("/filter/subscription/halfYearly/{price}")
    public List<GymClassModel> filterHalfYearly(@NotNull @PathVariable int price, @RequestBody List<GymClassModel> listGym) {

        return filterSubscriptionDao.filterByHalfYearly(price, listGym);
    }

    //Get Fitness by yearly price limit.
    @GetMapping("/filter/subscription/yearly/{price}")
    public List<GymClassModel> filterYearly(@NotNull @PathVariable int price, @RequestBody List<GymClassModel> listGym) {

        return filterSubscriptionDao.filterByYearly(price, listGym);
    }

    //Get Fitness by one workout price limit.
    @GetMapping("/filter/subscription/oneWorkout/{price}")
    public List<GymClassModel> filterOneWorkout(@NotNull @PathVariable int price, @RequestBody List<GymClassModel> listGym) {

        return filterSubscriptionDao.filterByOneWorkout(price, listGym);
    }


}
