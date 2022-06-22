package com.fitness.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.model.GymClassModel;
import com.fitness.app.model.GymRepresnt;
import com.fitness.app.service.FilterBySubscription;
import com.fitness.app.service.GymService;

@RestController
public class GymController {

	@Autowired
	private GymService gymService;
	
	@Autowired
	private FilterBySubscription filterSubscriptionService;

	// Adding new fitness center
	@PutMapping("/add/gym")
	public GymClass addNewGym(@RequestBody GymClassModel gymClassModel) {
		return gymService.addNewGym(gymClassModel);
	}

	// getting list of all registered fitness center.
	@GetMapping("/gym/all")
	public List<GymClass> getAllGym() {
		return gymService.getAllGym();
	}

	// Search Fitness centers by vendor email.
	@GetMapping("/gym/email/{email}")
	public List<GymRepresnt> getAllOfVendor(@PathVariable String email) {
		return gymService.getGymByVendorEmail(email);
	}

	// Update details and other in the fitness center.
	@PutMapping("gym/edit/{id}")
	public GymClass editGym(@RequestBody GymClassModel newGym, @PathVariable String id) {
		return gymService.editGym(newGym, id);
	}
    
	//get address of fitness center by its unique id.
	@GetMapping("/gym/address/{id}")
	public GymAddressClass getAddress(@PathVariable String id)
	{
		return gymService.findTheAddress(id);
	}
	
	// Search Fitness center by fitness id.
	@GetMapping("/gym/id/{id}")
	public GymRepresnt getGymById(@PathVariable String id) {
		return gymService.getGymByGymId(id);
	}

	//Remove all Fitness centers.
	@DeleteMapping("gym/delete/every")
	public String deletingEvery() {
		return gymService.wipingAll();
	}

	// Search gym by gymName
	@GetMapping("/gym/gymName/{gymName}")
	public List<GymClass> getGymByGymName(@PathVariable("gymName") String gymName) {
		List<GymClass> allGym = new ArrayList<>();
		allGym.add(gymService.getGymByGymName(gymName));
		return allGym;
	}

	// Search gym by City
	@GetMapping("/gym/city/{city}")
	public List<GymRepresnt> getGYmByCity(@PathVariable String city) {
		return gymService.getGymByCity(city);
	}
	

	//Get Fitness by Monthly price limit.
	@GetMapping("/filter/subscription/monthly/{price}")
	public List<GymClassModel> filterMonthly(@PathVariable int price, @RequestBody List<GymClassModel> listGym)
	{
		return filterSubscriptionService.filterByMonthly(price, listGym);
	}

	//Get Fitness by quarterly price limit.
	@GetMapping("/filter/subscription/quarterly/{price}")
	public List<GymClassModel> filterQuarterly(@PathVariable int price, @RequestBody List<GymClassModel> listGym) {

		return filterSubscriptionService.filterByQuarterly(price, listGym);
	}

	//Get Fitness by halfYearly price limit.
	@GetMapping("/filter/subscription/halfYearly/{price}")
	public List<GymClassModel> filterHalfYearly(@PathVariable int price, @RequestBody List<GymClassModel> listGym) {

		return filterSubscriptionService.filterByHalfYearly(price, listGym);
	}

	////Get Fitness by yearly price limit.
	@GetMapping("/filter/subscription/yearly/{price}")
	public List<GymClassModel> filterYearly(@PathVariable int price, @RequestBody List<GymClassModel> listGym) {

		return filterSubscriptionService.filterByYearly(price, listGym);
	}

	//Get Fitness by one workout price limit.
	@GetMapping("/filter/subscription/oneWorkout/{price}")
	public List<GymClassModel> filterOneWorkout(@PathVariable int price, @RequestBody List<GymClassModel> listGym) {

		return filterSubscriptionService.filterByOneWorkout(price, listGym);
	}




	
}
