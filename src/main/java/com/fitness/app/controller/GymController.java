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
import com.fitness.app.model.DeleteGymModel;
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

	// Adding new gym
	@PutMapping("/add/gym")
	public GymClass addNewGym(@RequestBody GymClassModel gymClassModel) {
		return gymService.addNewGym(gymClassModel);
	}

	// getting all gym..
	@GetMapping("/gym/all")
	public List<GymClass> getAllGym() {
		return gymService.getAllGym();
	}

	// Search gym by vendor email.
	@GetMapping("/gym/email/{email}")
	public List<GymRepresnt> getAllOfVendor(@PathVariable String email) {
		return gymService.getGymByVendorEmail(email);
	}

	// editing gym
	@PutMapping("gym/edit/{id}")
	public GymClass editGym(@RequestBody GymClassModel newGym, @PathVariable String id) {
		return gymService.editGym(newGym, id);
	}
    
	//get address
	@GetMapping("/gym/address/{id}")
	public GymAddressClass getAddress(@PathVariable String id)
	{
		return gymService.findTheAddress(id);
	}
	
	// Search gym by gym_id.
	@GetMapping("/gym/id/{id}")
	public GymRepresnt getGymById(@PathVariable String id) {
		return gymService.getGymByGymId(id);
	}

	/*// Delete Gym
	@DeleteMapping("/delete/gym")
	public String deleteAGymById(@RequestBody DeleteGymModel deleteGym) {
		return gymService.deleteGymById(deleteGym);
	}
*/
	@DeleteMapping("gym/delete/every")
	public String deletingEvery() {
		return gymService.wipingAll();
	}

	// Search gym by gymName
	@GetMapping("/gym/gymName/{gymName}")
	public List<GymClass> getGymByGymName(@PathVariable("gymName") String gymName) {
		List<GymClass> allGym = new ArrayList<GymClass>();
		allGym.add(gymService.getGymByGymName(gymName));
		return allGym;
	}

	// Search gym By Locality;

	@GetMapping("/gyms/locality/{locality}")
	public List<GymRepresnt> gymByLoaclity(@PathVariable String locality) {
		return gymService.gymByLocality(locality);
	}

	// Search gym by City

	@GetMapping("/gym/city/{city}")
	public List<GymRepresnt> getGYmByCity(@PathVariable String city) {
		return gymService.getGymByCity(city);
	}
	
	
	@GetMapping("/filter/subscription/monthly/{price}")
	public List<GymClassModel> filterMonthly(@PathVariable int price, @RequestBody List<GymClassModel> listGym)
	{
		return filterSubscriptionService.filterByMonthly(price, listGym);
	}

	@GetMapping("/filter/subscription/quarterly/{price}")
	public List<GymClassModel> filterQuarterly(@PathVariable int price, @RequestBody List<GymClassModel> listGym) {

		return filterSubscriptionService.filterByQuarterly(price, listGym);
	}

	@GetMapping("/filter/subscription/halfYearly/{price}")
	public List<GymClassModel> filterHalfYearly(@PathVariable int price, @RequestBody List<GymClassModel> listGym) {

		return filterSubscriptionService.filterByHalfYearly(price, listGym);
	}

	@GetMapping("/filter/subscription/yearly/{price}")
	public List<GymClassModel> filterYearly(@PathVariable int price, @RequestBody List<GymClassModel> listGym) {

		return filterSubscriptionService.filterByYearly(price, listGym);
	}

	@GetMapping("/filter/subscription/oneWorkout/{price}")
	public List<GymClassModel> filterOneWorkout(@PathVariable int price, @RequestBody List<GymClassModel> listGym) {

		return filterSubscriptionService.filterByOneWorkout(price, listGym);
	}

	
}
