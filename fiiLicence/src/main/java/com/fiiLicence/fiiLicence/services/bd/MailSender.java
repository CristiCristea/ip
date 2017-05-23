package com.fiiLicence.fiiLicence.services.bd;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailSender {

        public MailSender(String email, String hascode){

        final String username = "dan.giani123@gmail.com";
        final String password = "42428531";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("dan.giani@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Confirmare inscriere");
            message.setText("Stimate student/a,"
                    + "\n\nPentru a putea folosi serviciul te rugam sa confirmi la urmatorul link: " + "<a href=\"http://localhost:4200/activation?activate=+hascode\">"+ "http://localhost:4200/activation?activate=+hascode"+"</a>" +"\n\n\nEchipa B7 :)");

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        }

}