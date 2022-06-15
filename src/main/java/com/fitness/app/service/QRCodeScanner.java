package com.fitness.app.service;

        import com.google.zxing.BarcodeFormat;
        import com.google.zxing.WriterException;
        import com.google.zxing.client.j2se.MatrixToImageWriter;
        import com.google.zxing.common.BitMatrix;
        import com.google.zxing.qrcode.QRCodeWriter;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.web.multipart.MultipartFile;
        import org.springframework.web.multipart.commons.CommonsMultipartFile;


        import java.io.ByteArrayInputStream;
        import java.io.ByteArrayOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;

        import javax.servlet.http.HttpServletResponse;

@Service
public class QRCodeScanner implements QRCodeService{

//	@Autowired
//	MyEventsService myEventsService;

    @Override
    public byte[] generateQRCode(String qrContent, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }

    }

}