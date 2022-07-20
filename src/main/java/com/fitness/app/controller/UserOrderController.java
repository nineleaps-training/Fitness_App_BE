package com.fitness.app.controller;

import com.fitness.app.dto.UserOrderModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.UserOrderClass;
import com.fitness.app.service.UserOrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * The type User order controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class UserOrderController {


    private final UserOrderServiceImpl userOrderServiceImpl;

    /**
     * Check user can order api response.
     *
     * @param email the email
     * @return the api response
     */
    @GetMapping("/check-user-order/{email}")
    public ApiResponse checkUserCanOrder(@PathVariable String email) {
        return new ApiResponse(HttpStatus.OK, userOrderServiceImpl.canOrder(email));
    }

    /**
     * Order now api response.
     *
     * @param order the order
     * @return the api response
     * @throws Exception the exception
     */
//order now
    @PostMapping("/order-now")
    @ResponseBody
    public ApiResponse orderNow(@RequestBody UserOrderModel order) throws Exception {
        return new ApiResponse(HttpStatus.OK, userOrderServiceImpl.orderNow(order));
    }


    /**
     * Updating order api response.
     *
     * @param data the data
     * @return the api response
     */
//update_order after payment.
    @PutMapping("update-order")
    public ApiResponse updatingOrder(@RequestBody Map<String, String> data) {
        return userOrderServiceImpl.updateOrder(data);

    }

    /**
     * Pending order list response entity.
     *
     * @param email the email
     * @return the response entity
     */
//Check the pending orders by email id of the user
    @GetMapping("/pending-order/{email}")
    public ResponseEntity<List<UserOrderClass>> pendingOrderList(@PathVariable String email) {

        return new ResponseEntity<>(userOrderServiceImpl.pendingListOrder(email), HttpStatus.OK);

    }

    /**
     * Order history response entity.
     *
     * @param email the email
     * @return the response entity
     */
//Fetching the order history by email id of the user
    @GetMapping("/history/{email}")
    public ResponseEntity<List<UserOrderClass>> orderHistory(@PathVariable String email) {

        return new ResponseEntity<>(userOrderServiceImpl.orderListByEmail(email), HttpStatus.OK);

    }

    /**
     * All my users api response.
     *
     * @param gymId the gym id
     * @return the api response
     */
//Fetching the user of the particular Gym by gymId
    @GetMapping("/my-users/{gymId}")
    public ApiResponse allMyUsers(@PathVariable String gymId) {

        return userOrderServiceImpl.allMyUser(gymId);

    }

    /**
     * Booked gym response entity.
     *
     * @param email the email
     * @return the response entity
     */
//Fetching gyms booked by a particular user by email
    @GetMapping("/booked/gyms/{email}")
    public ResponseEntity<?> bookedGym(@PathVariable String email) {

        return new ResponseEntity<>(userOrderServiceImpl.bookedGym(email), HttpStatus.OK);

    }


}
