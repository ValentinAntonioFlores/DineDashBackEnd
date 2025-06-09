package com.example.demo.repository;

import com.example.demo.model.category.Category;
import com.example.demo.model.category.DTO.CategoryDTO;
import com.example.demo.model.restaurantUser.RestaurantUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByName(String name);
    Optional<Category> findByNameAndRestaurantUser_IdRestaurante(String name, UUID restaurantId);
}
