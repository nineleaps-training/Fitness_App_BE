package com.register.app.service;

import com.fitness.app.dto.DetailsModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorDetailsClass;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.repository.VendorDetailsRepository;
import com.fitness.app.service.VendorDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VendorDetailsClassServiceTest {

    @Mock
    private VendorDetailsRepository detailsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks

    private VendorDetailsServiceImpl vendorDetailsServiceImpl;


    UserClass USER1 = new UserClass("rahul01@gmail.com", "Rahul Kumar",
            "7651977515", "Rahul@123", "VENDOR", true, true, true);
    DetailsModel MODEL = new DetailsModel(
            "rahul01@gmail.com",
            "Male",
            "Patna, Bihar, India",
            "Patna",
            221204L
    );
    VendorDetailsClass DETAILS = new VendorDetailsClass(
            "rahul01@gmail.com",
            "Male",
            "Patna, Bihar, India",
            "Patna",
            221204L
    );


    @Test
    void addVendorDetails() {
        Mockito.when(userRepository.findByEmail(MODEL.getEmail())).thenReturn(USER1);

        ApiResponse vendorDetailsClass = vendorDetailsServiceImpl.addVendorDetails(

                MODEL);

        Assertions.assertNotNull(vendorDetailsClass);
        Assertions.assertEquals(vendorDetailsClass.getBody().getClass(), DETAILS.getClass());
    }


    @Test
    void addVendorDetailsWithNUll() {
        Mockito.when(userRepository.findByEmail(MODEL.getEmail())).thenReturn(null);

        ApiResponse vendorDetailsClass = vendorDetailsServiceImpl.addVendorDetails(MODEL);

        Assertions.assertNull(vendorDetailsClass);
    }


    @Test
    void getVendorDetails() {
        Mockito.when(detailsRepository.findByVendorEmail(DETAILS.getVendorEmail())).thenReturn(DETAILS);

        VendorDetailsClass details = vendorDetailsServiceImpl.getVendorDetails(DETAILS.getVendorEmail());

        Assertions.assertNotNull(details);
        Assertions.assertEquals(details.getVendorPostal(), DETAILS.getVendorPostal());
    }

}
