package com.fitness.app.service;

import com.fitness.app.entity.AdminPayClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.model.AdminPayModel;
import com.razorpay.Order;

import java.util.List;
import java.util.Map;

public interface AdminService {

    AdminPayClass getDataPay(AdminPayModel payModel);

    boolean PayNow(AdminPayModel payModel, Order order);

    AdminPayClass vendorPayment(String vendor);

    AdminPayClass updatePayment(Map<String, String> data);

    List<AdminPayClass> paidHistoryVendor(String vendor) throws DataNotFoundException;


}
