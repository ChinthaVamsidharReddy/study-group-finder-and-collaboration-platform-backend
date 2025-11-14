package com.infy.project.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP for password reset");
        message.setText(
        	    "Hello,\n\n" +
        	    "We hope this email finds you well.\n\n" +
        	    "You have requested to reset your password for *Study Group Finder & Collaboration Platform*.\n\n" +
        	    "Your OTP is: " + otp + "\n\n" +
        	    "This OTP will expire in 5 minutes. Please use it promptly to complete your password change process.\n\n" +
        	    "Note: This is an automatically generated email. Please do not reply to this message.\n\n" +
        	    "Best regards,\n" +
        	    "Study Group Finder & Collaboration Platform Team"
        	);
        mailSender.send(message);
    }
}
