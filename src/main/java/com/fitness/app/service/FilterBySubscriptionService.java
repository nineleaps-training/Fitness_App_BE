package com.fitness.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fitness.app.dao.FilterBySubscriptionDAO;
import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.model.GymClassModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FilterBySubscriptionService implements FilterBySubscriptionDAO {

	/**
	 * This function is used to filter the gyms by monthly subscription
	 * 
	 * @param price
	 * @param listGym
	 * @return
	 */
	public List<GymClassModel> filterByMonthly(int price, List<GymClassModel> listGym) {

		log.info("FilterBySubscriptionService >> filterByMonthly >> Initiateds");
		List<GymClassModel> newList = new ArrayList<>();
		for (GymClassModel gym : listGym) {
			GymSubscriptionClass subscription = gym.getMSubscription();
			if ((subscription.getMonthly() <= price) || (subscription.getMonthly() <= (price + 200))) // Filter based on monthly subscription
			{
				newList.add(gym);
			}
		}
		log.info("FilterBySubscriptionService >> filterByMonthly >> Terminated");
		return newList;
	}

	/**
	 * This function is used to filter the gyms by quaterly subscription
	 * 
	 * @param price
	 * @param listGym
	 * @return
	 */
	public List<GymClassModel> filterByQuarterly(int price, List<GymClassModel> listGym) {
		log.info("FilterBySubscriptionService >> filterByQuarterly >> Initiated");
		List<GymClassModel> list = new ArrayList<>();
		for (GymClassModel gym : listGym) {

			GymSubscriptionClass subscription = gym.getMSubscription();
			if (subscription.getQuaterly() <= price || subscription.getQuaterly() <= (price + 500)) // Filter based on quarterly subscription
			{
				list.add(gym);
			}
		}
		log.info("FilterBySubscriptionService >> filterByQuarterly >> end");
		return list;
	}

	/**
	 * This function is used to filter the gyms by half-yearly subscription
	 * 
	 * @param price
	 * @param listGym
	 * @return
	 */
	public List<GymClassModel> filterByHalfYearly(int price, List<GymClassModel> listGym) {
		log.info("FilterBySubscriptionService >> filterByHalfYearly >> Initiated");
		List<GymClassModel> list = new ArrayList<>();
		for (GymClassModel gym : listGym) {

			GymSubscriptionClass subscription = gym.getMSubscription();
			if (subscription.getHalf() <= price || subscription.getHalf() <= (price + 1000)) // Filter based on half-yearly subscription
			{
				list.add(gym);
			}
		}
		log.info("FilterBySubscriptionService >> filterByHalfYearly >> end");
		return list;
	}

	/**
	 * This function is used to filter the gyms by yearly subscription
	 * 
	 * @param price
	 * @param listGym
	 * @return
	 */
	public List<GymClassModel> filterByYearly(int price, List<GymClassModel> listGym) {
		log.info("FilterBySubscriptionService >> filterByYearly >> Initiated");
		List<GymClassModel> list = new ArrayList<>();
		for (GymClassModel gym : listGym) {

			GymSubscriptionClass subscription = gym.getMSubscription();
			if (subscription.getYearly() <= price || subscription.getYearly() <= (price + 1500)) // Filter based on yearly subscription
			{
				list.add(gym);
			}
		}
		log.info("FilterBySubscriptionService >> filterByYearly >> end");
		return list;
	}

	/**
	 * This function is used to filter the gyms by workout subscription
	 * 
	 * @param price
	 * @param listGym
	 * @return
	 */
	public List<GymClassModel> filterByOneWorkout(int price, List<GymClassModel> listGym) {
		log.info("FilterBySubscriptionService >> filterByOneWorkout >> Initiated");
		List<GymClassModel> list = new ArrayList<>();
		for (GymClassModel gym : listGym) {

			GymSubscriptionClass subscription = gym.getMSubscription();
			if (subscription.getOneWorkout() <= price || subscription.getOneWorkout() <= (price + 200)) // Filter based on workout subscription
			{
				list.add(gym);
			}
		}
		log.info("FilterBySubscriptionService >> filterByOneWorkout >> end");
		return list;
	}

}
