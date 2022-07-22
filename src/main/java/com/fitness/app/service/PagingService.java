package com.fitness.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fitness.app.dao.PagingDAO;
import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserBankDetails;
import com.fitness.app.entity.UserClass;
import com.fitness.app.exception.DataNotFoundException;
import com.fitness.app.repository.AddGymRepo;
import com.fitness.app.repository.UserBankDetailsRepo;
import com.fitness.app.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PagingService implements PagingDAO {

    AddGymRepo gymRepository;
    
    UserRepo userRepository;

    UserBankDetailsRepo repo;

    // Initializing constructor
    /**
     * This constructor is used to initialize the repositories
     * 
     * @param addGymRepository    - Gym Repository
     * @param userRepository2     - User Repository
     * @param userBankDetailsRepo - User Bank Details Repository
     */
    @Autowired
    public PagingService(AddGymRepo addGymRepository, UserRepo userRepository2,
            UserBankDetailsRepo userBankDetailsRepo) {
        this.gymRepository = addGymRepository;
        this.userRepository = userRepository2;
        this.repo = userBankDetailsRepo;
    }

    /**
     * This function is used to fetch all the registered gyms
     * 
     * @param pageNo   - Page Number
     * @param pageSize - Size of the page
     * @return - List of gyms
     */
    public List<GymClass> getallGyms(int pageNo, int pageSize) {
        log.info("PagingService >> getallGyms >> Initiated");
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<GymClass> allGyms = gymRepository.findAll(pageable); // Fetching all gyms
        return allGyms.getContent();
    }

    /**
     * This function is used to fetch all the registered vendors
     * 
     * @param pageNo   - Page Number
     * @param pageSize - Size of the page
     * @return - List of all the vendors
     * @throws DataNotFoundException
     */
    public List<UserClass> getallVendors(int pageNo, int pageSize) {
        log.info("PagingService >> getallVendors >> Initiated");
        List<UserClass> l = userRepository.findAll();
        l = l.stream().filter(e -> e.getRole().equals("VENDOR")).collect(Collectors.toList()); // Fetching all Vendors
        if (l.isEmpty()) {
            log.error("PagingService >> getallVendors >> Error thrown");
            throw new DataNotFoundException("No Vendors are registered");
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserClass> page1 = new PageImpl<>(l, pageable, l.size());
        return page1.getContent();
    }

    /**
     * This function is used to fetch all the registered users
     * 
     * @param pageNo   - Page Number
     * @param pageSize - Size of the page
     * @return - List of all the users
     * @throws DataNotFoundException
     */
    public List<UserClass> getallUsers(int pageNo, int pageSize) {
        log.info("PagingService >> getallUsers >> Initiated");
        List<UserClass> l = userRepository.findAll();
        l = l.stream().filter(e -> e.getRole().equals("USER")).collect(Collectors.toList()); // Fetching all Users
        if (l.isEmpty()) {
            log.error("PagingService >> getallUsers >> Error thrown");
            throw new DataNotFoundException("No Users are registered");
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserClass> page1 = new PageImpl<>(l, pageable, l.size());
        return page1.getContent();
    }

    /**
     * This function is used to fetch all the bank details of the user
     * 
     * @param pageNo   - Page Number
     * @param pageSize - Size of the page
     * @return - List of bank details
     */
    public List<UserBankDetails> getallDetails(int pageNo, int pageSize) {
        log.info("PagingService >> getallDetails >> Initiated");
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserBankDetails> page = repo.findAll(pageable); // Fetching all Bank Details
        return page.getContent();

    }

}
