package com.fitness.app.dao;

import com.fitness.app.auth.Authenticate;
import com.fitness.app.entity.AdminPay;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.model.AdminPayModel;
import com.fitness.app.model.SignUpResponse;
import com.razorpay.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface AdminDao {

    ResponseEntity<SignUpResponse> authenticateUser(Authenticate authCredential);
    List<UserClass> getAllUsers(int pageNo, int pageSize);
    List<UserClass> getAllVendors(int pageNo, int pageSize);
    List<GymClass> getAllGyms(int pageNo, int pageSize);
    List<GymClass> getAllGymsByEmail(@PathVariable String email);
    AdminPay getDataPay(AdminPayModel payment);
    boolean payNow(AdminPayModel payment, Order myOrder);
    AdminPay vendorPayment(String vendor);
    AdminPay updatePayment(Map<String, String> data);
    List<AdminPay> paidHistoryVendor(String vendor);
    ResponseEntity<List<String>> getAllNumber();

}
