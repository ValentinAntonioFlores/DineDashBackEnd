package com.example.demo.model.restaurantUser.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class RestaurantLoginResponseDTO {

    private String token;
    private String restaurantName;
    private String email;
    private UUID idRestaurante;
    @JsonProperty("imageBase64")
    private String imagenBase64;
    private String userType;

    public RestaurantLoginResponseDTO(String token, String restaurantName, String email, UUID idRestaurante, String imagenBase64, String userType) {
        this.token = token;
        this.restaurantName = restaurantName;
        this.email = email;
        this.idRestaurante = idRestaurante;
        this.imagenBase64 = imagenBase64;
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

    public String getImagen() {
        return imagenBase64;
    }
}
