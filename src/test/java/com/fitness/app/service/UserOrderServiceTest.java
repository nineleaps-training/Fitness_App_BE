package com.fitness.app.service;

import com.fitness.app.entity.*;
import com.fitness.app.model.GymRepresent;
import com.fitness.app.model.UserPerformanceModel;
import com.fitness.app.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserOrderServiceTest {

    HashMap<String, String> data;

    @MockBean
    private UserOrderRepo userOrderRepo;

    @MockBean
    private AddGymRepository gymRepo;

    @MockBean
    private AttendanceRepo attendanceRepo;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private VendorPayRepo vendorOrderRepo;

    @MockBean
    private GymService gymService;

    @Autowired
    UserOrderService userOrderService;


    @BeforeEach
    public void setUp() {
        data = new HashMap<>();
        data.put("order_id", "12345");
        data.put("payment_id", "54321");
        data.put("status", "Completed");

    }


    @Test
    void orderNow() {
        List<String> services = new ArrayList<>();

        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com",
                "Fitness", services, "monthly", "Evening", 500, "Expired",
                "created", "123abc", "112233", LocalDate.now(), LocalTime.now());

        userOrderService.orderNow(userOrder);
        assertNotNull(userOrder);
    }

    @Test
    void updateOrderIfSubscriptionIsMonthly() {
        List<String> workout = new ArrayList<>();
        List<String> services = new ArrayList<>();

        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);
        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", services, "monthly", "Evening", 500, "Expired", "Due", "123abc", "112233", LocalDate.now(), LocalTime.now());

        when(userOrderRepo.findById(data.get("order_id"))).thenReturn(Optional.of(userOrder));
        when(gymRepo.findById(any())).thenReturn(Optional.of(gymClass));

        UserOrder actual = userOrderService.updateOrder(data);
        assertEquals(userOrder, actual);
    }

    @Test
    void updateOrderIfSubscriptionIsQuarterly() {
        List<String> workout = new ArrayList<>();
        List<String> services = new ArrayList<>();

        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);
        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", services, "quarterly", "Evening", 500, "Expired", "Due", "123abc", "112233", LocalDate.now(), LocalTime.now());

        when(userOrderRepo.findById(data.get("order_id"))).thenReturn(Optional.of(userOrder));
        when(gymRepo.findById(any())).thenReturn(Optional.of(gymClass));

        UserOrder actual = userOrderService.updateOrder(data);
        assertEquals(userOrder, actual);
    }

    @Test
    void updateOrderIfSubscriptionIsHalfYearly() {
        List<String> workout = new ArrayList<>();
        List<String> services = new ArrayList<>();

        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);
        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", services, "half", "Evening", 500, "Expired", "Due", "123abc", "112233", LocalDate.now(), LocalTime.now());

        when(userOrderRepo.findById(data.get("order_id"))).thenReturn(Optional.of(userOrder));
        when(gymRepo.findById(any())).thenReturn(Optional.of(gymClass));

        UserOrder actual = userOrderService.updateOrder(data);
        assertEquals(userOrder, actual);
    }

    @Test
    void updateOrderIfSubscriptionIsYearly() {
        List<String> workout = new ArrayList<>();
        List<String> services = new ArrayList<>();

        GymClass gymClass = new GymClass("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);
        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", services, "Yearly", "Evening", 500, "Expired", "Due", "123abc", "112233", LocalDate.now(), LocalTime.now());

        when(userOrderRepo.findById(data.get("order_id"))).thenReturn(Optional.of(userOrder));
        when(gymRepo.findById(any())).thenReturn(Optional.of(gymClass));

        UserOrder actual = userOrderService.updateOrder(data);
        assertEquals(userOrder, actual);
    }

    @Test
    void pendingListOrder() {
        List<String> services = new ArrayList<>();

        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", services, "monthly", "Evening", 500, "Expired", "created", "123abc", "112233", LocalDate.now(), LocalTime.now());
        List<UserOrder> orders = new ArrayList<>();
        orders.add(userOrder);

        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn((orders));

        List<UserOrder> actual = userOrderService.pendingListOrder(userOrder.getEmail());
        assertEquals(orders, actual);
    }

    @Test
    void pendingListOrderIfDateIsNotSame() {
        List<String> services = new ArrayList<>();

        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", services, "monthly", "Evening", 500, "Expired", "created", "123abc", "112233", LocalDate.MIN, LocalTime.now());
        List<UserOrder> orders = new ArrayList<>();
        orders.add(userOrder);

        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn((orders));

        List<UserOrder> actual = userOrderService.pendingListOrder(userOrder.getEmail());
        assertEquals(orders, actual);
    }

    @Test
    void orderListOrder() {
        List<String> services = new ArrayList<>();

        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", services, "monthly", "Evening", 500, "Expired", "Completed", "123abc", "112233", LocalDate.now(), LocalTime.now());

        List<UserOrder> orders = new ArrayList<>();
        orders.add(userOrder);

        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(orders);

        List<UserOrder> actual = userOrderService.orderListOrder(userOrder.getEmail());
        assertEquals(orders, actual);
    }

    @Test
    void returnAllOrderListWhenStatusIsCompleted() {
        List<String> services = new ArrayList<>();

        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness",
                services, "monthly", "Evening", 500, "Expired", "Completed",
                "123abc", "112233", LocalDate.now(), LocalTime.now());
        UserOrder userOrder1 = new UserOrder("2", "priyanshi.chaturvedi@nineleaps.com", "Fitness",
                services, "monthly", "Evening", 500, "Expired", "Completed",
                "123abc", "112233", LocalDate.now(), LocalTime.now());

        List<UserOrder> orders = new ArrayList<>();
        orders.add(userOrder);
        orders.add(userOrder1);

        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(orders);

        List<UserOrder> actual = userOrderService.orderListOrder(userOrder.getEmail());
        assertEquals(orders, actual);
    }

    @Test
    void doNotReturnOrderListWhenStatusIsNotCompleted() {
        List<String> services = new ArrayList<>();

        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness",
                services, "monthly", "Evening", 500, "Expired", "Due",
                "123abc", "112233", LocalDate.now(), LocalTime.now());
        UserOrder userOrder1 = new UserOrder("2", "priyanshi.chaturvedi@nineleaps.com", "Fitness",
                services, "monthly", "Evening", 500, "Expired", "Created",
                "123abc", "112233", LocalDate.now(), LocalTime.now());

        List<UserOrder> orders = new ArrayList<>();
        orders.add(userOrder);
        orders.add(userOrder1);

        List<UserOrder> orderNull = new ArrayList<>();

        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(orders);

        List<UserOrder> actual = userOrderService.orderListOrder(userOrder.getEmail());
        assertEquals(orderNull, actual);
    }

    @Test
    void returnOnlyThoseOrderListWhoseStatusIsCompleted() {
        List<String> services = new ArrayList<>();

        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness",
                services, "monthly", "Evening", 500, "Expired", "Completed",
                "123abc", "112233", LocalDate.now(), LocalTime.now());
        UserOrder userOrder1 = new UserOrder("2", "priyanshi.chaturvedi@nineleaps.com", "Fitness",
                services, "monthly", "Evening", 500, "Expired", "Created",
                "123abc", "112233", LocalDate.now(), LocalTime.now());

        List<UserOrder> orders = new ArrayList<>();
        orders.add(userOrder);
        orders.add(userOrder1);

        List<UserOrder> orderList = new ArrayList<>();
        orderList.add(userOrder);

        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(orders);

        List<UserOrder> actual = userOrderService.orderListOrder(userOrder.getEmail());
        assertEquals(orderList, actual);
    }

    @Test
    void returnNUllOrderListIfStatusIsNull() {
        UserOrder userOrder = new UserOrder();
        List<UserOrder> orders = new ArrayList<>();

        List<UserOrder> orderNull = new ArrayList<>();

        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn((orders));

        List<UserOrder> actual = userOrderService.orderListOrder(userOrder.getEmail());
        assertEquals(orderNull, actual);
    }

    @Test
    void allMyUser() {
        List<String> services = new ArrayList<>();
        List<UserOrder> orders = new ArrayList<>();

        UserOrder userOrder = new UserOrder("Fitness", "priyanshi.chaturvedi@nineleaps.com", "Fitness", services,
                "monthly", "Evening", 500, "Expired", "Completed",
                "123abc", "112233", LocalDate.now(), LocalTime.now());
        orders.add(userOrder);
        List<Integer> attendance1 = new ArrayList<>();

        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 5, 1, attendance1, 4.2);

        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi", "9685903290", "12345", "Enthusiast", true, true, true);
        List<String> workout = new ArrayList<>();
        GymClass gymClass = new GymClass("Fitness", "priyanshi.chaturvedi@nineleaps.com", "Fitness", workout, 9685903290L, 4.2, 100);

        UserPerformanceModel userPerformanceModel = new UserPerformanceModel("Priyanshi", "priyanshi.chaturvedi@nineleaps.com", "Fitness", "priyanshi.chaturvedi@nineleaps.com", attendance1, 4.2);
        Set<UserPerformanceModel> users = new HashSet<>();
        users.add(userPerformanceModel);

        when(userOrderRepo.findByGym(userOrder.getId())).thenReturn(orders);
        when(gymRepo.findById(userOrder.getId())).thenReturn(Optional.of(gymClass));
        when(attendanceRepo.findByEmailAndVendor(userOrder.getEmail(), userOrder.getEmail())).thenReturn(userAttendance);
        when(userRepository.findByEmail(any())).thenReturn(userClass);

        Set<UserPerformanceModel> actual = userOrderService.allMyUser(userOrder.getId());
        assertEquals(users, actual);
    }

    @Test
    void returnAllMyUserIfOptionalIsNotPresent() {
        List<String> services = new ArrayList<>();
        List<UserOrder> orders = new ArrayList<>();

        UserOrder userOrder = new UserOrder("Fitness", "priyanshi.chaturvedi@nineleaps.com", "Fitness", services,
                "monthly", "Evening", 500, "Expired", "Completed",
                "123abc", "112233", LocalDate.now(), LocalTime.now());
        orders.add(userOrder);
        List<Integer> attendance1 = new ArrayList<>();

        UserAttendance userAttendance = new UserAttendance("priyanshi.chaturvedi@nineleaps.com", "Fitness", "Priyanshi", 5, 1, attendance1, 4.2);

        UserClass userClass = new UserClass("priyanshi.chaturvedi@nineleaps.com", "Priyanshi", "9685903290", "12345", "Enthusiast", true, true, true);
        List<String> workout = new ArrayList<>();
        GymClass gymClass = new GymClass();
        gymClass.setEmail("priyanshi.chaturvedi@nineleaps.com");

        UserPerformanceModel userPerformanceModel = new UserPerformanceModel("Priyanshi", "priyanshi.chaturvedi@nineleaps.com", "Fitness", "priyanshi.chaturvedi@nineleaps.com", attendance1, 4.2);
        Set<UserPerformanceModel> users = new HashSet<>();
        users.add(userPerformanceModel);

        when(userOrderRepo.findByGym(userOrder.getId())).thenReturn(orders);
        when(gymRepo.findById(userOrder.getId())).thenReturn(Optional.of(gymClass));
        when(attendanceRepo.findByEmailAndVendor(userOrder.getEmail(), userOrder.getEmail())).thenReturn(userAttendance);
        when(userRepository.findByEmail(any())).thenReturn(userClass);

        Set<UserPerformanceModel> actual = userOrderService.allMyUser(userOrder.getId());
        assertEquals(users, actual);
    }

    @Test
    void bookedGym() {
        List<String> services = new ArrayList<>();
        List<UserOrder> orders = new ArrayList<>();

        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com",
                "Fitness", services, "monthly", "Evening", 500, "Expired",
                "Completed", "123abc", "112233", LocalDate.now(), LocalTime.now());
        orders.add(userOrder);
        GymAddressClass gymAddressClass = new GymAddressClass();
        List<String> workout = new ArrayList<>();
        GymTime gymTime = new GymTime();
        GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass();

        GymRepresent gymRepresent = new GymRepresent("1", "priyanshi.chaturvedi@nineleaps.com",
                "Fitness", gymAddressClass, workout, gymTime, gymSubscriptionClass,
                9685903290L, 4.2, 100);
        List<GymRepresent> gymRepresents = new ArrayList<>();
        gymRepresents.add(gymRepresent);

        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(orders);
        when(gymService.getGymByGymId(any())).thenReturn(gymRepresent);

        List<GymRepresent> actual = userOrderService.bookedGym(userOrder.getEmail());
        assertEquals(gymRepresents, actual);
    }

    @Test
    void returnBookedGymIfBookedIsCurrent() {
        List<String> services = new ArrayList<>();
        List<UserOrder> orders = new ArrayList<>();

        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com",
                "Fitness", services, "monthly", "Evening", 500, "Current",
                "Completed", "123abc", "112233", LocalDate.now(), LocalTime.now());
        orders.add(userOrder);
        GymAddressClass gymAddressClass = new GymAddressClass();
        List<String> workout = new ArrayList<>();
        GymTime gymTime = new GymTime();
        GymSubscriptionClass gymSubscriptionClass = new GymSubscriptionClass();

        GymRepresent gymRepresent = new GymRepresent("1", "priyanshi.chaturvedi@nineleaps.com",
                "Fitness", gymAddressClass, workout, gymTime, gymSubscriptionClass,
                9685903290L, 4.2, 100);
        List<GymRepresent> gymRepresents = new ArrayList<>();
        gymRepresents.add(gymRepresent);

        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(orders);
        when(gymService.getGymByGymId(any())).thenReturn(gymRepresent);

        List<GymRepresent> actual = userOrderService.bookedGym(userOrder.getEmail());
        assertEquals(gymRepresents, actual);
    }


    @Test
    void returnNullIfUserOrderIsNull() {
        UserOrder userOrder = new UserOrder();
        List<UserOrder> orders = null;

        GymRepresent gymRepresent = new GymRepresent();
        List<GymRepresent> gymRepresents = new ArrayList<>();

        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(orders);
        when(gymService.getGymByGymId(any())).thenReturn(gymRepresent);

        List<GymRepresent> actual = userOrderService.bookedGym(userOrder.getEmail());
        assertEquals(gymRepresents, actual);
    }

    @Test
    void returnTrueIfUserOrderIsNull() {
        List<UserOrder> orders = new ArrayList<>();

        UserOrder userOrder = new UserOrder();
        userOrder.setEmail("priyanshi.chaturvedi@nineleaps.com");

        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(orders);

        Boolean actual = userOrderService.canOrder(userOrder.getEmail());
        assertEquals(true, actual);
    }

    @Test
    void returnTrueIfUserOrderIsNotNull() {
        List<String> services = new ArrayList<>();

        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", services, "monthly", "Evening", 500, "Current", "Completed", "123abc", "112233", LocalDate.MIN, LocalTime.now());
        List<UserOrder> orders = new ArrayList<>();
        orders.add(userOrder);

        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(orders);

        Boolean actual = userOrderService.canOrder(userOrder.getEmail());
        assertEquals(false, actual);
    }

    @Test
    void returnTrueIfSubscriptionIsMonthly() {
        List<String> services = new ArrayList<>();

        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", services, "Monthly", "Evening", 500, "Current", "Completed", "123abc", "112233", LocalDate.now(), LocalTime.now());
        List<UserOrder> orders = new ArrayList<>();
        orders.add(userOrder);

        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(orders);

        Boolean actual = userOrderService.canOrder(userOrder.getEmail());
        assertEquals(false, actual);
    }

    @Test
    void returnTrueIfSubscriptionIsQuarterly() {
        List<String> services = new ArrayList<>();

        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", services, "Quarterly", "Evening", 500, "Current", "Completed", "123abc", "112233", LocalDate.now(), LocalTime.now());
        List<UserOrder> orders = new ArrayList<>();
        orders.add(userOrder);

        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(orders);

        Boolean actual = userOrderService.canOrder(userOrder.getEmail());
        assertEquals(false, actual);
    }

    @Test
    void returnTrueIfSubscriptionIsHalfYearly() {
        List<String> services = new ArrayList<>();

        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", services, "Half", "Evening", 500, "Current", "Completed", "123abc", "112233", LocalDate.now(), LocalTime.now());
        List<UserOrder> orders = new ArrayList<>();
        orders.add(userOrder);

        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(orders);

        Boolean actual = userOrderService.canOrder(userOrder.getEmail());
        assertEquals(false, actual);
    }

    @Test
    void returnTrueIfSubscriptionIsYearly() {
        List<String> services = new ArrayList<>();

        UserOrder userOrder = new UserOrder("1", "priyanshi.chaturvedi@nineleaps.com", "Fitness", services, "Yearly", "Evening", 500, "Current", "Completed", "123abc", "112233", LocalDate.now(), LocalTime.now());
        List<UserOrder> orders = new ArrayList<>();
        orders.add(userOrder);

        when(userOrderRepo.findByEmail(userOrder.getEmail())).thenReturn(orders);

        Boolean actual = userOrderService.canOrder(userOrder.getEmail());
        assertEquals(false, actual);
    }

}