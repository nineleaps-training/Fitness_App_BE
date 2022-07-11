package com.fitness.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	
	
	public AdminService(AdminPayRepo adminPayRepo2, VendorPayRepo vendorPayRepo) {

		this.adminPayRepo=adminPayRepo2;
		this.vendorPay=vendorPayRepo;
    }


    public AdminPayRequestModel getDataPay(AdminPayRequestModel payment)
	{
		 return adminPayRepo.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(), "Due");
		
	}
	
	
	public AdminPayRequestModel payNow(AdminPayRequestModel payment, Order myOrder)
	{
		LocalDate date=LocalDate.now();
		LocalTime time=LocalTime.now();
	    
		AdminPayRequestModel payVendor=adminPayRepo.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(), "Due");
		
		
		if(payVendor==null)
		{
			return payVendor;
		}
		payVendor.setOrderId(myOrder.get("id"));
		payVendor.setStatus(myOrder.get("status"));
		payVendor.setPaymentId(null);
		payVendor.setReciept(myOrder.get("receipt"));
		payVendor.setDate(date);
		payVendor.setTime(time);
		
		adminPayRepo.save(payVendor);
		return payVendor;
		}
	
	
	
	public AdminPayRequestModel vendorPayment(String vendor) {
		
		List<VendorPayment> payments=vendorPay.findByVendor(vendor);
		int amount=0;
		if(payments!=null)
		{
			payments=payments.stream().filter(p->p.getStatus().equals("Due")).collect(Collectors.toList());
			for(VendorPayment pay:payments)
			{

				amount+=pay.getAmount();

			}
			
		}
		
		AdminPayRequestModel payment=new AdminPayRequestModel();
		payment.setVendor(vendor);
		payment.setStatus("Due");
		payment.setAmount(amount);

		int s=adminPayRepo.findAll().size();
		String id="P0"+ s;
		payment.setId(id);
		AdminPayRequestModel oldPay=adminPayRepo.findByVendorAndAmountAndStatus(vendor, amount, "Due");
		if(oldPay!=null) {
			return oldPay;
		}
		adminPayRepo.save(payment);
		
		return payment;
	}



	public AdminPayRequestModel updatePayment(Map<String, String> data) {
		 LocalDate date=LocalDate.now();
		 LocalTime time=LocalTime.now();
		 
		 AdminPayRequestModel payment=adminPayRepo.findByOrderId(data.get("order_id"));
		 
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
         
		 
		adminPayRepo.save(payment);
		return payment;
	}



	public List<AdminPayRequestModel> paidHistroyVendor(String vendor)
	{
		List<AdminPayRequestModel> allPaid=adminPayRepo.findByVendor(vendor);
		allPaid=allPaid.stream().filter(p->p.getStatus().equals("Completed")).collect(Collectors.toList());
		return allPaid;	
	}
}
