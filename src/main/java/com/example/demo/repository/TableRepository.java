package com.example.demo.repository;

import com.example.demo.model.table.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TableRepository extends JpaRepository<RestaurantTable, UUID> {
    List<RestaurantTable> findByRestaurantId(UUID restaurantId);

    boolean existsByRestaurantIdAndPositionXAndPositionY(UUID restaurantId, int positionX, int positionY);
}