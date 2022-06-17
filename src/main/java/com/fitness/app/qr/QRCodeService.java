package com.fitness.app.qr;


public interface QRCodeService {
    byte[] generateQRCode(String qrContent, int width, int height);
    
}
