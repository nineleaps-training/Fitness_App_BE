package com.fitness.app.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.LocalDate;
import java.time.LocalTime;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.app.entity.UserOrderClass;
import com.fitness.app.model.BookedGymModel;
import com.fitness.app.model.UserOrderModel;
import com.fitness.app.model.UserPerfomanceModel;
import com.fitness.app.service.UserOrderService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@RestController
public class UserOrderController {


    @Autowired
    private UserOrderService userOrderService;

    //key_id: rzp_test_vmHcJh5Dj4v5EB
    //sec_key: SGff6EaJ7l3RzR47hnE4dYJz


    @GetMapping("/check-user-order/{email}")
    public Boolean checkUserCanOrder(@PathVariable String email) {
        return userOrderService.canOrder(email);
    }

    //order now
    @PostMapping("/order/now")
    @ResponseBody
    public String orderNow(@RequestBody UserOrderModel order) throws Exception {
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

        userOrderService.orderNow(userOrderClass);
        return myOrder.toString();
    }


    //update_order after payment.

    @PutMapping("update/order")
    public UserOrderClass updatingOrder(@RequestBody Map<String, String> data) {

        return userOrderService.updateOrder(data);
    }

    //Check the pending orders by email id of the user
    @GetMapping("/pending/order/{email}")
    public ResponseEntity<List<UserOrderClass>> pedingOrerList(@PathVariable String email) {
        return new ResponseEntity<>(userOrderService.pendingListOrder(email), HttpStatus.OK);
    }

    //Fetching the order history by email id of the user
    @GetMapping("/order/history/{email}")
    public ResponseEntity<List<UserOrderClass>> orderHistory(@PathVariable String email) {
        return new ResponseEntity<>(userOrderService.OrderListOrder(email), HttpStatus.OK);
    }

    //Fetching the user of the particular Gym by gymId
    @GetMapping("/my/users/{gymId}")
    public Set<UserPerfomanceModel> allMyUsers(@PathVariable String gymId) {
        return userOrderService.allMyUser(gymId);
    }

    //Fetching gyms booked by a particular user by email
    @GetMapping("/booked/gyms/{email}")
    public List<BookedGymModel> bookedGym(@PathVariable String email) {
        return userOrderService.bookedGym(email);
    }


    //@GetMapping("/order-to-gym/{gymId}")


}
