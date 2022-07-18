package com.fitness.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fitness.app.entity.AdminPayClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.model.AdminPayModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.app.entity.VendorPaymentClass;
import com.fitness.app.repository.AdminPayRepo;
import com.fitness.app.repository.VendorPayRepo;
import com.razorpay.Order;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class AdminService {

	
	
	@Autowired 
	private VendorPayRepo vendorPay;
    
	@Autowired
	private AdminPayRepo adminPayRepo;
	
	
	public AdminPayClass getDataPay(AdminPayModel payment)
	{
		 log.info("Payment to admin is being accessed");
		 return adminPayRepo.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(), "Due");
		
	}
	
	
	public boolean PayNow(AdminPayModel  payment, Order myOrder)
	{
		LocalDate date=LocalDate.now();
		LocalTime time=LocalTime.now();
	    
		AdminPayClass payVendor=adminPayRepo.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(), "Due");
		
		
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
	
	
	
	public AdminPayClass vendorPayment(String vendor) {
		
		List<VendorPaymentClass> payments=vendorPay.findByVendor(vendor);
		
		payments=payments.stream().filter(p->p.getStatus().equals("Due")).collect(Collectors.toList());
		
		AdminPayClass payment=new AdminPayClass();
		
		payment.setVendor(vendor);
		payment.setStatus("Due");
		int amount=0;
		if(payments!=null)
		{
			for(VendorPaymentClass pay:payments)
			{

				amount+=pay.getAmount();

			}
			
		}
		payment.setAmount(amount);
		int s=adminPayRepo.findAll().size();
		String id="P0"+ s;
		payment.setId(id);
		AdminPayClass oldPay=adminPayRepo.findByVendorAndAmountAndStatus(vendor, amount, "Due");
		if(oldPay!=null) {
			return oldPay;
		}
		adminPayRepo.save(payment);
		
		return payment;
	}



	public AdminPayClass updatePayment(Map<String, String> data) {
		 LocalDate date=LocalDate.now();
		 LocalTime time=LocalTime.now();
		 
		 AdminPayClass payment=adminPayRepo.findByOrderId(data.get("order_id"));
		 
		 payment.setPaymentId(data.get("payment_id"));
		 payment.setStatus(data.get("status"));
		 payment.setDate(date);
		 payment.setTime(time);
		 
		 List<VendorPaymentClass> vendorPaids= vendorPay.findByVendorAndStatus(payment.getVendor(), "Due");
		 
		 for(VendorPaymentClass pays:vendorPaids)
		 {
			 pays.setStatus("Paid");
			 vendorPay.save(pays);
		 }
         
		 
	 adminPayRepo.save(payment);
	 return payment;
	}



	public List<AdminPayClass> paidHistroyVendor(String vendor) throws DataNotFoundException
	{
		try {
			
			List<AdminPayClass> allPaid=adminPayRepo.findByVendor(vendor);
            if(allPaid==null)
			{
				throw new DataNotFoundException("No Payment Is there");
			}
			allPaid=allPaid.stream().filter(p->p.getStatus().equals("Completed")).collect(Collectors.toList());
			return allPaid;


		} catch (DataNotFoundException e) {

			throw new DataNotFoundException(vendor+" error: "+ e.getMessage());
		}
		
	}



}
