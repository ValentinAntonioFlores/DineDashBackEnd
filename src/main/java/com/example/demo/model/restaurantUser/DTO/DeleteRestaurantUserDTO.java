package com.example.demo.model.restaurantUser.DTO;


import java.util.UUID;

public class DeleteRestaurantUserDTO {
    private UUID id;

    public DeleteRestaurantUserDTO() {}

    public DeleteRestaurantUserDTO(UUID id) {
        this.id = id;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
}
