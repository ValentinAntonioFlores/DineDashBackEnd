package com.example.demo.services;

import com.example.demo.model.Favorite.Favorite;
import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    public void markAsFavorite(ClientUser clientUser, RestaurantUser restaurantUser) {
        if (!favoriteRepository.existsByClientUserAndRestaurantUser(clientUser, restaurantUser)) {
            Favorite favorite = new Favorite();
            favorite.setClientUser(clientUser);
            favorite.setRestaurantUser(restaurantUser);
            favoriteRepository.save(favorite);
        } else {
            throw new IllegalArgumentException("Restaurant is already marked as favorite.");
        }
    }

    public List<Favorite> getFavoritesForClient(ClientUser clientUser) {
        return favoriteRepository.findByClientUser(clientUser);
    }
}
