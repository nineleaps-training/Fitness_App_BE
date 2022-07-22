package com.fitness.app.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.app.dao.UserOrderDAO;
import com.fitness.app.entity.UserOrder;
import com.fitness.app.model.GymRepresentModel;
import com.fitness.app.model.UserOrderModel;
import com.fitness.app.model.UserPerfomanceModel;
import com.razorpay.RazorpayException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Validated
public class UserOrderController {

    @Autowired
    private UserOrderDAO userOrderService;

    @ApiOperation(value = "Check User Can Order", notes = "Checking if user can order or not")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "User can order", response = Boolean.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @GetMapping("/v1/userOrder/checkUserOrder/{email}")
    public Boolean checkUserCanOrder(@NotBlank @NotEmpty @NotNull @Email @PathVariable String email) {
        return userOrderService.canOrder(email);
    }

    /**
     * This controller is used for creating orders of the user
     * 
     * @param order - Order details of the user
     * @return - Response is okay if success or else exception is thrown
     * @throws RazorpayException
     */
    @ApiOperation(value = "Order Now", notes = "User can do the payment for the services")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Payment Done", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @PostMapping(value = "/v1/userOrder/order/now", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @Validated
    public String orderNow(@Valid @RequestBody UserOrderModel order) throws RazorpayException{

        return userOrderService.orderNow(order); 

    }

    /**
     * This controller is used to update the order
     * 
     * @param data - Updation details of the order
     * @return - Updated order details
     */
    @ApiOperation(value = "Updating Order", notes = "User can update their orders")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Order Updated", response = UserOrder.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @PutMapping(value = "/v1/userOrder/update/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserOrder updatingOrder(@RequestBody Map<String, String> data) {

        return userOrderService.updateOrder(data); // update_order after payment.
    }

    /**
     * This controller is used for checking the pending order by email id of the user
     * 
     * @param email - Email of the user
     * @return - Response is okay if success or else bad request
     */
    @ApiOperation(value = "Pending Orders", notes = "User can check pending orders")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "List Fetched", response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @GetMapping(value = "/v1/userOrder/pending/order/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> pedingOrerList(@NotBlank @NotEmpty @NotNull @Email @PathVariable String email) {
        return new ResponseEntity<>(userOrderService.pendingListOrder(email), HttpStatus.OK); // Check the pending
                                                                                              // orders of the user
    }

    /**
     * This controller is used to fetch the order history of the user from his email
     * 
     * @param email - Email of the user
     * @return - Order history of the user
     */
    @ApiOperation(value = "Order History", notes = "Users can check their order history")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Order History Fetched", response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @GetMapping(value = "/v1/userOrder/order/history/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> orderHistory(@NotBlank @NotEmpty @NotNull @Email @PathVariable String email) {
        return new ResponseEntity<>(userOrderService.orderListOrder(email), HttpStatus.OK); // Fetching the order
                                                                                            // history by email id of
                                                                                            // the user
    }

    /**
     * This controller is used for fetching all the users of a particular gym from gym id
     * 
     * @param gymId - id of the gym
     * @return - List of users of the gym
     */
    @ApiOperation(value = "All users of Gym", notes = "Vendor can view all their users")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Users Fetched"),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @GetMapping(value = "/v1/userOrder/my/users/{gymId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Set<UserPerfomanceModel> allMyUsers(@NotBlank @NotEmpty @NotNull @PathVariable String gymId) {
        return userOrderService.allMyUser(gymId); // Fetching the user of the particular Gym by gymId
    }

    /**
     * This controller is used for fetching the booked gyms by a particular user by email
     * 
     * @param email - Email id of the user
     * @return - List of all the gyms booked by the user
     */
    @ApiOperation(value = "Gyms booked", notes = "Vendor can view all of his booked gyms")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "List Fetched"),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
    @GetMapping(value = "/v1/userOrder/booked/gyms/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GymRepresentModel> bookedGym(@NotBlank @NotEmpty @NotNull @Email @PathVariable String email) {
        return userOrderService.bookedGym(email); // Fetching gyms booked by a particular user by email
    }
}
