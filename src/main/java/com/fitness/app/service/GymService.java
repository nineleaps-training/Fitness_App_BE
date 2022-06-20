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
		GymClass gym = gymRepository.findByName(gymClassModel.getGym_name());
		Long idLast = gymRepository.count() + 1;
		String gym_id = "GM" + idLast;
		if (gym != null) {
			gym_id = gym.getId();
		}

		// Creating address of gym
		GymAddressClass address = gymClassModel.getGymAddress();
		address.setId(gym_id);

		addressRepo.save(address);

		// set time
		GymTime time =gymClassModel.getTiming();
		time.setId(gym_id);
		timeRepo.save(time);

		// set subscription.
		GymSubscriptionClass subscription = gymClassModel.getSubscription();
		subscription.setId(gym_id);
		subcriptionRepo.save(subscription);

		GymClass newGym = new GymClass();
		newGym.setId(gym_id);
		newGym.setEmail(gymClassModel.getVendor_email());
		newGym.setName(gymClassModel.getGym_name());
		newGym.setWorkout(gymClassModel.getWorkoutList());
		newGym.setContact(gymClassModel.getContact());
		newGym.setCapacity(gymClassModel.getCapacity());

		return gymRepository.save(newGym);
	}

	// Find gym by Gym_id
	public GymRepresnt getGymByGymId(String gym_id) {
		GymRepresnt gym = new GymRepresnt();

		GymClass gym_cl = gymRepository.findById(gym_id).get();
		gym.setId(gym_cl.getId());
		gym.setEmail(gym_cl.getEmail());
		gym.setGym_name(gym_cl.getName());
		gym.setGymAddress(addressRepo.findById(gym_id).get());
		gym.setWorkoutList(gym_cl.getWorkout());
		gym.setTiming(timeRepo.findById(gym_id).get());
		gym.setSubscription(subcriptionRepo.findById(gym_id).get());
		gym.setContact(gym_cl.getContact());
		gym.setCapacity(gym_cl.getCapacity());
		gym.setRating(gym_cl.getRating());

		return gym;
	}

	// Find All gym of a vendor by email id..
	public List<GymRepresnt> getGymByVendorEmail(String email) {

		List<GymClass> gymList = gymRepository.findByEmail(email);
		List<GymRepresnt> gyms = new ArrayList<>();

		for (GymClass eachGym : gymList) {
			GymRepresnt gym = new GymRepresnt();
			Double rate= ratingService.getRating(eachGym.getId());
			eachGym.setRating(rate);
			gymRepository.save(eachGym);
			String id = eachGym.getId();
			gym.setEmail(eachGym.getEmail());
			gym.setId(eachGym.getId());
			gym.setGym_name(eachGym.getName());
			gym.setGymAddress(addressRepo.findById(id).get());
			gym.setWorkoutList(eachGym.getWorkout());
			gym.setTiming(timeRepo.findById(id).get());
			gym.setSubscription(subcriptionRepo.findById(id).get());
			gym.setContact(eachGym.getContact());
			gym.setRating(rate);
			gym.setCapacity(eachGym.getCapacity());

			gyms.add(gym);
		}
		return gyms;
	}

	// Find gym address by gym id
	public GymAddressClass findTheAddress(String id) {
		return addressRepo.findById(id).get();
	}

	// Find All gym from database..
	public List<GymClass> getAllGym() {
		return gymRepository.findAll();
	}

	// edit gym
	public GymClass editGym(GymClassModel gymClassModel, String gym_id) {

		GymClass theGym = gymRepository.findById(gym_id).get();

		// Creating address of gym
		addressRepo.deleteById(theGym.getId());
		GymAddressClass address = gymClassModel.getGymAddress();
		address.setId(gym_id);
		addressRepo.save(address);

		// set time
		timeRepo.deleteById(theGym.getId());

		GymTime time = gymClassModel.getTiming();
		time.setId(gym_id);
		timeRepo.save(time);

		// set subscription.
		subcriptionRepo.deleteById(theGym.getId());
		GymSubscriptionClass subscription = gymClassModel.getSubscription();
		subscription.setId(gym_id);
		subcriptionRepo.save(subscription);

		gymRepository.delete(theGym);
		theGym.setId(gym_id);
		theGym.setEmail(gymClassModel.getVendor_email());
		theGym.setName(gymClassModel.getGym_name());
		theGym.setWorkout(gymClassModel.getWorkoutList());
		theGym.setContact(gymClassModel.getContact());
		theGym.setCapacity(gymClassModel.getCapacity());

		return gymRepository.save(theGym);

	}




	public GymClass getGymByGymName(String gymName) {
		return gymRepository.findByName(gymName);
	}




	// Find by City
	public List<GymRepresnt> getGymByCity(String city) {

		List<GymAddressClass> addressList = addressRepo.findByCity(city);
		List<GymRepresnt> gyms = new ArrayList<>();

		for (GymAddressClass address : addressList) {
			String id = address.getId();
			GymRepresnt gym = new GymRepresnt();
			GymClass gymClass = gymRepository.findById(id).get();
			GymSubscriptionClass subscription = subcriptionRepo.findById(id).get();
			GymTime time = timeRepo.findById(id).get();
			gym.setId(gymClass.getId());
			gym.setEmail(gymClass.getEmail());
			gym.setGym_name(gymClass.getName());
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
