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
	
	
	public AdminPay getDataPay(AdminPay payment)
	{
		 return adminPayRepo.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(), "Due");
		
	}
	
	
	public boolean PayNow(AdminPay payment, Order myOrder)
	{
		LocalDate date=LocalDate.now();
		LocalTime time=LocalTime.now();
	    
		AdminPay payVendor=adminPayRepo.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(), "Due");
		
		if(payVendor==null)
		{
			return false;
		}
		payVendor.setOrderId(myOrder.get("id"));
		payVendor.setStatus(myOrder.get("status"));
		payVendor.setPaymentId(null);
		payVendor.setReciept(myOrder.get("receipt"));
		payVendor.setDate(date);
		payVendor.setTime(time);
		
		adminPayRepo.save(payVendor);
		return true;
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
			for(VendorPayment pay:payments)https://nineleaps-fitness.herokuapp.com/swagger-ui/index.html#/
			{
				amount+=pay.getAmount();
			}
			
		}
		payment.setAmount(amount);
		int s=adminPayRepo.findAll().size();
		String id="P0"+ s;
		payment.setId(id);
		AdminPay oldPay=adminPayRepo.findByVendorAndAmountAndStatus(vendor, amount, "Due");
		if(oldPay!=null) {
			return oldPay;
		}
		adminPayRepo.save(payment);
		
		return payment;
	}



	public AdminPay updatePayment(Map<String, String> data) {
		 LocalDate date=LocalDate.now();
		 LocalTime time=LocalTime.now();
		 
		 AdminPay payment=adminPayRepo.findByOrderId(data.get("order_id"));
		 
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
