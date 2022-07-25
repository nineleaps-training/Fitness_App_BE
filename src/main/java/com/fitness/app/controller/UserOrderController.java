package com.fitness.app.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fitness.app.dao.UserOrderDao;
import com.fitness.app.model.GymRepresent;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.app.entity.UserOrder;
import com.fitness.app.model.UserOrderModel;
import com.fitness.app.model.UserPerformanceModel;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class UserOrderController {

    @Autowired
    private UserOrderDao userOrderDao;

    @GetMapping("/check-user-order/{email}")
    public Boolean checkUserCanOrder(@NotBlank @NotNull @NotEmpty @Email @PathVariable String email) {
        return userOrderDao.canOrder(email);
    }

    //order now
    @PostMapping("/order/now")
    @ResponseBody
    @Validated
    public String orderNow(@Valid @RequestBody UserOrderModel order) throws RazorpayException {

        return userOrderDao.orderNow(order);
    }


    //update_order after payment.
    @PutMapping("/update/order")
    public UserOrder updatingOrder(@RequestBody Map<String, String> data) {

        return userOrderDao.updateOrder(data);
    }

    //Check the pending orders by email id of the user
    @GetMapping("/pending/order/{email}")
    public ResponseEntity<List<UserOrder>> pendingOrderList(@NotBlank @NotNull @NotEmpty @Email @PathVariable String email) {
        return new ResponseEntity<>(userOrderDao.pendingListOrder(email), HttpStatus.OK);
    }

    //Fetching the order history by email id of the user
    @GetMapping("/order/history/{email}")
    public ResponseEntity<List<UserOrder>> orderHistory(@NotBlank @NotNull @NotEmpty @Email @PathVariable String email) {
        return new ResponseEntity<>(userOrderDao.orderListOrder(email), HttpStatus.OK);
    }

    //Fetching the user of the particular Gym by gymId
    @GetMapping("/my/users/{gymId}")
    public Set<UserPerformanceModel> allMyUsers(@NotBlank @NotNull @NotEmpty @PathVariable String gymId) {
        return userOrderDao.allMyUser(gymId);
    }

    //Fetching gyms booked by a particular user by email
    @GetMapping("/booked/gyms/{email}")
    public List<GymRepresent> bookedGym(@NotBlank @NotNull @NotEmpty @Email @PathVariable String email) {
        return userOrderDao.bookedGym(email);
    }


    //@GetMapping("/order-to-gym/{gymId}")


}
