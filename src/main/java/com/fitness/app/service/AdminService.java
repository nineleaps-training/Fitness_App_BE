package com.fitness.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fitness.app.entity.AdminPay;
import com.fitness.app.model.AdminPayModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public AdminPay getDataPay(AdminPayModel payment) {
        return adminPayRepo.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(), "Due");

    }

    public boolean payNow(AdminPayModel payment, Order myOrder) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        AdminPay payVendor = adminPayRepo.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(), "Due");

        if (payVendor == null) {
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

        List<VendorPayment> payments = vendorPay.findByVendor(vendor);

        payments = payments.stream().filter(p -> p.getStatus().equals("Due")).collect(Collectors.toList());

        AdminPay payment = new AdminPay();

        payment.setVendor(vendor);
        payment.setStatus("Due");
        int amount = 0;
        for (VendorPayment pay : payments) {

            amount += pay.getAmount();

        }

        payment.setAmount(amount);
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


    public AdminPay updatePayment(Map<String, String> data) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        AdminPay payment = adminPayRepo.findByOrderId(data.get("order_id"));

        payment.setPaymentId(data.get("payment_id"));
        payment.setStatus(data.get("status"));
        payment.setDate(date);
        payment.setTime(time);

        List<VendorPayment> vendorPaid = vendorPay.findByVendorAndStatus(payment.getVendor(), "Due");

        for (VendorPayment pays : vendorPaid) {
            pays.setStatus("Paid");
            vendorPay.save(pays);
        }


        adminPayRepo.save(payment);
        return payment;
    }


    public List<AdminPay> paidHistoryVendor(String vendor) {

        List<AdminPay> allPaid = adminPayRepo.findByVendor(vendor);
        return allPaid.stream().filter(p -> p.getStatus().equals("Completed")).collect(Collectors.toList());

    }


}
