package com.example.demo.model.restaurantUser.DTO;

import java.util.UUID;

public class RestaurantUserResponseDTO {
    private String message;
    private UUID id;

    public RestaurantUserResponseDTO() {}

    public RestaurantUserResponseDTO(String message, UUID id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
}
