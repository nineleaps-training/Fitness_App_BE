package com.fitness.app.service;

import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.dto.GymClassModel;
import com.fitness.app.dto.GymRepresent;

import java.util.List;

public interface GymService {

     ApiResponse addNewGym(GymClassModel gymClassModel);
     GymRepresent getGymByGymId(String gym_id);
     List<GymRepresent> getGymByVendorEmail(String email);
     GymAddressClass findTheAddress(String id);
     List<GymClass> getAllGym();
     GymClass getGymByGymName(String gymName);
     List<GymRepresent> getGymByCity(String city) throws DataNotFoundException;

}
