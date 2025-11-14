package com.infy.project.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String to, String otp) {
        try {
            String subject = "🔐 Your OTP for Password Reset";

            // ✅ Professional HTML Email Template
            String htmlBody = """
                <div style="font-family: 'Segoe UI', Arial, sans-serif; max-width: 600px; margin: 0 auto;
                            background-color: #f9fafb; padding: 25px; border-radius: 12px; border: 1px solid #e5e7eb;">
                    
                    <div style="text-align: center;">
                        <h2 style="color: #2563eb;">Study Group Finder & Collaboration Platform</h2>
                        <p style="color: #374151; font-size: 15px;">Secure Password Reset</p>
                    </div>
                    
                    <hr style="margin: 20px 0; border: none; border-top: 1px solid #e5e7eb;">
                    
                    <p style="font-size: 15px; color: #111827;">
                        Hello,
                    </p>
                    
                    <p style="font-size: 15px; color: #1f2937;">
                        We received a request to reset your password for your
                        <strong>Study Group Finder & Collaboration Platform</strong> account.
                    </p>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <div style="display: inline-block; background-color: #2563eb; color: white;
                                    font-size: 24px; font-weight: bold; letter-spacing: 4px;
                                    padding: 12px 30px; border-radius: 8px;">
                            %s
                        </div>
                    </div>
                    
                    <p style="font-size: 15px; color: #374151;">
                        This OTP will expire in <strong>5 minutes</strong>. Please use it promptly to complete your password reset.
                    </p>
                    
                    <p style="font-size: 14px; color: #6b7280; margin-top: 20px;">
                        ⚠️ Note: This is an automated email — please do not reply to this message.
                    </p>
                    
                    <div style="margin-top: 25px; text-align: center; font-size: 14px; color: #6b7280;">
                        Regards,<br>
                        <strong>Study Group Finder & Collaboration Platform Team</strong>
                    </div>
                </div>
                """.formatted(otp);

            // ✅ Use MimeMessage for HTML email
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true → send as HTML

            mailSender.send(mimeMessage);

            System.out.println("✅ OTP HTML email sent successfully to: " + to);

        } catch (Exception e) {
            System.err.println("❌ Failed to send OTP email: " + e.getMessage());
        }
    }
}
