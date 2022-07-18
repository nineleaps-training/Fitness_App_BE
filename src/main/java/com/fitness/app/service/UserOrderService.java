package com.fitness.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserAttendance;
import com.fitness.app.entity.UserOrder;
import com.fitness.app.entity.VendorPayment;
import com.fitness.app.model.GymRepresentModel;
import com.fitness.app.model.UserPerfomanceModel;
import com.fitness.app.repository.AddGymRepo;
import com.fitness.app.repository.AttendanceRepo;
import com.fitness.app.repository.UserOrderRepo;
import com.fitness.app.repository.UserRepo;
import com.fitness.app.repository.VendorPayRepo;

@Service
public class UserOrderService {

    @Autowired
    private UserOrderRepo userOrderRepo;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private AttendanceRepo attendanceRepo;

    @Autowired
    private AddGymRepo gymRepo;

    @Autowired
    private GymService gymService;

    @Autowired
    private VendorPayRepo vendorOrderRepo;

    private static final String CURRE_STRING = "Current";

    // Initializing constructor
    /**
     * This constructor is used to initialize the repositories
     * 
     * @param userRepository2  - User Repository
     * @param userOrderRepo2   - User Order Repository
     * @param attendanceRepo2  - Attendance Repository
     * @param gymRepo2         - Gym Repository
     * @param gymService2      - Gym Service
     * @param vendorOrderRepo2 - Vendor Order Repository
     */
    public UserOrderService(UserRepo userRepository2, UserOrderRepo userOrderRepo2,
            AttendanceRepo attendanceRepo2, AddGymRepo gymRepo2, GymService gymService2,
            VendorPayRepo vendorOrderRepo2) {

        this.userOrderRepo = userOrderRepo2;
        this.userRepository = userRepository2;
        this.attendanceRepo = attendanceRepo2;
        this.gymRepo = gymRepo2;
        this.gymService = gymService2;
        this.vendorOrderRepo = vendorOrderRepo2;
    }

    /**
     * This function is save the order
     * 
     * @param userOrder - Details of the order
     * @return - Order Details
     */
    public UserOrder orderNow(UserOrder userOrder) {
        return userOrderRepo.save(userOrder); // Saving order
    }

    /**
     * This function is used to update the order details
     * 
     * @param data - Updated order details
     * @return - Details of the order
     */
    public UserOrder updateOrder(Map<String, String> data) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        UserOrder order;
        Optional<UserOrder> optional = userOrderRepo.findById(data.get("order_id"));
        if (optional.isPresent()) {
            order = optional.get();
            order.setPaymentId(data.get("payment_id")); // updating order
            order.setStatus(data.get("status"));
            order.setBooked(CURRE_STRING);
            order.setDate(date);
            order.setTime(time);

            // update booked...
            int booked;
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
            Optional<GymClass> optional2 = gymRepo.findById(order.getGym());
            if (optional2.isPresent()) {
                String vendor = optional.get().getEmail();
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
        } else {
            return null;
        }
        return order;
    }

    /**
     * This function is used to fetch the pending order list of the user
     * 
     * @param email - Email if of the user
     * @return - List of pending orders
     */
    public List<UserOrder> pendingListOrder(String email) {
        List<UserOrder> orders = userOrderRepo.findByEmail(email);
        orders = orders.stream().filter(o -> o.getStatus().equals("created")).collect(Collectors.toList()); // pending order list of user
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

    /**
     * This function is used to fetch the completed order details
     * 
     * @param email - Email id of the user
     * @return - List of order details
     */
    public List<UserOrder> orderListOrder(String email) {
        List<UserOrder> orders = userOrderRepo.findByEmail(email);
        orders = orders.stream().filter(o -> o.getStatus().equals("Completed")).collect(Collectors.toList()); // Fetching order details which are 'Completed'
        return orders;
    }

    /**
     * This function is used to fetch all the users of a particular gym
     * 
     * @param gymId - Id of the gym
     * @return - List of all the users
     */
    public Set<UserPerfomanceModel> allMyUser(String gymId) {
        List<UserOrder> orders = userOrderRepo.findByGym(gymId);
        orders = orders.stream().filter(o -> o.getStatus().equals("Completed")).collect(Collectors.toList());
        Set<UserPerfomanceModel> users = new HashSet<>();
        Optional<GymClass> optional = gymRepo.findById(gymId);
        if (optional.isPresent()) {
            String vendor = optional.get().getEmail(); // Fetching all the users of a particular gym
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
        }
        return users;
    }

    /**
     * This function is used to fetch all the gyms which are booked
     * 
     * @param email - Email id of the user
     * @return - List of gyms which are booked
     */
    public List<GymRepresentModel> bookedGym(String email) {
        List<UserOrder> orders = userOrderRepo.findByEmail(email);
        if (orders == null) {

            return Collections.emptyList();
        }
        List<GymRepresentModel> gyms = new ArrayList<>();
        for (UserOrder order : orders) {
            if (order.getBooked().equals(CURRE_STRING)) {
                gyms.add(gymService.getGymByGymId(order.getGym()));
            }
        }
        orders = orders.stream().filter(o -> o.getBooked().equals("Expired")).collect(Collectors.toList()); // Fetching all gyms which are booked
        for (UserOrder order : orders) {
            gyms.add(gymService.getGymByGymId(order.getGym()));
        }
        return gyms;

    }

    /**
     * This function is used to check if user can order
     * 
     * @param email - Email id of the user
     * @return - true if user can order or else false
     */
    public Boolean canOrder(String email) {
        List<UserOrder> orders = userOrderRepo.findByEmail(email);
        if (orders == null) {
            return true;
        }
        orders = orders.stream().filter(o -> o.getBooked().equals(CURRE_STRING)).collect(Collectors.toList());
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
                default:
                    currentDate = LocalDate.now();
            }
            int comp = localDate.compareTo(currentDate);
            if (comp > 0) { // Checking if user can order
                order.setBooked("Expired");
                userOrderRepo.save(order);
            }

        }
        return (orders.isEmpty());
    }

}
