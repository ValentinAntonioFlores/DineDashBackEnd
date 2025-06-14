package com.example.demo.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envía un correo electrónico simple (solo texto)
     * 
     * @param to Dirección de correo del destinatario
     * @param subject Asunto del correo
     * @param text Contenido del correo
     */
    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        
        mailSender.send(message);
    }

    /**
     * Envía un correo electrónico con contenido HTML
     * 
     * @param to Dirección de correo del destinatario
     * @param subject Asunto del correo
     * @param htmlContent Contenido HTML del correo
     * @throws MessagingException Si ocurre un error al enviar el correo
     */
    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true indica que el contenido es HTML
        
        mailSender.send(message);
    }
}