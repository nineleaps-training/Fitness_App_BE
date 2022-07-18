package com.register.app.service;

import com.fitness.app.entity.GymAddressClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.GymSubscriptionClass;
import com.fitness.app.entity.GymTimeClass;
import com.fitness.app.exceptions.DataNotFoundException;
import com.fitness.app.model.GymClassModel;
import com.fitness.app.model.GymRepresnt;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.GymAddressRepository;
import com.fitness.app.repository.GymSubscriptionRepository;
import com.fitness.app.repository.GymTimeRepository;
import com.fitness.app.service.GymServiceImpl;
import com.fitness.app.service.RatingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GymServiceImplTest {




    @Mock
    private AddGymRepository gymRepository;

    @Mock
    private GymAddressRepository addressRepo;

    @Mock
    private GymTimeRepository timeRepo;

    @Mock
    private GymSubscriptionRepository subscriptionRepo;

    @Mock
    private RatingServiceImpl ratingServiceImpl;











    List<String> workout=new ArrayList<>(Arrays.asList("Zumba", "Dance", "Running"));


    long contact=7651977515L;

    GymClass FITNESS1=new GymClass(
            "GM1",
            "manishsingh@gmail.com",
            "Fitness Center",
            workout,
            contact,
            4.0,
            100
    );

    GymClass FITNESS2=new GymClass(
            "GM2",
            "manishsingh@gmail.com",
            "Fitness Center1",
            workout,
            contact,
            3.0,
            150
    );

    GymAddressClass GYM_ADDRESS=new GymAddressClass(1.6523589, 1.20053546478, "Varanasi, India", "Varanasi");
    GymSubscriptionClass GYM_SUBS=new GymSubscriptionClass(100,1500,2500,3500,6000,200);
    GymTimeClass GYM_TIME=new GymTimeClass("6AM-10AM", "4PM-9PM", "SUNDAY");

    GymAddressClass GYM_ADDRESS_ID=new GymAddressClass("GM1", 1.6523589, 1.20053546478, "Varanasi, India", "Varanasi");
    GymSubscriptionClass GYM_SUBS_ID=new GymSubscriptionClass("GM1",100,1500,2500,3500,6000,200);
    GymTimeClass GYM_TIME_ID=new GymTimeClass("GM1","6AM-10AM", "4PM-9PM", "SUNDAY" );

    GymClassModel GYM_MODEL=new GymClassModel(
            "manishsingh@gmail.com",
            "Fitness Center",
            GYM_ADDRESS,
            workout,
            GYM_TIME,
            GYM_SUBS,
            contact,
            100
    );


    Optional<GymClass> gymClass= Optional.of(FITNESS1);
    Optional<GymAddressClass> addressClass=Optional.of(GYM_ADDRESS_ID);
    Optional<GymSubscriptionClass> subscriptionClass=Optional.of(GYM_SUBS_ID);
    Optional<GymTimeClass> time=Optional.of(GYM_TIME_ID);
    @InjectMocks
    private GymServiceImpl gymServiceImpl;


    @Test
     void addNewGym()
    {
        Long idlast=1L;
        Mockito.when(gymRepository.findByName(GYM_MODEL.getGym_name())).thenReturn(FITNESS1);
        Mockito.when(gymRepository.count()+1).thenReturn(idlast);


        GymClass returnedGYm= gymServiceImpl.addNewGym(GYM_MODEL);

        Assertions.assertNotNull(returnedGYm);

        Assertions.assertEquals(returnedGYm.getContact(), contact);
    }



    @Test
     void getGymBYGymId()
    {

       Mockito.when(gymRepository.findById(FITNESS1.getId())).thenReturn(gymClass);
       Mockito.when(addressRepo.findById(FITNESS1.getId())).thenReturn(addressClass);
       Mockito.when(subscriptionRepo.findById(FITNESS1.getId())).thenReturn(subscriptionClass);
       Mockito.when(timeRepo.findById(FITNESS1.getId())).thenReturn(time);

       GymRepresnt returnedGym= gymServiceImpl.getGymByGymId(FITNESS1.getId());

       Assertions.assertNotNull(returnedGym);
       Assertions.assertEquals(returnedGym.getCapacity(), FITNESS1.getCapacity());

    }


    @Test
     void gymByVendorEmail()
    {
       List<GymClass> gyms=new ArrayList<>( Arrays.asList(FITNESS1));
       Mockito.when(gymRepository.findByEmail(FITNESS1.getEmail())).thenReturn(gyms);
       Mockito.when(ratingServiceImpl.getRating(FITNESS1.getId())).thenReturn(4.3);
        Mockito.when(addressRepo.findById(FITNESS1.getId())).thenReturn(addressClass);
        Mockito.when(subscriptionRepo.findById(FITNESS1.getId())).thenReturn(subscriptionClass);
        Mockito.when(timeRepo.findById(FITNESS1.getId())).thenReturn(time);

        List<GymRepresnt> returnedGyms= gymServiceImpl.getGymByVendorEmail(FITNESS1.getEmail());

        Assertions.assertNotNull(returnedGyms);
        Assertions.assertEquals(100, returnedGyms.get(0).getCapacity());

    }

    @Test
    void  gymByGymCity()
    {

        List<GymAddressClass> addressClasses=new ArrayList<>(Arrays.asList(GYM_ADDRESS_ID));
        Mockito.when(addressRepo.findByCity(GYM_ADDRESS_ID.getCity())).thenReturn(addressClasses);

        Mockito.when(gymRepository.findById(FITNESS1.getId())).thenReturn(gymClass);
        Mockito.when(subscriptionRepo.findById(FITNESS1.getId())).thenReturn(subscriptionClass);
        Mockito.when(timeRepo.findById(FITNESS1.getId())).thenReturn(time);

        List<GymRepresnt> returnedGyms= gymServiceImpl.getGymByCity(GYM_ADDRESS.getCity());

        Assertions.assertNotNull(returnedGyms);

        Assertions.assertEquals(returnedGyms.get(0).getGym_name(), FITNESS1.getName());
    }

    @Test
    @DisplayName("Exception testing:")
    void  gymByGymCityException() //throws  DataNotFoundException
    {
        List<GymAddressClass> addressClasses=null;
        Mockito.when(addressRepo.findByCity(GYM_ADDRESS_ID.getCity())).thenReturn(addressClasses);
        String city= GYM_ADDRESS.getCity();
           Assertions.assertThrows(DataNotFoundException.class,()->{

               gymServiceImpl.getGymByCity(city);

           }," data not found;");



    }



    @Test
     void findTheAddress(){
        Mockito.when(addressRepo.findById(GYM_ADDRESS_ID.getId())).thenReturn(addressClass);
        GymAddressClass returnedAdd= gymServiceImpl.findTheAddress(GYM_ADDRESS_ID.getId());

        Assertions.assertNotNull(returnedAdd);
        Assertions.assertEquals(returnedAdd.getLat().floatValue(), GYM_ADDRESS_ID.getLat().floatValue());
    }

    @Test
    void getAllGyms(){

        List<GymClass> gymClasses=new ArrayList<>(Arrays.asList(FITNESS1, FITNESS2));

        Mockito.when(gymRepository.findAll()).thenReturn(gymClasses);

        List<GymClass> returnedGyms= gymServiceImpl.getAllGym();

        Assertions.assertNotNull(returnedGyms);
        Assertions.assertEquals(returnedGyms.get(0).getCapacity(), FITNESS1.getCapacity());

    }

    @Test
     void getGymsByName()
    {
        Mockito.when(gymRepository.findByName(FITNESS1.getName())).thenReturn(FITNESS1);
        GymClass returnedGyms= gymServiceImpl.getGymByGymName(FITNESS1.getName());

        Assertions.assertNotNull(returnedGyms);
        Assertions.assertEquals(returnedGyms.getCapacity(), FITNESS1.getCapacity());
    }
}
