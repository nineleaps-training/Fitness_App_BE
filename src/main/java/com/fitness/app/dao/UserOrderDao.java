package com.fitness.app.dao;

import com.fitness.app.entity.UserOrder;
import com.fitness.app.model.GymRepresent;
import com.fitness.app.model.UserOrderModel;
import com.fitness.app.model.UserPerformanceModel;
import com.razorpay.RazorpayException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserOrderDao {

    String orderNow(UserOrderModel userOrder) throws RazorpayException;
    UserOrder updateOrder(Map<String, String> data);
    List<UserOrder> pendingListOrder(String email);
    List<UserOrder> orderListOrder(String email);
    Set<UserPerformanceModel> allMyUser(String gymId);
    List<GymRepresent> bookedGym(String email);
    Boolean canOrder(String email);
}
