package com.fitness.app.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fitness.app.model.GymClassModel;

@Component
public interface FilterBySubscriptionDAO {

    public List<GymClassModel> filterByMonthly(int price, List<GymClassModel> listGym);

    public List<GymClassModel> filterByQuarterly(int price, List<GymClassModel> listGym);

    public List<GymClassModel> filterByHalfYearly(int price, List<GymClassModel> listGym);

    public List<GymClassModel> filterByYearly(int price, List<GymClassModel> listGym);

    public List<GymClassModel> filterByOneWorkout(int price, List<GymClassModel> listGym);

}