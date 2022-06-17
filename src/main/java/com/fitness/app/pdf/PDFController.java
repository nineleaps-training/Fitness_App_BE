package com.fitness.app.pdf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class PDFController {
    @Autowired
    private PDFService pdfService;

    @GetMapping("/pdf/generate")
    public void generatePDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDate + ".pdf";
        System.out.println(headerValue);
        response.setHeader(headerKey, headerValue);
        String pdfFilename = "/home/nineleaps/Downloads/pdf_"+currentDate+".pdf";
		pdfService.createPDF(pdfFilename);
    }
}
