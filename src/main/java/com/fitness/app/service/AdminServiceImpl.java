package com.fitness.app.service;

import com.fitness.app.entity.AdminPayClass;
import com.fitness.app.entity.VendorPaymentClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.model.AdminPayModel;
import com.fitness.app.repository.AdminPayRepository;
import com.fitness.app.repository.VendorPayRepository;
import com.razorpay.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    final private VendorPayRepository vendorPay;

    final private AdminPayRepository adminPayRepository;

    @Override
    public AdminPayClass getDataPay(AdminPayModel payment) {
        log.info("Payment to admin is being accessed");
        return adminPayRepository.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(), "Due");

    }

    @Override
    public boolean PayNow(AdminPayModel payment, Order myOrder) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        AdminPayClass payVendor = adminPayRepository.findByVendorAndAmountAndStatus(payment.getVendor(), payment.getAmount(), "Due");
        if (payVendor == null) {
            return false;
        }
        payVendor.setOrderId(myOrder.get("id"));
        payVendor.setStatus(myOrder.get("status"));
        payVendor.setPaymentId(null);
        payVendor.setReciept(myOrder.get("receipt"));
        payVendor.setDate(date);
        payVendor.setTime(time);
        adminPayRepository.save(payVendor);
        return true;
    }

    @Override
    public AdminPayClass vendorPayment(String vendor) {
        List<VendorPaymentClass> payments = vendorPay.findByVendor(vendor);
        payments = payments.stream().filter(p -> p.getStatus().equals("Due")).collect(Collectors.toList());
        AdminPayClass payment = new AdminPayClass();
        payment.setVendor(vendor);
        payment.setStatus("Due");
        int amount = 0;
        if (payments != null) {
            for (VendorPaymentClass pay : payments) {
                amount += pay.getAmount();
            }
        }
        payment.setAmount(amount);
        int s = adminPayRepository.findAll().size();
        String id = "P0" + s;
        payment.setId(id);
        AdminPayClass oldPay = adminPayRepository.findByVendorAndAmountAndStatus(vendor, amount, "Due");
        if (oldPay != null) {
            return oldPay;
        }
        adminPayRepository.save(payment);
        return payment;
    }

    @Override
    public AdminPayClass updatePayment(Map<String, String> data) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        AdminPayClass payment = adminPayRepository.findByOrderId(data.get("order_id"));
        payment.setPaymentId(data.get("payment_id"));
        payment.setStatus(data.get("status"));
        payment.setDate(date);
        payment.setTime(time);
        List<VendorPaymentClass> vendorPaids = vendorPay.findByVendorAndStatus(payment.getVendor(), "Due");
        for (VendorPaymentClass pays : vendorPaids) {
            pays.setStatus("Paid");
            vendorPay.save(pays);
        }
        adminPayRepository.save(payment);
        return payment;
    }

    @Override
    public List<AdminPayClass> paidHistoryVendor(String vendor) throws DataNotFoundException {
        try {
            List<AdminPayClass> allPaid = adminPayRepository.findByVendor(vendor);
            if (allPaid == null) {
                throw new DataNotFoundException("No Payment Is there");
            }
            allPaid = allPaid.stream().filter(p -> p.getStatus().equals("Completed")).collect(Collectors.toList());
            return allPaid;
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException(vendor + " error: " + e.getMessage());
        }
    }

}
