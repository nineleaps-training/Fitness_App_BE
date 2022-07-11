package com.fitness.app.service;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fitness.app.entity.UserClass;
import com.fitness.app.model.VendorDetailsRequestModel;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.repository.VendorDetailsRepository;

@ExtendWith(MockitoExtension.class)
class VendorDetailsServiceTest {

    @Mock
    VendorDetailsRepository vendorDetailsRepository;

    @Mock
    UserRepository userRepository;

    VendorDetailsService vendorDetailsService;

    VendorDetailsRequestModel vendorDetailsRequestModel;

    long l=1234;
   
    MockMvc mockMvc;
    UserClass userClass;

    @BeforeEach
    public void initcase() {
        vendorDetailsService=new VendorDetailsService(userRepository,vendorDetailsRepository);
    }

    @Test
    void addUserDetailsIfUserIsNotNullAndStatusIsActivated() {
        VendorDetailsRequestModel userDetailsModel = new VendorDetailsRequestModel("pankaj.jain@nineleaps.com", "Male",
                "80 Feet Road, Koramangala", "Bangalore",5678L);

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj",
                "9999999999", "12345", "Enthusiast", true, true, true);

        when(userRepository.findByEmail(userDetailsModel.getVEmail())).thenReturn(userClass);

        VendorDetailsRequestModel actual = vendorDetailsService.addVendorDetails(userDetailsModel);

        Assertions.assertEquals(null, actual);
    }

    @Test
    void doNotAddUserDetailsIfUserIsNotNullAndStatusIsNotActivated() {
        VendorDetailsRequestModel userDetailsModel = new VendorDetailsRequestModel("pankaj.jain@nineleaps.com", "Male",
                "80 Feet Road, Koramangala", "Bangalore",5678L);

        UserClass userClass = new UserClass("pankaj.jain@nineleaps.com", "Pankaj",
                "9999999999", "12345", "Enthusiast", false, true, true);

        when(userRepository.findByEmail(userDetailsModel.getVEmail())).thenReturn(userClass);

        VendorDetailsRequestModel actual = vendorDetailsService.addVendorDetails(userDetailsModel);

        Assertions.assertEquals(null,actual);
    }

    @Test
    void doNotAddUserDetailsIfUserIsNullAndStatusIsNotActivated() {
        VendorDetailsRequestModel userDetailsModel = new VendorDetailsRequestModel("pankaj.jain@nineleaps.com", "Male",
                "80 Feet Road, Koramangala", "Bangalore",5678L);

        UserClass userClass = new UserClass();
        userClass.setActivated(false);

        when(userRepository.findByEmail(userDetailsModel.getVEmail())).thenReturn(userClass);

        VendorDetailsRequestModel actual = vendorDetailsService.addVendorDetails(userDetailsModel);

        Assertions.assertEquals(null,actual);
    }

    @Test
    void doNotAddUserDetailsIfUserIsNullAndStatusIsActivated() {
        VendorDetailsRequestModel userDetailsModel = new VendorDetailsRequestModel("pankaj.jain@nineleaps.com", "Male",
                "80 Feet Road, Koramangala", "Bangalore",5678L);

        UserClass userClass = new UserClass();
        userClass.setActivated(true);

        VendorDetailsRequestModel actual = vendorDetailsService.addVendorDetails(userDetailsModel);

        Assertions.assertEquals(null,actual);
    }


    @Test
    void testGetVendorDetails() {
        long l=12345;
        vendorDetailsRequestModel = new VendorDetailsRequestModel("pankaj.jain@nineleaps.com","male","Bhatar","Surat",l);
        when(vendorDetailsRepository.findByVEmail(vendorDetailsRequestModel.getVEmail())).thenReturn(vendorDetailsRequestModel);
        Assertions.assertEquals(vendorDetailsRequestModel.getVEmail(), vendorDetailsService.getVendorDetails("pankaj.jain@nineleaps.com").getVEmail());
    }
}
