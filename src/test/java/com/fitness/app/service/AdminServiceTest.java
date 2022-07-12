package com.fitness.app.service;

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

        when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayModel.getVendor(), adminPayModel.getAmount(), "Due")).thenReturn(adminPayModel);

        AdminPayModel dataPay = adminService.getDataPay(adminPayModel);
        assertEquals(adminPayModel, dataPay);

    }

    @Test
    void payNow() {
        AdminPayModel adminPayModel = new AdminPayModel("1", "123456", "Priyanshi", 100,
                "Completed", "54321", "XYZ", LocalDate.now(), LocalTime.now());

        when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayModel.getVendor(), adminPayModel.getAmount(), "Due")).thenReturn(adminPayModel);

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
        AdminPayModel adminPayModel = new AdminPayModel("P00", "123456", "Priyanshi",
                100, "Due", "54321", "XYZ", LocalDate.now(), LocalTime.now());

        VendorPayment vendorPayment = new VendorPayment("Priyanshi", "Geet", "Fitness",
                0, "Due", LocalDate.now(), LocalTime.now());

        List<VendorPayment> vendorPayments = new ArrayList<>();
        vendorPayments.add(vendorPayment);

        when(vendorPay.findByVendor(adminPayModel.getVendor())).thenReturn(vendorPayments);
        when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayModel.getVendor(), adminPayModel.getAmount(), "Due")).thenReturn(adminPayModel);

        String actual = adminService.vendorPayment(adminPayModel.getVendor()).getVendor();
        assertEquals(adminPayModel.getVendor(), actual);
    }

    @Test
    void returnAdminPayModelIfAdminPayModelIsNull() {
        AdminPayModel adminPayModel = new AdminPayModel();

        VendorPayment vendorPayment = new VendorPayment("Priyanshi", "Geet", "Fitness", 0,
                "Due", LocalDate.now(), LocalTime.now());
        List<VendorPayment> vendorPayments = new ArrayList<>();
        vendorPayments.add(vendorPayment);

        when(vendorPay.findByVendor(adminPayModel.getVendor())).thenReturn(vendorPayments);
        when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayModel.getVendor(), adminPayModel.getAmount(), "Due")).thenReturn(adminPayModel);

        AdminPayModel actual = adminService.vendorPayment(adminPayModel.getVendor());
        assertEquals(adminPayModel, actual);
    }

    @Test
    void updatePayment() {
        AdminPayModel adminPayModel = new AdminPayModel("1", "123456", "Priyanshi", 100,
                "Completed", "54321", "XYZ", LocalDate.now(), LocalTime.now());

        VendorPayment vendorPayment = new VendorPayment("Priyanshi", "Geet", "Fitness", 100,
                "Due", LocalDate.now(), LocalTime.now());
        List<VendorPayment> vendorPayments = new ArrayList<>();
        vendorPayments.add(vendorPayment);

        when(adminPayRepo.findByOrderId(data.get("order_id"))).thenReturn(adminPayModel);
        when(vendorPay.findByVendorAndStatus(adminPayModel.getVendor(), "Due")).thenReturn(vendorPayments);

        AdminPayModel actual = adminService.updatePayment(data);
        assertEquals(adminPayModel, actual);
    }

    @Test
    void returnAllAdminPayModelWhenStatusIsCompleted() {

        AdminPayModel adminPayModel1 = new AdminPayModel("1", "123456", "Priyanshi", 100,
                "Completed", "54321", "XYZ", LocalDate.now(), LocalTime.now());
        AdminPayModel adminPayModel2 = new AdminPayModel("2", "234567", "Priyanshi", 200,
                "Completed", "43210", "ABC", LocalDate.now(), LocalTime.now());

        List<AdminPayModel> adminPayModelList = new ArrayList<>();
        adminPayModelList.add(adminPayModel1);
        adminPayModelList.add(adminPayModel2);

        when(adminPayRepo.findByVendor(adminPayModel1.getVendor())).thenReturn(adminPayModelList);
        when(adminPayRepo.findByVendor(adminPayModel2.getVendor())).thenReturn(adminPayModelList);

        List<AdminPayModel> actual = adminService.paidHistoryVendor(adminPayModel1.getVendor());
        assertEquals(adminPayModelList, actual);

    }

    @Test
    void doNotReturnAdminPayModelWhenStatusIsNotCompleted() {

        AdminPayModel adminPayModel1 = new AdminPayModel("1", "123456", "Priyanshi", 100,
                "Due", "54321", "XYZ", LocalDate.now(), LocalTime.now());
        AdminPayModel adminPayModel2 = new AdminPayModel("2", "234567", "Priyanshi", 200,
                "Processing", "43210", "ABC", LocalDate.now(), LocalTime.now());

        List<AdminPayModel> adminPayModelList = new ArrayList<>();
        adminPayModelList.add(adminPayModel1);
        adminPayModelList.add(adminPayModel2);

        List<AdminPayModel> adminPayModelNull = new ArrayList<>();

        when(adminPayRepo.findByVendor(adminPayModel1.getVendor())).thenReturn(adminPayModelList);
        when(adminPayRepo.findByVendor(adminPayModel2.getVendor())).thenReturn(adminPayModelList);

        List<AdminPayModel> actual = adminService.paidHistoryVendor(adminPayModel1.getVendor());
        assertEquals(adminPayModelNull, actual);

    }

    @Test
    void returnOnlyThoseAdminPayModelWhoseStatusIsCompleted() {

        AdminPayModel adminPayModel1 = new AdminPayModel("1", "123456", "Priyanshi", 100,
                "Due", "54321", "XYZ", LocalDate.now(), LocalTime.now());
        AdminPayModel adminPayModel2 = new AdminPayModel("2", "234567", "Priyanshi", 200,
                "Completed", "43210", "ABC", LocalDate.now(), LocalTime.now());

        List<AdminPayModel> adminPayModelList = new ArrayList<>();
        adminPayModelList.add(adminPayModel1);
        adminPayModelList.add(adminPayModel2);

        List<AdminPayModel> adminPayModels = new ArrayList<>();
        adminPayModels.add(adminPayModel2);

        when(adminPayRepo.findByVendor(adminPayModel1.getVendor())).thenReturn(adminPayModelList);
        when(adminPayRepo.findByVendor(adminPayModel2.getVendor())).thenReturn(adminPayModelList);

        List<AdminPayModel> actual = adminService.paidHistoryVendor(adminPayModel1.getVendor());
        assertEquals(adminPayModels, actual);

    }

    @Test
    void returnNullWhenAdminPayModelIsNull() {
        List<AdminPayModel> adminPayModels = new ArrayList<>();

        List<AdminPayModel> actual = adminService.paidHistoryVendor("Priyanshi");
        assertEquals(adminPayModels, actual);
    }
}