package com.fitness.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fitness.app.dao.GymDao;
import com.fitness.app.entity.*;
import com.fitness.app.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fitness.app.model.GymClassModel;
import com.fitness.app.model.GymRepresent;

@Component
@Slf4j
public class GymService implements GymDao {

    private AddGymRepository gymRepository;

    private GymAddressRepository addressRepo;

    private GymTimeRepository timeRepo;

    private GymSubscriptionRepository subscriptionRepo;

    private RatingService ratingService;


    @Autowired
    public GymService(AddGymRepository gymRepository, GymAddressRepository addressRepo, GymTimeRepository timeRepo, GymSubscriptionRepository subscriptionRepo, RatingService ratingService) {
        this.gymRepository = gymRepository;
        this.addressRepo = addressRepo;
        this.timeRepo = timeRepo;
        this.subscriptionRepo = subscriptionRepo;
        this.ratingService = ratingService;
    }

    // Add New Gym
    public GymClass addNewGym(GymClassModel gymClassModel) {
        log.info("GymService >> addNewGym >> Initiated");
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
        subscriptionRepo.save(subscription);

        GymClass newGym = new GymClass();
        newGym.setId(gymId);
        newGym.setEmail(gymClassModel.getVendorEmail());
        newGym.setName(gymClassModel.getGymName());
        newGym.setWorkout(gymClassModel.getWorkoutList());
        newGym.setContact(gymClassModel.getContact());
        newGym.setCapacity(gymClassModel.getCapacity());

        gymRepository.save(newGym);
        log.info("GymService >> addNewGym >> Ends");
        return newGym;
    }

    // Find gym by Gym_id
    public GymRepresent getGymByGymId(String gymId) {
        log.info("GymService >> getGymByGymId >> Initiated");
        GymRepresent gym = new GymRepresent();
        GymClass gymClass;
        GymAddressClass gymAddressClass;
        GymTime gymTime;
        GymSubscriptionClass gymSubscriptionClass;

        Optional<GymClass> optional = gymRepository.findById(gymId);
        if (optional.isPresent()) {
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

            Optional<GymSubscriptionClass> optional3 = subscriptionRepo.findById(gymId);
            if (optional3.isPresent()) {
                gymSubscriptionClass = optional3.get();
                gym.setSubscription(gymSubscriptionClass);
            }

            gym.setContact(gymClass.getContact());
            gym.setCapacity(gymClass.getCapacity());
            gym.setRating(gymClass.getRate());
        }
        log.info("GymService >> getGymByGymId >> Ends");
        return gym;
    }

    // Find All gym of a vendor by email id..
    public List<GymRepresent> getGymByVendorEmail(String email) {
        log.info("GymService >> getGymByVendorEmail >> Initiated");

        List<GymClass> gymList = gymRepository.findByEmail(email);
        List<GymRepresent> gyms = new ArrayList<>();

        GymAddressClass gymAddressClass;
        GymTime gymTime;
        GymSubscriptionClass gymSubscriptionClass;

        for (GymClass eachGym : gymList) {
            GymRepresent gym = new GymRepresent();
            Double rate = ratingService.getRating(eachGym.getId());
            eachGym.setRate(rate);
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

            Optional<GymSubscriptionClass> optional2 = subscriptionRepo.findById(id);
            if (optional2.isPresent()) {
                gymSubscriptionClass = optional2.get();
                gym.setSubscription(gymSubscriptionClass);
            }
            gym.setContact(eachGym.getContact());
            gym.setRating(rate);
            gym.setCapacity(eachGym.getCapacity());

            gyms.add(gym);
        }
        log.info("GymService >> getGymByVendorEmail >> Ends");
        return gyms;
    }

    // Find gym address by gym id
    public GymAddressClass findTheAddress(String id) {
        log.info("GymService >> findTheAddress >> Initiated");
        Optional<GymAddressClass> optional = addressRepo.findById(id);

        log.info("GymService >> findTheAddress >> Ends");
        return optional.orElse(null);
    }

    // Find All gym from database..
    public List<GymClass> getAllGym() {
        log.info("GymService >> getAllGym >> Initiated");
        return gymRepository.findAll();
    }

    // edit gym
    public GymClass editGym(GymClassModel gymClassModel, String gymId) {
        log.info("GymService >> editGym >> Initiated");
        Optional<GymClass> optional = gymRepository.findById(gymId);
        GymClass theGym = new GymClass();
        if (optional.isPresent()) {
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
        subscriptionRepo.deleteById(theGym.getId());
        GymSubscriptionClass subscription;
        subscription = gymClassModel.getSubscription();
        subscription.setId(gymId);
        subscriptionRepo.save(subscription);

        gymRepository.delete(theGym);
        theGym.setId(gymId);
        theGym.setEmail(gymClassModel.getVendorEmail());
        theGym.setName(gymClassModel.getGymName());
        theGym.setWorkout(gymClassModel.getWorkoutList());
        theGym.setContact(gymClassModel.getContact());
        theGym.setCapacity(gymClassModel.getCapacity());

        gymRepository.save(theGym);
        log.info("GymService >> editGym >> Ends");
        return theGym;
    }

    public String wipingAll() {
        log.info("GymService >> wipingAll >> Initiated");
        timeRepo.deleteAll();
        subscriptionRepo.deleteAll();
        addressRepo.deleteAll();
        gymRepository.deleteAll();

        log.info("GymService >> wipingAll >> Ends");
        return "done";
    }

    public GymClass getGymByGymName(String gymName) {
        log.info("GymService >> getGymByGymName >> Initiated");
        return gymRepository.findByName(gymName);
    }

    // Gym By City or Loacltiy..


    // Find by City
    public List<GymRepresent> getGymByCity(String city) {
        log.info("GymService >> getGymByCity >> Initiated");
        List<GymAddressClass> addressList = addressRepo.findByCity(city);
        List<GymRepresent> gyms = new ArrayList<>();
        GymClass gymClass = new GymClass();
        GymSubscriptionClass subscription = new GymSubscriptionClass();
        GymTime time = new GymTime();

        for (GymAddressClass address : addressList) {
            String id = address.getId();
            GymRepresent gym = new GymRepresent();

            Optional<GymClass> optional = gymRepository.findById(id);
            if (optional.isPresent()) {
                gymClass = optional.get();
            }

            Optional<GymSubscriptionClass> optional1 = subscriptionRepo.findById(id);
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
            gym.setRating(gymClass.getRate());
            gym.setCapacity(gymClass.getCapacity());

            gyms.add(gym);
        }
        log.info("GymService >> getGymByCity >> Ends");
        return gyms;
    }


}
