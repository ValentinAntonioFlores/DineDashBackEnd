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
import java.util.List;
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

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("{reservationId}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable UUID reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("table/{tableId}")
    public ResponseEntity<List<Reservation>> getReservationsByTable(@PathVariable UUID tableId) {
        RestaurantTable table = tableService.getTableById(tableId);
        List<Reservation> reservations = reservationService.getReservationsByTable(table);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUser(@PathVariable UUID userId) {
        RestaurantUser user = restaurantUserService.getRestaurantUserById(userId);
        List<Reservation> reservations = reservationService.getReservationsByUser(user);
        return ResponseEntity.ok(reservations);
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

    @PatchMapping("{reservationId}/status")
    public ResponseEntity<Reservation> updateReservationStatus(
            @PathVariable UUID reservationId,
            @RequestBody ReservationStatus status) {
        Reservation updatedReservation = reservationService.updateReservationStatus(reservationId, status);
        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("{reservationId}")
    public ResponseEntity<String> deleteReservation(@PathVariable UUID reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.ok("Reservation canceled successfully.");
    }
}