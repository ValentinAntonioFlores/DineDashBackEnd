package com.example.demo.services;

import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.model.review.Review;
import com.example.demo.model.review.ReviewType;
import com.example.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review createClientToRestaurantReview(UUID restaurantId, UUID clientId, int starRating) {
        Review review = new Review();
        review.setRestaurantUser(new RestaurantUser());
        review.getRestaurantUser().setIdRestaurante(restaurantId);
        review.setClientUser(new ClientUser());
        review.getClientUser().setIdUsuario(clientId);
        review.setReviewType(ReviewType.CLIENT_TO_RESTAURANT);
        review.setStarRating(starRating);
        return reviewRepository.save(review);
    }

    public Review createRestaurantToClientReview(UUID clientId, UUID restaurantId, boolean isPositive) {
        Review review = new Review();
        review.setClientUser(new ClientUser());
        review.getClientUser().setIdUsuario(clientId);
        review.setRestaurantUser(new RestaurantUser());
        review.getRestaurantUser().setIdRestaurante(restaurantId);
        review.setReviewType(ReviewType.RESTAURANT_TO_CLIENT);
        review.setIsPositive(isPositive);
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByRestaurant(UUID restaurantId) {
        return reviewRepository.findByRestaurantId(restaurantId);
    }

    public List<Review> getReviewsByClient(UUID clientId) {
        return reviewRepository.findByClientId(clientId);
    }
}