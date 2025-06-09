package com.example.demo.controller;

import com.example.demo.model.review.DTO.CreateReviewRequestDTO;
import com.example.demo.model.review.DTO.ReviewDTO;
import com.example.demo.model.review.DTO.UpdateReviewRequestDTO;
import com.example.demo.model.review.Review;
import com.example.demo.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/client-to-restaurant")
    public ResponseEntity<ReviewDTO> createClientToRestaurantReview(@RequestBody CreateReviewRequestDTO request) {
        Review review = reviewService.createOrUpdateClientToRestaurantReview(
                request.getRestaurantId(),
                request.getClientId(),
                request.getStarRating()
        );
        return ResponseEntity.ok(reviewService.mapToDTO(review));
    }


    @PostMapping("/restaurant-to-client")
    public ResponseEntity<ReviewDTO> createRestaurantToClientReview(
            @RequestParam UUID clientId,
            @RequestParam UUID restaurantId,
            @RequestParam boolean isPositive) {
        Review review = reviewService.createOrUpdateRestaurantToClientReview(clientId, restaurantId, isPositive);
        return ResponseEntity.ok(reviewService.mapToDTO(review));
    }


    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByRestaurant(@PathVariable UUID restaurantId) {
        List<Review> reviews = reviewService.getReviewsByRestaurant(restaurantId);
        List<ReviewDTO> dtos = reviews.stream()
                .map(reviewService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByClient(@PathVariable UUID clientId) {
        List<Review> reviews = reviewService.getReviewsByClient(clientId);
        List<ReviewDTO> dtos = reviews.stream()
                .map(reviewService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/restaurant/{restaurantId}/average-rating")
    public ResponseEntity<Double> getAverageRatingForRestaurant(@PathVariable UUID restaurantId) {
        double average = reviewService.calculateAverageRating(restaurantId);
        return ResponseEntity.ok(average);
    }

}