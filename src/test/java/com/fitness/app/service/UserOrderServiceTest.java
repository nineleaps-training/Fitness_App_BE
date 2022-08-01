package com.fitness.app.service;

import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.entity.GymTime;
import com.fitness.app.entity.UserAttendance;
import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.UserOrder;
import com.fitness.app.model.GymRepresentModel;
import com.fitness.app.model.UserOrderModel;
import com.fitness.app.model.UserPerformanceModel;
import com.fitness.app.repository.AddGymRepo;
import com.fitness.app.repository.AttendanceRepo;
import com.fitness.app.repository.GymTimeRepo;
import com.fitness.app.repository.UserOrderRepo;
import com.fitness.app.repository.UserRepo;
import com.fitness.app.repository.VendorPayRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayException;

@ExtendWith(MockitoExtension.class)
class UserOrderServiceTest {

        @InjectMocks
        UserOrderService userOrderService;

        @Mock
        UserOrderRepo userOrderRepo;

        @Mock
        UserRepo userRepository;

        @Mock
        AttendanceRepo attendanceRepo;

        @Mock
        AddGymRepo gymRepo;

        @Mock
        GymService gymService;

        @Mock
        GymTimeRepo gymTimeRepo;

        @Mock
        Order order;

        @Mock
        VendorPayRepo vendorOrderRepo;
        LocalDate localDate;
        LocalTime localTime;
        UserOrder userOrder;
        String subscription = "Monthly";
        String subscription2 = "quarterly";

        @Test
        @DisplayName("Testing of fetching all the users of a particular gym")
        void testAllMyUser() {
                UserClass userClass = new UserClass("email", "name", "mobile", "password", "USER", true, true, true);
                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                UserOrder userOrder = new UserOrder("id", "email", "gym", services, "subscription", "slot", 12345,
                                "booked",
                                "Completed", "paymentId", "receipt", localDate, localTime);
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                Optional<GymClass> optional = Optional.of(new GymClass("id", "email", "name", workout, l, 3.5, 20));
                UserAttendance userAttendance = new UserAttendance();
                when(userOrderRepo.findByGym(userOrder.getId())).thenReturn(list);
                when(attendanceRepo.findByEmailAndVendor(userOrder.getEmail(), optional.get().getEmail()))
                                .thenReturn(userAttendance);
                when(userRepository.findByEmail(userOrder.getEmail())).thenReturn(userClass);
                when(gymRepo.findById(userOrder.getId())).thenReturn(optional);
                Set<UserPerformanceModel> uModels3 = userOrderService.allMyUser(userOrder.getId());
                Assertions.assertEquals(uModels2.size(), uModels3.size());
        }

        @Test
        void testOrderNowService() throws RazorpayException {
                List<String> services = new ArrayList<>();
                services.add("zumba");
                UserOrderModel userOrder = new UserOrderModel("GM6", "pankaj.jain@nineleaps.com", services,
                                "subscription", 123, "weekly");
                List<UserOrderModel> list = new ArrayList<>();
                list.add(userOrder);
                String order = userOrderService.orderNow(userOrder).toString();
                Assertions.assertEquals(true, order.contains("amount"));

        }

        @Test
        @DisplayName("Testing of fetching all the users of a particular gym")
        void testAllMyUserOptional() {
                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                UserOrder userOrder = new UserOrder("id", "email", "gym", services, "subscription", "slot", 12345,
                                "booked",
                                "Completed", "paymentId", "receipt", localDate, localTime);
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                Optional<GymClass> optional = Optional.empty();
                when(userOrderRepo.findByGym(userOrder.getId())).thenReturn(list);
                when(gymRepo.findById(userOrder.getId())).thenReturn(optional);
                Set<UserPerformanceModel> uModels3 = userOrderService.allMyUser(userOrder.getId());
                Assertions.assertEquals(uModels2, uModels3);
        }

        @Test
        @DisplayName("Testing of fetching all the booked gyms")
        void testBookedGym() {
                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                UserOrder userOrder = new UserOrder("id", "email", "gym", services, "subscription", "slot", 12345,
                                "Current",
                                "Completed", "paymentId", "receipt", localDate, localTime);
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymRepresentModel gymRepresent = new GymRepresentModel("id", "email", "gymName", gymAddressClass,
                                workoutList, gymTime,
                                gymSubscriptionClass, l, 3.5, 20);
                List<GymRepresentModel> list2 = new ArrayList<>();
                list2.add(gymRepresent);
                when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
                when(gymService.getGymByGymId(userOrder.getGym())).thenReturn(gymRepresent);
                List<GymRepresentModel> list3 = userOrderService.bookedGym(userOrder.getEmail());
                Assertions.assertEquals(list2, list3);
        }

        @Test
        @DisplayName("Testing of fetching all the booked gyms")
        void testBookedGymOrders() {
                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                UserOrder userOrder = new UserOrder("id", "email", "gym", services, "subscription", "slot", 12345,
                                "Current",
                                "Completed", "paymentId", "receipt", localDate, localTime);
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = null;
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                List<GymRepresentModel> list2 = new ArrayList<>();
                when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
                List<GymRepresentModel> list3 = userOrderService.bookedGym(userOrder.getEmail());
                Assertions.assertEquals(list2, list3);
        }

        @Test
        @DisplayName("Testing of fetching all the booked gyms")
        void testBookedGymCurrent() {

                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                UserOrder userOrder = new UserOrder("id", "email", "gym", services, "subscription", "slot", 12345,
                                "Expired",
                                "Completed", "paymentId", "receipt", localDate, localTime);
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymRepresentModel gymRepresent = new GymRepresentModel("id", "email", "gymName", gymAddressClass,
                                workoutList, gymTime,
                                gymSubscriptionClass, l, 3.5, 20);
                List<GymRepresentModel> list2 = new ArrayList<>();
                list2.add(gymRepresent);
                when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
                when(gymService.getGymByGymId(userOrder.getGym())).thenReturn(gymRepresent);
                List<GymRepresentModel> list3 = userOrderService.bookedGym(userOrder.getEmail());
                Assertions.assertEquals(list2, list3);
        }

        @Test
        @DisplayName("Testing to check if the user can order")
        void testCanOrder() {

                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                localDate = LocalDate.of(2022, 6, 4);
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                userOrder = new UserOrder("id", "email", "gym", services, subscription, "slot", 12345, "Current",
                                "Completed",
                                "paymentId", "receipt", localDate, localTime);
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymRepresentModel gymRepresent = new GymRepresentModel("id", "email", "gymName", gymAddressClass,
                                workoutList, gymTime,
                                gymSubscriptionClass, l, 3.5, 20);
                List<GymRepresentModel> list2 = new ArrayList<>();
                list2.add(gymRepresent);
                when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
                Boolean boo = userOrderService.canOrder(userOrder.getEmail());
                Assertions.assertEquals(false, boo);
        }

        @Test
        @DisplayName("Testing to check if the user can order")
        void testCanOrderQuarterly() {

                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                localDate = LocalDate.of(2022, 6, 2);
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                userOrder = new UserOrder();
                userOrder.setAmount(12345);
                userOrder.setSubscription("Quarterly");
                userOrder.setId("id");
                userOrder.setEmail("email");
                userOrder.setGym("gym");
                userOrder.setServices(services);
                userOrder.setSlot("slot");
                userOrder.setBooked("Current");
                userOrder.setStatus("Completed");
                userOrder.setDate(localDate);
                userOrder.setPaymentId("paymentId");
                userOrder.setReceipt("receipt");
                userOrder.setTime(localTime);
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymRepresentModel gymRepresent = new GymRepresentModel("id", "email", "gymName", gymAddressClass,
                                workoutList, gymTime,
                                gymSubscriptionClass, l, 3.5, 20);
                List<GymRepresentModel> list2 = new ArrayList<>();
                list2.add(gymRepresent);
                when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
                Boolean boo = userOrderService.canOrder(userOrder.getEmail());
                Assertions.assertEquals(false, boo);
        }

        @Test
        @DisplayName("Testing to check if the user can order")
        void testCanOrderHalf() {

                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                localDate = LocalDate.of(2022, 6, 3);
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                userOrder = new UserOrder();
                userOrder.setAmount(12345);
                userOrder.setSubscription("Half");
                userOrder.setId("id");
                userOrder.setEmail("email");
                userOrder.setGym("gym");
                userOrder.setServices(services);
                userOrder.setSlot("slot");
                userOrder.setBooked("Current");
                userOrder.setStatus("Completed");
                userOrder.setDate(localDate);
                userOrder.setPaymentId("paymentId");
                userOrder.setReceipt("receipt");
                userOrder.setTime(localTime);
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymRepresentModel gymRepresent = new GymRepresentModel("id", "email", "gymName", gymAddressClass,
                                workoutList, gymTime,
                                gymSubscriptionClass, l, 3.5, 20);
                List<GymRepresentModel> list2 = new ArrayList<>();
                list2.add(gymRepresent);
                when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
                Boolean boo = userOrderService.canOrder(userOrder.getEmail());
                Assertions.assertEquals(false, boo);
        }

        @Test
        @DisplayName("Testing to check if the user can order")
        void testCanOrderYearly() {

                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                localDate = LocalDate.of(2022, 6, 5);
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                userOrder = new UserOrder("id", "email", "gym", services, "Yearly", "slot", 12345, "Current",
                                "Completed",
                                "paymentId", "receipt", localDate, localTime);
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymRepresentModel gymRepresent = new GymRepresentModel("id", "email", "gymName", gymAddressClass,
                                workoutList, gymTime,
                                gymSubscriptionClass, l, 3.5, 20);
                List<GymRepresentModel> list2 = new ArrayList<>();
                list2.add(gymRepresent);
                when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
                Boolean boo = userOrderService.canOrder(userOrder.getEmail());
                Assertions.assertEquals(false, boo);
        }

        @Test
        @DisplayName("Testing to check if the user can order")
        void testCanOrderNone() {

                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                localDate = LocalDate.of(2022, 6, 6);
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                userOrder = new UserOrder("id", "email", "gym", services, "None", "slot", 12345, "Current", "Completed",
                                "paymentId", "receipt", localDate, localTime);
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymRepresentModel gymRepresent = new GymRepresentModel("id", "email", "gymName", gymAddressClass,
                                workoutList, gymTime,
                                gymSubscriptionClass, l, 3.5, 20);
                List<GymRepresentModel> list2 = new ArrayList<>();
                list2.add(gymRepresent);
                when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
                Boolean boo = userOrderService.canOrder(userOrder.getEmail());
                Assertions.assertEquals(false, boo);
        }

        @Test
        @DisplayName("Testing to check if the user can order")
        void testCanOrderComp() {

                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                LocalDate localDate = LocalDate.of(2021, 6, 2);
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                UserOrder userOrder = new UserOrder("id", "email", "gym", services, subscription, "slot", 12345,
                                "Current",
                                "Completed", "paymentId", "receipt", localDate, localTime);
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymRepresentModel gymRepresent = new GymRepresentModel("id", "email", "gymName", gymAddressClass,
                                workoutList, gymTime,
                                gymSubscriptionClass, l, 3.5, 20);
                List<GymRepresentModel> list2 = new ArrayList<>();
                list2.add(gymRepresent);
                when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
                Boolean boo = userOrderService.canOrder(userOrder.getEmail());
                Assertions.assertEquals(false, boo);
        }

        @Test
        @DisplayName("Testing to check if the user can order")
        void testCanOrderWithNull() {

                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                localDate = LocalDate.of(2022, 6, 8);
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                userOrder = new UserOrder("id", "email", "gym", services, subscription, "slot", 12345, "Current",
                                "Completed",
                                "paymentId", "receipt", localDate, localTime);
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymRepresentModel gymRepresent = new GymRepresentModel("id", "email", "gymName", gymAddressClass,
                                workoutList, gymTime,
                                gymSubscriptionClass, l, 3.5, 20);
                List<GymRepresentModel> list2 = new ArrayList<>();
                list2.add(gymRepresent);
                when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
                Boolean boo = userOrderService.canOrder(userOrder.getEmail());
                Assertions.assertEquals(false, boo);
        }

        @Test
        @DisplayName("Testing to check if the user can order")
        void testCanOrdersNull() {

                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                localDate = LocalDate.of(2022, 6, 9);
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                userOrder = new UserOrder("id", "email", "gym", services, subscription, "slot", 12345, "Current",
                                "Completed",
                                "paymentId", "receipt", localDate, localTime);
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymRepresentModel gymRepresent = new GymRepresentModel("id", "email", "gymName", gymAddressClass,
                                workoutList, gymTime,
                                gymSubscriptionClass, l, 3.5, 20);
                List<GymRepresentModel> list2 = new ArrayList<>();
                list2.add(gymRepresent);
                when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
                Boolean boo = userOrderService.canOrder(userOrder.getEmail());
                Assertions.assertEquals(true, boo);
        }

        @Test
        @DisplayName("Testing to check if the user can order")
        void testCanOrderNull() {

                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                localDate = LocalDate.of(2022, 6, 4);
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                userOrder = new UserOrder("id", "email", "gym", services, subscription, "slot", 12345, "Current",
                                "Completed",
                                "paymentId", "receipt", localDate, localTime);
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = null;
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymRepresentModel gymRepresent = new GymRepresentModel("id", "email", "gymName", gymAddressClass,
                                workoutList, gymTime,
                                gymSubscriptionClass, l, 3.5, 20);
                List<GymRepresentModel> list2 = new ArrayList<>();
                list2.add(gymRepresent);
                when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
                Boolean boo = userOrderService.canOrder(userOrder.getEmail());
                Assertions.assertEquals(true, boo);
        }

        @Test
        @DisplayName("Testing of fetching the order history")
        void testOrderListOrder() {

                List<String> services = new ArrayList<>();
                services.add("zumba");
                localDate = LocalDate.now();
                localTime = LocalTime.now();
                UserOrder userOrder = new UserOrder("id", "email", "gym", services, "subscription", "slot", 1234,
                                "booked",
                                "Completed", "paymentId", "receipt", localDate, localTime);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
                List<UserOrder> list2 = userOrderService.orderListOrder(userOrder.getEmail());
                Assertions.assertEquals(list.size(), list2.size());

        }

        @Test
        @DisplayName("Testing of ordering the services")
        void testOrderNow() throws RazorpayException {

                List<String> services = new ArrayList<>();
                services.add("zumba");
                UserOrderModel userOrder = new UserOrderModel("GM6", "pankaj.jain@nineleaps.com", services,
                                "subscription", 123, "weekly");
                List<UserOrderModel> list = new ArrayList<>();
                list.add(userOrder);
                String order = userOrderService.orderNow(userOrder).toString();
                Assertions.assertEquals(219, order.length());

        }

        @Test
        @DisplayName("Testing to fetch the pending order list")
        void testPendingListOrder() {

                List<String> services = new ArrayList<>();
                services.add("zumba");
                localDate = LocalDate.now();
                localTime = LocalTime.now();
                UserOrder userOrder = new UserOrder("id", "email", "gym", services, "subscription", "slot", 1234,
                                "booked",
                                "created", "paymentId", "receipt", localDate, localTime);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
                List<UserOrder> list2 = userOrderService.pendingListOrder(userOrder.getEmail());
                Assertions.assertEquals(list.size(), list2.size());
        }

        @Test
        @DisplayName("Testing to fetch the pending order list")
        void testPendingListOrderGreaterThanZero() {

                List<String> services = new ArrayList<>();
                services.add("zumba");
                localDate = LocalDate.of(2021, 6, 12);
                localTime = LocalTime.now();
                UserOrder userOrder = new UserOrder("id", "email", "gym", services, "subscription", "slot", 1234,
                                "booked",
                                "created", "paymentId", "receipt", localDate, localTime);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
                List<UserOrder> list2 = userOrderService.pendingListOrder(userOrder.getEmail());
                Assertions.assertEquals(list, list2);
        }

        @Test
        @DisplayName("Testing of updating the order details")
        void testUpdateOrderQuarterly() {
                Map<String, String> data = new HashMap<>();
                data.put("order_id", "id");
                data.put("payment_id", "paymentId");
                data.put("status", "status");
                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                Optional<GymClass> optional1 = Optional.of(new GymClass("id", "email", "name", workout, l, 3.5, 20));
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                userOrder = new UserOrder("id", "email", "gym", services, subscription2, "slot", 12345, "Current",
                                "status",
                                "paymentId", "receipt", localDate, localTime);
                Optional<UserOrder> optional = Optional.of(new UserOrder("id", "email", "gym", services, subscription2,
                                "slot",
                                12345, "Current", "Completed", "paymentId", "receipt", localDate, localTime));
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymRepresentModel gymRepresent = new GymRepresentModel("id", "email", "gymName", gymAddressClass,
                                workoutList, gymTime,
                                gymSubscriptionClass, l, 3.5, 20);
                List<GymRepresentModel> list2 = new ArrayList<>();
                list2.add(gymRepresent);
                when(userOrderRepo.findById(data.get("order_id"))).thenReturn(optional);
                when(gymRepo.findById(userOrder.getGym())).thenReturn(optional1);
                UserOrder userOrder2 = userOrderService.updateOrder(data);
                Assertions.assertEquals(userOrder.getId(), userOrder2.getId());
        }

        @Test
        @DisplayName("Testing of updating the order details")
        void testUpdateOrderHalfYearly() {
                Map<String, String> data = new HashMap<>();
                data.put("order_id", "id");
                data.put("payment_id", "paymentId");
                data.put("status", "status");
                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                Optional<GymClass> optional1 = Optional.of(new GymClass("id", "email", "name", workout, l, 3.5, 20));
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                userOrder = new UserOrder();
                userOrder.setAmount(12345);
                userOrder.setSubscription("half");
                userOrder.setId("id");
                userOrder.setEmail("email");
                userOrder.setGym("gym");
                userOrder.setServices(services);
                userOrder.setSlot("slot");
                userOrder.setBooked("Current");
                userOrder.setStatus("Completed");
                userOrder.setDate(localDate);
                userOrder.setPaymentId("paymentId");
                userOrder.setReceipt("receipt");
                userOrder.setTime(localTime);
                Optional<UserOrder> optional = Optional
                                .of(new UserOrder("id", "email", "gym", services, "half", "slot", 12345,
                                                "Current", "Completed", "paymentId", "receipt", localDate, localTime));
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymRepresentModel gymRepresent = new GymRepresentModel("id", "email", "gymName", gymAddressClass,
                                workoutList, gymTime,
                                gymSubscriptionClass, l, 3.5, 20);
                List<GymRepresentModel> list2 = new ArrayList<>();
                list2.add(gymRepresent);
                when(userOrderRepo.findById(data.get("order_id"))).thenReturn(optional);
                when(gymRepo.findById(userOrder.getGym())).thenReturn(optional1);
                UserOrder userOrder2 = userOrderService.updateOrder(data);
                Assertions.assertEquals(userOrder.getId(), userOrder2.getId());
        }

        @Test
        @DisplayName("Testing of updating the order details")
        void testUpdateOrderNone() {
                Map<String, String> data = new HashMap<>();
                data.put("order_id", "id");
                data.put("payment_id", "paymentId");
                data.put("status", "status");
                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                Optional<GymClass> optional1 = Optional.of(new GymClass("id", "email", "name", workout, l, 3.5, 20));
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                userOrder = new UserOrder("id", "email", "gym", services, "none", "slot", 12345, "Current", "status",
                                "paymentId", "receipt", localDate, localTime);
                Optional<UserOrder> optional = Optional
                                .of(new UserOrder("id", "email", "gym", services, "none", "slot", 12345,
                                                "Current", "Completed", "paymentId", "receipt", localDate, localTime));
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymRepresentModel gymRepresent = new GymRepresentModel("id", "email", "gymName", gymAddressClass,
                                workoutList, gymTime,
                                gymSubscriptionClass, l, 3.5, 20);
                List<GymRepresentModel> list2 = new ArrayList<>();
                list2.add(gymRepresent);
                when(userOrderRepo.findById(data.get("order_id"))).thenReturn(optional);
                when(gymRepo.findById(userOrder.getGym())).thenReturn(optional1);
                UserOrder userOrder2 = userOrderService.updateOrder(data);
                Assertions.assertEquals(userOrder.getId(), userOrder2.getId());
        }

        @Test
        @DisplayName("Testing of updating the order details")
        void testUpdateOrder() {
                Map<String, String> data = new HashMap<>();
                data.put("order_id", "id");
                data.put("payment_id", "paymentId");
                data.put("status", "status");
                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                Optional<GymClass> optional1 = Optional.of(new GymClass("id", "email", "name", workout, l, 3.5, 20));
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                userOrder = new UserOrder("id", "email", "gym", services, "monthly", "slot", 12345, "Current", "status",
                                "paymentId", "receipt", localDate, localTime);
                Optional<UserOrder> optional = Optional.of(new UserOrder("id", "email", "gym", services, "monthly",
                                "slot",
                                12345, "Current", "Completed", "paymentId", "receipt", localDate, localTime));
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymRepresentModel gymRepresent = new GymRepresentModel("id", "email", "gymName", gymAddressClass,
                                workoutList, gymTime,
                                gymSubscriptionClass, l, 3.5, 20);
                List<GymRepresentModel> list2 = new ArrayList<>();
                list2.add(gymRepresent);
                when(userOrderRepo.findById(data.get("order_id"))).thenReturn(optional);
                when(gymRepo.findById(userOrder.getGym())).thenReturn(optional1);
                UserOrder userOrder2 = userOrderService.updateOrder(data);
                Assertions.assertEquals(userOrder.getId(), userOrder2.getId());
        }

        @Test
        @DisplayName("Testing of updating the order details")
        void testUpdateOrderOptional() {
                Map<String, String> data = new HashMap<>();
                data.put("order_id", "id");
                data.put("payment_id", "paymentId");
                data.put("status", "status");
                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                UserOrder userOrder = new UserOrder("id", "email", "gym", services, "monthly", "slot", 12345, "Current",
                                "status", "paymentId", "receipt", localDate, localTime);
                Optional<UserOrder> optional = Optional.empty();
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymRepresentModel gymRepresent = new GymRepresentModel("id", "email", "gymName", gymAddressClass,
                                workoutList, gymTime,
                                gymSubscriptionClass, l, 3.5, 20);
                List<GymRepresentModel> list2 = new ArrayList<>();
                list2.add(gymRepresent);
                when(userOrderRepo.findById(data.get("order_id"))).thenReturn(optional);
                UserOrder userOrder2 = userOrderService.updateOrder(data);
                Assertions.assertEquals(null, userOrder2);
        }

        @Test
        @DisplayName("Testing of updating the order details")
        void testUpdateOrderOptionalGymClass() {
                Map<String, String> data = new HashMap<>();
                data.put("order_id", "id");
                data.put("payment_id", "paymentId");
                data.put("status", "status");
                List<String> workout = new ArrayList<>();
                workout.add("zumba");
                long l = 1234;
                Optional<GymClass> optional1 = Optional.empty();
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                List<String> services = new ArrayList<>();
                services.add("zumba");
                UserOrder userOrder = new UserOrder("id", "email", "gym", services, "monthly", "slot", 12345, "Current",
                                "status", "paymentId", "receipt", localDate, localTime);
                Optional<UserOrder> optional = Optional.of(new UserOrder("id", "email", "gym", services, "monthly",
                                "slot",
                                12345, "Current", "Completed", "paymentId", "receipt", localDate, localTime));
                List<Integer> attendance = new ArrayList<>();
                attendance.add(3);
                List<UserOrder> list = new ArrayList<>();
                list.add(userOrder);
                UserPerformanceModel uModel2 = new UserPerformanceModel("name", "email", "gym", "vendor", attendance,
                                3.5);
                Set<UserPerformanceModel> uModels2 = new HashSet<>();
                uModels2.add(uModel2);
                GymAddressClass gymAddressClass = new GymAddressClass("id", 27.13214, 24.14241, "address", "city");
                List<String> workoutList = new ArrayList<>();
                workoutList.add("zumba");
                GymTime gymTime = new GymTime("id", "yes", "yes", "yes");
                GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass("GM6", 1, 1, 1, 1, 1, 1);
                GymRepresentModel gymRepresent = new GymRepresentModel("id", "email", "gymName", gymAddressClass,
                                workoutList, gymTime,
                                gymSubscriptionClass, l, 3.5, 20);
                List<GymRepresentModel> list2 = new ArrayList<>();
                list2.add(gymRepresent);
                when(userOrderRepo.findById(data.get("order_id"))).thenReturn(optional);
                when(gymRepo.findById(userOrder.getGym())).thenReturn(optional1);
                UserOrder userOrder2 = userOrderService.updateOrder(data);
                Assertions.assertEquals(userOrder.getId(), userOrder2.getId());
        }
}
