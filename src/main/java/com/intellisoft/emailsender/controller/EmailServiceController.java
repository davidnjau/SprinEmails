package com.intellisoft.emailsender.controller;

import com.intellisoft.emailsender.helper_class.DbResults;
import com.intellisoft.emailsender.helper_class.EmailDetails;
import com.intellisoft.emailsender.helper_class.ResultsResponse;
import com.intellisoft.emailsender.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.activation.DataHandler;
import javax.sql.DataSource;
import java.io.File;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


@RequestMapping(value="/api/v1/")
@RestController
public class EmailServiceController {

    @Autowired private EmailService emailService;

    @RequestMapping(path = "send-email", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(
            @RequestParam("to") String to,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            @RequestParam("file") MultipartFile file) {

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setReceiver(to);
        emailDetails.setSubject(subject);
        emailDetails.setMessage(message);

        //Check if the file is empty
        ResultsResponse resultsResponse = new ResultsResponse();

        if(!file.isEmpty()){

            //Check if the file is a pdf
            if(!Objects.requireNonNull(file.getContentType()).equals("application/pdf")){
                resultsResponse.setCode(400);
                resultsResponse.setDetails("File must be a pdf");
                return getResponseDetails(resultsResponse);
            }

            //Check if file size exceeds 15mb
            if(file.getSize() > 15728640){
                resultsResponse.setCode(400);
                resultsResponse.setDetails("File size must not exceed 15mb. Please send as link.");
                return getResponseDetails(resultsResponse);
            }

            //Send mail with attachment
            emailDetails.setFile(file);
            resultsResponse = emailService.sendEmailWithAttachment(emailDetails);
        }else {
            //Send mail without attachment
             resultsResponse = emailService.sendSimpleMail(emailDetails);
        }

        //Process the response from email service
        return getResponseDetails(resultsResponse);

    }

    private ResponseEntity<?> getResponseDetails(ResultsResponse resultsResponse){

        int code = resultsResponse.getCode();
        String details = resultsResponse.getDetails();
        if (code == 200){
            return ResponseEntity.ok().body(new DbResults(details));
        }else {
            return ResponseEntity.badRequest().body(new DbResults(details));
        }

    }



}
