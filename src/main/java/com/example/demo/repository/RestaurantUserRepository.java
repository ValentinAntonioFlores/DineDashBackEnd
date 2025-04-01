package com.example.demo.repository;

import com.example.demo.model.RestaurantUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface RestaurantUserRepository extends JpaRepository<RestaurantUser, UUID>{
    Optional<RestaurantUser> findByEmail(String email);

}




