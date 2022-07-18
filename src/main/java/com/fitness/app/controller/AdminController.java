package com.fitness.app.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.naming.AuthenticationException;
import javax.validation.Valid;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import com.fitness.app.auth.Authenticate;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.entity.AdminPay;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.AdminPayRequestModel;
import com.fitness.app.model.SignUpResponce;
import com.fitness.app.repository.AddGymRepo;
import com.fitness.app.repository.UserRepo;
import com.fitness.app.security.service.UserDetailsServiceImpl;
import com.fitness.app.service.AdminService;
import com.fitness.app.service.PagingService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AdminController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AddGymRepo gymRepo;

	@Autowired
	private AdminService adminService;

	@Autowired
	private PagingService pagingService;

	/**
	 * This controller is used for logging in the Admin
	 * 
	 * @param authCredential - Email id and Password of Admin
	 * @return - Response with Email and JWT Token
	 */
	@PostMapping(value = "/v1/login/admin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Retryable(value = AuthenticationException.class, maxAttempts = 2)
	@ResponseStatus(HttpStatus.CREATED)
	@Validated
	@ApiOperation(value = "Admin Login", notes = "Admin can login for access")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Login Sucessfull", response = SignUpResponce.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class) })
	public ResponseEntity<SignUpResponce> authenticateUser(@Valid @RequestBody Authenticate authCredential) {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authCredential.getEmail(), authCredential.getPassword()));
		final UserDetails usrDetails = userDetailsService.loadUserByUsername(authCredential.getEmail());
		final String jwt = jwtUtils.generateToken(usrDetails);
		final UserClass localUser = userRepo.findByEmail(authCredential.getEmail());
		if (localUser.getRole().equals("ADMIN")) {
			return ResponseEntity.ok(new SignUpResponce(localUser, jwt)); // Returning the response after authenticating
																			// the ADMIN
		} else {
			return ResponseEntity.ok(new SignUpResponce(null, null));
		}

	}

	/**
	 * This controller is used to fetch all the registered users in the application
	 * 
	 * @param pageNo   - Page number
	 * @param pageSize - Size of Page
	 * @return - List of registered users
	 */
	@GetMapping(value = "/v1/get-all-users/{pageNo}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get all Users", notes = "Admin can fetch all users")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of users"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	public List<UserClass> getAllUsers(@PathVariable int pageNo, @PathVariable int pageSize) {

		return pagingService.getallUsers(pageNo, pageSize); // Returning all users with pagenation.
	}

	/**
	 * This controller is used to fetch all the registered vendors in the
	 * application
	 * 
	 * @param pageNo   - Page number
	 * @param pageSize - Size of page
	 * @return - List of registered vendors
	 */
	@GetMapping(value = "/v1/get-all-vendors/{pageNo}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get all Vendors", notes = "Admin can fetch all vendors")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of vendors"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	public List<UserClass> getAllVendors(@PathVariable int pageNo, @PathVariable int pageSize) {
		return pagingService.getallVendors(pageNo, pageSize); // Returning all vendors with pagenation.
	}

	/**
	 * This controller is used to fetch all the registered gyms in the application
	 * 
	 * @param pageNo   - Page number
	 * @param pageSize - Size of page
	 * @return - List of registered gyms
	 */
	@GetMapping(value = "/v1/get-all-gyms/{pageNo}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get all Gyms", notes = "Admin can fetch all gyms")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of gyms"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	public List<GymClass> getAllGyms(@PathVariable int pageNo, @PathVariable int pageSize) {
		return pagingService.getallGyms(pageNo, pageSize); // Returning all fitness centers with pagenation
	}

	/**
	 * This controller is used to fetch all the registered fitness centers of a
	 * particular vendor
	 * 
	 * @param email - Email id of vendor
	 * @return - List of all the gyms of the vendor
	 */
	@GetMapping(value = "/v1/get-all-gyms-by-email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get Gyms By Email", notes = "Admin can fetch all gyms of a particular vendor from the his email")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of gyms of a particular vendor"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	public List<GymClass> getAllGymsByEmail(@PathVariable String email) {
		return gymRepo.findByEmail(email); // Returning list of registered fitness center by email id of vendor.
	}

	/**
	 * This controller is used to fetch the total amount that is to be paid to the
	 * vendor with details.
	 * 
	 * @param vendor - Email id of Vendor
	 * @return - Amount to be paid to the vendor with relevant details
	 */
	@GetMapping(value = "/v1/vendor-payment/{vendor}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get Payments By Email", notes = "Admin can fetch all the payments of a particular vendor from the his email")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of payments of a particular vendor", response = AdminPayRequestModel.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	public AdminPay vendorPayment(@PathVariable String vendor) {
		return adminService.vendorPayment(vendor); // Returning the total amount be paid to the vendor.
	}

	/**
	 * This controller is used to fetch the order details
	 * 
	 * @param pay - Order Details
	 * @return - Amount with details
	 */
	@GetMapping(value = "/v1/get-data-pay", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get Data Pay", notes = "Admin can fetch all details of payment of a particular vendor from his email")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of payment details of a particular vendor", response = AdminPayRequestModel.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	public AdminPay getDatapay(@RequestBody AdminPayRequestModel pay) {
		return adminService.getDataPay(pay); // Returning the amount with details
	}

	/**
	 * This controller is used to make payments to the vendor
	 * 
	 * @param payment - Order Details
	 * @return - Amount with details
	 * @throws RazorpayException
	 */
	@ApiOperation(value = "Paying Vendor", notes = "Admin can pay a particular vendor")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Payment done", response = String.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@PutMapping(value = "/v1/pay-vendor-now", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Validated
	@ResponseBody
	@Retryable(value = RazorpayException.class, maxAttempts = 2)
	public AdminPay payNow(@Valid @RequestBody AdminPayRequestModel payment) throws RazorpayException {
		RazorpayClient razorpayClient = new RazorpayClient(System.getenv("RAZORPAY_KEY"),
				System.getenv("RAZORPAY_SECRET"));

		JSONObject ob = new JSONObject();
		ob.put("amount", payment.getAmount() * 100);
		ob.put("currency", "INR");
		ob.put("receipt", "txn_201456");

		Order myOrder = razorpayClient.Orders.create(ob); // Initiating payment to the vendor

		return adminService.payNow(payment, myOrder); // Retuning order details
	}

	/**
	 * This controller is used to update the status of payment by Admin
	 * 
	 * @param data - Relevant details of the order
	 * @return - Updated Order status
	 */
	@ApiOperation(value = "Update the payment order", notes = "Admin can update the payment order")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Order Updated", response = AdminPayRequestModel.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@PutMapping(value = "/v1/update-vendor-payment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public AdminPay updatingOrder(@RequestBody Map<String, String> data) {

		return adminService.updatePayment(data); // Updating status of payment made to the vendor by Admin.
	}

	/**
	 * This controller is used to fetch the payment history of a particular vendor
	 * 
	 * @param vendor - Email id of the vendor
	 * @return - List of order history of the vendor
	 */
	@ApiOperation(value = "Get Payment History By Email", notes = "Admin can fetch all payment history of a particular vendor from the his email")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of payment history of a particular vendor"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@GetMapping(value = "/v1/paid-history/{vendor}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<AdminPay> paidHistroy(@PathVariable String vendor) {
		return adminService.paidHistroyVendor(vendor); // Finding payment history of the vendor.
	}

	/**
	 * This controller is used to fetch the total number of registered vendors,
	 * enthusiasts and fitness centers.
	 * 
	 * @return - Response with details of the total number of vendors, enthusiasts
	 *         and fitness centers.
	 */
	@ApiOperation(value = "Get Statistics", notes = "Admin can fetch total number of gyms, vendors and users registered in the application")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of total number of gyms, vendors and users"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	@GetMapping(value = "/v1/all-numbers", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> getAllNumber() {
		List<UserClass> l = userRepo.findAll(); // Fetching all the registered users

		int u = l.stream().filter(e -> e.getRole().equals("USER")).collect(Collectors.toList()).size(); // Filtering users
		
		int v = l.stream().filter(e -> e.getRole().equals("VENDOR")).collect(Collectors.toList()).size(); // Filtering vendors																									

		List<GymClass> gyms = gymRepo.findAll(); // Fetching all the registered gyms

		int g = gyms.size();

		List<String> nums = new ArrayList<>();
		nums.add(Integer.toString(u));
		nums.add(Integer.toString(v));
		nums.add(Integer.toString(g));

		return new ResponseEntity<>(nums, HttpStatus.OK);
	}

}