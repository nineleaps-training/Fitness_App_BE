package com.fitness.app.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fitness.app.entity.*;
import com.fitness.app.exceptions.DataNotFoundException;
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
		GymClass gym_cl=new GymClass();
		GymAddressClass address=new GymAddressClass();
		GymSubscriptionClass subs=new GymSubscriptionClass();
		GymTime time=new GymTime();
		
		Optional<GymClass> data=gymRepository.findById(gym_id);
		Optional<GymAddressClass> dataAddress=addressRepo.findById(gym_id);
        Optional<GymSubscriptionClass> dataSubs=subcriptionRepo.findById(gym_id);
        Optional<GymTime>dataTime=timeRepo.findById(gym_id);
		if(data.isPresent())
		{
			gym_cl=data.get();
		}
		
		if(dataAddress.isPresent())
		{
			address=dataAddress.get();
		}
		
		if(dataSubs.isPresent())
		{
			subs=dataSubs.get();
		}
		
		if(dataTime.isPresent())
		{
			time=dataTime.get();
		}
		
        
        
		
		gym.setId(gym_cl.getId());
		gym.setEmail(gym_cl.getEmail());
		gym.setGym_name(gym_cl.getName());
		gym.setGymAddress(address);
		gym.setWorkoutList(gym_cl.getWorkout());
		gym.setTiming(time);
		gym.setSubscription(subs);
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
			gym.setGymAddress(addressRepo.findById(id).orElse(null));
			gym.setWorkoutList(eachGym.getWorkout());
			gym.setTiming(timeRepo.findById(id).orElse(null));
			gym.setSubscription(subcriptionRepo.findById(id).orElse(null));
			gym.setContact(eachGym.getContact());
			gym.setRating(rate);
			gym.setCapacity(eachGym.getCapacity());

			gyms.add(gym);
		}
		return gyms;
	}

	// Find gym address by gym id
	public GymAddressClass findTheAddress(String id) {
		return addressRepo.findById(id).orElse(null);
	}

	// Find All gym from database..
	public List<GymClass> getAllGym() {
		return gymRepository.findAll();
	}

	// edit gym
	public GymClass editGym(GymClassModel gymClassModel, String gym_id) throws DataNotFoundException{

		try {
			
			GymClass theGym = new GymClass();
            Optional<GymClass> gymData=gymRepository.findById(gym_id);
            
            
            if(gymData.isPresent())
            {
              theGym=gymData.get();
            }
            else
            {
            	throw new DataNotFoundException("Data not found");
            }
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
			
		} catch(DataNotFoundException  e) {
			throw new  DataNotFoundException(e.getMessage());
		}

	}




	public GymClass getGymByGymName(String gymName) {
		return gymRepository.findByName(gymName);
	}




	// Find by City
	public List<GymRepresnt> getGymByCity(String city) throws DataNotFoundException {

		try {
			List<GymAddressClass> addressList = addressRepo.findByCity(city);
			List<GymRepresnt> gyms = new ArrayList<>();

			for (GymAddressClass address : addressList) {
				String id = address.getId();
				GymRepresnt gym = new GymRepresnt();
				GymClass gymClass = gymRepository.findById(id).orElse(null);
				GymSubscriptionClass subscription = subcriptionRepo.findById(id).orElse(null);
				GymTime time = timeRepo.findById(id).orElse(null);
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
		} catch (DataNotFoundException e) {
			throw new DataNotFoundException("Somthing went Wrong"+e.getMessage());
		}
	}





}
