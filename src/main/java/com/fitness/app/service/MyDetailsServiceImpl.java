package com.fitness.app.service;

import com.fitness.app.model.UserOrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class MyDetailsServiceImpl implements MyDetailsService{
    @Autowired
    QRCodeService qrCodeService;

    LocalDate localDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String currentDate = localDate.format(formatter);
    SimpleDateFormat sdformat = new SimpleDateFormat("yyy-MM-dd");
    @Override
    public byte[] saveMyDetails(String gymId, String name, String gym_name) {
        UserOrderModel userOrderModel=new UserOrderModel();
        userOrderModel.setGym(gym_name);
        try {
            Date presentDate=sdformat.parse(currentDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String confirm ="{\"MyGym\":\""+userOrderModel.getGym()+"\","+"\"BookingDate\":\""+currentDate+"\"}";
        System.out.println("Confirm "+confirm);
        byte[] qrcode=qrCodeService.generateQRCode(confirm, 200, 200);
        System.out.println("qrcode "+qrcode);
        try {
            //FileOutputStream fout=new FileOutputStream("/home/nineleaps/Documents/qrcode.txt");
            OutputStream outStream = new FileOutputStream("/home/nineleaps/Documents/qrcode.png");
            outStream.write(qrcode);
            outStream.close();
            //fout.close();
        } catch (Exception e) {
            System.out.print(e);
        }
        return qrcode;
    }

}
