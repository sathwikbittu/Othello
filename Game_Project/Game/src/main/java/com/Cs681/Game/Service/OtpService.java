package com.Cs681.Game.Service;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
@Service
public class OtpService {
	private static final Logger logger = LogManager.getLogger(OtpService.class);
    
    @Value("${spring.mail.username}")
    public  String userName;
    @Value("${spring.mail.password}")
    public  String userPassword;


	    // Generate a random 6-digit OTP
	    private static String generateOTP() {
	        String otp = "";
	        for (int i = 0; i < 6; i++) {
	            otp += (int) (Math.random() * 10);
	        }
	        return otp;
	    }

	    // Send the OTP via email
	    public  String sendOTP(String recipientEmail) throws MessagingException {
	        // Generate a new OTP
	    	logger.info("here");
	        String otp = generateOTP();

	        // Setup mail server properties
	        Properties properties = new Properties();
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");
	        properties.put("mail.smtp.host", "smtp.gmail.com");
	        properties.put("mail.smtp.port", "587");
	        logger.info("Here again");
	        // Set account credentials
	        logger.info("username: "+userName);

	        // Create a new session with the account credentials
	        try {
	        Session session = Session.getInstance(properties, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(userName, userPassword);
	            }
	        });
	        
	        
	        logger.info("after username authentication");
	        

	        // Create a new message
	        Message message = new MimeMessage(session);

	        // Set the message recipients
	        message.setFrom(new InternetAddress(userName));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

	        // Set the message subject and body
	        message.setSubject("OTP Verification");
	        message.setText("Your OTP is: " + otp);

	        // Send the message
	        Transport.send(message);

	        // Print the OTP for testing purposes (you can remove this in production)
	        System.out.println("OTP sent to " + recipientEmail + ": " + otp);
	        return otp;
	        }
	        catch(Exception e) {
	        	logger.error("Authentication failed: "+e);
	        }
			return otp;
	    
	}


}


