package com.fitness.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.model.GymClassModel;
import com.fitness.app.model.GymRepresnt;
import com.fitness.app.service.FilterBySubscription;
import com.fitness.app.service.GymService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class GymController {

	@Autowired
	private GymService gymService;
	
	@Autowired
	private FilterBySubscription filterSubscriptionService;

	// Adding new fitness center
	@ApiOperation(value = "Adding New Gym", notes = "Vendor can add his gyms")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Gym Added", response = GymClass.class),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	@PutMapping(value = "/v1/add/gym", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Validated
	@ResponseStatus(HttpStatus.OK)
	public GymClass addNewGym(@Valid @RequestBody GymClassModel gymClassModel) {
		return gymService.addNewGym(gymClassModel);
	}

	// getting list of all registered fitness center.
	@ApiOperation(value = "Get All Gyms", notes = "Fetching all the gyms")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Gyms Fetched"),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	@GetMapping(value = "/v1/gym/all", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymClass> getAllGym() {
		return gymService.getAllGym();
	}

	// Search Fitness centers by vendor email.
	@ApiOperation(value = "Fetch gym by vendor email", notes = "Vendor can fetch all his registered gyms")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Gym List"),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	@GetMapping(value = "/v1/gym/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymRepresnt> getAllOfVendor(@PathVariable String email) {
		return gymService.getGymByVendorEmail(email);
	}

	// Update details and other in the fitness center.
	@ApiOperation(value = "Editing the Gym", notes = "Vendor can edit his gyms")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Gym Edited", response = GymClass.class),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	@PutMapping(value = "/v1/gym/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Validated
	public GymClass editGym(@Valid @RequestBody GymClassModel newGym,@NotNull @NotBlank @PathVariable String id) {
		return gymService.editGym(newGym, id);
	}
    
	//get address of fitness center by its unique id.
	@ApiOperation(value = "Address of Gym", notes = "Fetching the address of the gym")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Gym Address Fetched", response = GymAddressClass.class),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	@GetMapping(value = "/v1/gym/address/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public GymAddressClass getAddress(@PathVariable String id)
	{
		return gymService.findTheAddress(id);
	}
	
	// Search Fitness center by fitness id.
	@ApiOperation(value = "Get gym by id", notes = "Fetching gym by it's id")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Gym Fetched", response = GymRepresnt.class),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	@GetMapping(value = "/v1/gym/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public GymRepresnt getGymById(@PathVariable String id) {
		return gymService.getGymByGymId(id);
	}

	//Remove all Fitness centers.
	@ApiOperation(value = "Deleting all gyms", notes = "Admin can delete all the registered gyms")
	@ApiResponses(value = { @ApiResponse(code=200, message = "All gyms deleted", response = String.class),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	@DeleteMapping(value = "/v1/gym/delete/every")
	@ResponseStatus(HttpStatus.OK)
	public String deletingEvery() {
		return gymService.wipingAll();
	}

	// Search gym by gymName
	@ApiOperation(value = "Get gym by name", notes = "Users can fetch the gym by it's name")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Gym Fetched"),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	@GetMapping(value = "/v1/gym/gymName/{gymName}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymClass> getGymByGymName(@PathVariable("gymName") String gymName) {
		List<GymClass> allGym = new ArrayList<>();
		allGym.add(gymService.getGymByGymName(gymName));
		return allGym;
	}

	// Search gym By Locality
	@ApiOperation(value = "Get gym by city", notes = "Users can fetch the gym by it's city")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Gym Fetched"),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	@GetMapping(value = "/v1/gym/city/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymRepresnt> getGYmByCity(@PathVariable String city) {
		return gymService.getGymByCity(city);
	}
	

	//Get Fitness by Monthly price limit.
	@ApiOperation(value = "Filter gym by monthly subscription", notes = "Users can filter the gyms based on monthly subscription")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Gyms Fetched"),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	@GetMapping(value = "/v1/filter/subscription/monthly/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymClassModel> filterMonthly(@NotBlank @NotNull @PathVariable int price, @RequestBody List<GymClassModel> listGym)
	{
		return filterSubscriptionService.filterByMonthly(price, listGym);
	}

	//Get Fitness by quarterly price limit.
	@ApiOperation(value = "Filter gym by quaterly subscription", notes = "Users can filter the gyms based on quaterly subscription")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Gyms Fetched"),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	@GetMapping(value = "/v1/filter/subscription/quarterly/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymClassModel> filterQuarterly(@NotBlank @NotNull @PathVariable int price, @RequestBody List<GymClassModel> listGym) {

		return filterSubscriptionService.filterByQuarterly(price, listGym);
	}

	//Get Fitness by halfYearly price limit.
	@ApiOperation(value = "Filter gym by half-yearly subscription", notes = "Users can filter the gyms based on half-yearly subscription")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Gyms Fetched"),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	@GetMapping(value = "/v1/filter/subscription/halfYearly/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymClassModel> filterHalfYearly(@NotBlank @NotNull @PathVariable int price, @RequestBody List<GymClassModel> listGym) {

		return filterSubscriptionService.filterByHalfYearly(price, listGym);
	}

	////Get Fitness by yearly price limit.
	@ApiOperation(value = "Filter gym by yearly subscription", notes = "Users can filter the gyms based on yearly subscription")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Gyms Fetched"),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	@GetMapping(value = "/v1/filter/subscription/yearly/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymClassModel> filterYearly(@NotBlank @NotNull @PathVariable int price, @RequestBody List<GymClassModel> listGym) {

		return filterSubscriptionService.filterByYearly(price, listGym);
	}

	//Get Fitness by one workout price limit.
	@ApiOperation(value = "Filter gym by workout subscription", notes = "Users can filter the gyms based on workout subscription")
	@ApiResponses(value = { @ApiResponse(code=200, message = "Gyms Fetched"),
	@ApiResponse(code = 404, message ="Not Found", response=NotFoundException.class),@ApiResponse(code = 403, message ="Forbidden", response=ForbiddenException.class),
	@ApiResponse(code = 401, message ="Unauthorized", response=AuthenticationException.class)})
	@GetMapping(value = "/v1/filter/subscription/oneWorkout/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymClassModel> filterOneWorkout(@NotBlank @NotNull @PathVariable int price, @RequestBody List<GymClassModel> listGym) {

		return filterSubscriptionService.filterByOneWorkout(price, listGym);
	}	
}
