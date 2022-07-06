package com.fitness.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


import com.fitness.app.componets.Components;
import com.fitness.app.entity.*;
import com.fitness.app.model.BookedGymModel;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    private VendorPayRepo vendorOrderRepo;



    //creating order
    public void orderNow(UserOrder userOrder) {
        userOrderRepo.save(userOrder);
    }
    final String CURRENT="Current";

    //updating order

    public UserOrder updateOrder(Map<String, String> data) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        
        if(data==null) {return null;}
        UserOrder order=new UserOrder();
        Optional<UserOrder>orderData =userOrderRepo.findById(data.get("order_id"));
        if(orderData.isPresent())
        {
        	order=orderData.get();
        }
        order.setPaymentId(data.get("payment_id"));
        order.setStatus(data.get("status"));
        order.setBooked(CURRENT);
        order.setDate(date);
        order.setTime(time);



        //update booked...
        int booked ;
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
        String vendor=null;
        Optional<GymClass>gymOp = gymRepo.findById(order.getGym());
        if(gymOp.isPresent())
        {
        	vendor=gymOp.get().getEmail();
        }

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

        userOrderRepo.save(order);
        return order;
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
        Optional<GymClass>gymdata=gymRepo.findById(gymId);
        String vendor="";
        if(gymdata.isPresent()) {
               vendor=gymdata.get().getEmail();
        }
        
        for (UserOrder order : orders) {
            UserAttendance newAtt = attendanceRepo.findByEmailAndVendor(order.getEmail(), vendor);
            UserPerfomanceModel user = new UserPerfomanceModel();
            UserClass userClass=userRepository.findByEmail(order.getEmail());
            user.setName(userClass.getFullName());
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
    private Components components;

    public List<BookedGymModel> bookedGym(String email) {




    List<UserOrder> orders = userOrderRepo.findByEmail(email);
        List<BookedGymModel> gyms = new ArrayList<>();
        if (orders == null) {
            return gyms;
        }

        BookedGymModel gymModel=components.gymModelByStatus(orders, CURRENT);
        if(gymModel==null || gymModel.getId()==null)
        {
            gymModel=null;
        }
        gyms.add(gymModel);

        gymModel=components.gymModelByStatus(orders, "Expired");
        gyms.add(gymModel);
      return  gyms;

    }

    
    



    public Boolean canOrder(String email) {

        List<UserOrder> orders = userOrderRepo.findByEmail(email);
        orders = orders.stream().filter(o -> o.getBooked().equals(CURRENT)).collect(Collectors.toList());

        if (orders == null) {
            return true;
        }
        LocalDate localDate = LocalDate.now();
        for (UserOrder order : orders) {
            LocalDate currentDate = order.getDate();
            String subs = order.getSubscription();
            currentDate=currentDate.plusDays(components.calculateTotalTime(subs));

            int comp = localDate.compareTo(currentDate);
            if (comp > 0) {
                order.setBooked("Expired");
                userOrderRepo.save(order);
            }

        }

        if (orders.size() < 1) {
            return true;
        } else {
            return false;
        }

    }

}
