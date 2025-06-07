package com.example.demo.repository;

import com.example.demo.model.review.Review;
import com.example.demo.model.review.ReviewType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByRestaurantUser_IdRestaurante(UUID restaurantUserId);
    List<Review> findByClientUser_IdUsuario(UUID clientUserId);
    Optional<Review> findByClientUser_IdUsuarioAndRestaurantUser_IdRestauranteAndReviewType(UUID clientId, UUID restaurantId, ReviewType reviewType);

    Optional<Review> findByRestaurantUser_IdRestauranteAndClientUser_IdUsuarioAndReviewType(UUID restaurantId, UUID clientId, ReviewType reviewType);

}