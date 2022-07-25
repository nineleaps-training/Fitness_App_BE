package com.fitness.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fitness.app.dao.FilterBySubscriptionDao;

import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.model.GymClassModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FilterBySubscription implements FilterBySubscriptionDao {

    //Filter based on monthly subscription
    public List<GymClassModel> filterByMonthly(int price, List<GymClassModel> listGym) {
        log.info("FilterBySubscription >> filterByMonthly >> Initiated");

        return listGym.stream()
                .filter(gym -> gym.getSubscription().getMonthly() <= (price + 200))
                .collect(Collectors.toList());
    }

    //Filter based on quarterly subscription
    public List<GymClassModel> filterByQuarterly(int price, List<GymClassModel> listGym) {
        log.info("FilterBySubscription >> filterByQuarterly >> Initiated");
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
        log.info("FilterBySubscription >> filterByHalfYearly >> Initiated");
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
        log.info("FilterBySubscription >> filterByYearly >> Initiated");
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
        log.info("FilterBySubscription >> filterByOneWorkout >> Initiated");
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
