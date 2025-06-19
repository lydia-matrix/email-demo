package com.example.emaildemo;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private EmailLogRepository emailLogRepository;

    @Transactional
    public void sendEmail(String to, String subject, String text){
        
        logger.info("Sending email - to: {}, subject: {}, content: {}", to, subject, text);
        
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        
        mailSender.send(message);
        
        EmailLog log = new EmailLog();
        
        log.setToEmail(to);
        log.setSubject(subject);
        log.setContent(text);
        log.setSentAt(LocalDateTime.now());
        
        emailLogRepository.save(log);
        
        logger.info("Email sent and logged to DB successfully.");
    }
}
