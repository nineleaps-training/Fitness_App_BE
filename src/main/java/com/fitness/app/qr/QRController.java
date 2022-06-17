package com.fitness.app.qr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
