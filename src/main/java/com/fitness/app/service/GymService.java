package com.fitness.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fitness.app.entity.*;
import com.fitness.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.app.model.GymClassModel;
import com.fitness.app.model.GymRepresnt;

@Service
public class GymService {

	@Autowired
	private AddGymRepository gymRepository;

	@Autowired
	private GymAddressRepo addressRepo;

	@Autowired
	private GymTimeRepo timeRepo;

	@Autowired
	private GymSubscriptionRepo subcriptionRepo;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private RatingService ratingService;

	@Autowired
	private UserOrderRepo userOrderRepo;

	// Add New Gym
	public GymClass addNewGym(GymClassModel gymClassModel) {
		GymClass gym = gymRepository.findByName(gymClassModel.getGymName());
		Long idLast = gymRepository.count() + 1;
		String gymId = "GM" + idLast;
		if (gym != null) {
			gymId = gym.getId();
		}

		// Creating address of gym
		GymAddressClass address;
		address = gymClassModel.getGymAddress();
		address.setId(gymId);

		addressRepo.save(address);

		// set time
		GymTime time;
		time = gymClassModel.getTiming();
		time.setId(gymId);
		timeRepo.save(time);

		// set subscription.
		GymSubscriptionClass subscription;
		subscription = gymClassModel.getSubscription();
		subscription.setId(gymId);
		subcriptionRepo.save(subscription);

		GymClass newGym = new GymClass();
		newGym.setId(gymId);
		newGym.setEmail(gymClassModel.getVendorEmail());
		newGym.setName(gymClassModel.getGymName());
		newGym.setWorkout(gymClassModel.getWorkoutList());
		newGym.setContact(gymClassModel.getContact());
		newGym.setCapacity(gymClassModel.getCapacity());

		return gymRepository.save(newGym);
	}

	// Find gym by Gym_id
	public GymRepresnt getGymByGymId(String gymId) {
		GymRepresnt gym = new GymRepresnt();
		GymClass gymClass;
		GymAddressClass gymAddressClass;
		GymTime gymTime;
		GymSubscriptionClass gymSubscriptionClass;

		Optional<GymClass> optional = gymRepository.findById(gymId);
		if(optional.isPresent()) {
			gymClass = optional.get();
			gym.setId(gymClass.getId());
			gym.setEmail(gymClass.getEmail());
			gym.setGymName(gymClass.getName());

			Optional<GymAddressClass> optional1 = addressRepo.findById(gymId);
			if (optional1.isPresent()) {
				gymAddressClass = optional1.get();
				gym.setGymAddress(gymAddressClass);
			}

			gym.setWorkoutList(gymClass.getWorkout());

			Optional<GymTime> optional2 = timeRepo.findById(gymId);
			if (optional2.isPresent()) {
				gymTime = optional2.get();
				gym.setTiming(gymTime);
			}

			Optional<GymSubscriptionClass> optional3 = subcriptionRepo.findById(gymId);
			if (optional3.isPresent()) {
				gymSubscriptionClass = optional3.get();
				gym.setSubscription(gymSubscriptionClass);
			}

			gym.setContact(gymClass.getContact());
			gym.setCapacity(gymClass.getCapacity());
			gym.setRating(gymClass.getRating());
		}
		return gym;
	}

	// Find All gym of a vendor by email id..
	public List<GymRepresnt> getGymByVendorEmail(String email) {

		List<GymClass> gymList = gymRepository.findByEmail(email);
		List<GymRepresnt> gyms = new ArrayList<>();

		GymAddressClass gymAddressClass;
		GymTime gymTime;
		GymSubscriptionClass gymSubscriptionClass;

		for (GymClass eachGym : gymList) {
			GymRepresnt gym = new GymRepresnt();
			Double rate= ratingService.getRating(eachGym.getId());
			eachGym.setRating(rate);
			gymRepository.save(eachGym);
			String id = eachGym.getId();
			gym.setEmail(eachGym.getEmail());
			gym.setId(eachGym.getId());
			gym.setGymName(eachGym.getName());

			Optional<GymAddressClass> optional = addressRepo.findById(id);
			if (optional.isPresent()) {
				gymAddressClass = optional.get();
				gym.setGymAddress(gymAddressClass);
			}

			gym.setWorkoutList(eachGym.getWorkout());

			Optional<GymTime> optional1 = timeRepo.findById(id);
			if (optional1.isPresent()) {
				gymTime = optional1.get();
				gym.setTiming(gymTime);
			}

			Optional<GymSubscriptionClass> optional2 = subcriptionRepo.findById(id);
			if (optional2.isPresent()) {
				gymSubscriptionClass = optional2.get();
				gym.setSubscription(gymSubscriptionClass);
			}
			gym.setContact(eachGym.getContact());
			gym.setRating(rate);
			gym.setCapacity(eachGym.getCapacity());

			gyms.add(gym);
		}
		return gyms;
	}

	// Find gym address by gym id
	public GymAddressClass findTheAddress(String id) {
		Optional<GymAddressClass> optional = addressRepo.findById(id);
		return optional.orElse(null);
	}

	// Find All gym from database..
	public List<GymClass> getAllGym() {
		return gymRepository.findAll();
	}

	// edit gym
	public GymClass editGym(GymClassModel gymClassModel, String gymId) {

		Optional<GymClass> optional = gymRepository.findById(gymId);
		GymClass theGym = new GymClass();
		if(optional.isPresent()) {
			theGym = optional.get();
		}

		// Creating address of gym
		addressRepo.deleteById(theGym.getId());
		GymAddressClass address;
		address = gymClassModel.getGymAddress();
		address.setId(gymId);
		addressRepo.save(address);

		// set time
		timeRepo.deleteById(theGym.getId());

		GymTime time;
		time = gymClassModel.getTiming();
		time.setId(gymId);
		timeRepo.save(time);

		// set subscription.
		subcriptionRepo.deleteById(theGym.getId());
		GymSubscriptionClass subscription;
		subscription = gymClassModel.getSubscription();
		subscription.setId(gymId);
		subcriptionRepo.save(subscription);

		gymRepository.delete(theGym);
		theGym.setId(gymId);
		theGym.setEmail(gymClassModel.getVendorEmail());
		theGym.setName(gymClassModel.getGymName());
		theGym.setWorkout(gymClassModel.getWorkoutList());
		theGym.setContact(gymClassModel.getContact());
		theGym.setCapacity(gymClassModel.getCapacity());

		return gymRepository.save(theGym);

	}
	
	public String wipingAll() {
		timeRepo.deleteAll();
		subcriptionRepo.deleteAll();
		addressRepo.deleteAll();
		gymRepository.deleteAll();

		return "done";
	}

	public GymClass getGymByGymName(String gymName) {
		return gymRepository.findByName(gymName);
	}

	// Gym By City or Loacltiy..


	// Find by City
	public List<GymRepresnt> getGymByCity(String city) {

		List<GymAddressClass> addressList = addressRepo.findByCity(city);
		List<GymRepresnt> gyms = new ArrayList<>();
		GymClass gymClass = new GymClass();
		GymSubscriptionClass subscription = new GymSubscriptionClass();
		GymTime time = new GymTime();

		for (GymAddressClass address : addressList) {
			String id = address.getId();
			GymRepresnt gym = new GymRepresnt();

			Optional<GymClass> optional = gymRepository.findById(id);
			if (optional.isPresent()) {
				gymClass = optional.get();
			}

			Optional<GymSubscriptionClass> optional1 = subcriptionRepo.findById(id);
			if (optional1.isPresent()) {
				subscription = optional1.get();
			}

			Optional<GymTime> optional2 = timeRepo.findById(id);
			if (optional2.isPresent()) {
				time = optional2.get();
			}

			gym.setId(gymClass.getId());
			gym.setEmail(gymClass.getEmail());
			gym.setGymName(gymClass.getName());
			gym.setGymAddress(address);
			gym.setWorkoutList(gymClass.getWorkout());
			gym.setTiming(time);
			gym.setSubscription(subscription);
			gym.setContact(gymClass.getContact());
			gym.setRating(gymClass.getRating());
			gym.setCapacity(gymClass.getCapacity());

			gyms.add(gym);
		}
		return gyms;
	}





}
