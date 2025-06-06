package com.example.demo.model.review.DTO;

import com.example.demo.model.review.ReviewType;

import java.util.UUID;

public class ReviewDTO {

    private UUID id;
    private UUID restaurantId;
    private UUID clientId;
    private Integer starRating;
    private Boolean isPositive;
    private ReviewType reviewType;

    public ReviewDTO(UUID id, UUID restaurantId, UUID clientId, Integer starRating, Boolean isPositive) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.clientId = clientId;
        this.starRating = starRating;
        this.isPositive = isPositive;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }
    public UUID getClientId() {
        return clientId;
    }
    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }
    public Integer getStarRating() {
        return starRating;
    }
    public void setStarRating(Integer starRating) {
        this.starRating = starRating;
    }
    public Boolean getIsPositive() {
        return isPositive;
    }
    public void setIsPositive(Boolean isPositive) {
        this.isPositive = isPositive;
    }
    public ReviewType getReviewType() {
        return reviewType;
    }
    public void setReviewType(ReviewType reviewType) {
        this.reviewType = reviewType;
    }
}
