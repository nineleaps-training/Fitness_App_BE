package com.fitness.app.controller;

import com.fitness.app.dto.request.UserOrderModel;
import com.fitness.app.dto.response.ApiResponse;
import com.fitness.app.entity.UserOrderClass;
import com.fitness.app.service.UserOrderDaoImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Map;

/**
 * The type User order controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class UserOrderController {


    private final UserOrderDaoImpl userOrderServiceImpl;

    /**
     * Check user can order api response.
     *
     * @param email the email
     * @return the api response
     */
    @GetMapping("/check/user/order/{email}")
    @ApiOperation(value = "User can order or not", notes = "Check if user can make order or not.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "True or false", response = ApiResponse.class),
    })
    @Validated
    public ApiResponse checkUserCanOrder(@PathVariable @Email String email) {
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
    @PostMapping("/order/now")
    @ResponseBody
    @ApiOperation(value = "Make payment", notes = "initiate payment and get order id")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "order id and payment details", response = ApiResponse.class),
    })
    @Validated
    public ApiResponse orderNow(@RequestBody @Valid UserOrderModel order) throws Exception {
        return new ApiResponse(HttpStatus.OK, userOrderServiceImpl.orderNow(order));
    }


    /**
     * Updating order api response.
     *
     * @param data the data
     * @return the api response
     */
//update_order after payment.
    @PutMapping("/update/order")
    @ApiOperation(value = "Update payment", notes = "update the status of payment")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Successful or Failed", response = ApiResponse.class),
    })
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
    @GetMapping("/pending/order/{email}")
    @ApiOperation(value = "Pending Order ", notes = "List of pending order.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Pending Orders list", response = UserOrderClass.class),
    })
    @Validated
    public ResponseEntity<List<UserOrderClass>> pendingOrderList(@PathVariable @Email String email) {

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
    @ApiOperation(value = "Order History ", notes = "List of orders.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Orders list", response = UserOrderClass.class),
    })
    @Validated
    public ResponseEntity<List<UserOrderClass>> orderHistory(@PathVariable @Valid @Email String email) {

        return new ResponseEntity<>(userOrderServiceImpl.orderListByEmail(email), HttpStatus.OK);

    }

    /**
     * All my users api response.
     *
     * @param gymId the gym id
     * @return the api response
     */
//Fetching the user of the particular Gym by gymId
    @GetMapping("/my/users/{gymId}")
    @ApiOperation(value = "All users of a fitness center ", notes = "List of Users.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "User list", response = List.class),
    })
    @Validated
    public ApiResponse allMyUsers(@PathVariable @Valid @Email String gymId) {

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
    @ApiOperation(value = "All Booked fitness center ", notes = "List of Fitness center.")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = "Fitness center list", response = List.class),
    })
    @Validated
    public ResponseEntity<?> bookedGym(@PathVariable @Valid @Email String email) {

        return new ResponseEntity<>(userOrderServiceImpl.bookedGym(email), HttpStatus.OK);

    }


}
