

package com.fitness.app.service.dao;

import com.fitness.app.dto.auth.Authenticate;
import com.fitness.app.dto.request.AdminPayModel;
import com.fitness.app.dto.response.ApiResponse;
import com.fitness.app.entity.AdminPayClass;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.exceptions.UserNotFoundException;
import com.razorpay.RazorpayException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;


public interface AdminDao {

    String authenticateAdmin(Authenticate authenticate) throws UserNotFoundException;
    List<UserClass> getAllUsersService(int offSet, int pageSize);
    List<UserClass> getAllVendorService(int offSet, int pageSize);

    List<GymClass> getAllGymsByEmail( String email, int offSet, int pageSize);
    Page<GymClass> getAllFitnessCenter(int offSet, int pageSize);
    AdminPayClass getDataPay(AdminPayModel adminPayModel);
    ApiResponse PayNow(AdminPayModel adminPayModel) throws RazorpayException;
    AdminPayClass vendorPayment(String vendor);
    AdminPayClass updatePayment(Map<String, String> data);
    List<AdminPayClass> paidHistoryVendor(String vendor);

    List<String>  getAllNumber();
}

