package com.example.demo.controller;

import com.example.demo.model.review.Review;
import com.example.demo.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/client-to-restaurant")
    public ResponseEntity<Review> createClientToRestaurantReview(
            @RequestParam UUID restaurantId,
            @RequestParam UUID clientId,
            @RequestParam int starRating) {
        Review review = reviewService.createClientToRestaurantReview(restaurantId, clientId, starRating);
        return ResponseEntity.ok(review);
    }

    @PostMapping("/restaurant-to-client")
    public ResponseEntity<Review> createRestaurantToClientReview(
            @RequestParam UUID clientId,
            @RequestParam UUID restaurantId,
            @RequestParam boolean isPositive) {
        Review review = reviewService.createRestaurantToClientReview(clientId, restaurantId, isPositive);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Review>> getReviewsByRestaurant(@PathVariable UUID restaurantId) {
        List<Review> reviews = reviewService.getReviewsByRestaurant(restaurantId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Review>> getReviewsByClient(@PathVariable UUID clientId) {
        List<Review> reviews = reviewService.getReviewsByClient(clientId);
        return ResponseEntity.ok(reviews);
    }
}