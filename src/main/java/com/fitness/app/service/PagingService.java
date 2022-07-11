package com.fitness.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fitness.app.entity.GymClass;
import com.fitness.app.entity.UserClass;
import com.fitness.app.repository.AddGymRepository;
import com.fitness.app.repository.UserRepository;

@Service
public class PagingService {

    @Autowired
    AddGymRepository gymRepository;

    @Autowired
    UserRepository userRepository;
    
    public PagingService(AddGymRepository addGymRepository, UserRepository userRepository2) {
        this.gymRepository=addGymRepository;
        this.userRepository=userRepository2;
    }

    public List<GymClass> getallGyms(int pageNo, int pageSize) {
        Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<GymClass> allGyms=gymRepository.findAll(pageable);
		return allGyms.getContent();
    }

    public List<UserClass> getallVendors(int pageNo, int pageSize) {
        Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<UserClass> page=userRepository.findAll(pageable);
        List<UserClass> l=page.getContent();
		l = l.stream().filter(e -> e.getRole().equals("VENDOR")).collect(Collectors.toList());
		return l;
    }

    public List<UserClass> getallUsers(int pageNo, int pageSize) {
        Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<UserClass> page=userRepository.findAll(pageable);
        List<UserClass> l=page.getContent();
		l = l.stream().filter(e -> e.getRole().equals("USER")).collect(Collectors.toList()); 
        return l;
    }

    
    
}
