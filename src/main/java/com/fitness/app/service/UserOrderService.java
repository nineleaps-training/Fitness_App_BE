package com.fitness.app.service;

import com.fitness.app.entity.UserOrderClass;
import com.fitness.app.model.BookedGymModel;
import com.fitness.app.model.UserPerfomanceModel;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserOrderService {

    void orderNow(UserOrderClass userOrderClass);
    UserOrderClass updateOrder(Map<String, String>data);
    List<UserOrderClass> pendingListOrder(String email);
    List<UserOrderClass> orderListByEmail(String email);
    Set<UserPerfomanceModel> allMyUser(String gymId);
    List<BookedGymModel> bookedGym(String email);
    Boolean canOrder(String email);


}
