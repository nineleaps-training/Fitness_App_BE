package com.fitness.app.dao;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.entity.AdminPay;
import com.fitness.app.model.AdminPayRequestModel;
import com.fitness.app.model.SignUpResponceModel;
import com.razorpay.Order;

@Component
public interface AdminDAO{

    public AdminPay getDataPay(AdminPayRequestModel payment);
    
    public AdminPay payNow(AdminPayRequestModel payment, Order myOrder);

    public AdminPay vendorPayment(String vendor);

    public AdminPay updatePayment(Map<String, String> data);

    public List<AdminPay> paidHistroyVendor(String vendor);

    public ResponseEntity<SignUpResponceModel> loginAdmin(@Valid @RequestBody Authenticate authCredential);

    public ResponseEntity<Object> getAllNumber();
}