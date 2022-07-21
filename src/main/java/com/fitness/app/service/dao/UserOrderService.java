package com.fitness.app.service.dao;

import com.fitness.app.dto.requestDtos.UserOrderModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.UserOrderClass;
import com.fitness.app.dto.responceDtos.BookedGymModel;
import com.razorpay.RazorpayException;

import java.util.List;
import java.util.Map;

public interface UserOrderService {

    String orderNow(UserOrderModel model) throws RazorpayException;

    ApiResponse updateOrder(Map<String, String> data);

    List<UserOrderClass> pendingListOrder(String email);

    List<UserOrderClass> orderListByEmail(String email);

    ApiResponse allMyUser(String gymId);

    List<BookedGymModel> bookedGym(String email);

    Boolean canOrder(String email);


}
