package com.fitness.app.service;


import com.fitness.app.dto.requestDtos.GymClassModel;
import com.fitness.app.dto.responceDtos.GymRepresent;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.entity.GymTimeClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.GymAddressRepository;
import com.fitness.app.repository.GymSubscriptionRepository;
import com.fitness.app.repository.GymTimeRepository;
import com.fitness.app.service.dao.GymService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GymServiceImpl implements GymService {


    final private AddGymRepository gymRepo;
    final private GymAddressRepository addressRepo;
    final private GymTimeRepository timeRepo;
    final private GymSubscriptionRepository subscriptionRepo;
    final private RatingServiceImpl ratingServiceImpl;


    // Add New Gym
    @Override
    public ApiResponse addNewGym(GymClassModel gymClassModel) {
        GymClass gym = gymRepo.findByName(gymClassModel.getGym_name());
        Long idLast = gymRepo.count() + 1;
        String gym_id = "GM" + idLast;
        if (gym != null) {
            gym_id = gym.getId();
        }

        // Creating address of gym
        GymAddressClass address = gymClassModel.getGymAddress();
        address.setId(gym_id);
        addressRepo.save(address);

        // set time
        GymTimeClass time = gymClassModel.getTiming();
        time.setId(gym_id);
        timeRepo.save(time);

        // set subscription.
        GymSubscriptionClass subscription = gymClassModel.getSubscription();
        subscription.setId(gym_id);
        subscriptionRepo.save(subscription);

        GymClass newGym = new GymClass();
        newGym.setId(gym_id);
        newGym.setEmail(gymClassModel.getVendor_email());
        newGym.setName(gymClassModel.getGym_name());
        newGym.setWorkout(gymClassModel.getWorkoutList());
        newGym.setContact(gymClassModel.getContact());
        newGym.setCapacity(gymClassModel.getCapacity());

        gymRepo.save(newGym);
        return new ApiResponse(HttpStatus.OK, "Successful");
    }


    // Find gym by Gym_id
    @Override
    public GymRepresent getGymByGymId(String gym_id) {
        GymRepresent gym = new GymRepresent();
        GymClass gym_cl = new GymClass();
        GymAddressClass address = new GymAddressClass();
        GymSubscriptionClass subs = new GymSubscriptionClass();
        GymTimeClass time = new GymTimeClass();
        Optional<GymClass> data = gymRepo.findById(gym_id);
        Optional<GymAddressClass> dataAddress = addressRepo.findById(gym_id);
        Optional<GymSubscriptionClass> dataSubs = subscriptionRepo.findById(gym_id);
        Optional<GymTimeClass> dataTime = timeRepo.findById(gym_id);
        if (data.isPresent()) {
            gym_cl = data.get();
        }
        if (dataAddress.isPresent()) {
            address = dataAddress.get();
        }
        if (dataSubs.isPresent()) {
            subs = dataSubs.get();
        }
        if (dataTime.isPresent()) {
            time = dataTime.get();
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
    @Override
    public List<GymRepresent> getGymByVendorEmail(String email) {

        List<GymClass> gymList = gymRepo.findByEmail(email);
        List<GymRepresent> gyms = new ArrayList<>();

        for (GymClass eachGym : gymList) {
            GymRepresent gym = new GymRepresent();
            Double rate = ratingServiceImpl.getRating(eachGym.getId());
            eachGym.setRating(rate);
            gymRepo.save(eachGym);
            String id = eachGym.getId();
            gym.setEmail(eachGym.getEmail());
            gym.setId(eachGym.getId());
            gym.setGym_name(eachGym.getName());
            gym.setGymAddress(addressRepo.findById(id).orElse(null));
            gym.setWorkoutList(eachGym.getWorkout());
            gym.setTiming(timeRepo.findById(id).orElse(null));
            gym.setSubscription(subscriptionRepo.findById(id).orElse(null));
            gym.setContact(eachGym.getContact());
            gym.setRating(rate);
            gym.setCapacity(eachGym.getCapacity());

            gyms.add(gym);
        }
        return gyms;
    }

    // Find gym address by gym id
    @Override
    public GymAddressClass findTheAddress(String id) {
        GymAddressClass address = new GymAddressClass();
        Optional<GymAddressClass> address_cl = addressRepo.findById(id);
        if (address_cl.isPresent()) {
            address = address_cl.get();
        }
        return address;
    }

    // Find All gym from database..
    @Override
    public List<GymClass> getAllGym() {
        return gymRepo.findAll();
    }

    @Override
    public GymClass getGymByGymName(String gymName) {
        return gymRepo.findByName(gymName);
    }

    // Find by City

    @Override
    public List<GymRepresent> getGymByCity(String city) throws DataNotFoundException {
        try {
            List<GymAddressClass> addressList = addressRepo.findByCity(city);
            List<GymRepresent> gyms = new ArrayList<>();
            if (addressList == null) {
                throw new DataNotFoundException("Data not found for Fitness Center: ");
            }
            for (GymAddressClass address : addressList) {
                String id = address.getId();
                GymRepresent gym = new GymRepresent();
                GymClass gymClass = new GymClass();
                GymTimeClass time = new GymTimeClass();
                GymSubscriptionClass subscription = new GymSubscriptionClass();
                Optional<GymClass> gym_cl = gymRepo.findById(id);
                Optional<GymSubscriptionClass> subscription_cl = subscriptionRepo.findById(id);
                Optional<GymTimeClass> time_cl = timeRepo.findById(id);
                if (gym_cl.isPresent()) {
                    gymClass = gym_cl.get();
                }
                if (subscription_cl.isPresent()) {
                    subscription = subscription_cl.get();
                }
                if (time_cl.isPresent()) {
                    time = time_cl.get();
                }
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
            log.error("GymServiceImpl ::-> getGymByCity :: Error found due to: {}", e.getMessage());
            throw new DataNotFoundException(" Something went Wrong: " + e.getMessage());
        }
    }


}
