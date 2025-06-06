package com.example.demo.repository;

import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.model.Favorite.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    List<Favorite> findByClientUser_IdUsuario(UUID idUsuario);
    boolean existsByClientUserAndRestaurantUser(ClientUser clientUser, RestaurantUser restaurantUser);
    Optional<Favorite> findByClientUserAndRestaurantUser(ClientUser clientUser, RestaurantUser restaurantUser);

}
