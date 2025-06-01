package com.example.demo.repository;

import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.reservation.Reservation;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.model.table.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    boolean existsByTableAndStartTimeBeforeAndEndTimeAfter(RestaurantTable table, LocalDateTime startTime, LocalDateTime endTime);

    List<Reservation> findByTable(RestaurantTable table);

    List<Reservation> findByRestaurantUser(RestaurantUser restaurantUser); // Ensure method name matches the field name

    List<Reservation> findByClientUser(ClientUser clientUser);

    @Query("""
    SELECT r FROM Reservation r
    WHERE r.table.restaurant.idRestaurante = :restaurantId
    AND r.status = 'ACCEPTED'
    AND r.startTime < :endTime
    AND r.endTime > :startTime
""")
    List<Reservation> findAcceptedReservationsWithOverlap(
            @Param("restaurantId") UUID restaurantId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

}

