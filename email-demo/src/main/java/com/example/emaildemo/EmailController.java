package com.example.emaildemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
    
    @Autowired
    private EmailService emailService;
    
    @PostMapping("/send")
    public ResponseEntity send(@RequestBody EmailRequest request) {
        
        logger.info("Received email send request - to: {}, subject: {}, content: {}", 
                     request.getTo(), request.getSubject(), request.getContent());
        
        emailService.sendEmail(request.getTo(), request.getSubject(), request.getContent());
        
        return ResponseEntity.ok("Email sent");
    }
}
