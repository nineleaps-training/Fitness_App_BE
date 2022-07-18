package com.fitness.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.app.entity.AdminPay;
import com.fitness.app.entity.VendorPayment;
import com.fitness.app.model.AdminPayRequestModel;
import com.fitness.app.repository.AdminPayRepo;
import com.fitness.app.repository.VendorPayRepo;
import com.razorpay.Order;

@Service
public class AdminService {

	@Autowired
	private VendorPayRepo vendorPay;

	@Autowired
	private AdminPayRepo adminPayRepo;

	/**
	 * This constructor is used to initialize the repositories
	 * 
	 * @param adminPayRepo2 - Admin Pay Repository
	 * @param vendorPayRepo - Vendor Pay Repository
	 */
	public AdminService(AdminPayRepo adminPayRepo2, VendorPayRepo vendorPayRepo) {
		// Initializing constructor
		this.adminPayRepo = adminPayRepo2;
		this.vendorPay = vendorPayRepo;
	}

	/**
	 * This function is used for fetching details of payment of the vendor
	 * 
	 * @param payment - Details of the vendor
	 * @return - Details of the payment
	 */
	public AdminPay getDataPay(AdminPayRequestModel payment) {
		return adminPayRepo.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(), "Due"); // Getting details of payment of vendor
	}

	/**
	 * This function is used to pay to the vendor
	 * 
	 * @param payment - Details of the vendor
	 * @param myOrder - Details of the order
	 * @return - Details of the order
	 */
	public AdminPay payNow(AdminPayRequestModel payment, Order myOrder) {
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();

		AdminPay payVendor = adminPayRepo.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(),
				"Due");

		if (payVendor == null) {
			return payVendor;
		}
		payVendor.setOrderId(myOrder.get("id"));
		payVendor.setStatus(myOrder.get("status"));
		payVendor.setPaymentId(null);
		payVendor.setReciept(myOrder.get("receipt"));
		payVendor.setDate(date);
		payVendor.setTime(time);

		adminPayRepo.save(payVendor); // Paying to Vendor
		return payVendor;
	}

	/**
	 * This function is used to create the order for payment to the vendor
	 * 
	 * @param vendor - Email id of the vendor
	 * @return - Details of the order
	 */
	public AdminPay vendorPayment(String vendor) {

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
		List<AdminPay> allPaid = adminPayRepo.findByVendor(vendor);
		allPaid = allPaid.stream().filter(p -> p.getStatus().equals("Completed")).collect(Collectors.toList()); // Fetching Vendor History Payments
		return allPaid;
	}
}
