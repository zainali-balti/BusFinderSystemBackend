package com.example.Bus.Finder.System.service.Mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImplementation implements MailService{
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendSignUpEmail(String toEmail, String userName) {
    try {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("🚍 Welcome to RUHNUMA!");

        String emailContent = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }"
                + ".container { background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1); }"
                + "h2 { color: #3498db; }"
                + "p { font-size: 16px; color: #555; }"
                + ".footer { margin-top: 20px; font-size: 14px; color: #777; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='container'>"
                + "<h2>Welcome to RUHNUMA, " + userName + "! 🚍</h2>"
                + "<p>We’re thrilled to have you on board! Now you can easily search and find buses for your preferred routes.</p>"
                + "<p>Start your journey today by exploring our platform and booking your first ride.</p>"
                + "<p>As a welcome gift, we have credited <strong>1000 PKR</strong> to your account. Enjoy your first ride on us!</p>"
                + "<p>If you have any questions, feel free to reach out to our support team.</p>"
                + "<p><strong>Happy Traveling! 🚀</strong></p>"
                + "<div class='footer'>"
                + "<p>Best Regards,</p>"
                + "<p><strong>RUHNUMA Team</strong></p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        helper.setText(emailContent, true);

        mailSender.send(message);
        System.out.println("Email sent successfully to: " + toEmail);
    } catch (Exception e) {
        System.err.println("Error sending email: " + e.getMessage());
    }
    }


}
