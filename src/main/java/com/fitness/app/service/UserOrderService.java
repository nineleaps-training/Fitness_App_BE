package com.fitness.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


import com.fitness.app.model.GymRepresnt;
import org.apache.catalina.User;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserAttendance;
import com.fitness.app.entity.UserOrder;
import com.fitness.app.entity.VendorPayment;
import com.fitness.app.model.BookedGymModel;
import com.fitness.app.model.UserPerfomanceModel;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.AttendanceRepo;
import com.fitness.app.repository.GymAddressRepo;
import com.fitness.app.repository.UserOrderRepo;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.repository.VendorPayRepo;

@Service
public class UserOrderService {

    @Autowired
    private UserOrderRepo userOrderRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttendanceRepo attendanceRepo;

    @Autowired
    private AddGymRepository gymRepo;

    @Autowired
    private GymService gymService;

    @Autowired
    private VendorPayRepo vendorOrderRepo;

    @Autowired
    private RatingService ratingService;

    //creating order
    public void orderNow(UserOrder userOrder) {
        userOrderRepo.save(userOrder);
    }


    //updating order

    public UserOrder updateOrder(Map<String, String> data) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        UserOrder order = userOrderRepo.findById(data.get("order_id")).get();
        order.setPaymentId(data.get("payment_id"));
        order.setStatus(data.get("status"));
        order.setBooked("Current");
        order.setDate(date);
        order.setTime(time);

        //TODO Create Invoice and Unique Id
        //TODO Send Invoice to User.


        //update booked...
        int booked = 30;
        if (order.getSubscription().equals("monthly")) {
            booked = 30;
        } else if (order.getSubscription().equals("quaterly")) {
            booked = 90;
        } else if (order.getSubscription().equals("half")) {
            booked = 180;
        } else {
            booked = 360;
        }

        UserAttendance attendance = new UserAttendance();
        String vendor = gymRepo.findById(order.getGym()).get().getEmail();

        attendance.setEmail(order.getEmail());
        attendance.setGym(order.getGym());
        attendance.setVendor(vendor);
        attendance.setBooked(booked);
        attendance.setAttended(0);
        attendance.setAttendance(null);
        attendance.setRating(0.0);
        attendanceRepo.save(attendance);


        VendorPayment vendorOrder = new VendorPayment();
        vendorOrder.setVendor(vendor);
        vendorOrder.setUser(order.getEmail());
        vendorOrder.setGym(order.getGym());
        vendorOrder.setAmount(order.getAmount());
        vendorOrder.setStatus("Due");
        vendorOrder.setDate(date);
        vendorOrder.setTime(time);

        vendorOrderRepo.save(vendorOrder);

        return userOrderRepo.save(order);
    }

    //pending order list of user


    public List<UserOrder> pendingListOrder(String email) {
        List<UserOrder> orders = userOrderRepo.findByEmail(email);
        orders = orders.stream().filter(o -> o.getStatus().equals("created")).collect(Collectors.toList());
        for (UserOrder eachOrder : orders) {
            LocalDate date = eachOrder.getDate();
            date = date.plusDays(5);
            LocalDate currenDate = LocalDate.now();
            int ans = currenDate.compareTo(date);
            if (ans < 0) {
                userOrderRepo.delete(eachOrder);
            }
        }
        return orders;
    }

    public List<UserOrder> OrderListOrder(String email) {
        List<UserOrder> orders = userOrderRepo.findByEmail(email);
        orders = orders.stream().filter(o -> o.getStatus().equals("Completed")).collect(Collectors.toList());
        return orders;
    }

    public Set<UserPerfomanceModel> allMyUser(String gymId) {
        List<UserOrder> orders = userOrderRepo.findByGym(gymId);
        orders = orders.stream().filter(o -> o.getStatus().equals("Completed")).collect(Collectors.toList());
        Set<UserPerfomanceModel> users = new HashSet<>();
        String vendor = gymRepo.findById(gymId).get().getEmail();
        for (UserOrder order : orders) {
            UserAttendance newAtt = attendanceRepo.findByEmailAndVendor(order.getEmail(), vendor);
            UserPerfomanceModel user = new UserPerfomanceModel();
            user.setName(userRepository.findByEmail(order.getEmail()).getFullName());
            user.setEmail(order.getEmail());
            user.setGym(gymId);
            user.setVendor(vendor);
            user.setAttendance(newAtt.getAttendance());
            user.setRating(newAtt.getRating());
            users.add(user);
        }
        return users;
    }

    @Autowired
    private GymAddressRepo gymAddressRepo;

    public List<GymRepresnt> bookedGym(String email) {




    List<UserOrder> orders = userOrderRepo.findByEmail(email);
        if (orders == null) {
            return null;
        }
        List<GymRepresnt> gyms = new ArrayList<>();
        for(UserOrder order:orders)
        {
            if(order.getBooked().equals("Current"))
            {
                gyms.add(gymService.getGymByGymId(order.getGym()));
            }
        }
       orders = orders.stream().filter(o -> o.getBooked().equals("Expired")).collect(Collectors.toList());
        for (UserOrder order : orders) {
            gyms.add(gymService.getGymByGymId(order.getGym()));
        }
        return gyms;

    }
    

    public Boolean canOrder(String email) {
        int count = 0;
        List<UserOrder> orders = userOrderRepo.findByEmail(email);
        orders = orders.stream().filter(o -> o.getBooked().equals("Current")).collect(Collectors.toList());

        if (orders == null) {
            return true;
        }
        LocalDate localDate = LocalDate.now();
        for (UserOrder order : orders) {
            LocalDate currentDate = order.getDate();
            String subs = order.getSubscription();
            switch (subs) {
                case "Monthly":
                    currentDate = currentDate.plusDays(25);
                    break;
                case "Quaterly":
                    currentDate = currentDate.plusDays(75);
                    break;
                case "Half":
                    currentDate = currentDate.plusDays(150);
                    break;
                case "Yearly":
                    currentDate = currentDate.plusDays(300);
                    break;
            }
            int comp = localDate.compareTo(currentDate);
            if (comp > 0) {
                order.setBooked("Expired");
                userOrderRepo.save(order);
            }

        }

        if (orders != null && orders.size() < 1) {
            return true;
        } else {
            return false;
        }

    }

}
