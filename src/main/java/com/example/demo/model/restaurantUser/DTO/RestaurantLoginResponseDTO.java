package com.example.demo.model.restaurantUser.DTO;

import java.util.UUID;

public class RestaurantLoginResponseDTO {

    private String token;
    private String restaurantName;
    private String email;
    private UUID idRestaurante;
    private String userType;

    public RestaurantLoginResponseDTO(String token, String restaurantName, String email, UUID idRestaurante, String userType) {
        this.token = token;
        this.restaurantName = restaurantName;
        this.email = email;
        this.idRestaurante = idRestaurante;
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getEmail() {
        return email;
    }

    public UUID getIdRestaurante() {
        return idRestaurante;
    }

    public String getUserType() {
        return userType;
    }
}
