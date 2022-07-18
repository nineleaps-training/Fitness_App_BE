package com.register.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.UserDetailsClass;
import com.fitness.app.model.DetailsModel;

import com.fitness.app.repository.UserDetailsRepository;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserDetailsClassServiceTest {

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    UserClass USER1=new UserClass("rahul01@gmail.com", "Rahul Kumar",
            "7651977515","Rahul@123","USER", true, true, true );

   DetailsModel MODEL=new DetailsModel(
           "rahul01@gmail.com",
           "Male",
           "Patna, Bihar, India",
           "Patna",
           221204L
   );

   UserDetailsClass DETAILS=new UserDetailsClass("rahul01@gmail.com", "Male", "Patna", "Patna", 221204L);

   @Test
     void addUserDetails(){

       Mockito.when(userRepository.findByEmail(MODEL.getEmail())).thenReturn(USER1);
       UserDetailsClass userDetailsClass = userDetailsServiceImpl.addUserDetails(MODEL);

       Assertions.assertNotNull(userDetailsClass);
       Assertions.assertEquals(MODEL.getPostal(), userDetailsClass.getUseroPostal());

   }

    @Test
     void addUserDetailsWithNull(){

        Mockito.when(userRepository.findByEmail(MODEL.getEmail())).thenReturn(null);
        UserDetailsClass userDetailsClass = userDetailsServiceImpl.addUserDetails(MODEL);

        Assertions.assertNull(userDetailsClass);


    }

   @Test
     void getUserDetails()
   {
       Mockito.when(userDetailsRepository.findByUserEmail(DETAILS.getUserEmail())).thenReturn(DETAILS);
       UserDetailsClass userDetailsClass = userDetailsServiceImpl.getUserDetails(DETAILS.getUserEmail());
       Assertions.assertNotNull(userDetailsClass);
       Assertions.assertEquals(userDetailsClass.getUseroPostal(), DETAILS.getUseroPostal());
   }



}
