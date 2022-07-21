package com.register.app.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.AdminPayClass;
import com.fitness.app.entity.VendorPaymentClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.dto.requestDtos.AdminPayModel;

import com.fitness.app.repository.AdminPayRepository;
import com.fitness.app.repository.VendorPayRepository;
import com.fitness.app.service.AdminServiceImpl;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
class AdminServiceImplementationTest {

    @InjectMocks
    private AdminServiceImpl adminServiceImplementation;


    @Mock
    private AdminPayRepository adminPayRepo;
    private MockMvc mockMvc;


    AdminPayClass VENDOR_PAY=new AdminPayClass("id", "orderId", "manish.kumar@nineleaps.com",
            4000, "Due","paymentID","reciept", LocalDate.now(), LocalTime.now() );

    VendorPaymentClass VENDOR_PAYMENT=new VendorPaymentClass("manish.kumar@nineleaps.com",
            "rahul.kumar01@nineleaps.com",
            "GM1", 2000, "Due", LocalDate.now(), LocalTime.now());

    ObjectMapper objectMapper=new ObjectMapper();
    com.fasterxml.jackson.databind.ObjectWriter objectWriter= objectMapper.writer();


    @BeforeEach
     void setUp()
    {
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(adminServiceImplementation).build();



    }


    AdminPayModel VENDOR_DUE=new AdminPayModel("manish.kumar@nineleaps.com", 4000);

    @Test
     void getDataPay()
    {
        when(adminPayRepo.findByVendorAndAmountAndStatus(VENDOR_PAY.getVendor(), VENDOR_PAY.getAmount(), "Due")).thenReturn(VENDOR_PAY);
        AdminPayClass myPay= adminServiceImplementation.getDataPay(VENDOR_DUE);
        System.out.println(myPay.getAmount() +"\n"+ VENDOR_PAY.getAmount());
        Assertions.assertEquals(myPay.getAmount(), VENDOR_PAY.getAmount());
    }


    @Test
     void paymentNow() throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_vmHcJh5Dj4v5EB", "SGff6EaJ7l3RzR47hnE4dYJz");

        JSONObject ob = new JSONObject();
        ob.put("amount", VENDOR_DUE.getAmount() * 100);
        ob.put("currency", "INR");
        ob.put("receipt", "txn_201456");

        Order myOrder = razorpayClient.Orders.create(ob);
        when(adminPayRepo.findByVendorAndAmountAndStatus(VENDOR_DUE.getVendor(), VENDOR_DUE.getAmount(), "Due")).thenReturn(VENDOR_PAY);
        ApiResponse expected= adminServiceImplementation.PayNow(VENDOR_DUE);

        Assertions.assertNotNull(VENDOR_PAY);

    }

    @Test
    @DisplayName("Vendor payment is null")
     void paymentNowForNull() throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_vmHcJh5Dj4v5EB", "SGff6EaJ7l3RzR47hnE4dYJz");

        JSONObject ob = new JSONObject();
        ob.put("amount", VENDOR_DUE.getAmount() * 100);
        ob.put("currency", "INR");
        ob.put("receipt", "txn_201456");

        Order myOrder = razorpayClient.Orders.create(ob);
        when(adminPayRepo.findByVendorAndAmountAndStatus(VENDOR_DUE.getVendor(), VENDOR_DUE.getAmount(), "Due")).thenReturn(null);
        ApiResponse expected= adminServiceImplementation.PayNow(VENDOR_DUE);
        Assertions.assertNotNull(expected);

    }


    @Mock
    private VendorPayRepository vendorPayRepo;

    @Test
    void vendorPayment()
    {
        List<VendorPaymentClass> vendorPaymentClassList =new ArrayList<>();
        vendorPaymentClassList.add(VENDOR_PAYMENT);
        when(vendorPayRepo.findByVendor("manish.kumar@nineleaps.com")).thenReturn(vendorPaymentClassList);
        when(adminPayRepo.findByVendorAndAmountAndStatus("manish.kumar@nineleaps.com", 2000, "Due")).thenReturn(VENDOR_PAY);
        AdminPayClass expected= adminServiceImplementation.vendorPayment("manish.kumar@nineleaps.com");
        Assertions.assertEquals(expected.getOrderId(), VENDOR_PAY.getOrderId());
    }

    @Test
    @DisplayName("Saving the payment adding")
     void vendorPaymentForNewSave()
    {

        VendorPaymentClass vendorPaymentClass =new VendorPaymentClass("manish.kumar@nineleaps.com",
                "rahul.kumar01@nineleaps.com",
                "GM1", 2000, "Due", LocalDate.now(), LocalTime.now());

        List<VendorPaymentClass> vendorPaymentClassList =new ArrayList<>();
        vendorPaymentClassList.add(vendorPaymentClass);
        when(vendorPayRepo.findByVendor("manish.kumar@nineleaps.com")).thenReturn(vendorPaymentClassList);
        when(adminPayRepo.findByVendorAndAmountAndStatus("manish.kumar@nineleaps.com", 2000, "Due")).thenReturn(null);
        AdminPayClass expected= adminServiceImplementation.vendorPayment("manish.kumar@nineleaps.com");
        Assertions.assertEquals(expected.getAmount(), vendorPaymentClass.getAmount());
    }



    @Test
     void updatingPayment()  {
        HashMap<String, String> data=new HashMap<>();
        data.put("order_id", "orderId");
        data.put("payment_id", "paymentId");
        data.put("status", "Due");

        List<VendorPaymentClass>listVendors=new ArrayList<>();
        listVendors.add(VENDOR_PAYMENT);

        AdminPayClass vendorPay=new AdminPayClass("id", "orderId", "manish.kumar@nineleaps.com",
                2000, "Due","paymentID","reciept", LocalDate.now(), LocalTime.now() );
        when(adminPayRepo.findByOrderId(data.get("order_id"))).thenReturn(vendorPay);
        when(vendorPayRepo.findByVendorAndStatus(vendorPay.getVendor(), "Due")).thenReturn(listVendors);

        AdminPayClass expected= adminServiceImplementation.updatePayment(data);

        Assertions.assertEquals(expected.getAmount(), VENDOR_PAYMENT.getAmount());

    }

    @Test
    void vendorPaymentHistory() throws  Exception
    {
        AdminPayClass vendorPay=new AdminPayClass("id", "orderId", "manish.kumar@nineleaps.com",
                2000, "Completed","paymentID","reciept", LocalDate.now(), LocalTime.now() );
        List<AdminPayClass> allPaid=new ArrayList<>();
        allPaid.add(vendorPay);
        when(adminPayRepo.findByVendor(vendorPay.getVendor())).thenReturn(allPaid);
        List <AdminPayClass> expected= adminServiceImplementation.paidHistoryVendor(vendorPay.getVendor());

        Assertions.assertEquals(2000, expected.get(0).getAmount());


    }

    @Test
    @DisplayName("For Exception test: ")
     void paidHistoryVendorEx()
    {
        AdminPayClass vendorPay=new AdminPayClass("id", "orderId", "manish.kumar@nineleaps.com",
                2000, "Completed","paymentID","reciept", LocalDate.now(), LocalTime.now() );
        String vendor=vendorPay.getVendor();
        when(adminPayRepo.findByVendor(vendorPay.getVendor())).thenReturn(null);
        Assertions.assertThrows(DataNotFoundException.class, ()->{

            List <AdminPayClass> expected= adminServiceImplementation.paidHistoryVendor(vendor);
        }, "Error handled");
    }
}
