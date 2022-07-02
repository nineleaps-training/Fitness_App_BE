package com.register.app.service;

import com.fitness.app.entity.*;
import com.fitness.app.model.BookedGymModel;
import com.fitness.app.model.UserPerfomanceModel;
import com.fitness.app.repository.*;
import com.fitness.app.service.UserOrderService;
import org.junit.jupiter.api.Assertions;
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
public class UserOrderServiceTest {


    @Mock
    private UserOrderRepo userOrderRepo;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddGymRepository gymRepo;

    @Mock
    private VendorPayRepo vendorPayRepo;

    @Mock
    private GymAddressRepo gymAddressRepo;
    @Mock
    private AttendanceRepo attendanceRepo;

    @InjectMocks
    private com.fitness.app.service.UserOrderService userOrderService;

    List<String>SERVICE=new ArrayList<>(Arrays.asList("Dance","Zumba","Karate"));
    final String CURRENT="Current";
    final String VENDOR="Manish";
    UserOrder USER_ORDER=new UserOrder("orderId", "rahul", "GM1",
            SERVICE,"Monthly" ,"Evening",2000, CURRENT,
            "Created", "paymentId", "receipt",
            LocalDate.now(), LocalTime.now());
    UserOrder USER_ORDER_CREATED=new UserOrder("orderId", "rahul", "GM1",
            SERVICE,"Monthly" ,"Evening",2000, CURRENT,
            "created", "paymentId", "receipt",
            LocalDate.now(), LocalTime.now());

    UserOrder USER_ORDER_COM=new UserOrder("orderId", "rahul", "GM1",
            SERVICE,"Monthly" ,"Evening",2000, CURRENT,
            "Completed", "paymentId", "receipt",
            LocalDate.now(), LocalTime.now());
    UserOrder USER_ORDER_EX=new UserOrder("orderId", "rahul", "GM1",
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
    UserAttendance USER_ATT=new UserAttendance(
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

    @Test
    public void orderNow()
    {
        userOrderService.orderNow(USER_ORDER);
        Assertions.assertNotNull(USER_ORDER);
    }


    @Test
    public void updateOrderForNUll()
    {
        UserOrder userOrder=userOrderService.updateOrder(null);
        Assertions.assertNull(userOrder);
    }

    @Test
    public void updateOrder()
    {
        HashMap<String, String> data=new HashMap<>();
        data.put("order_id", "orderId");
        data.put("payment_id", "paymentId");
        data.put("status", "Completed");

        Optional<GymClass> optional_gym=Optional.of(FITNESS2);
        Optional<UserOrder> optional_order=Optional.of(USER_ORDER);
        Mockito.when(userOrderRepo.findById(data.get("order_id"))).thenReturn(optional_order);
        Mockito.when(gymRepo.findById(USER_ORDER.getGym())).thenReturn(optional_gym);

        UserOrder userOrder=userOrderService.updateOrder(data);
        Assertions.assertNotNull(userOrder);
        Assertions.assertEquals(userOrder.getStatus(), USER_ORDER_COM.getStatus());

    }

    @Test
    public void pendingListOrder()
    {
        List<UserOrder> listOrder=new ArrayList<>(Arrays.asList(USER_ORDER_CREATED));
        Mockito.when(userOrderRepo.findByEmail(USER_ORDER_CREATED.getEmail())).thenReturn(listOrder);

        List<UserOrder> userOrderList=userOrderService.pendingListOrder(USER_ORDER_CREATED.getEmail());

        Assertions.assertNotNull(userOrderList);
        Assertions.assertSame(userOrderList.get(0).getClass(), USER_ORDER_CREATED.getClass());
        Assertions.assertEquals(userOrderList.get(0).getAmount(), USER_ORDER_CREATED.getAmount());
    }

    @Test
    public void pendingListOrderNotCompleted()
    {

        UserOrder userOrder=USER_ORDER_CREATED;
        userOrder.setDate(userOrder.getDate().plusDays(10));
        List<UserOrder> listOrder=new ArrayList<>(Arrays.asList(userOrder));
        Mockito.when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(listOrder);

        List<UserOrder> userOrderList=userOrderService.pendingListOrder(userOrder.getEmail());

        Assertions.assertNotNull(userOrderList);
        Assertions.assertSame(userOrderList.get(0).getClass(), USER_ORDER_CREATED.getClass());
        Assertions.assertEquals(userOrderList.get(0).getAmount(), USER_ORDER_CREATED.getAmount());
    }

    @Test
    public void orderListOrder()
    {
        List<UserOrder> listOrder=new ArrayList<>(Arrays.asList(USER_ORDER_COM));
        Mockito.when(userOrderRepo.findByEmail(USER_ORDER_COM.getEmail())).thenReturn(listOrder);

        List<UserOrder>userOrderList=userOrderService.OrderListOrder(USER_ORDER_COM.getEmail());


        Assertions.assertNotNull(userOrderList);
        Assertions.assertEquals(userOrderList.get(0).getAmount(), USER_ORDER_COM.getAmount());
    }


    @Test
    public void allMyUser()
    {
        Set<UserPerfomanceModel> userPerfomanceModelSet=new HashSet<>();
        List<UserOrder> listOrder=new ArrayList<>(Arrays.asList(USER_ORDER_COM));
        userPerfomanceModelSet.add(PERFORMANCE_MODEL);
        Optional<GymClass> optional_gym=Optional.of(FITNESS2);
        Mockito.lenient().when(userOrderRepo.findByEmail(USER_ORDER_COM.getEmail())).thenReturn(listOrder);
        Mockito.lenient().when(gymRepo.findById("GM1")).thenReturn(optional_gym);
        Mockito.lenient().when(attendanceRepo.findByEmailAndVendor(USER_ORDER_COM.getEmail(), VENDOR)).thenReturn(USER_ATT);
        Mockito.lenient().when(userRepository.findByEmail(USER_ORDER_COM.getEmail())).thenReturn(USER1);

        Set<UserPerfomanceModel> userPerfomanceModelSet1=userOrderService.allMyUser("GM1");
        System.out.println(userPerfomanceModelSet1);
        Assertions.assertNotNull(userPerfomanceModelSet1);


    }

    @Test
    public void bookedGym()
    {
        List<UserOrder> listOrder=new ArrayList<>(Arrays.asList(USER_ORDER_COM, USER_ORDER_EX));
        Optional<GymClass> gymClasses=Optional.of(FITNESS2);
        Optional<GymAddressClass> gymAddresses=Optional.of(GYM_ADDRESS_ID);
        Mockito.when(userOrderRepo.findByEmail(USER_ORDER_COM.getEmail())).thenReturn(listOrder);
        Mockito.when(gymRepo.findById("GM1")).thenReturn(gymClasses);
        Mockito.when(gymAddressRepo.findById("GM1")).thenReturn(gymAddresses);

        List<BookedGymModel> bookedGyms=userOrderService.bookedGym("rahul");

        Assertions.assertNotNull(bookedGyms);
        Assertions.assertEquals(bookedGyms.get(0).getId(), "GM1");

    }


    @Test
    public void canOrder()
    {
        List<UserOrder> listOrder=new ArrayList<>(Arrays.asList(USER_ORDER_EX));
        Mockito.when(userOrderRepo.findByEmail(USER_ORDER_EX.getEmail())).thenReturn(listOrder);

        Boolean ans=userOrderService.canOrder(USER_ORDER_EX.getEmail());
         Assertions.assertTrue(ans);
        //Assertions.assertFalse(ans);

    }

    @Test
    public void canOrderWithFalse()
    {
        List<UserOrder> listOrder=new ArrayList<>(Arrays.asList(USER_ORDER_COM,USER_ORDER_EX));
        Mockito.when(userOrderRepo.findByEmail(USER_ORDER_EX.getEmail())).thenReturn(listOrder);

        Boolean ans=userOrderService.canOrder(USER_ORDER_EX.getEmail());

        Assertions.assertFalse(ans);
    }

    @Test
    public void canOrderWithFalseAndSetEX()
    {
        UserOrder order=USER_ORDER_COM;
        order.setDate(order.getDate().plusDays(30));
        List<UserOrder> listOrder=new ArrayList<>(Arrays.asList(USER_ORDER_COM,USER_ORDER_EX));
        Mockito.when(userOrderRepo.findByEmail(USER_ORDER_EX.getEmail())).thenReturn(listOrder);

        Boolean ans=userOrderService.canOrder(USER_ORDER_EX.getEmail());

        Assertions.assertFalse(ans);
    }



}
