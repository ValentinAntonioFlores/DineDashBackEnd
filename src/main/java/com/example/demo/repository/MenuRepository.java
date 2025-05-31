package com.example.demo.repository;

import com.example.demo.model.menu.Menu;
import com.example.demo.model.restaurantUser.RestaurantUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuRepository extends JpaRepository<Menu, UUID> {
    List<Menu> findByRestaurantUser(RestaurantUser restaurantUser);
}