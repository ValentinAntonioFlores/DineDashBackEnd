package com.example.demo.model.restaurantUser.DTO;

import java.util.UUID;

public class LoginRestaurantUserDTO {

    private String email;
    private String contraseña;

    public LoginRestaurantUserDTO(String nombreRestaurante, String email) {}

    public LoginRestaurantUserDTO(UUID idRestaurante, String email, String contraseña) {
        this.email = email;
        this.contraseña = contraseña;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
}
