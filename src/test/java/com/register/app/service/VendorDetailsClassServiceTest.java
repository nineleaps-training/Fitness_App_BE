package com.register.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorDetailsClass;
import com.fitness.app.model.DetailsModel;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.repository.VendorDetailsRepository;
<<<<<<< HEAD
import com.fitness.app.service.VendorDetailsService;
=======
import com.fitness.app.service.VendorDetailsServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
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
<<<<<<< HEAD
    private VendorDetailsService vendorDetailsService;
=======
    private VendorDetailsServiceImpl vendorDetailsServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48


    UserClass USER1=new UserClass("rahul01@gmail.com", "Rahul Kumar",
            "7651977515","Rahul@123","VENDOR", true, true, true );
    DetailsModel MODEL=new DetailsModel(
            "rahul01@gmail.com",
            "Male",
            "Patna, Bihar, India",
            "Patna",
            221204L
    );
    VendorDetailsClass DETAILS=new VendorDetailsClass(
            "rahul01@gmail.com",
            "Male",
            "Patna, Bihar, India",
            "Patna",
            221204L
    );


    @Test
     void addVendorDetails()
    {
        Mockito.when(userRepository.findByEmail(MODEL.getEmail())).thenReturn(USER1);
<<<<<<< HEAD
        VendorDetailsClass vendorDetailsClass =vendorDetailsService.addVendorDetails(MODEL);
=======
        VendorDetailsClass vendorDetailsClass = vendorDetailsServiceImpl.addVendorDetails(MODEL);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        Assertions.assertNotNull(vendorDetailsClass);
        Assertions.assertEquals(vendorDetailsClass.getVendorPostal(), DETAILS.getVendorPostal());
    }


    @Test
    void addVendorDetailsWithNUll()
    {
        Mockito.when(userRepository.findByEmail(MODEL.getEmail())).thenReturn(null);
<<<<<<< HEAD
        VendorDetailsClass vendorDetailsClass =vendorDetailsService.addVendorDetails(MODEL);
=======
        VendorDetailsClass vendorDetailsClass = vendorDetailsServiceImpl.addVendorDetails(MODEL);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        Assertions.assertNull(vendorDetailsClass);
    }


    @Test
     void getVendorDetails()
    {
        Mockito.when(detailsRepository.findByVendorEmail(DETAILS.getVendorEmail())).thenReturn(DETAILS);
<<<<<<< HEAD
        VendorDetailsClass details=vendorDetailsService.getVendorDetails(DETAILS.getVendorEmail());
=======
        VendorDetailsClass details= vendorDetailsServiceImpl.getVendorDetails(DETAILS.getVendorEmail());
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        Assertions.assertNotNull(details);
        Assertions.assertEquals(details.getVendorPostal(), DETAILS.getVendorPostal());
    }

}
