package com.fitness.app.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.fitness.app.entity.UserOrder;
import com.fitness.app.model.GymRepresentModel;
import com.fitness.app.model.UserOrderModel;
import com.fitness.app.model.UserPerfomanceModel;
import com.razorpay.RazorpayException;

@Component
public interface UserOrderDAO {

    public String orderNow(@Valid UserOrderModel order) throws RazorpayException;

    public UserOrder updateOrder(Map<String, String> data);

    public List<UserOrder> pendingListOrder(String email);

    public List<UserOrder> orderListOrder(String email);

    public Set<UserPerfomanceModel> allMyUser(String gymId);

    public List<GymRepresentModel> bookedGym(String email);

    public Boolean canOrder(String email);


    
}
