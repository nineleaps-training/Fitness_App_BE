package com.fitness.app.service.dao;

import com.fitness.app.dto.response.ApiResponse;
import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.dto.request.GymClassModel;
import com.fitness.app.dto.response.GymRepresent;

import java.util.List;

public interface GymDao {

     ApiResponse addNewGym(GymClassModel gymClassModel);
     GymRepresent getGymByGymId(String gym_id);
     List<GymRepresent> getGymByVendorEmail(String email, int offSet, int pageSize);
     GymAddressClass findTheAddress(String id);
     List<GymClass> getAllGym();
     GymClass getGymByGymName(String gymName);
     List<GymRepresent> getGymByCity(String city, int offSet, int pageSize) throws DataNotFoundException;

}
