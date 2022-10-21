package com.intellisoft.emailsender;

import com.intellisoft.emailsender.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailSenderApplication {

    @Autowired
    private EmailService emailSenderService;

    public static void main(String[] args) {
        SpringApplication.run(EmailSenderApplication.class, args);
    }

}
