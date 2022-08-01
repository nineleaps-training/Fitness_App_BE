package com.fitness.app.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.entity.GymTime;
import com.fitness.app.model.GymClassModel;

@ExtendWith(MockitoExtension.class)
class FilterBySubscriptionTest {

        @InjectMocks
        FilterBySubscriptionService filterBySubscription;

        @Test
        void testFilterByHalfYearly() {

                GymAddressClass gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                long l = 12345;
                GymTime gymTime = new GymTime("GM6", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymClassModel gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                                workoutList, gymTime, gymSubscriptionClass, l, 20);
                List<GymClassModel> list = new ArrayList<>();
                list.add(gymClassModel);
                List<GymClassModel> list2 = filterBySubscription.filterByHalfYearly(
                                gymClassModel.getMSubscription().getHalf(),
                                list);
                Assertions.assertEquals(list, list2);

        }

        @Test
        void testFilterByMonthly() {

                GymAddressClass gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                long l = 12345;
                GymTime gymTime = new GymTime("GM6", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymClassModel gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                                workoutList, gymTime, gymSubscriptionClass, l, 20);
                List<GymClassModel> list = new ArrayList<>();
                list.add(gymClassModel);
                List<GymClassModel> list2 = filterBySubscription.filterByMonthly(
                                gymClassModel.getMSubscription().getMonthly(),
                                list);
                Assertions.assertEquals(list, list2);

        }

        @Test
        void testFilterByOneWorkout() {

                GymAddressClass gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                long l = 12345;
                GymTime gymTime = new GymTime("GM6", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymClassModel gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                                workoutList, gymTime, gymSubscriptionClass, l, 20);
                List<GymClassModel> list = new ArrayList<>();
                list.add(gymClassModel);
                List<GymClassModel> list2 = filterBySubscription
                                .filterByOneWorkout(gymClassModel.getMSubscription().getOneWorkout(), list);
                Assertions.assertEquals(list, list2);

        }

        @Test
        void testFilterByQuarterly() {

                GymAddressClass gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                long l = 12345;
                GymTime gymTime = new GymTime("GM6", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymClassModel gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                                workoutList, gymTime, gymSubscriptionClass, l, 20);
                List<GymClassModel> list = new ArrayList<>();
                list.add(gymClassModel);
                List<GymClassModel> list2 = filterBySubscription
                                .filterByQuarterly(gymClassModel.getMSubscription().getQuarterly(), list);
                Assertions.assertEquals(list, list2);

        }

        @Test
        void testFilterByYearly() {

                GymAddressClass gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                long l = 12345;
                GymTime gymTime = new GymTime("GM6", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymClassModel gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                                workoutList, gymTime, gymSubscriptionClass, l, 20);
                List<GymClassModel> list = new ArrayList<>();
                list.add(gymClassModel);
                List<GymClassModel> list2 = filterBySubscription.filterByYearly(
                                gymClassModel.getMSubscription().getYearly(),
                                list);
                Assertions.assertEquals(list, list2);
        }

        @Test
        void testFilterByQuarterlyPrice() {

                GymAddressClass gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                long l = 12345;
                GymTime gymTime = new GymTime("GM6", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 5000, 5000, 1, 1, 1);
                GymClassModel gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                                workoutList, gymTime, gymSubscriptionClass, l, 20);
                List<GymClassModel> list = new ArrayList<>();
                list.add(gymClassModel);
                List<GymClassModel> list3 = new ArrayList<>();
                List<GymClassModel> list2 = filterBySubscription.filterByQuarterly(0, list);
                Assertions.assertEquals(list3, list2);
        }

        @Test
        void testFilterByQuarterlyPriceBetween() {

                GymAddressClass gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                long l = 12345;
                GymTime gymTime = new GymTime("GM6", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 5, 5, 1, 1, 1);
                GymClassModel gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                                workoutList, gymTime, gymSubscriptionClass, l, 20);
                List<GymClassModel> list = new ArrayList<>();
                list.add(gymClassModel);
                List<GymClassModel> list2 = filterBySubscription.filterByQuarterly(0, list);
                Assertions.assertEquals(list, list2);
        }

        @Test
        void testFilterByHalfYearlyPrice() {

                GymAddressClass gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                long l = 12345;
                GymTime gymTime = new GymTime("GM6", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 5000, 1, 5000, 1, 1);
                GymClassModel gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                                workoutList, gymTime, gymSubscriptionClass, l, 20);
                List<GymClassModel> list = new ArrayList<>();
                list.add(gymClassModel);
                List<GymClassModel> list3 = new ArrayList<>();
                List<GymClassModel> list2 = filterBySubscription.filterByHalfYearly(0, list);
                Assertions.assertEquals(list3, list2);
        }

        @Test
        void testFilterByHalfYearlyPriceBetween() {

                GymAddressClass gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                long l = 12345;
                GymTime gymTime = new GymTime("GM6", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 5, 1, 5, 1, 1);
                GymClassModel gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                                workoutList, gymTime, gymSubscriptionClass, l, 20);
                List<GymClassModel> list = new ArrayList<>();
                list.add(gymClassModel);
                List<GymClassModel> list2 = filterBySubscription.filterByHalfYearly(0, list);
                Assertions.assertEquals(list, list2);
        }

        @Test
        void testFilterByYearlyPrice() {

                GymAddressClass gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                long l = 12345;
                GymTime gymTime = new GymTime("GM6", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 5000, 1, 1, 5000, 1);
                GymClassModel gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                                workoutList, gymTime, gymSubscriptionClass, l, 20);
                List<GymClassModel> list = new ArrayList<>();
                list.add(gymClassModel);
                List<GymClassModel> list3 = new ArrayList<>();
                List<GymClassModel> list2 = filterBySubscription.filterByYearly(0, list);
                Assertions.assertEquals(list3, list2);
        }

        @Test
        void testFilterByYearlyPriceBetween() {

                GymAddressClass gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                long l = 12345;
                GymTime gymTime = new GymTime("GM6", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 5, 1, 1, 5, 1);
                GymClassModel gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                                workoutList, gymTime, gymSubscriptionClass, l, 20);
                List<GymClassModel> list = new ArrayList<>();
                list.add(gymClassModel);
                List<GymClassModel> list2 = filterBySubscription.filterByYearly(0, list);
                Assertions.assertEquals(list, list2);
        }

        @Test
        void testFilterByOneWorkoutPrice() {

                GymAddressClass gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                long l = 12345;
                GymTime gymTime = new GymTime("GM6", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 5000, 5000, 1, 1, 1, 1);
                GymClassModel gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                                workoutList, gymTime, gymSubscriptionClass, l, 20);
                List<GymClassModel> list = new ArrayList<>();
                list.add(gymClassModel);
                List<GymClassModel> list3 = new ArrayList<>();
                List<GymClassModel> list2 = filterBySubscription.filterByOneWorkout(0, list);
                Assertions.assertEquals(list3, list2);
        }

        @Test
        void testFilterByOneWorkoutPriceBetween() {

                GymAddressClass gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                long l = 12345;
                GymTime gymTime = new GymTime("GM6", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 5, 5, 1, 1, 1, 1);
                GymClassModel gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                                workoutList, gymTime, gymSubscriptionClass, l, 20);
                List<GymClassModel> list = new ArrayList<>();
                list.add(gymClassModel);
                List<GymClassModel> list2 = filterBySubscription.filterByOneWorkout(0, list);
                Assertions.assertEquals(list, list2);
        }

        @Test
        void testFilterByMonthlyPrice() {

                GymAddressClass gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                long l = 12345;
                GymTime gymTime = new GymTime("GM6", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 5000, 1, 1, 1, 1);
                GymClassModel gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                                workoutList, gymTime, gymSubscriptionClass, l, 20);
                List<GymClassModel> list = new ArrayList<>();
                list.add(gymClassModel);
                List<GymClassModel> list3 = new ArrayList<>();
                List<GymClassModel> list2 = filterBySubscription.filterByMonthly(0, list);
                Assertions.assertEquals(list3, list2);
        }

        @Test
        void testFilterByMonthlyPriceBetween() {

                GymAddressClass gymAddressClass = new GymAddressClass("GM6", 27.13214, 24.14241, "Bhatar", "Surat");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                long l = 12345;
                GymTime gymTime = new GymTime("GM6", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 5, 1, 1, 1, 1);
                GymClassModel gymClassModel = new GymClassModel("pankaj.jain@nineleaps.com", "Fitness", gymAddressClass,
                                workoutList, gymTime, gymSubscriptionClass, l, 20);
                List<GymClassModel> list = new ArrayList<>();
                list.add(gymClassModel);
                List<GymClassModel> list2 = filterBySubscription.filterByMonthly(0, list);
                Assertions.assertEquals(list, list2);
        }
}
