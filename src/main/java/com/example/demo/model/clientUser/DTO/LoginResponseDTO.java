package com.example.demo.model.clientUser.DTO;

import java.util.UUID;

public class LoginResponseDTO {

    private String token;
    private String firstName;
    private String lastName;
    private String email;
    private UUID idUsuario;
    private String userType; // <-- Add this


    public LoginResponseDTO(String token, String firstName, String lastName, String email, UUID idUsuario, String userType) {
        this.token = token;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.idUsuario = idUsuario;
        this.userType = userType;


    }

    // Getters and setters (or use Lombok with @Data if you prefer)
    public String getToken() { return token; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public UUID getIdUsuario() { return idUsuario; }
    public String getUserType() { return userType; }
}
