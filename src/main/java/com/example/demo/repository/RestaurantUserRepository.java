package com.example.demo.repository;

import com.example.demo.model.RestaurantUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RestaurantUserRepository extends JpaRepository<RestaurantUser, Long>{
    Optional<RestaurantUser> findByEmail(String email);

}


