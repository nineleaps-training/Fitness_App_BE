package com.fitness.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.model.GymClassModel;

@Service
public class FilterBySubscription {

	
	
	
	
	//Filter based on monthly subscription
	public List<GymClassModel> filterByMonthly(int price, List<GymClassModel> listGym)
	{
		
		List<GymClassModel> newList=new ArrayList<>();
		for(GymClassModel gym: listGym)
		{
			GymSubscriptionClass subscription=gym.getSubscription();
			if((subscription.getMonthly()<=price) || (subscription.getMonthly()<=(price+200)))
			{
				newList.add(gym);
			}
		}
		
		return newList;
	}

	//Filter based on quarterly subscription
	public List<GymClassModel> filterByQuarterly(int price, List<GymClassModel> listGym) {

		List<GymClassModel> list = new ArrayList<>();
		for(GymClassModel gym: listGym) {

			GymSubscriptionClass subscription = gym.getSubscription();
			if(subscription.getQuaterly()<=price || subscription.getQuaterly()<=(price+500)) {
				list.add(gym);
			}
		}
		return list;
	}


	public List<GymClassModel> filterByHalfYearly(int price, List<GymClassModel> listGym) {

		List<GymClassModel> list = new ArrayList<>();
		for(GymClassModel gym: listGym) {

			GymSubscriptionClass subscription = gym.getSubscription();
			if(subscription.getHalf()<=price || subscription.getHalf()<=(price+1000)) {
				list.add(gym);
			}
		}
		return list;
	}

	public List<GymClassModel> filterByYearly(int price, List<GymClassModel> listGym) {

		List<GymClassModel> list = new ArrayList<>();
		for(GymClassModel gym: listGym) {

			GymSubscriptionClass subscription = gym.getSubscription();
			if(subscription.getYearly()<=price || subscription.getYearly()<=(price+1500)) {
				list.add(gym);
			}
		}
		return list;
	}

	public List<GymClassModel> filterByOneWorkout(int price, List<GymClassModel> listGym) {

		List<GymClassModel> list = new ArrayList<>();
		for(GymClassModel gym: listGym) {

			GymSubscriptionClass subscription = gym.getSubscription();
			if(subscription.getOneWorkout()<=price || subscription.getOneWorkout()<=(price+200)) {
				list.add(gym);
			}
		}
		return list;
	}

}
