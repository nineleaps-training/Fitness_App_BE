package com.register.app.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.entity.AdminPay;
import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorPayment;
import com.fitness.app.model.AdminPayModel;
import com.fitness.app.repository.AdminPayRepo;

import com.fitness.app.repository.VendorPayRepo;
import com.fitness.app.service.AdminService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class AdminServiceTest {

    @Mock
    private AdminPayRepo adminPayRepo;
    private MockMvc mockMvc;


    AdminPay VENDOR_PAY=new AdminPay("id", "orderId", "manish.kumar@nineleaps.com",
            4000, "Due","paymentID","reciept", LocalDate.now(), LocalTime.now() );



    ObjectMapper objectMapper=new ObjectMapper();
    com.fasterxml.jackson.databind.ObjectWriter objectWriter= objectMapper.writer();


    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(adminService ).build();



    }
    @InjectMocks
    private AdminService adminService;



    AdminPayModel VENDOR_DUE=new AdminPayModel("manish.kumar@nineleaps.com", 4000);

    @Test
    public void getDataPay()
    {
        when(adminPayRepo.findByVendorAndAmountAndStatus(VENDOR_PAY.getVendor(), VENDOR_PAY.getAmount(), "Due")).thenReturn(VENDOR_PAY);
        AdminPay myPay=adminService.getDataPay(VENDOR_DUE);
        System.out.println(myPay.getAmount() +"\n"+ VENDOR_PAY.getAmount());
        Assertions.assertEquals(myPay.getAmount(), VENDOR_PAY.getAmount());
    }


    @Test
    public void paymentNow() throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_vmHcJh5Dj4v5EB", "SGff6EaJ7l3RzR47hnE4dYJz");

        JSONObject ob = new JSONObject();
        ob.put("amount", VENDOR_DUE.getAmount() * 100);
        ob.put("currency", "INR");
        ob.put("receipt", "txn_201456");

        Order myOrder = razorpayClient.Orders.create(ob);
        when(adminPayRepo.findByVendorAndAmountAndStatus(VENDOR_DUE.getVendor(), VENDOR_DUE.getAmount(), "Due")).thenReturn(VENDOR_PAY);
        Boolean expected=adminService.PayNow(VENDOR_DUE, myOrder);

        Assertions.assertNotNull(VENDOR_PAY);
        Assertions.assertTrue(expected);
    }


    @Mock
    private VendorPayRepo vendorPayRepo;

    @Test
    public void vendorPayment()
    {
        List<VendorPayment>vendorPaymentList=new ArrayList<>();
        vendorPaymentList.add(VENDOR_PAYMENT);
        when(vendorPayRepo.findByVendor("manish.kumar@nineleaps.com")).thenReturn(vendorPaymentList);
        when(adminPayRepo.findByVendorAndAmountAndStatus("manish.kumar@nineleaps.com", 2000, "Due")).thenReturn(VENDOR_PAY);
        AdminPay expected=adminService.vendorPayment("manish.kumar@nineleaps.com");
        Assertions.assertEquals(expected.getOrderId(), VENDOR_PAY.getOrderId());
    }
    VendorPayment VENDOR_PAYMENT=new VendorPayment("manish.kumar@nineleaps.com",
            "rahul.kumar01@nineleaps.com",
            "GM1", 2000, "Due", LocalDate.now(), LocalTime.now());


    @Test
    public void updatingPayment()  {
        HashMap<String, String> data=new HashMap<>();
        data.put("order_id", "orderId");
        data.put("payment_id", "paymentId");
        data.put("status", "Due");

        List<VendorPayment>listVendors=new ArrayList<>();
        listVendors.add(VENDOR_PAYMENT);

        AdminPay vendorPay=new AdminPay("id", "orderId", "manish.kumar@nineleaps.com",
                2000, "Due","paymentID","reciept", LocalDate.now(), LocalTime.now() );
        when(adminPayRepo.findByOrderId(data.get("order_id"))).thenReturn(vendorPay);
        when(vendorPayRepo.findByVendorAndStatus(vendorPay.getVendor(), "Due")).thenReturn(listVendors);

        AdminPay expected=adminService.updatePayment(data);

        Assertions.assertEquals(expected.getAmount(), VENDOR_PAYMENT.getAmount());

    }

    @Test
    public void vendorPaymentHistory() throws  Exception
    {
        AdminPay vendorPay=new AdminPay("id", "orderId", "manish.kumar@nineleaps.com",
                2000, "Completed","paymentID","reciept", LocalDate.now(), LocalTime.now() );
        List<AdminPay> allPaid=new ArrayList<>();
        allPaid.add(vendorPay);
        when(adminPayRepo.findByVendor(vendorPay.getVendor())).thenReturn(allPaid);
        List <AdminPay> expected=adminService.paidHistroyVendor(vendorPay.getVendor());

        Assertions.assertEquals(expected.get(0).getAmount(), 2000);


    }
}
