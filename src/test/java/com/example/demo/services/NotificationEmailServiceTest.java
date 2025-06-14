package com.example.demo.services;

import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.repository.ClientUserRepository;
import com.example.demo.repository.RestaurantUserRepository;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class NotificationEmailServiceTest {

    @InjectMocks
    private NotificationEmailService notificationEmailService;

    @Mock
    private EmailService emailService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private ClientUserRepository clientUserRepository;

    @Mock
    private RestaurantUserRepository restaurantUserRepository;

    private UUID clientId;
    private UUID restaurantId;
    private ClientUser clientUser;
    private RestaurantUser restaurantUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        
        clientId = UUID.randomUUID();
        restaurantId = UUID.randomUUID();
        
        // Setup client user
        clientUser = new ClientUser();
        clientUser.setIdUsuario(clientId);
        clientUser.setEmail("cliente@example.com");
        clientUser.setEmailNotificationsEnabled(true);
        
        // Setup restaurant user
        restaurantUser = new RestaurantUser();
        restaurantUser.setIdRestaurante(restaurantId);
        restaurantUser.setEmail("restaurante@example.com");
        restaurantUser.setEmailNotificationsEnabled(true);
        
        // Mock repository responses
        when(clientUserRepository.findById(clientId)).thenReturn(Optional.of(clientUser));
        when(restaurantUserRepository.findById(restaurantId)).thenReturn(Optional.of(restaurantUser));
    }

    @Test
    public void testNotifyClient_WithEmailNotificationsEnabled() throws MessagingException {
        // Arrange
        String message = "Tienes una nueva reserva";
        
        // Act
        notificationEmailService.notifyClient(clientId, message);
        
        // Assert
        // Verify that internal notification was created
        verify(notificationService).createNotificationForClient(clientId, message);
        
        // Verify that email was sent
        verify(emailService).sendHtmlEmail(
            eq("cliente@example.com"), 
            anyString(), 
            contains(message)
        );
    }
    
    @Test
    public void testNotifyClient_WithEmailNotificationsDisabled() throws MessagingException {
        // Arrange
        clientUser.setEmailNotificationsEnabled(false);
        String message = "Tienes una nueva reserva";
        
        // Act
        notificationEmailService.notifyClient(clientId, message);
        
        // Assert
        // Verify that internal notification was created
        verify(notificationService).createNotificationForClient(clientId, message);
        
        // Verify that email was NOT sent
        verify(emailService, never()).sendHtmlEmail(anyString(), anyString(), anyString());
    }
    
    @Test
    public void testNotifyRestaurant_WithEmailNotificationsEnabled() throws MessagingException {
        // Arrange
        String message = "Tienes una nueva reserva";
        
        // Act
        notificationEmailService.notifyRestaurant(restaurantId, message);
        
        // Assert
        // Verify that internal notification was created
        verify(notificationService).createNotificationForRestaurant(restaurantId, message);
        
        // Verify that email was sent
        verify(emailService).sendHtmlEmail(
            eq("restaurante@example.com"), 
            anyString(), 
            contains(message)
        );
    }
    
    @Test
    public void testNotifyRestaurant_WithEmailNotificationsDisabled() throws MessagingException {
        // Arrange
        restaurantUser.setEmailNotificationsEnabled(false);
        String message = "Tienes una nueva reserva";
        
        // Act
        notificationEmailService.notifyRestaurant(restaurantId, message);
        
        // Assert
        // Verify that internal notification was created
        verify(notificationService).createNotificationForRestaurant(restaurantId, message);
        
        // Verify that email was NOT sent
        verify(emailService, never()).sendHtmlEmail(anyString(), anyString(), anyString());
    }
}