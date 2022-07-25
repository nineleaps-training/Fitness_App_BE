package com.fitness.app.dao;

import com.fitness.app.model.GymClassModel;

import java.util.List;

public interface FilterBySubscriptionDao {
    List<GymClassModel> filterByMonthly(int price, List<GymClassModel> listGym);
    List<GymClassModel> filterByQuarterly(int price, List<GymClassModel> listGym);
    List<GymClassModel> filterByHalfYearly(int price, List<GymClassModel> listGym);
    List<GymClassModel> filterByYearly(int price, List<GymClassModel> listGym);
    List<GymClassModel> filterByOneWorkout(int price, List<GymClassModel> listGym);
}
