package com.fitness.app.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import com.fitness.app.pdf.PDFService;
import com.fitness.app.qr.MyDetailsService;
import com.fitness.app.service.EmailSenderService;
import com.fitness.app.service.MailFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
	private EmailSenderService service;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailFormat mailFormat;
    @Autowired
    private MyDetailsService myDetailsService;
    @Autowired
    private PDFService pdfService;

    @RequestMapping("/email")
	public void triggerMail() throws MessagingException {

		service.sendSimpleEmail("smartychunnujain@gmail.com",
				"This is Email Body with Attachment...",
				"This email has attachment");

	}
    @RequestMapping("/emailwithattachment")
    public void sendEmailWithAttachment(HttpServletResponse response,@RequestParam String toEmail,
                                        @RequestParam String body,
                                        @RequestParam String subject,
                                        @RequestParam String attachment) throws MessagingException,IOException {
        
        myDetailsService.saveMyDetails("GM6","Aishwarya Bharucha","Fitness Freak");
        String pdfFilename = "/home/nineleaps/Downloads/Invoice.pdf";
		pdfService.createPDF(pdfFilename);
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("smartychunnujain@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(mailFormat.getBillbody(),true);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystem
                = new FileSystemResource(new File(attachment));

        mimeMessageHelper.addAttachment(fileSystem.getFilename(),
                fileSystem);

        mailSender.send(mimeMessage);
        System.out.println("Mail Send...");

    }
}
