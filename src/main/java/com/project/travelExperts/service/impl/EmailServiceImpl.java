package com.project.travelExperts.service.impl;

import com.project.travelExperts.data.model.Customer;
import com.project.travelExperts.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${contactUs.email}")
    private String contactAdminEmail;

    @Value("${spring.mail.sender}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendAgentManagerCreationEmail(String to, String agentPrefix, String password) {

        String text =
        """
                Hello %s,

                         An account  with username  %s and password %s has just been created for you,
                         Do not hesitate to use the credentials to login to your account.
                         If you have any more questions, you can contact %s
                         Best regards,
                         Travel Expert Team
        """;

        String formattedText = String.format(text, agentPrefix, to, password, contactAdminEmail);

        sendSimpleMessage(to, "Account Created", formattedText);
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setTo(to.split(","));
            helper.setFrom("tligali@paga.com", sender);
            helper.setSubject(subject);
            helper.setBcc(adminEmail.split(","));
            helper.setText(text);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            // handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void sendCustomerVerificationMail(Customer newCustomer, String verificationToken) {
        String text =
                """
                        Hello %s,
        
                                 Thanks for signing up with us  here is the link to verify your account %s,
                                 If you did not initiate this request , you can contact us %s
                                 Best regards,
                                 Travel Expert Team
                """;

        String formattedText = String.format(text, newCustomer.getFirstName(), verificationToken, contactAdminEmail);

        sendSimpleMessage(newCustomer.getEmail(), "Account Verification", formattedText);
    }
}
