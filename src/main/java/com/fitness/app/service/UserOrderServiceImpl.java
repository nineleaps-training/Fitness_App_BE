package com.fitness.app.service;

import com.fitness.app.utils.MessageComponents;
import com.fitness.app.dto.BookedGymModel;
import com.fitness.app.dto.UserOrderModel;
import com.fitness.app.dto.UserPerfomanceModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.*;
import com.fitness.app.repository.*;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserOrderServiceImpl implements UserOrderService {


    final private UserOrderRepository userOrderRepository;
    final private UserRepository userRepository;
    final private AttendanceRepository attendanceRepository;
    final private AddGymRepository gymRepo;
    final private VendorPayRepository vendorOrderRepo;
    final private MessageComponents messageComponents;
    final String CURRENT = "Current";

    //creating order
    @Override
    public String orderNow(UserOrderModel order) throws RazorpayException {

        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_vmHcJh5Dj4v5EB", "SGff6EaJ7l3RzR47hnE4dYJz");

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        JSONObject ob = new JSONObject();
        ob.put("amount", order.getAmount() * 100);
        ob.put("currency", "INR");
        ob.put("receipt", "txn_201456");

        Order myOrder = razorpayClient.Orders.create(ob);
        UserOrderClass userOrderClass = new UserOrderClass();

        userOrderClass.setId(myOrder.get("id"));
        userOrderClass.setEmail(order.getEmail());
        userOrderClass.setGym(order.getGym());
        userOrderClass.setServices(order.getServices());
        userOrderClass.setSubscription(order.getSubscription());
        userOrderClass.setSlot(order.getSlot());
        userOrderClass.setAmount(order.getAmount());
        userOrderClass.setBooked("");
        userOrderClass.setStatus(myOrder.get("status"));
        userOrderClass.setPaymentId(null);
        userOrderClass.setReceipt(myOrder.get("receipt"));
        userOrderClass.setDate(date);
        userOrderClass.setTime(time);


        userOrderRepository.save(userOrderClass);
        return myOrder.toString();
    }

    //updating order
    @Override
    public ApiResponse updateOrder(Map<String, String> data) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        if (data == null) {
            return new ApiResponse(HttpStatus.NO_CONTENT, null);
        }
        UserOrderClass order = new UserOrderClass();
        Optional<UserOrderClass> orderData = userOrderRepository.findById(data.get("order_id"));
        if (orderData.isPresent()) {
            order = orderData.get();
        }
        order.setPaymentId(data.get("payment_id"));
        order.setStatus(data.get("status"));
        order.setBooked(CURRENT);
        order.setDate(date);
        order.setTime(time);


        //update booked...
        int booked;
        String subs = order.getSubscription();
        switch (subs) {
            case "Monthly":
                booked = 30;
                break;
            case "Quaterly":
                booked = 90;
                break;
            case "Half":
                booked = 180;
                break;
            default:
                booked = 360;
                break;
        }


        UserAttendanceClass attendance = new UserAttendanceClass();
        String vendor = null;
        Optional<GymClass> gymOp = gymRepo.findById(order.getGym());
        if (gymOp.isPresent()) {
            vendor = gymOp.get().getEmail();
        }

        attendance.setEmail(order.getEmail());
        attendance.setGym(order.getGym());
        attendance.setVendor(vendor);
        attendance.setBooked(booked);
        attendance.setAttended(0);
        attendance.setAttendance(null);
        attendance.setRating(0.0);
        attendanceRepository.save(attendance);

        VendorPaymentClass vendorOrder = new VendorPaymentClass();
        vendorOrder.setVendor(vendor);
        vendorOrder.setUser(order.getEmail());
        vendorOrder.setGym(order.getGym());
        vendorOrder.setAmount(order.getAmount());
        vendorOrder.setStatus("Due");
        vendorOrder.setDate(date);
        vendorOrder.setTime(time);

        vendorOrderRepo.save(vendorOrder);

        userOrderRepository.save(order);
        return new ApiResponse(HttpStatus.OK
                , order);
    }

    //pending order list of user

    @Override
    public List<UserOrderClass> pendingListOrder(String email) {
        List<UserOrderClass> orders = userOrderRepository.findByEmail(email);
        orders = orders.stream().filter(o -> o.getStatus().equals("created")).collect(Collectors.toList());
        for (UserOrderClass eachOrder : orders) {
            LocalDate date = eachOrder.getDate();
            date = date.plusDays(5);
            LocalDate currentDate = LocalDate.now();
            int ans = currentDate.compareTo(date);
            if (ans < 0) {
                userOrderRepository.delete(eachOrder);
            }
        }
        return orders;
    }

    @Override
    public List<UserOrderClass> orderListByEmail(String email) {
        List<UserOrderClass> orders = userOrderRepository.findByEmail(email);
        orders = orders.stream().filter(o -> o.getStatus().equals("Completed")).collect(Collectors.toList());
        return orders;
    }

    @Override
    public ApiResponse allMyUser(String gymId) {
        List<UserOrderClass> orders = userOrderRepository.findByGym(gymId);
        orders = orders.stream().filter(o -> o.getStatus().equals("Completed")).collect(Collectors.toList());
        Set<UserPerfomanceModel> users = new HashSet<>();
        Optional<GymClass> gymdata = gymRepo.findById(gymId);
        String vendor = "";
        if (gymdata.isPresent()) {
            vendor = gymdata.get().getEmail();
        }

        for (UserOrderClass order : orders) {
            UserAttendanceClass newAtt = attendanceRepository.findByEmailAndVendor(order.getEmail(), vendor);
            UserPerfomanceModel user = new UserPerfomanceModel();
            UserClass userClass = userRepository.findByEmail(order.getEmail());
            user.setName(userClass.getFullName());
            user.setEmail(order.getEmail());
            user.setGym(gymId);
            user.setVendor(vendor);
            user.setAttendance(newAtt.getAttendance());
            user.setRating(newAtt.getRating());
            users.add(user);
        }
        return new ApiResponse(HttpStatus.OK, orders);
    }

    @Override
    public List<BookedGymModel> bookedGym(String email) {

        List<UserOrderClass> orders = userOrderRepository.findByEmail(email);
        List<BookedGymModel> gyms = new ArrayList<>();
        if (orders == null) {
            return gyms;
        }

        BookedGymModel gymModel = messageComponents.gymModelByStatus(orders, CURRENT);
        if (gymModel == null || gymModel.getId() == null) {
            gymModel = null;
        }
        gyms.add(gymModel);

        gymModel = messageComponents.gymModelByStatus(orders, "Expired");
        gyms.add(gymModel);
        return gyms;

    }

    @Override
    public Boolean canOrder(String email) {

        List<UserOrderClass> orders = userOrderRepository.findByEmail(email);
        if (orders == null || orders.size() < 0) {
            return true;
        }
        orders = orders.stream().filter(o -> o.getBooked().equals(CURRENT)).collect(Collectors.toList());
        LocalDate localDate = LocalDate.now();
        for (UserOrderClass order : orders) {
            LocalDate currentDate = order.getDate();
            String subs = order.getSubscription();
            currentDate = currentDate.plusDays(messageComponents.calculateTotalTime(subs));

            int comp = currentDate.compareTo(localDate);
            if (comp > 0) {
                order.setBooked("Expired");
                userOrderRepository.save(order);
            }
        }
        if (orders.size() < 1) {
            return true;
        } else {
            return false;
        }

    }

}
