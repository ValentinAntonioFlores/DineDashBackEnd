Sistema de Notificaciones por Correo Electrónico
================================================

Este documento describe el sistema de notificaciones por correo electrónico implementado en la aplicación.

Componentes Implementados
------------------------

1. Dependencias

Se ha agregado la dependencia spring-boot-starter-mail en el archivo build.gradle:

implementation 'org.springframework.boot:spring-boot-starter-mail'

2. Configuración SMTP

Se ha agregado la configuración SMTP en el archivo application.properties:

# Configuración de correo electrónico
spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=your-email@example.com
spring.mail.password=your-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.writetimeout=5000

IMPORTANTE: Antes de usar en producción, reemplaza los valores de ejemplo con la configuración real de tu servidor SMTP.

3. Servicios Implementados

EmailService:
- Servicio básico para enviar correos electrónicos
- Métodos:
  - sendSimpleEmail(String to, String subject, String text): Envía un correo electrónico de texto plano.
  - sendHtmlEmail(String to, String subject, String htmlContent): Envía un correo electrónico con contenido HTML.

NotificationEmailService:
- Integra el sistema de notificaciones interno con el envío de correos electrónicos
- Métodos:
  - notifyClient(UUID clientUserId, String message): Crea una notificación interna para un cliente y envía un correo si tiene habilitadas las notificaciones por correo.
  - notifyRestaurant(UUID restaurantUserId, String message): Crea una notificación interna para un restaurante y envía un correo si tiene habilitadas las notificaciones por correo.

Modelo de Datos
--------------

Los modelos ClientUser y RestaurantUser ya incluían un campo emailNotificationsEnabled (Boolean) que indica si un usuario desea recibir notificaciones por correo electrónico. Por defecto, este valor es true.

Cómo Usar el Sistema de Notificaciones
-------------------------------------

1. Enviar una Notificación a un Cliente:

   @Autowired
   private NotificationEmailService notificationEmailService;

   UUID clientId = /* ID del cliente */;
   String message = "Tu reserva ha sido confirmada";
   notificationEmailService.notifyClient(clientId, message);

2. Enviar una Notificación a un Restaurante:

   @Autowired
   private NotificationEmailService notificationEmailService;

   UUID restaurantId = /* ID del restaurante */;
   String message = "Tienes una nueva reserva pendiente";
   notificationEmailService.notifyRestaurant(restaurantId, message);

3. Actualizar Preferencias de Notificación:

   Para clientes:
   @Autowired
   private ClientUserService clientUserService;

   UUID clientId = /* ID del cliente */;
   boolean enableEmailNotifications = true; // o false para desactivar
   clientUserService.updateEmailNotifications(clientId, enableEmailNotifications);

   Para restaurantes:
   @Autowired
   private RestaurantUserService restaurantUserService;

   UUID restaurantId = /* ID del restaurante */;
   boolean enableEmailNotifications = true; // o false para desactivar
   restaurantUserService.updateEmailNotifications(restaurantId, enableEmailNotifications);

Pruebas
------

Se ha creado una clase de prueba NotificationEmailServiceTest que demuestra cómo usar y probar el servicio de notificaciones por correo electrónico.