

package com.fitness.app.service;

import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.dto.AdminPayModel;
import com.fitness.app.dto.responceDtos.ApiResponse;
import com.fitness.app.entity.AdminPayClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.exceptions.UserNotFoundException;
import com.razorpay.RazorpayException;

import java.util.List;
import java.util.Map;

public interface AdminService {

    String authenticateAdmin(Authenticate authenticate) throws UserNotFoundException;

    List<UserClass> getAllUsersService();
    List<UserClass> getAllVendorService();

    List<GymClass> getAllGymsByEmail( String email);
    List<GymClass> getAllFitnessCenter();
    AdminPayClass getDataPay(AdminPayModel adminPayModel);
    ApiResponse PayNow(AdminPayModel adminPayModel) throws RazorpayException;
    AdminPayClass vendorPayment(String vendor);
    AdminPayClass updatePayment(Map<String, String> data);
    List<AdminPayClass> paidHistoryVendor(String vendor);

    List<String>  getAllNumber();
}

