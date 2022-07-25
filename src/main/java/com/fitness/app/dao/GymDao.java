package com.fitness.app.dao;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.model.GymClassModel;
import com.fitness.app.model.GymRepresent;

import java.util.List;

public interface GymDao {

    GymClass addNewGym(GymClassModel gymClassModel);
    GymRepresent getGymByGymId(String gymId);
    List<GymRepresent> getGymByVendorEmail(String email);
    GymAddressClass findTheAddress(String id);
    List<GymClass> getAllGym();
    GymClass editGym(GymClassModel gymClassModel, String gymId);
    String wipingAll();
    GymClass getGymByGymName(String gymName);
    List<GymRepresent> getGymByCity(String city);

}
