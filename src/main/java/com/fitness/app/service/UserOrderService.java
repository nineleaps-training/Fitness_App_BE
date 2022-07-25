package com.fitness.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


import com.fitness.app.dao.UserOrderDao;
import com.fitness.app.entity.GymClass;
import com.fitness.app.model.GymRepresent;


import com.fitness.app.model.UserOrderModel;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fitness.app.entity.UserAttendance;
import com.fitness.app.entity.UserOrder;
import com.fitness.app.entity.VendorPayment;
import com.fitness.app.model.UserPerformanceModel;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.AttendanceRepository;
import com.fitness.app.repository.UserOrderRepository;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.repository.VendorPayRepository;

@Component
@Slf4j
public class UserOrderService implements UserOrderDao {

    private UserOrderRepository userOrderRepository;

    private UserRepository userRepository;

    private AttendanceRepository attendanceRepository;

    private AddGymRepository gymRepo;

    private GymService gymService;

    private VendorPayRepository vendorOrderRepo;

    Environment environment;


    String current = "Current";


    @Autowired
    public UserOrderService(UserOrderRepository userOrderRepository, UserRepository userRepository, AttendanceRepository attendanceRepository, AddGymRepository gymRepo, GymService gymService, VendorPayRepository vendorOrderRepo, Environment environment) {
        this.userOrderRepository = userOrderRepository;
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
        this.gymRepo = gymRepo;
        this.gymService = gymService;
        this.vendorOrderRepo = vendorOrderRepo;
        this.environment = environment;
    }

    //creating order
    public String orderNow(UserOrderModel order) throws RazorpayException {
        log.info("UserOrderService >> orderNow >> Initiated");
        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_vmHcJh5Dj4v5EB", "SGff6EaJ7l3RzR47hnE4dYJz");

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        JSONObject ob = new JSONObject();
        ob.put("amount", order.getAmount() * 100);
        ob.put("currency", "INR");
        ob.put("receipt", "txn_201456");

        Order myOrder = razorpayClient.Orders.create(ob);
        UserOrder userOrder = new UserOrder();

        userOrder.setId(myOrder.get("id"));
        userOrder.setEmail(order.getEmail());
        userOrder.setGym(order.getGym());
        userOrder.setServices(order.getServices());
        userOrder.setSubscription(order.getSubscription());
        userOrder.setSlot(order.getSlot());
        userOrder.setAmount(order.getAmount());
        userOrder.setBooked("");
        userOrder.setStatus(myOrder.get("status"));
        userOrder.setPaymentId(null);
        userOrder.setReceipt(myOrder.get("receipt"));
        userOrder.setDate(date);
        userOrder.setTime(time);
        userOrderRepository.save(userOrder);
        log.info("UserOrderService >> orderNow >> Ends");
        return myOrder.toString();
    }

    //updating order
    public UserOrder updateOrder(Map<String, String> data) {
        log.info("UserOrderService >> updateOrder >> Initiated");
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        UserOrder order = new UserOrder();

        Optional<UserOrder> optional = userOrderRepository.findById(data.get("order_id"));
        if (optional.isPresent()) {
            order = optional.get();
        }

        order.setPaymentId(data.get("payment_id"));
        order.setStatus(data.get("status"));
        order.setBooked(current);
        order.setDate(date);
        order.setTime(time);


        //update booked...
        int booked;
        switch (order.getSubscription()) {
            case "monthly":
                booked = 30;
                break;
            case "quarterly":
                booked = 90;
                break;
            case "half":
                booked = 180;
                break;
            default:
                booked = 360;
                break;
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
        attendanceRepository.save(attendance);


        VendorPayment vendorOrder = new VendorPayment();
        vendorOrder.setVendor(vendor);
        vendorOrder.setUser(order.getEmail());
        vendorOrder.setGym(order.getGym());
        vendorOrder.setAmount(order.getAmount());
        vendorOrder.setStatus("Due");
        vendorOrder.setDate(date);
        vendorOrder.setTime(time);

        vendorOrderRepo.save(vendorOrder);

        userOrderRepository.save(order);
        log.info("UserOrderService >> updateOrder >> Ends");
        return order;
    }

    //pending order list of user
    public List<UserOrder> pendingListOrder(String email) {
        log.info("UserOrderService >> pendingListOrder >> Initiated");
        List<UserOrder> orders = userOrderRepository.findByEmail(email);
        orders = orders.stream().filter(o -> o.getStatus().equals("created")).collect(Collectors.toList());
        for (UserOrder eachOrder : orders) {
            LocalDate date = eachOrder.getDate();
            date = date.plusDays(5);
            LocalDate currentDate = LocalDate.now();
            int ans = currentDate.compareTo(date);
            if (ans < 0) {
                log.info("UserOrderService >> pendingListOrder >> Deleted");
                userOrderRepository.delete(eachOrder);
            }
        }
        log.info("UserOrderService >> pendingListOrder >> Ends");
        return orders;
    }

    public List<UserOrder> orderListOrder(String email) {
        log.info("UserOrderService >> orderListOrder >> Initiated");
        List<UserOrder> orders = userOrderRepository.findByEmail(email);
        orders = orders.stream().filter(o -> o.getStatus().equals("Completed")).collect(Collectors.toList());
        log.info("UserOrderService >> orderListOrder >> Ends");
        return orders;
    }

    public Set<UserPerformanceModel> allMyUser(String gymId) {
        log.info("UserOrderService >> allMyUser >> Initiated");
        List<UserOrder> orders = userOrderRepository.findByGym(gymId);
        orders = orders.stream().filter(o -> o.getStatus().equals("Completed")).collect(Collectors.toList());
        Set<UserPerformanceModel> users = new HashSet<>();

        String vendor = "";
        Optional<GymClass> optional = gymRepo.findById(gymId);
        if (optional.isPresent()) {
            vendor = optional.get().getEmail();
        }

        for (UserOrder order : orders) {
            UserAttendance newAtt = attendanceRepository.findByEmailAndVendor(order.getEmail(), vendor);
            UserPerformanceModel user = new UserPerformanceModel();
            user.setName(userRepository.findByEmail(order.getEmail()).getFullName());
            user.setEmail(order.getEmail());
            user.setGym(gymId);
            user.setVendor(vendor);
            user.setAttendance(newAtt.getAttendance());
            user.setRating(newAtt.getRating());
            users.add(user);
        }
        log.info("UserOrderService >> allMyUser >> Ends");
        return users;
    }

    public List<GymRepresent> bookedGym(String email) {
        log.info("UserOrderService >> bookedGym >> Initiated");

        List<UserOrder> orders = userOrderRepository.findByEmail(email);
        if (orders == null) {
            log.warn("UserOrderService >> bookedGym >> returns empty list");
            return Collections.emptyList();
        }
        List<GymRepresent> gyms = new ArrayList<>();
        for (UserOrder order : orders) {
            if (order.getBooked().equals(current)) {
                gyms.add(gymService.getGymByGymId(order.getGym()));
            }
        }
        orders = orders.stream().filter(o -> o.getBooked().equals("Expired")).collect(Collectors.toList());
        for (UserOrder order : orders) {
            gyms.add(gymService.getGymByGymId(order.getGym()));
        }
        log.info("UserOrderService >> bookedGym >> Ends");
        return gyms;

    }


    public Boolean canOrder(String email) {
        log.info("UserOrderService >> canOrder >> Initiated");
        List<UserOrder> orders = userOrderRepository.findByEmail(email);
        orders = orders.stream().filter(o -> o.getBooked().equals(current)).collect(Collectors.toList());

        if (orders.isEmpty()) {
            log.info("UserOrderService >> canOrder >> UserOrder list is empty");
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
                case "Quarterly":
                    currentDate = currentDate.plusDays(75);
                    break;
                case "Half":
                    currentDate = currentDate.plusDays(150);
                    break;
                case "Yearly":
                    currentDate = currentDate.plusDays(300);
                    break;
                default:
                    currentDate = currentDate.plusDays(0);
            }
            int comp = localDate.compareTo(currentDate);
            if (comp > 0) {
                order.setBooked("Expired");
                userOrderRepository.save(order);
            }
        }
        log.info("UserOrderService >> canOrder >> Ends");
        return orders.isEmpty();
    }
}
