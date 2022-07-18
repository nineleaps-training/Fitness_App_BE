package com.fitness.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fitness.app.entity.*;
import com.fitness.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.app.model.GymClassModel;
import com.fitness.app.model.GymRepresentModel;

@Service
public class GymService {

	@Autowired
	private AddGymRepo gymRepository;

	@Autowired
	private GymAddressRepo addressRepo;

	@Autowired
	private GymTimeRepo timeRepo;

	@Autowired
	private GymSubscriptionRepo subcriptionRepo;

	@Autowired
	private RatingService ratingService;

	private GymAddressClass gymAddressClass;

	private GymSubscriptionClass gSubscriptionClass;

	private GymTime gymTime;

	private GymClass gymClass;

	// Initializing constructor
	/**
	 * This constructor is used to initialize the repositories
	 * 
	 * @param addGymRepository    - Gym Repository
	 * @param gymTimeRepo         - Gym Time Repository
	 * @param gymAddressRepo      - Gym Address Repository
	 * @param gymSubscriptionRepo - Gym Subscription Repository
	 * @param ratingService       - Rating Service
	 */
	public GymService(AddGymRepo addGymRepository, GymTimeRepo gymTimeRepo, GymAddressRepo gymAddressRepo,
			GymSubscriptionRepo gymSubscriptionRepo, RatingService ratingService) {

		this.gymRepository = addGymRepository;
		this.timeRepo = gymTimeRepo;
		this.addressRepo = gymAddressRepo;
		this.subcriptionRepo = gymSubscriptionRepo;
		this.ratingService = ratingService;
	}

	/**
	 * This function is used to add new gym
	 * 
	 * @param gymClassModel - Details of new gym
	 * @return - Details of the gym
	 */
	public GymClass addNewGym(GymClassModel gymClassModel) {
		gymClass = gymRepository.findByName(gymClassModel.getMGymname());
		Long idLast = gymRepository.count() + 1;
		String gId = "GM" + idLast;
		if (gymClass != null) {
			gId = gymClass.getId();
		}

		// Creating address of gym
		GymAddressClass address;
		address = gymClassModel.getMGymaddress();
		address.setId(gId);

		addressRepo.save(address);

		// set time
		GymTime time;
		time = gymClassModel.getMTiming();
		time.setId(gId);
		timeRepo.save(time);

		// set subscription.
		GymSubscriptionClass subscription;
		subscription = gymClassModel.getMSubscription();
		subscription.setId(gId);
		subcriptionRepo.save(subscription);

		GymClass newGym = new GymClass();
		newGym.setId(gId);
		newGym.setEmail(gymClassModel.getVendorEmail());
		newGym.setName(gymClassModel.getMGymname());
		newGym.setWorkout(gymClassModel.getMWorkoutlist());
		newGym.setContact(gymClassModel.getMContact());
		newGym.setCapacity(gymClassModel.getMCapacity());
		gymRepository.save(newGym); // Add New Gym
		return newGym;
	}

	/**
	 * This function is used to fetch gym by it's id
	 * 
	 * @param gymId - Id of the gym
	 * @return
	 */
	public GymRepresentModel getGymByGymId(String gymId) {
		GymRepresentModel gym = new GymRepresentModel();
		Optional<GymClass> optional = gymRepository.findById(gymId); // Find gym by Gym_id
		if (optional.isPresent()) {
			gymClass = optional.get();
			gym.setId(gymClass.getId());
			gym.setEmail(gymClass.getEmail());
			gym.setGymName(gymClass.getName());
			Optional<GymAddressClass> optional2 = addressRepo.findById(gymId);
			if (optional2.isPresent()) {
				gymAddressClass = optional2.get();
				gym.setGymAddress(gymAddressClass);
			}
			gym.setWorkoutList(gymClass.getWorkout());
			Optional<GymTime> optional3 = timeRepo.findById(gymId);
			if (optional3.isPresent()) {
				gymTime = optional3.get();
				gym.setTiming(gymTime);
			}
			Optional<GymSubscriptionClass> optional4 = subcriptionRepo.findById(gymId);
			if (optional4.isPresent()) {
				gSubscriptionClass = optional4.get();
				gym.setSubscription(gSubscriptionClass);
			}
			gym.setContact(gymClass.getContact());
			gym.setCapacity(gymClass.getCapacity());
			gym.setRating(gymClass.getRating());

			return gym;
		} else {
			return null;
		}
	}

	/**
	 * This function is used to fetch all gyms of a particular vendor from his email
	 * 
	 * @param email - Email id of the vendor
	 * @return - List of gyms
	 */
	public List<GymRepresentModel> getGymByVendorEmail(String email) {

		List<GymClass> gymList = gymRepository.findByEmail(email);
		List<GymRepresentModel> gyms = new ArrayList<>();

		for (GymClass eachGym : gymList) {
			GymRepresentModel gym = new GymRepresentModel();
			Double rate = ratingService.getRating(eachGym.getId());
			eachGym.setRating(rate);
			gymRepository.save(eachGym);
			String id = eachGym.getId();
			gym.setEmail(eachGym.getEmail());
			gym.setId(eachGym.getId());
			gym.setGymName(eachGym.getName());
			Optional<GymAddressClass> optional2 = addressRepo.findById(id);
			if (optional2.isPresent()) {
				gymAddressClass = optional2.get();
				gym.setGymAddress(gymAddressClass);
			}
			gym.setWorkoutList(eachGym.getWorkout());
			Optional<GymTime> optional3 = timeRepo.findById(id);
			if (optional3.isPresent()) {
				gymTime = optional3.get();
				gym.setTiming(gymTime);
			}
			Optional<GymSubscriptionClass> optional4 = subcriptionRepo.findById(id);
			if (optional4.isPresent()) {
				gSubscriptionClass = optional4.get();
				gym.setSubscription(gSubscriptionClass);
			}
			gym.setContact(eachGym.getContact());
			gym.setRating(rate);
			gym.setCapacity(eachGym.getCapacity());

			gyms.add(gym); // Find All gym of a vendor by email id..
		}
		return gyms;
	}

	/**
	 * This function is used to fetch the address of the gym from it's id
	 * 
	 * @param id - Id of the gym
	 * @return - Address of the gym
	 */
	public GymAddressClass findTheAddress(String id) {
		GymAddressClass gymAddressClass1 = new GymAddressClass();
		Optional<GymAddressClass> optional2 = addressRepo.findById(id); // Find gym address by gym id
		if (optional2.isPresent()) {
			gymAddressClass = optional2.get();
			return gymAddressClass;
		} else {
			return gymAddressClass1;
		}
	}

	/**
	 * This function is used to find all the registered gyms in the application
	 * 
	 * @return
	 */
	public List<GymClass> getAllGym() {
		return gymRepository.findAll(); // Find All gym from database..
	}

	/**
	 * This function is used to edit/update the gym details
	 * 
	 * @param gymClassModel - Updated details of the gym
	 * @param gymId         - Id of the gym
	 * @return - Updated gym details
	 */
	public GymClass editGym(GymClassModel gymClassModel, String gymId) {
		GymClass theGym;
		Optional<GymClass> optional = gymRepository.findById(gymId);
		if (optional.isPresent()) {
			theGym = optional.get();

			// Creating address of gym
			addressRepo.deleteById(theGym.getId());
			GymAddressClass gClass;
			gClass = gymClassModel.getMGymaddress();
			gClass.setId(gymId);
			addressRepo.save(gClass);

			// set time
			timeRepo.deleteById(theGym.getId());
			GymTime gTime;
			gTime = gymClassModel.getMTiming();
			gTime.setId(gymId);
			timeRepo.save(gTime);

			// set subscription.
			subcriptionRepo.deleteById(theGym.getId());
			gSubscriptionClass = gymClassModel.getMSubscription();
			gSubscriptionClass.setId(gymId);
			subcriptionRepo.save(gSubscriptionClass);

			gymRepository.delete(theGym);
			theGym.setId(gymId); // edit gym
			theGym.setEmail(gymClassModel.getVendorEmail());
			theGym.setName(gymClassModel.getMGymname());
			theGym.setWorkout(gymClassModel.getMWorkoutlist());
			theGym.setContact(gymClassModel.getMContact());
			theGym.setCapacity(gymClassModel.getMCapacity());

			gymRepository.save(theGym);
			return theGym;

		} else {
			return new GymClass();
		}

	}

	/**
	 * This function is used to delete all the gyms
	 * 
	 * @return - Done
	 */
	public String wipingAll() {
		timeRepo.deleteAll(); // Deleting all Gyms
		subcriptionRepo.deleteAll();
		addressRepo.deleteAll();
		gymRepository.deleteAll();

		return "done";
	}

	/**
	 * This function is used to fetch the gym from gym name
	 * 
	 * @param gymName - Name of the gym
	 * @return - Gym with the provided name
	 */
	public GymClass getGymByGymName(String gymName) {
		return gymRepository.findByName(gymName); // Fetching gym by gym name
	}

	/**
	 * This function is used to fetch the gym from gym city
	 * 
	 * @param city - City of the gym
	 * @return - List of gyms residing in the city
	 */
	public List<GymRepresentModel> getGymByCity(String city) {

		List<GymAddressClass> addressList = addressRepo.findByCity(city);
		List<GymRepresentModel> gyms = new ArrayList<>();

		for (GymAddressClass ad : addressList) {
			String id = ad.getId();
			GymRepresentModel gym = new GymRepresentModel();
			Optional<GymClass> optional = gymRepository.findById(id);// Gym By City or Locality
			if (optional.isPresent()) {
				gymClass = optional.get();
				Optional<GymTime> optional3 = timeRepo.findById(id);
				if (optional3.isPresent()) {
					gymTime = optional3.get();
					gym.setTiming(gymTime);
				}
				Optional<GymSubscriptionClass> optional4 = subcriptionRepo.findById(id);
				if (optional4.isPresent()) {
					gSubscriptionClass = optional4.get();
					gym.setSubscription(gSubscriptionClass);
				}
				gym.setId(gymClass.getId());
				gym.setEmail(gymClass.getEmail());
				gym.setGymName(gymClass.getName());
				gym.setGymAddress(ad);
				gym.setWorkoutList(gymClass.getWorkout());
				gym.setContact(gymClass.getContact());
				gym.setRating(gymClass.getRating());
				gym.setCapacity(gymClass.getCapacity());
			}

			gyms.add(gym);
		}
		return gyms;

	}
}
