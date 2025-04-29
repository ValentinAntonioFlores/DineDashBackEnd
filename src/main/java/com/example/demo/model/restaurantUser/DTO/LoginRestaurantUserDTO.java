package com.example.demo.model.restaurantUser.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class LoginRestaurantUserDTO {
    private String email;
    private String password;

    public LoginRestaurantUserDTO() {}  // Jackson needs this!

    public LoginRestaurantUserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
