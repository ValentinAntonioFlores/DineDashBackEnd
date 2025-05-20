package com.example.demo.repository;

import com.example.demo.model.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByRestaurantId(UUID restaurantId);
    List<Review> findByClientId(UUID clientId);
}