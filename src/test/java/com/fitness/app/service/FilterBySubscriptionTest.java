package com.fitness.app.service;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.entity.GymTime;
import com.fitness.app.model.GymClassModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FilterBySubscriptionTest {

    FilterBySubscription filterBySubscription = new FilterBySubscription();

    @Test
    void returnAllTheGymsWhenTheirMonthlySubscriptionIsLessThanGivenPrice() {
        GymAddressClass gymAddress = new GymAddressClass("123ab", 1234.0, 1234.00, "Koramangala", "Bangalore");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("Cardio");
        workoutList.add("Zumba");
        GymTime timing = new GymTime("123", "8 AM", "6 PM", "Sunday");
        GymSubscriptionClass subscription1 = new GymSubscriptionClass("123", 100, 500, 1500, 3000, 6000, 100);
        GymSubscriptionClass subscription2 = new GymSubscriptionClass("123", 100, 200, 1500, 3000, 6000, 100);


        GymClassModel gymClassModel1 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription1,
                9685903290L, 500);

        GymClassModel gymClassModel2 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription2,
                9685903290L, 500);

        List<GymClassModel> gymClassModels = new ArrayList<>();
        gymClassModels.add(gymClassModel1);
        gymClassModels.add(gymClassModel2);

        List<GymClassModel> actual = filterBySubscription.filterByMonthly(700, gymClassModels);
        assertEquals(gymClassModels, actual);

    }

    @Test
    void returnNoGymsWhenTheirMonthlySubscriptionIsGreaterThanGivenPrice() {
        GymAddressClass gymAddress = new GymAddressClass("123ab", 1234.0, 2345.0, "Koramangala", "Bangalore");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("Cardio");
        workoutList.add("Zumba");
        GymTime timing = new GymTime("123", "8 AM", "6 PM", "Sunday");
        GymSubscriptionClass subscription1 = new GymSubscriptionClass("123", 100, 500, 1500, 3000, 6000, 100);
        GymSubscriptionClass subscription2 = new GymSubscriptionClass("123", 100, 700, 1500, 3000, 6000, 100);


        GymClassModel gymClassModel1 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription1,
                9685903290L, 500);

        GymClassModel gymClassModel2 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription2,
                9685903290L, 500);

        List<GymClassModel> gymClassModels = new ArrayList<>();
        gymClassModels.add(gymClassModel1);
        gymClassModels.add(gymClassModel2);

        List<GymClassModel> gymClassModelNull = new ArrayList<>();

        List<GymClassModel> actual = filterBySubscription.filterByMonthly(100, gymClassModels);
        assertEquals(gymClassModelNull, actual);

    }

    @Test
    void returnOnlyThoseGymsWhoseMonthlySubscriptionIsLessThanGivenPrice() {
        GymAddressClass gymAddress = new GymAddressClass("123ab", 1234.0, 2345.0, "Koramangala", "Bangalore");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("Cardio");
        workoutList.add("Zumba");
        GymTime timing = new GymTime("123", "8 AM", "6 PM", "Sunday");
        GymSubscriptionClass subscription1 = new GymSubscriptionClass("123", 100, 500, 1500, 3000, 6000, 100);
        GymSubscriptionClass subscription2 = new GymSubscriptionClass("123", 100, 700, 1500, 3000, 6000, 100);


        GymClassModel gymClassModel1 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription1,
                9685903290L, 500);

        GymClassModel gymClassModel2 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription2,
                9685903290L, 500);

        List<GymClassModel> gymClassModels = new ArrayList<>();
        gymClassModels.add(gymClassModel1);
        gymClassModels.add(gymClassModel2);

        List<GymClassModel> gymClassModelList = new ArrayList<>();
        gymClassModelList.add(gymClassModel1);

        List<GymClassModel> actual = filterBySubscription.filterByMonthly(400, gymClassModels);
        assertEquals(gymClassModelList, actual);

    }

    @Test
    void returnNullGymListForMonthlySubscriptionWhenGymClassModelIsNull() {
        List<GymClassModel> gymClassModels = new ArrayList<>();

        List<GymClassModel> actual = filterBySubscription.filterByMonthly(500, gymClassModels);
        assertEquals(gymClassModels, actual);

    }

    @Test
    void returnAllTheGymsWhenTheirQuarterlySubscriptionIsLessThanGivenPrice() {
        GymAddressClass gymAddress = new GymAddressClass("123ab", 1234.0, 1234.00, "Koramangala", "Bangalore");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("Cardio");
        workoutList.add("Zumba");
        GymTime timing = new GymTime("123", "8 AM", "6 PM", "Sunday");
        GymSubscriptionClass subscription1 = new GymSubscriptionClass("123", 100, 500, 1500, 3000, 6000, 100);
        GymSubscriptionClass subscription2 = new GymSubscriptionClass("123", 100, 200, 1200, 2400, 4800, 100);


        GymClassModel gymClassModel1 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription1,
                9685903290L, 500);

        GymClassModel gymClassModel2 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription2,
                9685903290L, 500);

        List<GymClassModel> gymClassModels = new ArrayList<>();
        gymClassModels.add(gymClassModel1);
        gymClassModels.add(gymClassModel2);

        List<GymClassModel> actual = filterBySubscription.filterByQuarterly(1400, gymClassModels);
        assertEquals(gymClassModels, actual);

    }

    @Test
    void returnNoGymsWhenTheirQuarterlySubscriptionIsGreaterThanGivenPrice() {
        GymAddressClass gymAddress = new GymAddressClass("123ab", 1234.0, 1234.00, "Koramangala", "Bangalore");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("Cardio");
        workoutList.add("Zumba");
        GymTime timing = new GymTime("123", "8 AM", "6 PM", "Sunday");
        GymSubscriptionClass subscription1 = new GymSubscriptionClass("123", 100, 500, 1500, 3000, 6000, 100);
        GymSubscriptionClass subscription2 = new GymSubscriptionClass("123", 100, 200, 1200, 2400, 4800, 100);


        GymClassModel gymClassModel1 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription1,
                9685903290L, 500);

        GymClassModel gymClassModel2 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription2,
                9685903290L, 500);

        List<GymClassModel> gymClassModels = new ArrayList<>();
        gymClassModels.add(gymClassModel1);
        gymClassModels.add(gymClassModel2);

        List<GymClassModel> gymClassModelNull = new ArrayList<>();


        List<GymClassModel> actual = filterBySubscription.filterByQuarterly(600, gymClassModels);
        assertEquals(gymClassModelNull, actual);

    }

    @Test
    void returnOnlyThoseGymsWhoseQuarterlySubscriptionIsLessThanGivenPrice() {
        GymAddressClass gymAddress = new GymAddressClass("123ab", 1234.0, 1234.00, "Koramangala", "Bangalore");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("Cardio");
        workoutList.add("Zumba");
        GymTime timing = new GymTime("123", "8 AM", "6 PM", "Sunday");
        GymSubscriptionClass subscription1 = new GymSubscriptionClass("123", 100, 500, 1500, 3000, 6000, 100);
        GymSubscriptionClass subscription2 = new GymSubscriptionClass("123", 100, 200, 1200, 2400, 4800, 100);


        GymClassModel gymClassModel1 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription1,
                9685903290L, 500);

        GymClassModel gymClassModel2 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription2,
                9685903290L, 500);

        List<GymClassModel> gymClassModels = new ArrayList<>();
        gymClassModels.add(gymClassModel1);
        gymClassModels.add(gymClassModel2);

        List<GymClassModel> gymClassModelList = new ArrayList<>();
        gymClassModelList.add(gymClassModel2);



        List<GymClassModel> actual = filterBySubscription.filterByQuarterly(800, gymClassModels);
        assertEquals(gymClassModelList, actual);

    }

    @Test
    void returnNullGymListForQuarterlySubscriptionWhenGymClassModelIsNull() {
        List<GymClassModel> gymClassModels = new ArrayList<>();

        List<GymClassModel> actual = filterBySubscription.filterByQuarterly(500, gymClassModels);
        assertEquals(gymClassModels, actual);

    }

    @Test
    void returnAllTheGymsWhenTheirHalfYearlySubscriptionIsLessThanGivenPrice() {
        GymAddressClass gymAddress = new GymAddressClass("123ab", 1234.0, 1234.00, "Koramangala", "Bangalore");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("Cardio");
        workoutList.add("Zumba");
        GymTime timing = new GymTime("123", "8 AM", "6 PM", "Sunday");
        GymSubscriptionClass subscription1 = new GymSubscriptionClass("123", 100, 500, 1500, 3000, 6000, 100);
        GymSubscriptionClass subscription2 = new GymSubscriptionClass("123", 100, 200, 1200, 2400, 4800, 100);


        GymClassModel gymClassModel1 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription1,
                9685903290L, 500);

        GymClassModel gymClassModel2 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription2,
                9685903290L, 500);

        List<GymClassModel> gymClassModels = new ArrayList<>();
        gymClassModels.add(gymClassModel1);
        gymClassModels.add(gymClassModel2);

        List<GymClassModel> actual = filterBySubscription.filterByHalfYearly(2500, gymClassModels);
        assertEquals(gymClassModels, actual);

    }

    @Test
    void returnNoGymsWhenTheirHalfYearlySubscriptionIsGreaterThanGivenPrice() {
        GymAddressClass gymAddress = new GymAddressClass("123ab", 1234.0, 1234.00, "Koramangala", "Bangalore");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("Cardio");
        workoutList.add("Zumba");
        GymTime timing = new GymTime("123", "8 AM", "6 PM", "Sunday");
        GymSubscriptionClass subscription1 = new GymSubscriptionClass("123", 100, 500, 1500, 3000, 6000, 100);
        GymSubscriptionClass subscription2 = new GymSubscriptionClass("123", 100, 200, 1200, 2400, 4800, 100);


        GymClassModel gymClassModel1 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription1,
                9685903290L, 500);

        GymClassModel gymClassModel2 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription2,
                9685903290L, 500);

        List<GymClassModel> gymClassModels = new ArrayList<>();
        gymClassModels.add(gymClassModel1);
        gymClassModels.add(gymClassModel2);

        List<GymClassModel> gymClassModelNull = new ArrayList<>();


        List<GymClassModel> actual = filterBySubscription.filterByHalfYearly(1200, gymClassModels);
        assertEquals(gymClassModelNull, actual);
    }

    @Test
    void returnOnlyThoseGymsWhoseHalfYearlySubscriptionIsLessThanGivenPrice() {
        GymAddressClass gymAddress = new GymAddressClass("123ab", 1234.0, 1234.00, "Koramangala", "Bangalore");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("Cardio");
        workoutList.add("Zumba");
        GymTime timing = new GymTime("123", "8 AM", "6 PM", "Sunday");
        GymSubscriptionClass subscription1 = new GymSubscriptionClass("123", 100, 500, 1500, 3000, 6000, 100);
        GymSubscriptionClass subscription2 = new GymSubscriptionClass("123", 100, 200, 1200, 2400, 4800, 100);


        GymClassModel gymClassModel1 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription1,
                9685903290L, 500);

        GymClassModel gymClassModel2 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription2,
                9685903290L, 500);

        List<GymClassModel> gymClassModels = new ArrayList<>();
        gymClassModels.add(gymClassModel1);
        gymClassModels.add(gymClassModel2);

        List<GymClassModel> gymClassModelList = new ArrayList<>();
        gymClassModelList.add(gymClassModel2);



        List<GymClassModel> actual = filterBySubscription.filterByHalfYearly(1800, gymClassModels);
        assertEquals(gymClassModelList, actual);

    }

    @Test
    void returnNullGymListForHalfYearlySubscriptionWhenGymClassModelIsNull() {
        List<GymClassModel> gymClassModels = new ArrayList<>();

        List<GymClassModel> actual = filterBySubscription.filterByHalfYearly(1500, gymClassModels);
        assertEquals(gymClassModels, actual);

    }

    @Test
    void returnAllTheGymsWhenTheirYearlySubscriptionIsLessThanGivenPrice() {
        GymAddressClass gymAddress = new GymAddressClass("123ab", 1234.0, 1234.00, "Koramangala", "Bangalore");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("Cardio");
        workoutList.add("Zumba");
        GymTime timing = new GymTime("123", "8 AM", "6 PM", "Sunday");
        GymSubscriptionClass subscription1 = new GymSubscriptionClass("123", 100, 500, 1500, 3000, 6000, 100);
        GymSubscriptionClass subscription2 = new GymSubscriptionClass("123", 100, 200, 1200, 2400, 4800, 100);


        GymClassModel gymClassModel1 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription1,
                9685903290L, 500);

        GymClassModel gymClassModel2 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription2,
                9685903290L, 500);

        List<GymClassModel> gymClassModels = new ArrayList<>();
        gymClassModels.add(gymClassModel1);
        gymClassModels.add(gymClassModel2);

        List<GymClassModel> actual = filterBySubscription.filterByYearly(5600, gymClassModels);
        assertEquals(gymClassModels, actual);

    }

    @Test
    void returnNoGymsWhenTheirYearlySubscriptionIsGreaterThanGivenPrice() {
        GymAddressClass gymAddress = new GymAddressClass("123ab", 1234.0, 1234.00, "Koramangala", "Bangalore");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("Cardio");
        workoutList.add("Zumba");
        GymTime timing = new GymTime("123", "8 AM", "6 PM", "Sunday");
        GymSubscriptionClass subscription1 = new GymSubscriptionClass("123", 100, 500, 1500, 3000, 6000, 100);
        GymSubscriptionClass subscription2 = new GymSubscriptionClass("123", 100, 200, 1200, 2400, 4800, 100);


        GymClassModel gymClassModel1 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription1,
                9685903290L, 500);

        GymClassModel gymClassModel2 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription2,
                9685903290L, 500);

        List<GymClassModel> gymClassModels = new ArrayList<>();
        gymClassModels.add(gymClassModel1);
        gymClassModels.add(gymClassModel2);

        List<GymClassModel> gymClassModelNull = new ArrayList<>();


        List<GymClassModel> actual = filterBySubscription.filterByYearly(3000, gymClassModels);
        assertEquals(gymClassModelNull, actual);
    }

    @Test
    void returnOnlyThoseGymsWhoseYearlySubscriptionIsLessThanGivenPrice() {
        GymAddressClass gymAddress = new GymAddressClass("123ab", 1234.0, 1234.00, "Koramangala", "Bangalore");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("Cardio");
        workoutList.add("Zumba");
        GymTime timing = new GymTime("123", "8 AM", "6 PM", "Sunday");
        GymSubscriptionClass subscription1 = new GymSubscriptionClass("123", 100, 500, 1500, 3000, 6000, 100);
        GymSubscriptionClass subscription2 = new GymSubscriptionClass("123", 100, 200, 1200, 2400, 4800, 100);


        GymClassModel gymClassModel1 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription1,
                9685903290L, 500);

        GymClassModel gymClassModel2 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription2,
                9685903290L, 500);

        List<GymClassModel> gymClassModels = new ArrayList<>();
        gymClassModels.add(gymClassModel1);
        gymClassModels.add(gymClassModel2);

        List<GymClassModel> gymClassModelList = new ArrayList<>();
        gymClassModelList.add(gymClassModel2);



        List<GymClassModel> actual = filterBySubscription.filterByYearly(4000, gymClassModels);
        assertEquals(gymClassModelList, actual);

    }

    @Test
    void returnNullGymListForYearlySubscriptionWhenGymClassModelIsNull() {
        List<GymClassModel> gymClassModels = new ArrayList<>();

        List<GymClassModel> actual = filterBySubscription.filterByYearly(4500, gymClassModels);
        assertEquals(gymClassModels, actual);
    }

    @Test
    void returnAllTheGymsWhenTheirOneWorkoutIsLessThanGivenPrice() {
        GymAddressClass gymAddress = new GymAddressClass("123ab", 1234.0, 1234.00, "Koramangala", "Bangalore");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("Cardio");
        workoutList.add("Zumba");
        GymTime timing = new GymTime("123", "8 AM", "6 PM", "Sunday");
        GymSubscriptionClass subscription1 = new GymSubscriptionClass("123", 300, 500, 1500, 3000, 6000, 100);
        GymSubscriptionClass subscription2 = new GymSubscriptionClass("123", 100, 200, 1200, 2400, 4800, 100);


        GymClassModel gymClassModel1 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription1,
                9685903290L, 500);

        GymClassModel gymClassModel2 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription2,
                9685903290L, 500);

        List<GymClassModel> gymClassModels = new ArrayList<>();
        gymClassModels.add(gymClassModel1);
        gymClassModels.add(gymClassModel2);

        List<GymClassModel> actual = filterBySubscription.filterByOneWorkout(300, gymClassModels);
        assertEquals(gymClassModels, actual);

    }

    @Test
    void returnNoGymsWhenTheirOneWorkoutIsGreaterThanGivenPrice() {
        GymAddressClass gymAddress = new GymAddressClass("123ab", 1234.0, 1234.00, "Koramangala", "Bangalore");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("Cardio");
        workoutList.add("Zumba");
        GymTime timing = new GymTime("123", "8 AM", "6 PM", "Sunday");
        GymSubscriptionClass subscription1 = new GymSubscriptionClass("123", 300, 500, 1500, 3000, 6000, 100);
        GymSubscriptionClass subscription2 = new GymSubscriptionClass("123", 100, 200, 1200, 2400, 4800, 100);


        GymClassModel gymClassModel1 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription1,
                9685903290L, 500);

        GymClassModel gymClassModel2 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription2,
                9685903290L, 500);

        List<GymClassModel> gymClassModels = new ArrayList<>();
        gymClassModels.add(gymClassModel1);
        gymClassModels.add(gymClassModel2);

        List<GymClassModel> gymClassModelNull = new ArrayList<>();


        List<GymClassModel> actual = filterBySubscription.filterByOneWorkout(40, gymClassModels);
        assertEquals(gymClassModelNull, actual);
    }

    @Test
    void returnOnlyThoseGymsWhoseOneWorkoutIsLessThanGivenPrice() {
        GymAddressClass gymAddress = new GymAddressClass("123ab", 1234.0, 1234.00, "Koramangala", "Bangalore");
        List<String> workoutList = new ArrayList<>();
        workoutList.add("Cardio");
        workoutList.add("Zumba");
        GymTime timing = new GymTime("123", "8 AM", "6 PM", "Sunday");
        GymSubscriptionClass subscription1 = new GymSubscriptionClass("123", 300, 500, 1500, 3000, 6000, 100);
        GymSubscriptionClass subscription2 = new GymSubscriptionClass("123", 100, 200, 1200, 2400, 4800, 100);


        GymClassModel gymClassModel1 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription1,
                9685903290L, 500);

        GymClassModel gymClassModel2 = new GymClassModel("priyanshi.chturvedi@nineleaps.com",
                "Fitness Centre", gymAddress, workoutList, timing, subscription2,
                9685903290L, 500);

        List<GymClassModel> gymClassModels = new ArrayList<>();
        gymClassModels.add(gymClassModel1);
        gymClassModels.add(gymClassModel2);

        List<GymClassModel> gymClassModelList = new ArrayList<>();
        gymClassModelList.add(gymClassModel2);



        List<GymClassModel> actual = filterBySubscription.filterByOneWorkout(80, gymClassModels);
        assertEquals(gymClassModelList, actual);

    }

    @Test
    void returnNullGymListForOneWorkoutWhenGymClassModelIsNull() {
        List<GymClassModel> gymClassModels = new ArrayList<>();

        List<GymClassModel> actual = filterBySubscription.filterByOneWorkout(200, gymClassModels);
        assertEquals(gymClassModels, actual);
    }

}