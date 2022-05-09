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
import com.fitness.app.repository.AdminPayRepo;
import com.fitness.app.repository.VendorPayRepo;
import com.razorpay.Order;

@Service
public class AdminService {

	
	
	@Autowired 
	private VendorPayRepo vendorPay;
    
	@Autowired
	private AdminPayRepo adminPayRepo;
	
	
	
	public void PayNow(AdminPay payment, Order myOrder)
	{
		LocalDate date=LocalDate.now();
		LocalTime time=LocalTime.now();
		
		AdminPay payVendor=adminPayRepo.findByVendorAndStatus(payment.getVendor(), "Due");
		
		payVendor.setOrderId(myOrder.get("id"));
		payVendor.setVendor(payment.getVendor());
		payVendor.setAmount(payment.getAmount());
		payVendor.setStatus(myOrder.get("status"));
		payVendor.setPaymentId(null);
		payVendor.setReciept(myOrder.get("receipt"));
		payVendor.setDate(date);
		payVendor.setTime(time);
		
		adminPayRepo.save(payVendor);

		
		
	
		
	}
	
	
	
	public AdminPay vendorPayment(String vendor) {
		
		List<VendorPayment> payments=vendorPay.findByVendor(vendor);
		payments=payments.stream().filter(p->p.getStatus().equals("Due")).collect(Collectors.toList());
		
		AdminPay payment=new AdminPay();
		payment.setVendor(vendor);
		payment.setStatus("Due");
		int amount=0;
		if(payments!=null)
		{
			for(VendorPayment pay:payments)
			{
				amount+=pay.getAmount();
			}
			
		}
		payment.setAmount(amount);
		AdminPay oldPay=adminPayRepo.findByVendorAndStatus(vendor, "Due");
		if(oldPay!=null && oldPay.getAmount()==amount) {
			return oldPay;
		}
		adminPayRepo.save(payment);
		
		return adminPayRepo.findByVendorAndStatus(vendor, "Due");
	}



	public AdminPay updatePayment(Map<String, String> data) {
		 LocalDate date=LocalDate.now();
		 LocalTime time=LocalTime.now();
		 
		 AdminPay payment=adminPayRepo.findById(data.get("order_id")).get();
		 
		 payment.setPaymentId(data.get("payment_id"));
		 payment.setStatus(data.get("status"));
		 payment.setDate(date);
		 payment.setTime(time);
		 
		 List<VendorPayment> vendorPaids= vendorPay.findByVendorAndStatus(payment.getVendor(), "Due");
		 
		 for(VendorPayment pays:vendorPaids)
		 {
			 pays.setStatus("Paid");
			 vendorPay.save(pays);
		 }
         
		 
		return  adminPayRepo.save(payment);
	}



	public List<AdminPay> paidHistroyVendor(String vendor) throws Exception 
	{
		try {
			
			List<AdminPay> allPaid=adminPayRepo.findByVendor(vendor);
			allPaid=allPaid.stream().filter(p->p.getStatus().equals("Paid")).collect(Collectors.toList());
			return allPaid;
		} catch (Exception e) {
			throw new Exception(e.getMessage()+ vendor);
		}
		
	}



}
