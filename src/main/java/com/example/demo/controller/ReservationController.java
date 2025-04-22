package com.example.demo.controller;

import com.example.demo.model.reservation.Reservation;
import com.example.demo.model.reservation.ReservationStatus;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.model.table.RestaurantTable;
import com.example.demo.services.ReservationService;
import com.example.demo.services.RestaurantUserService;
import com.example.demo.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private TableService tableService;
    @Autowired
    private RestaurantUserService restaurantUserService;



    @GetMapping("{reservationId}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable UUID reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        return ResponseEntity.ok(reservation);
    }
    @PatchMapping("{reservationId}/status")
    public ResponseEntity<Reservation> updateReservationStatus(
            @PathVariable UUID reservationId,
            @RequestBody ReservationStatus status) {
        Reservation updatedReservation = reservationService.updateReservationStatus(reservationId, status);
        return ResponseEntity.ok(updatedReservation);
    }
    @PostMapping
    public ResponseEntity<Reservation> createReservation(
            @RequestParam UUID tableId,
            @RequestParam UUID restaurantUserId,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        RestaurantTable table = tableService.getTableById(tableId);
        RestaurantUser restaurantUser = restaurantUserService.getRestaurantUserById(restaurantUserId);
        Reservation reservation = reservationService.createReservation(table, restaurantUser, startTime, endTime, ReservationStatus.PENDING);
        return ResponseEntity.ok(reservation);
    }

}
