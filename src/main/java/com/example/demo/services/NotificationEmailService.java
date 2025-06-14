package com.example.demo.services;

import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.notification.Notification;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.repository.ClientUserRepository;
import com.example.demo.repository.RestaurantUserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationEmailService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ClientUserRepository clientUserRepository;

    @Autowired
    private RestaurantUserRepository restaurantUserRepository;

    /**
     * Crea una notificación para un cliente y envía un correo electrónico si tiene habilitadas las notificaciones por correo
     * 
     * @param clientUserId ID del cliente
     * @param message Mensaje de la notificación
     */
    public void notifyClient(UUID clientUserId, String message) {
        // Crear la notificación interna
        notificationService.createNotificationForClient(clientUserId, message);
        
        // Verificar si el cliente tiene habilitadas las notificaciones por correo
        Optional<ClientUser> clientUserOpt = clientUserRepository.findById(clientUserId);
        if (clientUserOpt.isPresent()) {
            ClientUser clientUser = clientUserOpt.get();
            if (clientUser.getEmailNotificationsEnabled()) {
                // Enviar correo electrónico
                try {
                    String subject = "Nueva notificación - Reserva App";
                    String emailContent = "<h2>Tienes una nueva notificación</h2>"
                            + "<p>" + message + "</p>"
                            + "<p>Ingresa a la aplicación para más detalles.</p>";
                    
                    emailService.sendHtmlEmail(clientUser.getEmail(), subject, emailContent);
                } catch (MessagingException e) {
                    // Log error but don't stop execution
                    System.err.println("Error al enviar correo electrónico: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Crea una notificación para un restaurante y envía un correo electrónico si tiene habilitadas las notificaciones por correo
     * 
     * @param restaurantUserId ID del restaurante
     * @param message Mensaje de la notificación
     */
    public void notifyRestaurant(UUID restaurantUserId, String message) {
        // Crear la notificación interna
        notificationService.createNotificationForRestaurant(restaurantUserId, message);
        
        // Verificar si el restaurante tiene habilitadas las notificaciones por correo
        Optional<RestaurantUser> restaurantUserOpt = restaurantUserRepository.findById(restaurantUserId);
        if (restaurantUserOpt.isPresent()) {
            RestaurantUser restaurantUser = restaurantUserOpt.get();
            if (restaurantUser.getEmailNotificationsEnabled()) {
                // Enviar correo electrónico
                try {
                    String subject = "Nueva notificación - Reserva App";
                    String emailContent = "<h2>Tienes una nueva notificación</h2>"
                            + "<p>" + message + "</p>"
                            + "<p>Ingresa a la aplicación para más detalles.</p>";
                    
                    emailService.sendHtmlEmail(restaurantUser.getEmail(), subject, emailContent);
                } catch (MessagingException e) {
                    // Log error but don't stop execution
                    System.err.println("Error al enviar correo electrónico: " + e.getMessage());
                }
            }
        }
    }
}