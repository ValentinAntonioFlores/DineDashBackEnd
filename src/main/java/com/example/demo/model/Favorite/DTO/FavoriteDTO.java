package com.example.demo.model.Favorite.DTO;
import java.util.UUID;


public class FavoriteDTO {


    private UUID restaurantId;

    public FavoriteDTO(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }
}
