package com.fitness.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.config.JwtUtils;
import com.fitness.app.dao.AdminDAO;
import com.fitness.app.entity.AdminPay;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorPayment;
import com.fitness.app.model.AdminPayRequestModel;
import com.fitness.app.model.SignUpResponceModel;
import com.fitness.app.repository.AddGymRepo;
import com.fitness.app.repository.AdminPayRepo;
import com.fitness.app.repository.UserRepo;
import com.fitness.app.repository.VendorPayRepo;
import com.fitness.app.security.service.UserDetailsServiceImpl;
import com.razorpay.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminService implements AdminDAO {

	
	private UserRepo userRepo;
	
	
	private VendorPayRepo vendorPay;

	
	private AdminPayRepo adminPayRepo;

	
	private AuthenticationManager authenticationManager;

	
	private UserDetailsServiceImpl userDetailsService;

	
	private AddGymRepo gymRepo;

	
	private JwtUtils jwtUtils;

	@Autowired
	public AdminService(AdminPayRepo adminPayRepo2, VendorPayRepo vendorPayRepo, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsServiceImpl, JwtUtils jwtUtils, UserRepo userRepo, AddGymRepo gymRepo) {
		// Initializing constructor
		this.adminPayRepo = adminPayRepo2;
		this.vendorPay = vendorPayRepo;
		this.authenticationManager=authenticationManager;
		this.userDetailsService=userDetailsServiceImpl;
		this.jwtUtils=jwtUtils;
		this.userRepo=userRepo;
		this.gymRepo=gymRepo;
	}

	/**
	 * This function is used for fetching details of payment of the vendor
	 * 
	 * @param payment - Details of the vendor
	 * @return - Details of the payment
	 */
	public AdminPay getDataPay(AdminPayRequestModel payment) {

		log.info("AdminService >> getDataPay >> Initiated");

		return adminPayRepo.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(), "Due"); // Getting details of payment of vendor
	}

	/**
	 * This controller is used to fetch all the registered fitness centers of a
	 * particular vendor
	 * 
	 * @param email - Email id of vendor
	 * @return - List of all the gyms of the vendor
	 */
	public List<GymClass> getAllGymsByEmail(String email) {
		return gymRepo.findByEmail(email); // Returning list of registered fitness center by email id of vendor.
	}

	public ResponseEntity<SignUpResponceModel> loginAdmin(@Valid @RequestBody Authenticate authCredential)
	{
		log.info("AdminService >> loginAdmin >> Initiated");
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authCredential.getEmail(), authCredential.getPassword()));
		final UserDetails usrDetails = userDetailsService.loadUserByUsername(authCredential.getEmail());
		final String jwt = jwtUtils.generateToken(usrDetails);
		final UserClass localUser = userRepo.findByEmail(authCredential.getEmail());
		if (localUser.getRole().equals("ADMIN")) {
			return ResponseEntity.ok(new SignUpResponceModel(localUser, jwt)); // Returning the response after authenticating
																			// the ADMIN
		} else {
			log.warn("AdminService >> loginAdmin >> User is not an admin");
			return ResponseEntity.ok(new SignUpResponceModel(null, null));
		}
	}

	/**
	 * This function is used to pay to the vendor
	 * 
	 * @param payment - Details of the vendor
	 * @param myOrder - Details of the order
	 * @return - Details of the order
	 */
	public AdminPay payNow(AdminPayRequestModel payment, Order myOrder) {

		log.info("AdminService >> payNow >> Initiated");
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();

		AdminPay payVendor = adminPayRepo.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(),
				"Due");

		if (payVendor == null) {
			log.error("AdminService >> payNow >> No payments are due");
			return payVendor;
		}
		payVendor.setOrderId(myOrder.get("id"));
		payVendor.setStatus(myOrder.get("status"));
		payVendor.setPaymentId(null);
		payVendor.setReciept(myOrder.get("receipt"));
		payVendor.setDate(date);
		payVendor.setTime(time);
		adminPayRepo.save(payVendor); // Paying to Vendor
		log.info("AdminService >> payNow >> Details saved to database");
		return payVendor;
	}

	/**
	 * This function is used to create the order for payment to the vendor
	 * 
	 * @param vendor - Email id of the vendor
	 * @return - Details of the order
	 */
	public AdminPay vendorPayment(String vendor) {

		log.info("AdminService >> vendorPayment >> Initiated");
		List<VendorPayment> payments = vendorPay.findByVendor(vendor);
		int amount = 0;
		if (!payments.isEmpty()) {
			payments = payments.stream().filter(p -> p.getStatus().equals("Due")).collect(Collectors.toList());
			if (!payments.isEmpty()) {
				for (VendorPayment pay : payments) {
					amount += pay.getAmount();
				}
			}
		}

		AdminPay payment = new AdminPay();
		payment.setVendor(vendor);
		payment.setStatus("Due");
		payment.setAmount(amount); // Creating Vendor Payment

		int s = adminPayRepo.findAll().size();
		String id = "P0" + s;
		payment.setId(id);
		AdminPay oldPay = adminPayRepo.findByVendorAndAmountAndStatus(vendor, amount, "Due");
		if (oldPay != null) {
			return oldPay;
		}
		adminPayRepo.save(payment);
		return payment;
	}

	/**
	 * This function is used to update the details of the order
	 * 
	 * @param data - Relevant details to be updated
	 * @return - Details of the order
	 */
	public AdminPay updatePayment(Map<String, String> data) {

		log.info("AdminService >> updatePayment >> Initiated");
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();

		AdminPay payment = adminPayRepo.findByOrderId(data.get("order_id"));

		payment.setPaymentId(data.get("payment_id"));
		payment.setStatus(data.get("status"));
		payment.setDate(date);
		payment.setTime(time); // Updating the Payment Details

		List<VendorPayment> vendorPaids = vendorPay.findByVendorAndStatus(payment.getVendor(), "Due");

		for (VendorPayment pays : vendorPaids) {
			pays.setStatus("Paid");
			vendorPay.save(pays);
		}

		adminPayRepo.save(payment);
		return payment;
	}

	/**
	 * This function is used to fetch the order history of the vendor
	 * 
	 * @param vendor - Email id of the vendor
	 * @return - List of details of the order
	 */
	public List<AdminPay> paidHistroyVendor(String vendor) {
		log.info("AdminService >> paidHistroyVendor >> Initiated");
		List<AdminPay> allPaid = adminPayRepo.findByVendor(vendor);
		allPaid = allPaid.stream().filter(p -> p.getStatus().equals("Completed")).collect(Collectors.toList()); // Fetching Vendor History Payments
		return allPaid;
	}

	public ResponseEntity<Object> getAllNumber(){

		log.info("AdminService >> getAllNumber >> Initiated");

		List<UserClass> l = userRepo.findAll(); // Fetching all the registered users

		int u = l.stream().filter(e -> e.getRole().equals("USER")).collect(Collectors.toList()).size(); // Filtering users
		
		int v = l.stream().filter(e -> e.getRole().equals("VENDOR")).collect(Collectors.toList()).size(); // Filtering vendors																									

		List<GymClass> gyms = gymRepo.findAll(); // Fetching all the registered gyms

		int g = gyms.size();

		List<String> nums = new ArrayList<>();
		nums.add(Integer.toString(u));
		nums.add(Integer.toString(v));
		nums.add(Integer.toString(g));

		log.info("AdminService >> getAllNumber >> Terminated");
		return new ResponseEntity<>(nums, HttpStatus.OK);
	}
}
