package com.example.demo.repository;

import com.example.demo.model.reservation.Reservation;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.model.table.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    boolean existsByTableAndStartTimeBeforeAndEndTimeAfter(RestaurantTable table, LocalDateTime startTime, LocalDateTime endTime);

    List<Reservation> findByTable(RestaurantTable table);

    List<Reservation> findByUser(RestaurantUser user);
}