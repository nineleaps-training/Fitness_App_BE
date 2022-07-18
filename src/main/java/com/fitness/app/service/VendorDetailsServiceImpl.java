package com.fitness.app.service;

import com.fitness.app.entity.UserClass;
import com.fitness.app.entity.VendorDetailsClass;
import com.fitness.app.model.DetailsModel;
import com.fitness.app.repository.UserRepository;
import com.fitness.app.repository.VendorDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorDetailsServiceImpl implements VendorDetailsService {


    final private VendorDetailsRepository vendordetailsRepository;
    final private UserRepository userRepository;

    // register new vendor service function.
    @Override
    public VendorDetailsClass addVendorDetails(DetailsModel vendorDetails) {

        UserClass user = userRepository.findByEmail(vendorDetails.getEmail());

        if (user != null && user.getActivated()) {
            VendorDetailsClass details = new VendorDetailsClass();
            details.setVendorEmail(vendorDetails.getEmail());
            details.setVendorGender(vendorDetails.getGender());
            details.setVendorFullAddress(vendorDetails.getFullAddress());
            details.setVendorCity(vendorDetails.getCity());
            details.setVendorPostal(vendorDetails.getPostal());


            vendordetailsRepository.save(details);
            return details;
        }
        return null;
    }

    @Override
    public VendorDetailsClass getVendorDetails(String email) {

        return vendordetailsRepository.findByVendorEmail(email);
    }
}
