package com.fitness.app.controller;

import com.fitness.app.service.MyDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@RestController
public class QRController {
    @Autowired
    MyDetailsService myDetailsService;

    @PostMapping("/qrcode")
    public void joinEvent(@RequestParam("gymId") String gymId, @RequestParam("name") String name,
                          @RequestParam("gym_name") String gym_name){
        myDetailsService.saveMyDetails(gymId,name,gym_name);
    }

}
