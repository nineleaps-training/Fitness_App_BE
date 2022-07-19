package com.fitness.app.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.model.GymClassModel;
import com.fitness.app.model.GymRepresentModel;

@Component
public interface GymDAO {

    public GymClass addNewGym(GymClassModel gymClassModel);

    public GymRepresentModel getGymByGymId(String gymId);

    public List<GymRepresentModel> getGymByVendorEmail(String email);

    public GymAddressClass findTheAddress(String id);

    public List<GymClass> getAllGym();

    public GymClass editGym(GymClassModel gymClassModel, String gymId);

    public GymClass getGymByGymName(String gymName);

    public List<GymRepresentModel> getGymByCity(String city); 
    
}