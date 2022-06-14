package com.fitness.app.controller;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.fitness.app.service.EmailSenderService;
import com.fitness.app.service.MailFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
    @RequestMapping("/email")
	public void triggerMail() throws MessagingException {

		service.sendSimpleEmail("smartychunnujain@gmail.com",
				"This is Email Body with Attachment...",
				"This email has attachment");

	}
    @RequestMapping("/emailwithattachment")
    public void sendEmailWithAttachment(@RequestParam String toEmail,
                                        @RequestParam String body,
                                        @RequestParam String subject,
                                        @RequestParam String attachment) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("pankaj.jain@nineleaps.com");
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
