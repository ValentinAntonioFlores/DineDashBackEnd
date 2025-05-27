package com.example.demo.model.reservation.DTO;

import java.util.UUID;

public class GetReservationsByRestaurantDTO {

    private UUID restaurantId;

    // Getter and setter
    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }
}
