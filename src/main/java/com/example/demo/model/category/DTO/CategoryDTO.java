package com.example.demo.model.category.DTO;

import java.util.UUID;

public class CategoryDTO {
    private UUID id;
    private String name;
    private UUID restaurantId;

    public CategoryDTO() {
    }

    public CategoryDTO(UUID id, String name, UUID restaurantId) {
        this.id = id;
        this.name = name;
        this.restaurantId = restaurantId;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }
}
