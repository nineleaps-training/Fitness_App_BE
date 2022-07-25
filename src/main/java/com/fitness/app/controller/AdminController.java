package com.fitness.app.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import java.time.Duration;
import java.util.List;
import java.util.Map;
import javax.naming.AuthenticationException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import com.fitness.app.auth.Authenticate;
import com.fitness.app.dao.AdminDAO;
import com.fitness.app.dao.PagingDAO;
import com.fitness.app.entity.AdminPay;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.exception.ExceededNumberOfAttemptsException;
import com.fitness.app.model.AdminPayRequestModel;
import com.fitness.app.model.SignUpResponceModel;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "http://localhost:3000")
@Validated
@RestController
public class AdminController {

	@Autowired
	private AdminDAO adminDAO;

	@Autowired
	private PagingDAO pagingDAO;

	private final Bucket bucket;

	public AdminController() {
		this.bucket = Bucket4j.builder()
				.addLimit(Bandwidth.classic(3, Refill.intervally(3, Duration.ofHours(24))))
				.build();
	}

	/**
	 * This controller is used for logging in the Admin
	 * 
	 * @param authCredential - Email id and Password of Admin
	 * @return - Response with Email and JWT Token
	 * @throws ExceededNumberOfAttemptsException
	 */
	@PostMapping(value = "/v1/admin/login/admin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Validated
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Admin Login", notes = "Admin can login for access")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Login Sucessfull", response = SignUpResponceModel.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class) })
	public ResponseEntity<SignUpResponceModel> authenticateUser(@Valid @RequestBody Authenticate authCredential)
			throws ExceededNumberOfAttemptsException {
		if (bucket.tryConsume(1)) {
			return adminDAO.loginAdmin(authCredential);
		} else {
			throw new ExceededNumberOfAttemptsException("Account Locked: Please try after 24 hours");
		}

	}

	/**
	 * This controller is used to fetch all the registered users in the application
	 * 
	 * @param pageNo   - Page number
	 * @param pageSize - Size of Page
	 * @return - List of registered users
	 */
	@GetMapping(value = "/v1/admin/getAllUsers/{pageNo}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get all Users", notes = "Admin can fetch all users")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of users"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	public List<UserClass> getAllUsers(
			@NotNull @Min(value = 1L, message = "Page number should be mininum 1") @Max(value = 1000L, message = "Page number can be maximum 999") @PathVariable int pageNo,
			@NotNull @Min(value = 1L, message = "Page size should be mininum 1") @Max(value = 50L, message = "Page size can be maximum 49") @NotNull @PathVariable int pageSize) {

		return pagingDAO.getallUsers(pageNo, pageSize); // Returning all users with pagenation.
	}

	/**
	 * This controller is used to fetch all the registered vendors in the
	 * application
	 * 
	 * @param pageNo   - Page number
	 * @param pageSize - Size of page
	 * @return - List of registered vendors
	 */
	@GetMapping(value = "/v1/admin/getAllVendors/{pageNo}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get all Vendors", notes = "Admin can fetch all vendors")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of vendors"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	public List<UserClass> getAllVendors(
			@NotNull @Min(value = 1L, message = "Page number should be mininum 1") @Max(value = 1000L, message = "Page number can be maximum 999") @PathVariable int pageNo,
			@NotNull @Min(value = 1L, message = "Page size should be mininum 1") @Max(value = 20L, message = "Page size can be maximum 19") @PathVariable int pageSize) {
		return pagingDAO.getallVendors(pageNo, pageSize); // Returning all vendors with pagenation.
	}

	/**
	 * This controller is used to fetch all the registered gyms in the application
	 * 
	 * @param pageNo   - Page number
	 * @param pageSize - Size of page
	 * @return - List of registered gyms
	 */
	@GetMapping(value = "/v1/admin/getAllGyms/{pageNo}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get all Gyms", notes = "Admin can fetch all gyms")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of gyms"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	public List<GymClass> getAllGyms(
			@NotNull @Min(value = 1L, message = "Page number should be mininum 1") @Max(value = 1000L, message = "Page number can be maximum 999") @PathVariable int pageNo,
			@NotNull @Min(value = 1L, message = "Page size should be mininum 1") @Max(value = 20L, message = "Page size can be maximum 19") @PathVariable int pageSize) {
		return pagingDAO.getallGyms(pageNo, pageSize); // Returning all fitness centers with pagenation
	}

	/**
	 * This controller is used to fetch all the registered fitness centers of a
	 * particular vendor
	 * 
	 * @param email - Email id of vendor
	 * @return - List of all the gyms of the vendor
	 */
	@GetMapping(value = "/v1/admin/getAllGymsByEmail/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get Gyms By Email", notes = "Admin can fetch all gyms of a particular vendor from the his email")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of gyms of a particular vendor"),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	public List<GymClass> getAllGymsByEmail(@NotNull @NotEmpty @NotBlank @Email @PathVariable String email) {
		return adminDAO.getAllGymsByEmail(email); // Returning list of registered fitness center by email id of
													// vendor.
	}

	/**
	 * This controller is used to fetch the total amount that is to be paid to the
	 * vendor with details.
	 * 
	 * @param vendor - Email id of Vendor
	 * @return - Amount to be paid to the vendor with relevant details
	 */
	@GetMapping(value = "/v1/admin/vendorPayment/{vendor}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get Payments By Email", notes = "Admin can fetch all the payments of a particular vendor from the his email")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of payments of a particular vendor", response = AdminPayRequestModel.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	public AdminPay vendorPayment(@NotBlank @NotNull @NotEmpty @PathVariable String vendor) {
		return adminDAO.vendorPayment(vendor); // Returning the total amount be paid to the vendor.
	}

	/**
	 * This controller is used to fetch the order details
	 * 
	 * @param pay - Order Details
	 * @return - Amount with details
	 */
	@GetMapping(value = "/v1/admin/getDataPay", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get Data Pay", notes = "Admin can fetch all details of payment of a particular vendor from his email")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of payment details of a particular vendor", response = AdminPayRequestModel.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = AuthenticationException.class) })
	public AdminPay getDatapay(@RequestBody AdminPayRequestModel pay) {
		return adminDAO.getDataPay(pay); // Returning the amount with details
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
	@PutMapping(value = "/v1/admin/payVendorNow", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public AdminPay payNow(@Valid @RequestBody AdminPayRequestModel payment) throws RazorpayException {
		RazorpayClient razorpayClient = new RazorpayClient(System.getenv("RAZORPAY_KEY"),
				System.getenv("RAZORPAY_SECRET"));

		JSONObject ob = new JSONObject();
		ob.put("amount", payment.getAmount() * 100);
		ob.put("currency", "INR");
		ob.put("receipt", "txn_201456");

		Order myOrder = razorpayClient.Orders.create(ob); // Initiating payment to the vendor

		return adminDAO.payNow(payment, myOrder); // Retuning order details
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
	@PutMapping(value = "/v1/admin/updateVendorPayment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public AdminPay updatingOrder(@RequestBody Map<String, String> data) {

		return adminDAO.updatePayment(data); // Updating status of payment made to the vendor by Admin.
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
	@GetMapping(value = "/v1/admin/paidHistory/{vendor}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<AdminPay> paidHistroy(@NotBlank @NotEmpty @NotNull @PathVariable String vendor) {
		return adminDAO.paidHistroyVendor(vendor); // Finding payment history of the vendor.
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
	@GetMapping(value = "/v1/admin/allNumbers", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> getAllNumber() {

		return adminDAO.getAllNumber();
	}

}