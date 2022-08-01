package com.fitness.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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

import com.fitness.app.dao.FilterBySubscriptionDAO;
import com.fitness.app.dao.GymDAO;
import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.model.GymClassModel;
import com.fitness.app.model.GymRepresentModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Validated
public class GymController {

	@Autowired
	private GymDAO gymDAO;

	@Autowired
	private FilterBySubscriptionDAO filterBySubscriptionDAO;

	/**
	 * This controller is used to add new fitness center in the application
	 * 
	 * @param gymClassModel - Details of the fitness center
	 * @return - Details of the gym
	 */
	@ApiOperation(value = "Adding New Gym", notes = "Vendor can add his gyms")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Gym Added", response = GymClass.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@PutMapping(value = "/v1/gym/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Validated
	@ResponseStatus(HttpStatus.OK)
	public GymClass addNewGym(@Valid @RequestBody GymClassModel gymClassModel) {
		return gymDAO.addNewGym(gymClassModel); // Adding new fitness center
	}

	/**
	 * This controller is used to fetch all the registered fitness centers in the
	 * application
	 * 
	 * @return - List of all the registered gyms
	 */
	@ApiOperation(value = "Get All Gyms", notes = "Fetching all the gyms")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Gyms Fetched"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@GetMapping(value = "/v1/gym/all", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymClass> getAllGym() {
		return gymDAO.getAllGym(); // Fetching list of all registered fitness center.
	}

	/**
	 * This controller is used to fetch the fitness centers by the email id of the
	 * vendor
	 * 
	 * @param email - Email id of the vendor
	 * @return - List of all the gyms of the Vendor
	 */
	@ApiOperation(value = "Fetch gym by vendor email", notes = "Vendor can fetch all his registered gyms")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Gym List"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@GetMapping(value = "/v1/gym/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymRepresentModel> getAllOfVendor(@NotBlank @NotEmpty @NotNull @Email @PathVariable String email) {
		return gymDAO.getGymByVendorEmail(email); // Search Fitness centers by vendor email.
	}

	/**
	 * This controller is used to update the details of the fitness center
	 * 
	 * @param newGym - Updated details of the gym
	 * @param id     - Gym id
	 * @return
	 */
	@ApiOperation(value = "Editing the Gym", notes = "Vendor can edit his gyms")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Gym Edited", response = GymClass.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@PutMapping(value = "/v1/gym/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Validated
	public GymClass editGym(@Valid @RequestBody GymClassModel newGym,
			@NotNull @NotBlank @NotEmpty @PathVariable String id) {
		return gymDAO.editGym(newGym, id); // Update details and other in the fitness center.
	}

	/**
	 * This controller is used to get the address of the gym from gym id
	 * 
	 * @param id - Gym id
	 * @return - Address of the gym
	 */
	@ApiOperation(value = "Address of Gym", notes = "Fetching the address of the gym")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Gym Address Fetched", response = GymAddressClass.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@GetMapping(value = "/v1/gym/address/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public GymAddressClass getAddress(@NotBlank @NotEmpty @NotNull @PathVariable String id) {
		return gymDAO.findTheAddress(id); // Returning address of fitness center by its unique id.
	}

	/**
	 * This controller is used to search the gym from gym id
	 * 
	 * @param id - Gym id
	 * @return - Fitness center details
	 */
	@ApiOperation(value = "Get gym by id", notes = "Fetching gym by it's id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Gym Fetched", response = GymRepresentModel.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@GetMapping(value = "/v1/gym/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public GymRepresentModel getGymById(@NotBlank @NotEmpty @NotNull @PathVariable String id) {
		return gymDAO.getGymByGymId(id); // Search Fitness center by fitness id.
	}

	/**
	 * This controller is used to delete all the registered fitness centers
	 * 
	 * @return - All the gyms deleted
	 */
	@ApiOperation(value = "Deleting all gyms", notes = "Admin can delete all the registered gyms")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "All gyms deleted", response = String.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@DeleteMapping(value = "/v1/gym/delete/every")
	@ResponseStatus(HttpStatus.OK)
	public String deletingEvery() {
		return gymDAO.wipingAll(); // Remove all Fitness centers.
	}

	/**
	 * This controller is used to search the fitness centers from their name
	 * 
	 * @param gymName - Name of the fitness center
	 * @return - List of gyms with the given name
	 */
	@ApiOperation(value = "Get gym by name", notes = "Users can fetch the gym by it's name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Gym Fetched"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@GetMapping(value = "/v1/gym/gymName/{gymName}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymClass> getGymByGymName(@NotBlank @NotEmpty @NotNull @PathVariable("gymName") String gymName) {
		List<GymClass> allGym = new ArrayList<>();
		allGym.add(gymDAO.getGymByGymName(gymName)); // Search gyms by gymName
		return allGym;
	}

	/**
	 * This controller is used to fetch fitness centers from their locality/city
	 * 
	 * @param city - City name
	 * @return - List of gyms in the particular city
	 */
	@ApiOperation(value = "Get gym by city", notes = "Users can fetch the gym by it's city")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Gym Fetched"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@GetMapping(value = "/v1/gym/city/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymRepresentModel> getGYmByCity(@NotBlank @NotEmpty @NotNull @PathVariable String city) {
		return gymDAO.getGymByCity(city); // Search gym By Locality
	}

	/**
	 * This controller is used to filter the gyms by monthly price
	 * 
	 * @param price   - price of the service of the gym
	 * @param listGym - List of all the gyms
	 * @return - Filtered gyms by monthly price
	 */
	@ApiOperation(value = "Filter gym by monthly subscription", notes = "Users can filter the gyms based on monthly subscription")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Gyms Fetched"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@GetMapping(value = "/v1/gym/filter/subscription/monthly/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymClassModel> filterMonthly(
			@NotNull @Min(value = 1, message = "Price should be at least 1") @PathVariable int price,
			@RequestBody List<GymClassModel> listGym) {
		return filterBySubscriptionDAO.filterByMonthly(price, listGym); // Returning gyms by monthly price limit.
	}

	/**
	 * This controller is used to filter the gyms by quarterly price
	 * 
	 * @param price   - price of the service of the gym
	 * @param listGym - List of all the gyms
	 * @return - Filtered gyms by quarterly price
	 */
	@ApiOperation(value = "Filter gym by quarterly subscription", notes = "Users can filter the gyms based on quarterly subscription")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Gyms Fetched"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@GetMapping(value = "/v1/gym/filter/subscription/quarterly/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymClassModel> filterQuarterly(
			@NotNull @Min(value = 1, message = "Price should be at least 1") @PathVariable int price,
			@RequestBody List<GymClassModel> listGym) {

		return filterBySubscriptionDAO.filterByQuarterly(price, listGym); // Returning gyms by quarterly price limit.
	}

	/**
	 * This controller is used to filter the gyms by half-yearly price
	 * 
	 * @param price   - price of the service of the gym
	 * @param listGym - List of all the gyms
	 * @return - Filtered gyms by half-yearly price
	 */
	@ApiOperation(value = "Filter gym by half-yearly subscription", notes = "Users can filter the gyms based on half-yearly subscription")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Gyms Fetched"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@GetMapping(value = "/v1/gym/filter/subscription/halfYearly/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymClassModel> filterHalfYearly(
			@NotNull @Min(value = 1, message = "Price should be at least 1") @PathVariable int price,
			@RequestBody List<GymClassModel> listGym) {

		return filterBySubscriptionDAO.filterByHalfYearly(price, listGym); // Returning gyms by half-yearly price
																			// limit.
	}

	/**
	 * This controller is used to filter the gyms by yearly price
	 * 
	 * @param price   - price of the service of the gym
	 * @param listGym - List of all the gyms
	 * @return - Filtered gyms by yearly price
	 */
	@ApiOperation(value = "Filter gym by yearly subscription", notes = "Users can filter the gyms based on yearly subscription")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Gyms Fetched"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@GetMapping(value = "/v1/gym/filter/subscription/yearly/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymClassModel> filterYearly(
			@NotNull @Min(value = 1, message = "Price should be at least 1") @PathVariable int price,
			@RequestBody List<GymClassModel> listGym) {

		return filterBySubscriptionDAO.filterByYearly(price, listGym); // Returning gyms by yearly price limit.
	}

	/**
	 * This controller is used to filter the gyms by workouts
	 * 
	 * @param price   - price of the service of the gym
	 * @param listGym - List of all the gyms
	 * @return - Filtered gyms by workout
	 */
	@ApiOperation(value = "Filter gym by workout subscription", notes = "Users can filter the gyms based on workout subscription")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Gyms Fetched"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@GetMapping(value = "/v1/gym/filter/subscription/oneWorkout/{price}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<GymClassModel> filterOneWorkout(
			@NotNull @Min(value = 1, message = "Price should be at least 1") @PathVariable int price,
			@RequestBody List<GymClassModel> listGym) {

		return filterBySubscriptionDAO.filterByOneWorkout(price, listGym); // Returning gyms by workout.
	}
}
