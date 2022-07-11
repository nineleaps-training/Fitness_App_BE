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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.entity.GymTime;
import com.fitness.app.entity.UserAttendance;
import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.UserOrder;
import com.fitness.app.model.GymRepresnt;
import com.fitness.app.model.UserPerfomanceModel;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.AttendanceRepo;
import com.fitness.app.repository.GymTimeRepo;
import com.fitness.app.repository.UserOrderRepo;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.repository.VendorPayRepo;

@ExtendWith(MockitoExtension.class)
class UserOrderServiceTest {

    UserOrderService userOrderService;

    @Mock
    UserOrderRepo userOrderRepo;

    @Mock
    UserRepository userRepository;

    @Mock
    AttendanceRepo attendanceRepo;

    @Mock
    AddGymRepository gymRepo;

    @Mock
    GymService gymService;

    @Mock
    GymTimeRepo gymTimeRepo;

    @Mock
    VendorPayRepo vendorOrderRepo;
    LocalDate localDate;
    LocalTime localTime;
    UserOrder userOrder;
    String subscription = "Monthly";
    String subscription2 = "quaterly";

    @BeforeEach
    public void initcase() {
        userOrderService=new UserOrderService(userRepository,userOrderRepo,attendanceRepo,gymRepo,gymService,vendorOrderRepo);
    }
    
    @Test
    void testAllMyUser() {
        UserClass userClass=new UserClass("email", "name", "mobile", "password", "USER", true, true, true);
        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        UserOrder userOrder = new UserOrder("id", "email", "gym", services, "subscription", "slot", 12345, "booked", "Completed", "paymentId", "receipt", localDate, localTime);
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        Optional<GymClass> optional=Optional.of(new GymClass("id", "email", "name", workout, l, 3.5, 20));
        UserAttendance userAttendance= new UserAttendance();
        when(userOrderRepo.findByGym(userOrder.getId())).thenReturn(list);
        when(attendanceRepo.findByEmailAndVendor(userOrder.getEmail(), optional.get().getEmail())).thenReturn(userAttendance);
        when(userRepository.findByEmail(userOrder.getEmail())).thenReturn(userClass);
        when(gymRepo.findById(userOrder.getId())).thenReturn(optional);
        Set<UserPerfomanceModel> uModels3 = userOrderService.allMyUser(userOrder.getId());
        Assertions.assertEquals(uModels2.size(),uModels3.size());
    }

    @Test
    void testAllMyUserOptional() {
        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        UserOrder userOrder = new UserOrder("id", "email", "gym", services, "subscription", "slot", 12345, "booked", "Completed", "paymentId", "receipt", localDate, localTime);
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        Optional<GymClass> optional=Optional.empty();
        when(userOrderRepo.findByGym(userOrder.getId())).thenReturn(list);
        when(gymRepo.findById(userOrder.getId())).thenReturn(optional);
        Set<UserPerfomanceModel> uModels3 = userOrderService.allMyUser(userOrder.getId());
        Assertions.assertEquals(uModels2,uModels3);
    }

    @Test
    void testBookedGym() {
        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        UserOrder userOrder = new UserOrder("id", "email", "gym", services, "subscription", "slot", 12345, "Current", "Completed", "paymentId", "receipt", localDate, localTime);
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        GymAddressClass gymAddressClass=new GymAddressClass("id",27.13214,24.14241,"address","city");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime=new GymTime("id","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("id", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresnt> list2=new ArrayList<>();
        list2.add(gymRepresnt);
        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
        when(gymService.getGymByGymId(userOrder.getGym())).thenReturn(gymRepresnt);      
        List<GymRepresnt> list3=userOrderService.bookedGym(userOrder.getEmail());
        Assertions.assertEquals(list2,list3);
    }

    @Test
    void testBookedGymOrders() {
        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        UserOrder userOrder = new UserOrder("id", "email", "gym", services, "subscription", "slot", 12345, "Current", "Completed", "paymentId", "receipt", localDate, localTime);
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=null;
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        List<GymRepresnt> list2 = new ArrayList<>();
        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);  
        List<GymRepresnt> list3=userOrderService.bookedGym(userOrder.getEmail());
        Assertions.assertEquals(list2,list3);
    }

    @Test
    void testBookedGymCurrent() {

        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        UserOrder userOrder = new UserOrder("id", "email", "gym", services, "subscription", "slot", 12345, "Expired", "Completed", "paymentId", "receipt", localDate, localTime);
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        GymAddressClass gymAddressClass=new GymAddressClass("id",27.13214,24.14241,"address","city");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime=new GymTime("id","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("id", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresnt> list2=new ArrayList<>();
        list2.add(gymRepresnt);
        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
        when(gymService.getGymByGymId(userOrder.getGym())).thenReturn(gymRepresnt);      
        List<GymRepresnt> list3=userOrderService.bookedGym(userOrder.getEmail());
        Assertions.assertEquals(list2,list3);
    }

    @Test
    void testCanOrder() {

        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        localDate=LocalDate.of(2022, 6, 4);
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        userOrder = new UserOrder("id", "email", "gym", services, subscription, "slot", 12345, "Current", "Completed", "paymentId", "receipt", localDate, localTime);
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        GymAddressClass gymAddressClass=new GymAddressClass("id",27.13214,24.14241,"address","city");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime=new GymTime("id","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("id", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresnt> list2=new ArrayList<>();
        list2.add(gymRepresnt);
        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
        Boolean boo=userOrderService.canOrder(userOrder.getEmail());
        Assertions.assertEquals(false,boo);
    }

    @Test
    void testCanOrderQuaterly() {

        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        localDate=LocalDate.of(2022, 6, 2);
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        userOrder = new UserOrder();
        userOrder.setAmount(12345);
        userOrder.setSubscription("Quaterly");
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
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        GymAddressClass gymAddressClass=new GymAddressClass("id",27.13214,24.14241,"address","city");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime=new GymTime("id","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("id", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresnt> list2=new ArrayList<>();
        list2.add(gymRepresnt);
        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
        Boolean boo=userOrderService.canOrder(userOrder.getEmail());
        Assertions.assertEquals(false,boo);
    }

    @Test
    void testCanOrderHalf() {

        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        localDate=LocalDate.of(2022, 6, 3);
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
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
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        GymAddressClass gymAddressClass=new GymAddressClass("id",27.13214,24.14241,"address","city");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime=new GymTime("id","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("id", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresnt> list2=new ArrayList<>();
        list2.add(gymRepresnt);
        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
        Boolean boo=userOrderService.canOrder(userOrder.getEmail());
        Assertions.assertEquals(false,boo);
    }

    @Test
    void testCanOrderYearly() {

        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        localDate=LocalDate.of(2022, 6, 5);
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        userOrder = new UserOrder("id", "email", "gym", services, "Yearly", "slot", 12345, "Current", "Completed", "paymentId", "receipt", localDate, localTime);
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        GymAddressClass gymAddressClass=new GymAddressClass("id",27.13214,24.14241,"address","city");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime=new GymTime("id","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("id", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresnt> list2=new ArrayList<>();
        list2.add(gymRepresnt);
        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
        Boolean boo=userOrderService.canOrder(userOrder.getEmail());
        Assertions.assertEquals(false,boo);
    }

    @Test
    void testCanOrderNone() {

        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        localDate=LocalDate.of(2022, 6, 6);
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        userOrder = new UserOrder("id", "email", "gym", services, "None", "slot", 12345, "Current", "Completed", "paymentId", "receipt", localDate, localTime);
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        GymAddressClass gymAddressClass=new GymAddressClass("id",27.13214,24.14241,"address","city");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime=new GymTime("id","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("id", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresnt> list2=new ArrayList<>();
        list2.add(gymRepresnt);
        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
        Boolean boo=userOrderService.canOrder(userOrder.getEmail());
        Assertions.assertEquals(false,boo);
    }

    @Test
    void testCanOrderComp() {

        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        LocalDate localDate=LocalDate.of(2021, 6, 2);
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        UserOrder userOrder = new UserOrder("id", "email", "gym", services, subscription, "slot", 12345, "Current", "Completed", "paymentId", "receipt", localDate, localTime);
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        GymAddressClass gymAddressClass=new GymAddressClass("id",27.13214,24.14241,"address","city");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime=new GymTime("id","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("id", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresnt> list2=new ArrayList<>();
        list2.add(gymRepresnt);
        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
        Boolean boo=userOrderService.canOrder(userOrder.getEmail());
        Assertions.assertEquals(false,boo);
    }

    @Test
    void testCanOrderwithNull() {

        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        localDate=LocalDate.of(2022, 6, 8);
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        userOrder = new UserOrder("id", "email", "gym", services, subscription, "slot", 12345, "Current", "Completed", "paymentId", "receipt", localDate, localTime);
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        GymAddressClass gymAddressClass=new GymAddressClass("id",27.13214,24.14241,"address","city");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime=new GymTime("id","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("id", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresnt> list2=new ArrayList<>();
        list2.add(gymRepresnt);
        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
        Boolean boo=userOrderService.canOrder(userOrder.getEmail());
        Assertions.assertEquals(false,boo);
    }

    @Test
    void testCanOrdersNull() {

        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        localDate=LocalDate.of(2022, 6, 9);
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        userOrder = new UserOrder("id", "email", "gym", services, subscription, "slot", 12345, "Current", "Completed", "paymentId", "receipt", localDate, localTime);
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        GymAddressClass gymAddressClass=new GymAddressClass("id",27.13214,24.14241,"address","city");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime=new GymTime("id","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("id", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresnt> list2=new ArrayList<>();
        list2.add(gymRepresnt);
        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
        Boolean boo=userOrderService.canOrder(userOrder.getEmail());
        Assertions.assertEquals(true,boo);
    }

    @Test
    void testCanOrderNull() {

        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        localDate=LocalDate.of(2022, 6, 4);
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        userOrder = new UserOrder("id", "email", "gym", services, subscription, "slot", 12345, "Current", "Completed", "paymentId", "receipt", localDate, localTime);
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=null;
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        GymAddressClass gymAddressClass=new GymAddressClass("id",27.13214,24.14241,"address","city");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime=new GymTime("id","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("id", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresnt> list2=new ArrayList<>();
        list2.add(gymRepresnt);
        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
        Boolean boo=userOrderService.canOrder(userOrder.getEmail());
        Assertions.assertEquals(true,boo);
    }

    

    @Test
    void testOrderListOrder() {

        List<String> services = new ArrayList<>();
        services.add("zumba");
        localDate=LocalDate.now();
        localTime=LocalTime.now();
        UserOrder userOrder=new UserOrder("id", "email", "gym", services, "subscription", "slot", 1234, "booked", "Completed", "paymentId", "receipt", localDate, localTime);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
        List<UserOrder> list2 = userOrderService.orderListOrder(userOrder.getEmail());
        Assertions.assertEquals(list.size(), list2.size());

    }

    @Test
    void testOrderNow() {

        List<String> services = new ArrayList<>();
        services.add("zumba");
        localDate=LocalDate.now();
        localTime=LocalTime.now();
        UserOrder userOrder=new UserOrder("id", "email", "gym", services, "subscription", "slot", 1234, "booked", "status", "paymentId", "receipt", localDate, localTime);
        when(userOrderRepo.save(userOrder)).thenReturn(userOrder);
        UserOrder userOrder2 = userOrderService.orderNow(userOrder);
        Assertions.assertEquals(userOrder.getEmail(), userOrder2.getEmail());

    }

    @Test
    void testPendingListOrder() {

        List<String> services = new ArrayList<>();
        services.add("zumba");
        localDate=LocalDate.now();
        localTime=LocalTime.now();
        UserOrder userOrder=new UserOrder("id", "email", "gym", services, "subscription", "slot", 1234, "booked", "created", "paymentId", "receipt", localDate, localTime);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
        List<UserOrder> list2 = userOrderService.pendingListOrder(userOrder.getEmail());
        Assertions.assertEquals(list.size(), list2.size());
    }

    @Test
    void testPendingListOrderGreaterThanZero() {

        List<String> services = new ArrayList<>();
        services.add("zumba");
        localDate=LocalDate.of(2021, 6, 12);
        localTime=LocalTime.now();
        UserOrder userOrder=new UserOrder("id", "email", "gym", services, "subscription", "slot", 1234, "booked", "created", "paymentId", "receipt", localDate, localTime);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(list);
        List<UserOrder> list2 = userOrderService.pendingListOrder(userOrder.getEmail());
        Assertions.assertEquals(list, list2);
    }

    @Test
    void testUpdateOrderQuaterly() {
        Map<String,String> data=new HashMap<>();
        data.put("order_id", "id");
        data.put("payment_id","paymentId");
        data.put("status","status");
        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        Optional<GymClass> optional1=Optional.of(new GymClass("id", "email", "name", workout, l, 3.5, 20));
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        userOrder = new UserOrder("id", "email", "gym", services, subscription2, "slot", 12345, "Current", "status", "paymentId", "receipt", localDate, localTime);
        Optional<UserOrder> optional=Optional.of(new UserOrder("id", "email", "gym", services, subscription2, "slot", 12345, "Current", "Completed", "paymentId", "receipt", localDate, localTime));
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        GymAddressClass gymAddressClass=new GymAddressClass("id",27.13214,24.14241,"address","city");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime=new GymTime("id","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("id", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresnt> list2=new ArrayList<>();
        list2.add(gymRepresnt);
        when(userOrderRepo.findById(data.get("order_id"))).thenReturn(optional);
        when(gymRepo.findById(userOrder.getGym())).thenReturn(optional1);
        UserOrder userOrder2 = userOrderService.updateOrder(data);
        Assertions.assertEquals(userOrder.getId(),userOrder2.getId());
    }

    @Test
    void testUpdateOrderHalfYearly() {
        Map<String,String> data=new HashMap<>();
        data.put("order_id", "id");
        data.put("payment_id","paymentId");
        data.put("status","status");
        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        Optional<GymClass> optional1=Optional.of(new GymClass("id", "email", "name", workout, l, 3.5, 20));
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
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
    Optional<UserOrder> optional=Optional.of(new UserOrder("id", "email", "gym", services, "half", "slot", 12345, "Current", "Completed", "paymentId", "receipt", localDate, localTime));
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        GymAddressClass gymAddressClass=new GymAddressClass("id",27.13214,24.14241,"address","city");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime=new GymTime("id","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("id", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresnt> list2=new ArrayList<>();
        list2.add(gymRepresnt);
        when(userOrderRepo.findById(data.get("order_id"))).thenReturn(optional);
        when(gymRepo.findById(userOrder.getGym())).thenReturn(optional1);
        UserOrder userOrder2 = userOrderService.updateOrder(data);
        Assertions.assertEquals(userOrder.getId(),userOrder2.getId());
    }

    @Test
    void testUpdateOrderNone() {
        Map<String,String> data=new HashMap<>();
        data.put("order_id", "id");
        data.put("payment_id","paymentId");
        data.put("status","status");
        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        Optional<GymClass> optional1=Optional.of(new GymClass("id", "email", "name", workout, l, 3.5, 20));
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        userOrder = new UserOrder("id", "email", "gym", services, "none", "slot", 12345, "Current", "status", "paymentId", "receipt", localDate, localTime);
        Optional<UserOrder> optional=Optional.of(new UserOrder("id", "email", "gym", services, "none", "slot", 12345, "Current", "Completed", "paymentId", "receipt", localDate, localTime));
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        GymAddressClass gymAddressClass=new GymAddressClass("id",27.13214,24.14241,"address","city");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime=new GymTime("id","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("id", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresnt> list2=new ArrayList<>();
        list2.add(gymRepresnt);
        when(userOrderRepo.findById(data.get("order_id"))).thenReturn(optional);
        when(gymRepo.findById(userOrder.getGym())).thenReturn(optional1);
        UserOrder userOrder2 = userOrderService.updateOrder(data);
        Assertions.assertEquals(userOrder.getId(),userOrder2.getId());
    }

    @Test
    void testUpdateOrder() {
        Map<String,String> data=new HashMap<>();
        data.put("order_id", "id");
        data.put("payment_id","paymentId");
        data.put("status","status");
        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        Optional<GymClass> optional1=Optional.of(new GymClass("id", "email", "name", workout, l, 3.5, 20));
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        userOrder = new UserOrder("id", "email", "gym", services, "monthly", "slot", 12345, "Current", "status", "paymentId", "receipt", localDate, localTime);
        Optional<UserOrder> optional=Optional.of(new UserOrder("id", "email", "gym", services, "monthly", "slot", 12345, "Current", "Completed", "paymentId", "receipt", localDate, localTime));
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        GymAddressClass gymAddressClass=new GymAddressClass("id",27.13214,24.14241,"address","city");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime=new GymTime("id","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("id", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresnt> list2=new ArrayList<>();
        list2.add(gymRepresnt);
        when(userOrderRepo.findById(data.get("order_id"))).thenReturn(optional);
        when(gymRepo.findById(userOrder.getGym())).thenReturn(optional1);
        UserOrder userOrder2 = userOrderService.updateOrder(data);
        Assertions.assertEquals(userOrder.getId(),userOrder2.getId());
    }

    @Test
    void testUpdateOrderOptional() {
        Map<String,String> data=new HashMap<>();
        data.put("order_id", "id");
        data.put("payment_id","paymentId");
        data.put("status","status");
        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        UserOrder userOrder = new UserOrder("id", "email", "gym", services, "monthly", "slot", 12345, "Current", "status", "paymentId", "receipt", localDate, localTime);
        Optional<UserOrder> optional=Optional.empty();
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        GymAddressClass gymAddressClass=new GymAddressClass("id",27.13214,24.14241,"address","city");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime=new GymTime("id","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("id", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresnt> list2=new ArrayList<>();
        list2.add(gymRepresnt);
        when(userOrderRepo.findById(data.get("order_id"))).thenReturn(optional);
        UserOrder userOrder2 = userOrderService.updateOrder(data);
        Assertions.assertEquals(null,userOrder2);
    }
    
    @Test
    void testUpdateOrderOptionalGymClass() {
        Map<String,String> data=new HashMap<>();
        data.put("order_id", "id");
        data.put("payment_id","paymentId");
        data.put("status","status");
        List<String> workout =new ArrayList<>();
        workout.add("zumba");
        long l=1234;
        Optional<GymClass> optional1=Optional.empty();
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        List<String> services=new ArrayList<>();
        services.add("zumba");
        UserOrder userOrder = new UserOrder("id", "email", "gym", services, "monthly", "slot", 12345, "Current", "status", "paymentId", "receipt", localDate, localTime);
        Optional<UserOrder> optional=Optional.of(new UserOrder("id", "email", "gym", services, "monthly", "slot", 12345, "Current", "Completed", "paymentId", "receipt", localDate, localTime));
        List<Integer> attendance=new ArrayList<>();
        attendance.add(3);
        List<UserOrder> list=new ArrayList<>();
        list.add(userOrder);
        UserPerfomanceModel uModel2=new UserPerfomanceModel("name", "email", "gym", "vendor", attendance, 3.5);
        Set<UserPerfomanceModel> uModels2=new HashSet<>();
        uModels2.add(uModel2);
        GymAddressClass gymAddressClass=new GymAddressClass("id",27.13214,24.14241,"address","city");
        List<String> workoutList=new ArrayList<>();
        workoutList.add("zumba");
        GymTime gymTime=new GymTime("id","yes","yes","yes");
        GymSubscriptionClass gymSubscriptionClass=new GymSubscriptionClass("GM6", 1,1,1,1,1,1);
        GymRepresnt gymRepresnt=new GymRepresnt("id", "email", "gymName", gymAddressClass, workoutList, gymTime, gymSubscriptionClass, l, 3.5, 20);
        List<GymRepresnt> list2=new ArrayList<>();
        list2.add(gymRepresnt);
        when(userOrderRepo.findById(data.get("order_id"))).thenReturn(optional);
        when(gymRepo.findById(userOrder.getGym())).thenReturn(optional1);
        UserOrder userOrder2 = userOrderService.updateOrder(data);
        Assertions.assertEquals(userOrder.getId(),userOrder2.getId());
    }
}
