package com.fitness.app.qr;

import com.fitness.app.model.UserOrderModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Service
public class MyDetailsServiceImpl implements MyDetailsService{
    @Autowired
    QRCodeService qrCodeService;

    LocalDate localDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String currentDate = localDate.format(formatter);
    SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
    LocalDate localDate1=LocalDate.now().plusDays(30);
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String currentDate1 = localDate1.format(formatter1);
    
    
    @Override
    public byte[] saveMyDetails(String gymId, String name, String gym_name) {
        UserOrderModel userOrderModel=new UserOrderModel();
        userOrderModel.setGym(gym_name);
        String confirm ="{\n\"GymID\" : \""+"GM6"+"\",\n"+"\"CustomerName\" : \""+"Bhavika Awtani"+"\",\n"+"\"GymName\" : \""+userOrderModel.getGym()+"\",\n"+"\"BookingDate\" : \""+currentDate+"\",\n"+"\"ExpirationDate\" : \""+currentDate1+"\"\n}";
        System.out.println(confirm);
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