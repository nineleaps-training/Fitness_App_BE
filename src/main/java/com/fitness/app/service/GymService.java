package com.fitness.app.service;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.model.GymClassModel;
import com.fitness.app.model.GymRepresnt;

import java.util.List;

public interface GymService {

     GymClass addNewGym(GymClassModel gymClassModel);
     GymRepresnt getGymByGymId(String gym_id);
     List<GymRepresnt> getGymByVendorEmail(String email);
     GymAddressClass findTheAddress(String id);
     List<GymClass> getAllGym();
     GymClass getGymByGymName(String gymName);
     List<GymRepresnt> getGymByCity(String city) throws DataNotFoundException;
}
