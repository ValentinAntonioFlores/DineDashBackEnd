package com.example.demo.model.review.DTO;

import java.util.UUID;

public class UpdateReviewRequestDTO {

    private UUID clientId;
    private UUID restaurantId;
    private int starRating;


    public UpdateReviewRequestDTO(UUID clientId, UUID restaurantId, int starRating) {
        this.clientId = clientId;
        this.restaurantId = restaurantId;
        this.starRating = starRating;
    }

    public UUID getClientId() {
        return clientId;
    }
    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getStarRating() {
        return starRating;
    }
    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }
}
