package com.register.app.service;

import com.fitness.app.componets.MessageComponents;
import com.fitness.app.entity.*;
import com.fitness.app.model.BookedGymModel;
import com.fitness.app.model.UserPerfomanceModel;
import com.fitness.app.repository.*;
<<<<<<< HEAD
=======
import com.fitness.app.service.UserOrderServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class UserOrderClassServiceTest {


    @Mock
<<<<<<< HEAD
    private UserOrderRepo userOrderRepo;
=======
    private UserOrderRepository userOrderRepository;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddGymRepository gymRepo;

    @Mock
<<<<<<< HEAD
    private VendorPayRepo vendorPayRepo;

    @Mock
    private GymAddressRepo gymAddressRepo;
    @Mock
    private AttendanceRepo attendanceRepo;
=======
    private VendorPayRepository vendorPayRepository;

    @Mock
    private GymAddressRepository gymAddressRepository;
    @Mock
    private AttendanceRepository attendanceRepository;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

    @Mock
    private MessageComponents messageComponents;

    @InjectMocks
<<<<<<< HEAD
    private com.fitness.app.service.UserOrderService userOrderService;
=======
    private UserOrderServiceImpl userOrderServiceImpl;
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

    List<String>SERVICE=new ArrayList<>(Arrays.asList("Dance","Zumba","Karate"));
    final String CURRENT="Current";
    final String VENDOR="Manish";
    UserOrderClass USER_ORDER=new UserOrderClass("orderId", "rahul", "GM1",
            SERVICE,"Monthly" ,"Evening",2000, CURRENT,
            "Created", "paymentId", "receipt",
            LocalDate.now(), LocalTime.now());
    UserOrderClass USER_ORDER_CREATED=new UserOrderClass("orderId", "rahul", "GM1",
            SERVICE,"Monthly" ,"Evening",2000, CURRENT,
            "created", "paymentId", "receipt",
            LocalDate.now(), LocalTime.now());

    UserOrderClass USER_ORDER_COM=new UserOrderClass("orderId", "rahul", "GM1",
            SERVICE,"Monthly" ,"Evening",2000, CURRENT,
            "Completed", "paymentId", "receipt",
            LocalDate.now(), LocalTime.now());
    UserOrderClass USER_ORDER_EX=new UserOrderClass("orderId", "rahul", "GM1",
            SERVICE,"Monthly" ,"Evening",2000, "Expired",
            "Expired", "paymentId", "receipt",
            LocalDate.now(), LocalTime.now());
    List<String> workout=new ArrayList<>(Arrays.asList("Zumba", "Dance", "Running"));

    List<Integer> attendance=new ArrayList<>(Arrays.asList(1,0,1,0,1,0,1,0,1,0,1));
    UserPerfomanceModel PERFORMANCE_MODEL=new UserPerfomanceModel(
            "Rahul",
            "Rahul",
            "GM1",
            "Manish",
            attendance,
            4.0
    );
    UserAttendanceClass USER_ATT=new UserAttendanceClass(
            "Rahul",
            "GM1",
            "Manish",
            30,
            attendance.size(),
            attendance,
            4.0
    );

    long contact=7651977515L;

    GymClass FITNESS2=new GymClass(
            "GM1",
            "Manish",
            "Fitness Center1",
            workout,
            contact,
            3.0,
            150
    );
    UserClass USER1=new UserClass(
            "Rahul",
            "Rahul Khamperia",
            "7651977515",
            "Rahul@123",
            "USER",
            false,
            false,
            true
    );
    GymAddressClass GYM_ADDRESS_ID=new GymAddressClass("GM1", 1.6523589, 1.20053546478, "Varanasi, India", "Varanasi");

    BookedGymModel bookedGymModel=new BookedGymModel(
            "GM1",
            "Fitness Cneter",
            "Manish",
            workout,
            "Evening",
            LocalDate.now(),
            GYM_ADDRESS_ID,
            7651977515L,
            4.0
    );
    @Test
    void orderNow()
    {
<<<<<<< HEAD
        userOrderService.orderNow(USER_ORDER);
=======
        userOrderServiceImpl.orderNow(USER_ORDER);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        Assertions.assertNotNull(USER_ORDER);
    }


    @Test
    void updateOrderForNUll()
    {
<<<<<<< HEAD
        UserOrderClass userOrderClass =userOrderService.updateOrder(null);
=======
        UserOrderClass userOrderClass = userOrderServiceImpl.updateOrder(null);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        Assertions.assertNull(userOrderClass);
    }

    @Test
    @DisplayName("Update order for Month case")
     void updateOrder()
    {
        HashMap<String, String> data=new HashMap<>();
        data.put("order_id", "orderId");
        data.put("payment_id", "paymentId");
        data.put("status", "Completed");
        UserOrderClass USER_ORDER=new UserOrderClass("orderId", "rahul", "GM1",
                SERVICE,"Monthly" ,"Evening",2000, CURRENT,
                "Created", "paymentId", "receipt",
                LocalDate.now(), LocalTime.now());
        Optional<GymClass> optional_gym=Optional.of(FITNESS2);
        Optional<UserOrderClass> optional_order=Optional.of(USER_ORDER);
<<<<<<< HEAD
        Mockito.when(userOrderRepo.findById(data.get("order_id"))).thenReturn(optional_order);
        Mockito.when(gymRepo.findById(USER_ORDER.getGym())).thenReturn(optional_gym);

        UserOrderClass userOrderClass =userOrderService.updateOrder(data);
=======
        Mockito.when(userOrderRepository.findById(data.get("order_id"))).thenReturn(optional_order);
        Mockito.when(gymRepo.findById(USER_ORDER.getGym())).thenReturn(optional_gym);

        UserOrderClass userOrderClass = userOrderServiceImpl.updateOrder(data);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        Assertions.assertNotNull(userOrderClass);
        Assertions.assertEquals(userOrderClass.getStatus(), USER_ORDER_COM.getStatus());

    }
    @Test
    @DisplayName("Update order for Quat case")
    void updateOrderQuat()
    {
        HashMap<String, String> data=new HashMap<>();
        data.put("order_id", "orderId");
        data.put("payment_id", "paymentId");
        data.put("status", "Completed");
        UserOrderClass USER_ORDER=new UserOrderClass("orderId", "rahul", "GM1",
                SERVICE,"Quaterly" ,"Evening",2000, CURRENT,
                "Created", "paymentId", "receipt",
                LocalDate.now(), LocalTime.now());
        Optional<GymClass> optional_gym=Optional.of(FITNESS2);
        Optional<UserOrderClass> optional_order=Optional.of(USER_ORDER);
<<<<<<< HEAD
        Mockito.when(userOrderRepo.findById(data.get("order_id"))).thenReturn(optional_order);
        Mockito.when(gymRepo.findById(USER_ORDER.getGym())).thenReturn(optional_gym);

        UserOrderClass userOrderClass =userOrderService.updateOrder(data);
=======
        Mockito.when(userOrderRepository.findById(data.get("order_id"))).thenReturn(optional_order);
        Mockito.when(gymRepo.findById(USER_ORDER.getGym())).thenReturn(optional_gym);

        UserOrderClass userOrderClass = userOrderServiceImpl.updateOrder(data);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        Assertions.assertNotNull(userOrderClass);
        Assertions.assertEquals(userOrderClass.getStatus(), USER_ORDER_COM.getStatus());

    }
    @Test
    @DisplayName("Update order for half case")
    void updateOrderHalf()
    {
        HashMap<String, String> data=new HashMap<>();
        data.put("order_id", "orderId");
        data.put("payment_id", "paymentId");
        data.put("status", "Completed");
        UserOrderClass USER_ORDER=new UserOrderClass("orderId", "rahul", "GM1",
                SERVICE,"Half" ,"Evening",2000, CURRENT,
                "Created", "paymentId", "receipt",
                LocalDate.now(), LocalTime.now());
        Optional<GymClass> optional_gym=Optional.of(FITNESS2);
        Optional<UserOrderClass> optional_order=Optional.of(USER_ORDER);
<<<<<<< HEAD
        Mockito.when(userOrderRepo.findById(data.get("order_id"))).thenReturn(optional_order);
        Mockito.when(gymRepo.findById(USER_ORDER.getGym())).thenReturn(optional_gym);

        UserOrderClass userOrderClass =userOrderService.updateOrder(data);
=======
        Mockito.when(userOrderRepository.findById(data.get("order_id"))).thenReturn(optional_order);
        Mockito.when(gymRepo.findById(USER_ORDER.getGym())).thenReturn(optional_gym);

        UserOrderClass userOrderClass = userOrderServiceImpl.updateOrder(data);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        Assertions.assertNotNull(userOrderClass);
        Assertions.assertEquals(userOrderClass.getStatus(), USER_ORDER_COM.getStatus());

    }

    @Test
    @DisplayName("Update order for default case")
    void updateOrderDefault()
    {
        HashMap<String, String> data=new HashMap<>();
        data.put("order_id", "orderId");
        data.put("payment_id", "paymentId");
        data.put("status", "Completed");
        UserOrderClass USER_ORDER=new UserOrderClass("orderId", "rahul", "GM1",
                SERVICE,"Yearly" ,"Evening",2000, CURRENT,
                "Created", "paymentId", "receipt",
                LocalDate.now(), LocalTime.now());
        Optional<GymClass> optional_gym=Optional.of(FITNESS2);
        Optional<UserOrderClass> optional_order=Optional.of(USER_ORDER);
<<<<<<< HEAD
        Mockito.when(userOrderRepo.findById(data.get("order_id"))).thenReturn(optional_order);
        Mockito.when(gymRepo.findById(USER_ORDER.getGym())).thenReturn(optional_gym);

        UserOrderClass userOrderClass =userOrderService.updateOrder(data);
=======
        Mockito.when(userOrderRepository.findById(data.get("order_id"))).thenReturn(optional_order);
        Mockito.when(gymRepo.findById(USER_ORDER.getGym())).thenReturn(optional_gym);

        UserOrderClass userOrderClass = userOrderServiceImpl.updateOrder(data);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        Assertions.assertNotNull(userOrderClass);
        Assertions.assertEquals(userOrderClass.getStatus(), USER_ORDER_COM.getStatus());

    }
    @Test
     void pendingListOrder()
    {
        List<UserOrderClass> listOrder=new ArrayList<>(Arrays.asList(USER_ORDER_CREATED));
<<<<<<< HEAD
        Mockito.when(userOrderRepo.findByEmail(USER_ORDER_CREATED.getEmail())).thenReturn(listOrder);

        List<UserOrderClass> userOrderClassList =userOrderService.pendingListOrder(USER_ORDER_CREATED.getEmail());
=======
        Mockito.when(userOrderRepository.findByEmail(USER_ORDER_CREATED.getEmail())).thenReturn(listOrder);

        List<UserOrderClass> userOrderClassList = userOrderServiceImpl.pendingListOrder(USER_ORDER_CREATED.getEmail());
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

        Assertions.assertNotNull(userOrderClassList);
        Assertions.assertSame(userOrderClassList.get(0).getClass(), USER_ORDER_CREATED.getClass());
        Assertions.assertEquals(userOrderClassList.get(0).getAmount(), USER_ORDER_CREATED.getAmount());
    }

    @Test
     void pendingListOrderNotCompleted()
    {

        UserOrderClass userOrderClass =USER_ORDER_CREATED;
        userOrderClass.setDate(userOrderClass.getDate().plusDays(10));
        List<UserOrderClass> listOrder=new ArrayList<>(Arrays.asList(userOrderClass));
<<<<<<< HEAD
        Mockito.when(userOrderRepo.findByEmail(userOrderClass.getEmail())).thenReturn(listOrder);

        List<UserOrderClass> userOrderClassList =userOrderService.pendingListOrder(userOrderClass.getEmail());
=======
        Mockito.when(userOrderRepository.findByEmail(userOrderClass.getEmail())).thenReturn(listOrder);

        List<UserOrderClass> userOrderClassList = userOrderServiceImpl.pendingListOrder(userOrderClass.getEmail());
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

        Assertions.assertNotNull(userOrderClassList);
        Assertions.assertSame(userOrderClassList.get(0).getClass(), USER_ORDER_CREATED.getClass());
        Assertions.assertEquals(userOrderClassList.get(0).getAmount(), USER_ORDER_CREATED.getAmount());
    }

    @Test
     void orderListOrder()
    {
        List<UserOrderClass> listOrder=new ArrayList<>(Arrays.asList(USER_ORDER_COM));
<<<<<<< HEAD
        Mockito.when(userOrderRepo.findByEmail(USER_ORDER_COM.getEmail())).thenReturn(listOrder);

        List<UserOrderClass> userOrderClassList =userOrderService.OrderListOrder(USER_ORDER_COM.getEmail());
=======
        Mockito.when(userOrderRepository.findByEmail(USER_ORDER_COM.getEmail())).thenReturn(listOrder);

        List<UserOrderClass> userOrderClassList = userOrderServiceImpl.orderListByEmail(USER_ORDER_COM.getEmail());
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48


        Assertions.assertNotNull(userOrderClassList);
        Assertions.assertEquals(userOrderClassList.get(0).getAmount(), USER_ORDER_COM.getAmount());
    }



 @Test
 @DisplayName("All my user from a fitness center")
 void allMyUser()
 {
     UserOrderClass USER_ORDER_COM=new UserOrderClass("orderId", "Rahul", "GM1",
             SERVICE,"Monthly" ,"Evening",2000, CURRENT,
             "Completed", "paymentId", "receipt",
             LocalDate.now(), LocalTime.now());
     UserOrderClass USER_ORDER_COM1=new UserOrderClass("orderId", "Rahul", "GM1",
             SERVICE,"Monthly" ,"Evening",2000, CURRENT,
             "Completed", "paymentId", "receipt",
             LocalDate.now(), LocalTime.now());
     UserAttendanceClass USER_ATT=new UserAttendanceClass(
             "Rahul",
             "GM1",
             "Manish",
             30,
             attendance.size(),
             attendance,
             4.0
     );
     UserClass USER1=new UserClass(
             "Rahul",
             "Rahul Khamperia",
             "7651977515",
             "Rahul@123",
             "USER",
             false,
             false,
             true
     );
     List<UserOrderClass> listOrder=new ArrayList<>(Arrays.asList(USER_ORDER_COM, USER_ORDER_COM1));
     Optional<GymClass> optional_gym=Optional.of(FITNESS2);


<<<<<<< HEAD
     Mockito.when(userOrderRepo.findByGym(USER_ORDER_COM.getGym())).thenReturn(listOrder);
     Mockito.when(gymRepo.findById(USER_ORDER_COM.getGym())).thenReturn(optional_gym);
     Mockito.when(attendanceRepo.findByEmailAndVendor(USER_ORDER_COM.getEmail(), VENDOR)).thenReturn(USER_ATT);
     Mockito.when(userRepository.findByEmail(USER_ORDER_COM.getEmail())).thenReturn(USER1);

     Set<UserPerfomanceModel> userPerfomanceModelSet1=userOrderService.allMyUser(USER_ORDER_COM.getGym());
=======
     Mockito.when(userOrderRepository.findByGym(USER_ORDER_COM.getGym())).thenReturn(listOrder);
     Mockito.when(gymRepo.findById(USER_ORDER_COM.getGym())).thenReturn(optional_gym);
     Mockito.when(attendanceRepository.findByEmailAndVendor(USER_ORDER_COM.getEmail(), VENDOR)).thenReturn(USER_ATT);
     Mockito.when(userRepository.findByEmail(USER_ORDER_COM.getEmail())).thenReturn(USER1);

     Set<UserPerfomanceModel> userPerfomanceModelSet1= userOrderServiceImpl.allMyUser(USER_ORDER_COM.getGym());
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
     UserPerfomanceModel up= userPerfomanceModelSet1.stream().findFirst().get();
     Assertions.assertNotNull(userPerfomanceModelSet1);
     Assertions.assertEquals("GM1", up.getGym());

 }



    @Test
     void bookedGym()
    {
        String filter="Current";
        List<UserOrderClass> listOrder=new ArrayList<>(Arrays.asList(USER_ORDER_COM, USER_ORDER_EX));
<<<<<<< HEAD
        Mockito.when(userOrderRepo.findByEmail(USER_ORDER_COM.getEmail())).thenReturn(listOrder);
=======
        Mockito.when(userOrderRepository.findByEmail(USER_ORDER_COM.getEmail())).thenReturn(listOrder);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        Mockito.when(messageComponents.gymModelByStatus(listOrder, "Current")).thenReturn(bookedGymModel);
        Mockito.when(messageComponents.gymModelByStatus(listOrder, "Expired")).thenReturn(bookedGymModel);


<<<<<<< HEAD
        List<BookedGymModel> bookedGyms=userOrderService.bookedGym("rahul");
=======
        List<BookedGymModel> bookedGyms= userOrderServiceImpl.bookedGym("rahul");
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

        Assertions.assertNotNull(bookedGyms);
        Assertions.assertEquals( "GM1", bookedGyms.get(0).getId());

    }

   @Test
   void bookedGymForNullGymModel()
   {
       String filter="Current";
       List<UserOrderClass> listOrder=new ArrayList<>(Arrays.asList(USER_ORDER_COM, USER_ORDER_EX));
<<<<<<< HEAD
       Mockito.when(userOrderRepo.findByEmail(USER_ORDER_COM.getEmail())).thenReturn(listOrder);
=======
       Mockito.when(userOrderRepository.findByEmail(USER_ORDER_COM.getEmail())).thenReturn(listOrder);
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
       Mockito.when(messageComponents.gymModelByStatus(listOrder, "Current")).thenReturn(null);
       Mockito.when(messageComponents.gymModelByStatus(listOrder, "Expired")).thenReturn(null);


<<<<<<< HEAD
       List<BookedGymModel> bookedGyms=userOrderService.bookedGym("rahul");
=======
       List<BookedGymModel> bookedGyms= userOrderServiceImpl.bookedGym("rahul");
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
       //System.out.println(bookedGyms);
       Assertions.assertNotNull(bookedGyms);
       Assertions.assertEquals( 2, bookedGyms.size());

   }
    @Test
    void bookedGYmForNullOrder()
    {
<<<<<<< HEAD
        Mockito.when(userOrderRepo.findByEmail(USER_ORDER_COM.getEmail())).thenReturn(null);
        List<BookedGymModel> bookedGyms=userOrderService.bookedGym("rahul");
=======
        Mockito.when(userOrderRepository.findByEmail(USER_ORDER_COM.getEmail())).thenReturn(null);
        List<BookedGymModel> bookedGyms= userOrderServiceImpl.bookedGym("rahul");
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

        Assertions.assertNotNull(bookedGyms);
        Assertions.assertEquals(0, bookedGyms.size());
    }

    @Test
    void canOrder()
    {
        List<UserOrderClass> listOrder=new ArrayList<>(Arrays.asList(USER_ORDER_EX));
<<<<<<< HEAD
        Mockito.when(userOrderRepo.findByEmail(USER_ORDER_EX.getEmail())).thenReturn(listOrder);

        Boolean ans=userOrderService.canOrder(USER_ORDER_EX.getEmail());
=======
        Mockito.when(userOrderRepository.findByEmail(USER_ORDER_EX.getEmail())).thenReturn(listOrder);

        Boolean ans= userOrderServiceImpl.canOrder(USER_ORDER_EX.getEmail());
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
         Assertions.assertTrue(ans);


    }
    @Test
    void canOrderNew()
    {
        List<UserOrderClass> listOrder=new ArrayList<>(Arrays.asList(USER_ORDER_EX));
<<<<<<< HEAD
        Mockito.when(userOrderRepo.findByEmail(USER_ORDER_EX.getEmail())).thenReturn(listOrder);

        Boolean ans=userOrderService.canOrder(USER_ORDER_EX.getEmail());
=======
        Mockito.when(userOrderRepository.findByEmail(USER_ORDER_EX.getEmail())).thenReturn(listOrder);

        Boolean ans= userOrderServiceImpl.canOrder(USER_ORDER_EX.getEmail());
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        Assertions.assertTrue(ans);
    }

    @Test
    @DisplayName("Can order if order is empty:")
    void canOrderSetExp()
    {
        UserOrderClass USER_ORDER_EX=new UserOrderClass("orderId", "rahul", "GM1",
                SERVICE,"Monthly" ,"Evening",2000, "Expired",
                "Expired", "paymentId", "receipt",
                LocalDate.of(2, 5, 3), LocalTime.now());

        List<UserOrderClass> listOrder=new ArrayList<>(Arrays.asList(USER_ORDER_EX));
<<<<<<< HEAD
        Mockito.when(userOrderRepo.findByEmail(USER_ORDER_EX.getEmail())).thenReturn(listOrder);

        Boolean ans=userOrderService.canOrder(USER_ORDER_EX.getEmail());
=======
        Mockito.when(userOrderRepository.findByEmail(USER_ORDER_EX.getEmail())).thenReturn(listOrder);

        Boolean ans= userOrderServiceImpl.canOrder(USER_ORDER_EX.getEmail());
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        Assertions.assertTrue(ans);
    }

    @Test
    @DisplayName("can order for empty order list:")
    void canOrderForEmptyOrder()
    {

<<<<<<< HEAD
        Mockito.when(userOrderRepo.findByEmail(USER_ORDER_EX.getEmail())).thenReturn(null
        );

        Boolean ans=userOrderService.canOrder(USER_ORDER_EX.getEmail());
=======
        Mockito.when(userOrderRepository.findByEmail(USER_ORDER_EX.getEmail())).thenReturn(null
        );

        Boolean ans= userOrderServiceImpl.canOrder(USER_ORDER_EX.getEmail());
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        Assertions.assertTrue(ans);
    }
    @Test
     void canOrderWithFalse()
    {
        List<UserOrderClass> listOrder=new ArrayList<>(Arrays.asList(USER_ORDER_COM,USER_ORDER_EX));
<<<<<<< HEAD
        Mockito.when(userOrderRepo.findByEmail(USER_ORDER_EX.getEmail())).thenReturn(listOrder);
        Boolean ans=userOrderService.canOrder(USER_ORDER_EX.getEmail());
=======
        Mockito.when(userOrderRepository.findByEmail(USER_ORDER_EX.getEmail())).thenReturn(listOrder);
        Boolean ans= userOrderServiceImpl.canOrder(USER_ORDER_EX.getEmail());
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48
        Assertions.assertFalse(ans);
    }

    @Test
     void canOrderWithFalseAndSetEX()
    {
        UserOrderClass order=USER_ORDER_COM;
        order.setDate(order.getDate().plusDays(120));
        List<UserOrderClass> listOrder=new ArrayList<>(Arrays.asList(order));
<<<<<<< HEAD
        Mockito.when(userOrderRepo.findByEmail(order.getEmail())).thenReturn(listOrder);

        Boolean ans=userOrderService.canOrder(order.getEmail());
=======
        Mockito.when(userOrderRepository.findByEmail(order.getEmail())).thenReturn(listOrder);

        Boolean ans= userOrderServiceImpl.canOrder(order.getEmail());
>>>>>>> ab44702953f521464a7b7eaa187535692b51af48

        Assertions.assertFalse(ans);
    }





}
