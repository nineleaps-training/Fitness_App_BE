package com.fitness.app.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fitness.app.entity.AdminPay;
import com.fitness.app.entity.VendorPayment;
import com.fitness.app.model.AdminPayRequestModel;
import com.fitness.app.repository.AdminPayRepo;
import com.fitness.app.repository.VendorPayRepo;
import com.razorpay.Order;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    AdminPayRepo adminPayRepo;

    @Mock
    VendorPayRepo vendorPayRepo;

    AdminService adminService;

    @BeforeEach
    public void initcase() {
        adminService = new AdminService(adminPayRepo, vendorPayRepo);
    }

    @Test
    @DisplayName("Testing to fetch the details of the order")
    void testGetDataPay() {

        LocalDate localDate = LocalDate.of(2022, 6, 24);
        LocalTime localTime = LocalTime.of(12, 32, 4);
        AdminPayRequestModel adminPayRequestModel = new AdminPayRequestModel("GM6", "orderid", "vendor", 123, "Due",
                "string", "string", localDate, localTime);
        AdminPay adminPay = new AdminPay("GM6", "orderid", "vendor", 123, "Due", "string", "string", localDate,
                localTime);
        when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayRequestModel.getVendor(),
                adminPayRequestModel.getAmount(), "Due")).thenReturn(adminPay);
        AdminPay adminPayRequestModel2 = adminService.getDataPay(adminPayRequestModel);
        Assertions.assertEquals(adminPay.getVendor(), adminPayRequestModel2.getVendor());

    }

    @Test
    @DisplayName("Testing to fetch the payment history of vendor")
    void testPaidHistroyVendor() {

        LocalDate localDate = LocalDate.of(2022, 6, 24);
        LocalTime localTime = LocalTime.of(12, 32, 4);
        AdminPayRequestModel adminPayRequestModel = new AdminPayRequestModel("GM6", "orderid", "vendor", 123,
                "Completed", "string", "string", localDate, localTime);
        AdminPay adminPay = new AdminPay("GM6", "orderid", "vendor", 123, "Completed", "string", "string", localDate,
                localTime);
        List<AdminPayRequestModel> list = new ArrayList<>();
        list.add(adminPayRequestModel);
        List<AdminPay> list2 = new ArrayList<>();
        list2.add(adminPay);
        when(adminPayRepo.findByVendor(adminPayRequestModel.getVendor())).thenReturn(list2);
        List<AdminPay> list3 = adminService.paidHistroyVendor(adminPayRequestModel.getVendor());
        Assertions.assertEquals(list2, list3);
    }

    @Test
    @DisplayName("Testing for payment to vendor")
    void testPayNow() {

        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        AdminPayRequestModel adminPayRequestModel = new AdminPayRequestModel("GM6", "orderid", "vendor", 123, "Due",
                "string", "string", localDate, localTime);
        AdminPay adminPay = new AdminPay("GM6", "orderid", "vendor", 123, "Due", "string", "string", localDate,
                localTime);
        List<AdminPayRequestModel> list = new ArrayList<>();
        list.add(adminPayRequestModel);
        List<AdminPay> list2 = new ArrayList<>();
        list2.add(adminPay);
        JSONObject jsonObject = new JSONObject();
        Order order = new Order(jsonObject);
        when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayRequestModel.getVendor(),
                adminPayRequestModel.getAmount(), adminPayRequestModel.getStatus())).thenReturn(adminPay);
        AdminPay adminPayRequestModel2 = adminService.payNow(adminPayRequestModel, order);
        Assertions.assertEquals(adminPay, adminPayRequestModel2);

    }

    @Test
    @DisplayName("Testing of payment exception")
    void testPayNowwithNull() {

        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        AdminPayRequestModel adminPayRequestModel = new AdminPayRequestModel("GM6", "orderid", "vendor", 123, "Due",
                "string", "string", localDate, localTime);
        List<AdminPayRequestModel> list = new ArrayList<>();
        list.add(adminPayRequestModel);
        JSONObject jsonObject = new JSONObject();
        Order order = new Order(jsonObject);
        when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayRequestModel.getVendor(),
                adminPayRequestModel.getAmount(), adminPayRequestModel.getStatus())).thenReturn(null);
        AdminPay adminPayRequestModel2 = adminService.payNow(adminPayRequestModel, order);
        Assertions.assertEquals(null, adminPayRequestModel2);

    }

    @Test
    @DisplayName("Testing of updating the order details")
    void testUpdatePayment() {

        Map<String, String> data = new HashMap<>();
        data.put("order_id", "id");
        data.put("payment_id", "paymentId");
        data.put("status", "Due");
        VendorPayment vendorPayment = new VendorPayment("email", "vendor", 123, "Due");
        List<VendorPayment> list = new ArrayList<>();
        list.add(vendorPayment);
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        AdminPayRequestModel adminPayRequestModel = new AdminPayRequestModel("GM6", "id", "vendor", 123, "Due",
                "string", "string", localDate, localTime);
        AdminPay adminPay = new AdminPay("GM6", "id", "vendor", 123, "Due", "string", "string", localDate, localTime);
        List<AdminPayRequestModel> list1 = new ArrayList<>();
        list1.add(adminPayRequestModel);
        when(adminPayRepo.findByOrderId(data.get("order_id"))).thenReturn(adminPay);
        when(vendorPayRepo.findByVendorAndStatus(adminPayRequestModel.getVendor(), adminPayRequestModel.getStatus()))
                .thenReturn(list);
        AdminPay adminPayRequestModel2 = adminService.updatePayment(data);
        Assertions.assertEquals(adminPay, adminPayRequestModel2);

    }

    @Test
    @DisplayName("Testing of Vendor Payment")
    void testVendorPayment() {

        Map<String, String> data = new HashMap<>();
        data.put("order_id", "id");
        data.put("payment_id", "paymentId");
        data.put("status", "Due");
        VendorPayment vendorPayment = new VendorPayment("email", "vendor", 123, "Due");
        List<VendorPayment> list = new ArrayList<>();
        list.add(vendorPayment);
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        AdminPayRequestModel adminPayRequestModel = new AdminPayRequestModel("GM6", "id", "vendor", 123, "Due",
                "string", "string", localDate, localTime);
        List<AdminPayRequestModel> list1 = new ArrayList<>();
        list1.add(adminPayRequestModel);
        AdminPay adminPay = new AdminPay("GM6", "id", "vendor", 123, "Due", "string", "string", localDate, localTime);
        List<AdminPay> list2 = new ArrayList<>();
        list2.add(adminPay);
        when(vendorPayRepo.findByVendor(vendorPayment.getVendor())).thenReturn(list);
        when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayRequestModel.getVendor(),
                adminPayRequestModel.getAmount(), "Due")).thenReturn(adminPay);
        when(adminPayRepo.findAll()).thenReturn(list2);
        AdminPay adminPayRequestModel2 = adminService.vendorPayment(adminPayRequestModel.getVendor());
        Assertions.assertEquals(adminPay, adminPayRequestModel2);
    }

    @Test
    @DisplayName("Testing of vendor payment when Optional is empty")
    void testVendorPaymentwithEmpty() {

        VendorPayment vendorPayment = new VendorPayment();
        AdminPayRequestModel adminPayRequestModel = new AdminPayRequestModel();
        List<AdminPayRequestModel> list1 = new ArrayList<>();
        list1.add(adminPayRequestModel);
        AdminPay adminPay = new AdminPay();
        List<AdminPay> list2 = new ArrayList<>();
        list2.add(adminPay);
        when(vendorPayRepo.findByVendor(vendorPayment.getVendor())).thenReturn(Collections.emptyList());
        when(adminPayRepo.findByVendorAndAmountAndStatus(null, 0, "Due")).thenReturn(null);
        when(adminPayRepo.findAll()).thenReturn(list2);
        when(adminPayRepo.save(any())).thenReturn(adminPay);
        AdminPay adminPayRequestModel2 = adminService.vendorPayment(vendorPayment.getVendor());
        Assertions.assertEquals(adminPay.getVendor(), adminPayRequestModel2.getVendor());
    }

    @Test
    @DisplayName("Testing of vendor payment when filter is empty")
    void testVendorPaymentwithFilterEmpty() {

        VendorPayment vendorPayment = new VendorPayment("email", "vendor", 123, "Completed");
        List<VendorPayment> list = new ArrayList<>();
        list.add(vendorPayment);
        AdminPayRequestModel adminPayRequestModel = new AdminPayRequestModel();
        List<AdminPayRequestModel> list1 = new ArrayList<>();
        list1.add(adminPayRequestModel);
        AdminPay adminPay = new AdminPay();
        List<AdminPay> list2 = new ArrayList<>();
        list2.add(adminPay);
        when(vendorPayRepo.findByVendor(vendorPayment.getVendor())).thenReturn(list);
        when(adminPayRepo.findByVendorAndAmountAndStatus("vendor", 0, "Due")).thenReturn(null);
        when(adminPayRepo.findAll()).thenReturn(list2);
        when(adminPayRepo.save(any())).thenReturn(adminPay);
        AdminPay adminPayRequestModel2 = adminService.vendorPayment(vendorPayment.getVendor());
        Assertions.assertEquals("vendor", adminPayRequestModel2.getVendor());
    }
}
