package com.fitness.app.service;

import com.fitness.app.entity.AdminPay;
import com.fitness.app.entity.VendorPayment;
import com.fitness.app.model.AdminPayModel;
import com.fitness.app.repository.AdminPayRepo;
import com.fitness.app.repository.VendorPayRepo;
import com.razorpay.Order;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdminServiceTest {

    Order order;
    HashMap<String, String> data;

    @MockBean
    AdminPayRepo adminPayRepo;

    @MockBean
    VendorPayRepo vendorPay;

    @Autowired
    AdminService adminService;

    @BeforeEach
    public void setUp() {

        JSONObject jsonObject = new JSONObject();
        order = new Order(jsonObject.put("abcd", 1234));

        data = new HashMap<>();
        data.put("order_id", "12345");
        data.put("payment_id", "54321");
        data.put("status", "Completed");

    }

    @Test
    void getDataPay() {
        AdminPayModel adminPayModel = new AdminPayModel("1", "123456", "Priyanshi", 100,
                "Completed", "54321", "XYZ", LocalDate.now(), LocalTime.now());

        AdminPay adminPay = new AdminPay("1", "123456", "Priyanshi", 100,
                "Completed", "54321", "XYZ", LocalDate.now(), LocalTime.now());

        when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayModel.getVendor(), adminPayModel.getAmount(), "Due")).thenReturn(adminPay);

        AdminPay dataPay = adminService.getDataPay(adminPayModel);
        assertEquals(adminPay, dataPay);

    }

    @Test
    void payNow() {
        AdminPayModel adminPayModel = new AdminPayModel("1", "123456", "Priyanshi", 100,
                "Completed", "54321", "XYZ", LocalDate.now(), LocalTime.now());

        AdminPay adminPay = new AdminPay("1", "123456", "Priyanshi", 100,
                "Completed", "54321", "XYZ", LocalDate.now(), LocalTime.now());

        when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayModel.getVendor(), adminPayModel.getAmount(), "Due")).thenReturn(adminPay);

        boolean condition = adminService.payNow(adminPayModel, order);
        assertTrue(condition);
    }

    @Test
    void returnFalseIfAdminPayModelIsNull() {
        AdminPayModel adminPayModel = new AdminPayModel();

        boolean condition = adminService.payNow(adminPayModel, order);
        assertFalse(condition);
    }

    @Test
    void vendorPayment() {

        AdminPay adminPay = new AdminPay("P00", "123456", "Priyanshi", 100,
                "Due", "54321", "XYZ", LocalDate.now(), LocalTime.now());

        VendorPayment vendorPayment = new VendorPayment("Priyanshi", "Geet", "Fitness",
                0, "Due", LocalDate.now(), LocalTime.now());

        List<VendorPayment> vendorPayments = new ArrayList<>();
        vendorPayments.add(vendorPayment);

        when(vendorPay.findByVendor(adminPay.getVendor())).thenReturn(vendorPayments);
        when(adminPayRepo.findByVendorAndAmountAndStatus(adminPay.getVendor(), adminPay.getAmount(), "Due")).thenReturn(adminPay);

        String actual = adminService.vendorPayment(adminPay.getVendor()).getVendor();
        assertEquals(adminPay.getVendor(), actual);
    }

    @Test
    void returnAdminPayModelIfAdminPayModelIsNull() {
        AdminPay adminPay = new AdminPay();
        VendorPayment vendorPayment = new VendorPayment("Priyanshi", "Geet", "Fitness", 0,
                "Due", LocalDate.now(), LocalTime.now());
        List<VendorPayment> vendorPayments = new ArrayList<>();
        vendorPayments.add(vendorPayment);

        when(vendorPay.findByVendor(adminPay.getVendor())).thenReturn(vendorPayments);
        when(adminPayRepo.findByVendorAndAmountAndStatus(adminPay.getVendor(), adminPay.getAmount(), "Due")).thenReturn(adminPay);

        AdminPay actual = adminService.vendorPayment(adminPay.getVendor());
        assertEquals(adminPay, actual);
    }

    @Test
    void updatePayment() {
        AdminPay adminPay = new AdminPay("1", "123456", "Priyanshi", 100,
                "Completed", "54321", "XYZ", LocalDate.now(), LocalTime.now());

        VendorPayment vendorPayment = new VendorPayment("Priyanshi", "Geet", "Fitness", 100,
                "Due", LocalDate.now(), LocalTime.now());
        List<VendorPayment> vendorPayments = new ArrayList<>();
        vendorPayments.add(vendorPayment);

        when(adminPayRepo.findByOrderId(data.get("order_id"))).thenReturn(adminPay);
        when(vendorPay.findByVendorAndStatus(adminPay.getVendor(), "Due")).thenReturn(vendorPayments);

        AdminPay actual = adminService.updatePayment(data);
        assertEquals(adminPay, actual);
    }

    @Test
    void returnAllAdminPayModelWhenStatusIsCompleted() {

        AdminPay adminPay1 = new AdminPay("1", "123456", "Priyanshi", 100,
                "Completed", "54321", "XYZ", LocalDate.now(), LocalTime.now());
        AdminPay adminPay2 = new AdminPay("2", "234567", "Priyanshi", 200,
                "Completed", "43210", "ABC", LocalDate.now(), LocalTime.now());

        List<AdminPay> adminPayList = new ArrayList<>();
        adminPayList.add(adminPay1);
        adminPayList.add(adminPay2);

        when(adminPayRepo.findByVendor(adminPay1.getVendor())).thenReturn(adminPayList);
        when(adminPayRepo.findByVendor(adminPay2.getVendor())).thenReturn(adminPayList);

        List<AdminPay> actual = adminService.paidHistoryVendor(adminPay1.getVendor());
        assertEquals(adminPayList, actual);

    }

    @Test
    void doNotReturnAdminPayModelWhenStatusIsNotCompleted() {

        AdminPay adminPay1 = new AdminPay("1", "123456", "Priyanshi", 100,
                "Due", "54321", "XYZ", LocalDate.now(), LocalTime.now());
        AdminPay adminPay2 = new AdminPay("2", "234567", "Priyanshi", 200,
                "Processing", "43210", "ABC", LocalDate.now(), LocalTime.now());

        List<AdminPay> adminPayList = new ArrayList<>();
        adminPayList.add(adminPay1);
        adminPayList.add(adminPay2);

        List<AdminPay> adminPayNull = new ArrayList<>();

        when(adminPayRepo.findByVendor(adminPay1.getVendor())).thenReturn(adminPayList);
        when(adminPayRepo.findByVendor(adminPay2.getVendor())).thenReturn(adminPayList);

        List<AdminPay> actual = adminService.paidHistoryVendor(adminPay1.getVendor());
        assertEquals(adminPayNull, actual);

    }

    @Test
    void returnOnlyThoseAdminPayModelWhoseStatusIsCompleted() {

        AdminPay adminPay1 = new AdminPay("1", "123456", "Priyanshi", 100,
                "Due", "54321", "XYZ", LocalDate.now(), LocalTime.now());
        AdminPay adminPay2 = new AdminPay("2", "234567", "Priyanshi", 200,
                "Completed", "43210", "ABC", LocalDate.now(), LocalTime.now());

        List<AdminPay> adminPayList = new ArrayList<>();
        adminPayList.add(adminPay1);
        adminPayList.add(adminPay2);

        List<AdminPay> adminPays = new ArrayList<>();
        adminPays.add(adminPay2);

        when(adminPayRepo.findByVendor(adminPay1.getVendor())).thenReturn(adminPayList);
        when(adminPayRepo.findByVendor(adminPay2.getVendor())).thenReturn(adminPayList);

        List<AdminPay> actual = adminService.paidHistoryVendor(adminPay1.getVendor());
        assertEquals(adminPays, actual);

    }

    @Test
    void returnNullWhenAdminPayModelIsNull() {
        List<AdminPay> adminPay = new ArrayList<>();

        List<AdminPay> actual = adminService.paidHistoryVendor("Priyanshi");
        assertEquals(adminPay, actual);
    }
}