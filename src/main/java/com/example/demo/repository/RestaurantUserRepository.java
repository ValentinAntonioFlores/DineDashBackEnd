package com.example.demo.repository;

import com.example.demo.model.restaurantUser.RestaurantUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface RestaurantUserRepository extends JpaRepository<RestaurantUser, UUID>{
    Optional<RestaurantUser> findByEmail(String email);

    List<RestaurantUser> findByNombreRestauranteContainingIgnoreCase(String nombreRestaurante);

}




