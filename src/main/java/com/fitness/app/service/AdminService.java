package com.fitness.app.service;

import com.fitness.app.entity.AdminPayClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.model.AdminPayModel;
import com.razorpay.Order;

import java.util.List;
import java.util.Map;

public interface AdminService {

    public AdminPayClass getDataPay(AdminPayModel payModel);

    public boolean PayNow(AdminPayModel payModel, Order order);

    public AdminPayClass vendorPayment(String vendor);

    public AdminPayClass updatePayment(Map<String, String> data);

    public List<AdminPayClass> paidHistoryVendor(String vendor) throws DataNotFoundException;


}
