package com.example.demo.model.review;

import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.model.clientUser.ClientUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurant_user_id", nullable = true)
    private RestaurantUser restaurantUser;

    @ManyToOne
    @JoinColumn(name = "client_user_id", nullable = true)
    private ClientUser clientUser;

    @Enumerated(EnumType.STRING)
    private ReviewType reviewType;

    private Integer starRating; // For client-to-restaurant reviews (1-5 stars)
    private Boolean isPositive; // For restaurant-to-client reviews (true = Positive, false = Negative)

    public Review() {}

    public Review(RestaurantUser restaurantUser, ClientUser clientUser, ReviewType reviewType, Integer starRating, Boolean isPositive) {
        this.restaurantUser = restaurantUser;
        this.clientUser = clientUser;
        this.reviewType = reviewType;
        this.starRating = starRating;
        this.isPositive = isPositive;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RestaurantUser getRestaurantUser() {
        return restaurantUser;
    }

    public void setRestaurantUser(RestaurantUser restaurantUser) {
        this.restaurantUser = restaurantUser;
    }

    public ClientUser getClientUser() {
        return clientUser;
    }

    public void setClientUser(ClientUser clientUser) {
        this.clientUser = clientUser;
    }

    public ReviewType getReviewType() {
        return reviewType;
    }

    public void setReviewType(ReviewType reviewType) {
        this.reviewType = reviewType;
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
}