package com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String fromEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo("spmbyakrama@gmail.com");
        message.setSubject(subject);

        
        message.setText(body);

        //sending the mail.
        mailSender.send(message);
        System.out.println("The mail send succefully...");
    } 
    

}
