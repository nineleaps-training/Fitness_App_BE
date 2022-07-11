package com.fitness.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.model.GymClassModel;

@Service
public class FilterBySubscription {


    //Filter based on monthly subscription
    public List<GymClassModel> filterByMonthly(int price, List<GymClassModel> listGym) {

        return listGym.stream()
                .filter(gym -> gym.getSubscription().getMonthly() <= (price + 200))
                .collect(Collectors.toList());
    }

    //Filter based on quarterly subscription
    public List<GymClassModel> filterByQuarterly(int price, List<GymClassModel> listGym) {

        List<GymClassModel> list = new ArrayList<>();
        for (GymClassModel gym : listGym) {

            GymSubscriptionClass subscription = gym.getSubscription();
            if (subscription.getQuarterly() <= price || subscription.getQuarterly() <= (price + 500)) {
                list.add(gym);
            }
        }
        return list;
    }


    public List<GymClassModel> filterByHalfYearly(int price, List<GymClassModel> listGym) {

        List<GymClassModel> list = new ArrayList<>();
        for (GymClassModel gym : listGym) {

            GymSubscriptionClass subscription = gym.getSubscription();
            if (subscription.getHalf() <= price || subscription.getHalf() <= (price + 1000)) {
                list.add(gym);
            }
        }
        return list;
    }

    public List<GymClassModel> filterByYearly(int price, List<GymClassModel> listGym) {

        List<GymClassModel> list = new ArrayList<>();
        for (GymClassModel gym : listGym) {

            GymSubscriptionClass subscription = gym.getSubscription();
            if (subscription.getYearly() <= price || subscription.getYearly() <= (price + 1500)) {
                list.add(gym);
            }
        }
        return list;
    }

    public List<GymClassModel> filterByOneWorkout(int price, List<GymClassModel> listGym) {

        List<GymClassModel> list = new ArrayList<>();
        for (GymClassModel gym : listGym) {

            GymSubscriptionClass subscription = gym.getSubscription();
            if (subscription.getOneWorkout() <= price || subscription.getOneWorkout() <= (price + 50)) {
                list.add(gym);
            }
        }
        return list;
    }

}
