package com.fitness.app.service;

import com.fitness.app.dto.UserOrderModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.UserOrderClass;
import com.fitness.app.dto.BookedGymModel;
import com.fitness.app.dto.UserPerfomanceModel;
import com.razorpay.RazorpayException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserOrderService {

    String orderNow(UserOrderModel model) throws RazorpayException;

    ApiResponse updateOrder(Map<String, String> data);

    List<UserOrderClass> pendingListOrder(String email);

    List<UserOrderClass> orderListByEmail(String email);

    ApiResponse allMyUser(String gymId);

    List<BookedGymModel> bookedGym(String email);

    Boolean canOrder(String email);


}
