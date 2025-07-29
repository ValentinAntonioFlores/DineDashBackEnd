package com.example.demo.services;

import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.model.review.DTO.ReviewDTO;
import com.example.demo.model.review.Review;
import com.example.demo.model.review.ReviewType;
import com.example.demo.repository.ClientUserRepository;
import com.example.demo.repository.RestaurantUserRepository;
import com.example.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ClientUserRepository clientUserRepository;

    @Autowired
    private RestaurantUserRepository restaurantUserRepository;

    public Review createOrUpdateClientToRestaurantReview(UUID restaurantId, UUID clientId, int starRating) {
        return reviewRepository.findByClientUser_IdUsuarioAndRestaurantUser_IdRestauranteAndReviewType(
                clientId, restaurantId, ReviewType.CLIENT_TO_RESTAURANT
        ).map(review -> {
            review.setStarRating(starRating);
            review.setIsPositive(null); // Not relevant
            return reviewRepository.save(review);
        }).orElseGet(() -> {
            Review review = new Review();
            review.setClientUser(clientUserRepository.getReferenceById(clientId));
            review.setRestaurantUser(restaurantUserRepository.getReferenceById(restaurantId));
            review.setReviewType(ReviewType.CLIENT_TO_RESTAURANT);
            review.setStarRating(starRating);
            review.setIsPositive(null);
            return reviewRepository.save(review);
        });
    }


        public ReviewDTO mapToDTO(Review review) {
        UUID restaurantId = review.getRestaurantUser() != null ? review.getRestaurantUser().getIdRestaurante() : null;
        UUID clientId = review.getClientUser() != null ? review.getClientUser().getIdUsuario() : null;

        ReviewDTO dto = new ReviewDTO(
                review.getId(),
                restaurantId,
                clientId,
                review.getStarRating(),
                review.getIsPositive()
        );
        dto.setReviewType(review.getReviewType());
        return dto;
    }


    public Review createOrUpdateRestaurantToClientReview(UUID clientId, UUID restaurantId, boolean isPositive) {
        return reviewRepository.findByRestaurantUser_IdRestauranteAndClientUser_IdUsuarioAndReviewType(
                restaurantId, clientId, ReviewType.RESTAURANT_TO_CLIENT
        ).map(review -> {
            review.setIsPositive(isPositive);
            review.setStarRating(null); // Not relevant
            return reviewRepository.save(review);
        }).orElseGet(() -> {
            Review review = new Review();
            review.setRestaurantUser(restaurantUserRepository.getReferenceById(restaurantId));
            review.setClientUser(clientUserRepository.getReferenceById(clientId));
            review.setReviewType(ReviewType.RESTAURANT_TO_CLIENT);
            review.setIsPositive(isPositive);
            review.setStarRating(null);
            return reviewRepository.save(review);
        });
    }



        public List<Review> getReviewsByRestaurant(UUID restaurantId) {
        return reviewRepository.findByRestaurantUser_IdRestaurante(restaurantId);
    }

    public List<Review> getReviewsByClient(UUID clientId) {
        return reviewRepository.findByClientUser_IdUsuario(clientId);
    }
}
