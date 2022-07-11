package com.fitness.app.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        adminService=new AdminService(adminPayRepo,vendorPayRepo);
    }
    
    @Test
    void testGetDataPay() {

        LocalDate localDate=LocalDate.of(2022, 6, 24);
        LocalTime localTime=LocalTime.of(12, 32, 4);
        AdminPayRequestModel adminPayRequestModel=new AdminPayRequestModel("GM6","orderid","vendor",123,"Due","string","string",localDate,localTime);
        when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayRequestModel.getVendor(),adminPayRequestModel.getAmount(),"Due")).thenReturn(adminPayRequestModel);
        AdminPayRequestModel adminPayRequestModel2 = adminService.getDataPay(adminPayRequestModel);
        Assertions.assertEquals(adminPayRequestModel.getVendor(), adminPayRequestModel2.getVendor());

    }

    @Test
    void testPaidHistroyVendor() {

        LocalDate localDate=LocalDate.of(2022, 6, 24);
        LocalTime localTime=LocalTime.of(12, 32, 4);
        AdminPayRequestModel adminPayRequestModel=new AdminPayRequestModel("GM6","orderid","vendor",123,"Completed","string","string",localDate,localTime);
        List<AdminPayRequestModel> list=new ArrayList<>();
        list.add(adminPayRequestModel);
        when(adminPayRepo.findByVendor(adminPayRequestModel.getVendor())).thenReturn(list);
        List<AdminPayRequestModel> list2 = adminService.paidHistroyVendor(adminPayRequestModel.getVendor());
        Assertions.assertEquals(list, list2);
    }

    @Test
    void testPayNow() {
        
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        AdminPayRequestModel adminPayRequestModel=new AdminPayRequestModel("GM6","orderid","vendor",123,"Due","string","string",localDate,localTime);
        List<AdminPayRequestModel> list=new ArrayList<>();
        list.add(adminPayRequestModel);
        JSONObject jsonObject=new JSONObject();
        Order order=new Order(jsonObject);
        when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayRequestModel.getVendor(),adminPayRequestModel.getAmount(),adminPayRequestModel.getStatus())).thenReturn(adminPayRequestModel);
        AdminPayRequestModel adminPayRequestModel2 = adminService.payNow(adminPayRequestModel, order);
        Assertions.assertEquals(adminPayRequestModel,adminPayRequestModel2);

    }

    @Test
    void testPayNowwithNull() {
        
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        AdminPayRequestModel adminPayRequestModel=new AdminPayRequestModel("GM6","orderid","vendor",123,"Due","string","string",localDate,localTime);
        List<AdminPayRequestModel> list=new ArrayList<>();
        list.add(adminPayRequestModel);
        JSONObject jsonObject=new JSONObject();
        Order order=new Order(jsonObject);
        when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayRequestModel.getVendor(),adminPayRequestModel.getAmount(),adminPayRequestModel.getStatus())).thenReturn(null);
        AdminPayRequestModel adminPayRequestModel2 = adminService.payNow(adminPayRequestModel, order);
        Assertions.assertEquals(null,adminPayRequestModel2);

    }

    @Test
    void testUpdatePayment() {

        Map<String,String> data = new HashMap<>();
        data.put("order_id", "id");
        data.put("payment_id", "paymentId");
        data.put("status", "Due");
        VendorPayment vendorPayment=new VendorPayment("email", "vendor", 123, "Due");
        List<VendorPayment> list=new ArrayList<>();
        list.add(vendorPayment);
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        AdminPayRequestModel adminPayRequestModel=new AdminPayRequestModel("GM6","id","vendor",123,"Due","string","string",localDate,localTime);
        List<AdminPayRequestModel> list1=new ArrayList<>();
        list1.add(adminPayRequestModel);
        when(adminPayRepo.findByOrderId(data.get("order_id"))).thenReturn(adminPayRequestModel);
        when(vendorPayRepo.findByVendorAndStatus(adminPayRequestModel.getVendor(), adminPayRequestModel.getStatus())).thenReturn(list);
        AdminPayRequestModel adminPayRequestModel2 = adminService.updatePayment(data);
        Assertions.assertEquals(adminPayRequestModel,adminPayRequestModel2);

    }

    @Test
    void testVendorPayment() {

        Map<String,String> data = new HashMap<>();
        data.put("order_id", "id");
        data.put("payment_id", "paymentId");
        data.put("status", "Due");
        VendorPayment vendorPayment=new VendorPayment("email", "vendor", 123, "Due");
        List<VendorPayment> list=new ArrayList<>();
        list.add(vendorPayment);
        LocalDate localDate=LocalDate.now();
        LocalTime localTime=LocalTime.now();
        AdminPayRequestModel adminPayRequestModel=new AdminPayRequestModel("GM6","id","vendor",123,"Due","string","string",localDate,localTime);
        List<AdminPayRequestModel> list1=new ArrayList<>();
        list1.add(adminPayRequestModel);
        when(vendorPayRepo.findByVendor(vendorPayment.getVendor())).thenReturn(list);
        when(adminPayRepo.findByVendorAndAmountAndStatus(adminPayRequestModel.getVendor(),adminPayRequestModel.getAmount(),"Due")).thenReturn(adminPayRequestModel);
        when(adminPayRepo.findAll()).thenReturn(list1);
        AdminPayRequestModel adminPayRequestModel2 = adminService.vendorPayment(adminPayRequestModel.getVendor());
        Assertions.assertEquals(adminPayRequestModel, adminPayRequestModel2);
    }

    @Test
    void testVendorPaymentwithNull() {
        
        VendorPayment vendorPayment=new VendorPayment();
        AdminPayRequestModel adminPayRequestModel=new AdminPayRequestModel();
        List<AdminPayRequestModel> list1=new ArrayList<>();
        list1.add(adminPayRequestModel);
        when(vendorPayRepo.findByVendor(vendorPayment.getVendor())).thenReturn(null);
        when(adminPayRepo.findByVendorAndAmountAndStatus(null,0,"Due")).thenReturn(null);
        when(adminPayRepo.findAll()).thenReturn(list1);
        when(adminPayRepo.save(any())).thenReturn(adminPayRequestModel);
        AdminPayRequestModel adminPayRequestModel2 = adminService.vendorPayment(vendorPayment.getVendor());
        Assertions.assertEquals(adminPayRequestModel.getVendor(), adminPayRequestModel2.getVendor());
    }
}
