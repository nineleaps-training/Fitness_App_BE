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
	private RatingService ratingService;

	private GymAddressClass gymAddressClass;

	private GymSubscriptionClass gSubscriptionClass;

	private GymTime gymTime;

	private GymClass gymClass;

	public GymService(AddGymRepository addGymRepository, GymTimeRepo gymTimeRepo, GymAddressRepo gymAddressRepo,
			GymSubscriptionRepo gymSubscriptionRepo, RatingService ratingService) {

				this.gymRepository=addGymRepository;
				this.timeRepo=gymTimeRepo;
				this.addressRepo=gymAddressRepo;
				this.subcriptionRepo=gymSubscriptionRepo;
				this.ratingService=ratingService;
	}

    // Add New Gym
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
		gymRepository.save(newGym);
		return newGym;
	}

	// Find gym by Gym_id
	public GymRepresnt getGymByGymId(String gymId) {
		GymRepresnt gym = new GymRepresnt();
		Optional<GymClass> optional=gymRepository.findById(gymId);
		if(optional.isPresent())
		{
			gymClass=optional.get();
			gym.setId(gymClass.getId());
			gym.setEmail(gymClass.getEmail());
			gym.setGymName(gymClass.getName());
			Optional<GymAddressClass> optional2=addressRepo.findById(gymId);
			if(optional2.isPresent())
			{
				gymAddressClass=optional2.get();
				gym.setGymAddress(gymAddressClass);
			}
			gym.setWorkoutList(gymClass.getWorkout());
			Optional<GymTime> optional3=timeRepo.findById(gymId);
			if(optional3.isPresent())
			{
				gymTime=optional3.get();
				gym.setTiming(gymTime);
			}
			Optional<GymSubscriptionClass> optional4=subcriptionRepo.findById(gymId);
			if(optional4.isPresent())
			{
				gSubscriptionClass=optional4.get();
				gym.setSubscription(gSubscriptionClass);
			}
			gym.setContact(gymClass.getContact());
			gym.setCapacity(gymClass.getCapacity());
			gym.setRating(gymClass.getRating());

		return gym;
		}
		else
		{
			return null;
		}
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
			gym.setGymName(eachGym.getName());
			Optional<GymAddressClass> optional2=addressRepo.findById(id);
			if(optional2.isPresent())
			{
				gymAddressClass=optional2.get();
				gym.setGymAddress(gymAddressClass);
			}
			gym.setWorkoutList(eachGym.getWorkout());
			Optional<GymTime> optional3=timeRepo.findById(id);
			if(optional3.isPresent())
			{
				gymTime=optional3.get();
				gym.setTiming(gymTime);
			}
			Optional<GymSubscriptionClass> optional4=subcriptionRepo.findById(id);
			if(optional4.isPresent())
			{
				gSubscriptionClass=optional4.get();
				gym.setSubscription(gSubscriptionClass);
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
		GymAddressClass gymAddressClass1=new GymAddressClass();
		Optional<GymAddressClass> optional2=addressRepo.findById(id);
			if(optional2.isPresent())
			{
				gymAddressClass=optional2.get();
				return gymAddressClass;
			}
			else
			{
				return gymAddressClass1;
			}
	}

	// Find All gym from database..
	public List<GymClass> getAllGym() {
		return gymRepository.findAll();
	}

	// edit gym
	public GymClass editGym(GymClassModel gymClassModel, String gymId) {
		GymClass theGym;
		Optional<GymClass> optional=gymRepository.findById(gymId);
		if(optional.isPresent())
		{
			theGym=optional.get();

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
		theGym.setId(gymId);
		theGym.setEmail(gymClassModel.getVendorEmail());
		theGym.setName(gymClassModel.getMGymname());
		theGym.setWorkout(gymClassModel.getMWorkoutlist());
		theGym.setContact(gymClassModel.getMContact());
		theGym.setCapacity(gymClassModel.getMCapacity());

		gymRepository.save(theGym);
		return theGym;
		
		}
		else
		{
			return new GymClass();
		}

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

			for (GymAddressClass ad : addressList) {
				String id = ad.getId();
				GymRepresnt gym = new GymRepresnt();
				Optional<GymClass> optional=gymRepository.findById(id);
				if(optional.isPresent())
				{
					gymClass=optional.get();
					Optional<GymTime> optional3=timeRepo.findById(id);
				if(optional3.isPresent())
				{
					gymTime=optional3.get();
					gym.setTiming(gymTime);
				}
				Optional<GymSubscriptionClass> optional4=subcriptionRepo.findById(id);
				if(optional4.isPresent())
				{
					gSubscriptionClass=optional4.get();
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
