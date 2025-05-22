package com.example.demo.controller;

import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.reservation.DTO.*;
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

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private TableService tableService;
    @Autowired
    private RestaurantUserService restaurantUserService;



    @PostMapping("all")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }





    @PostMapping("by-id")
    public ResponseEntity<Reservation> getReservationById(@RequestBody GetReservationByIdDTO dto) {
        Reservation reservation = reservationService.getReservationById(dto.getReservationId());
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("by-table")
    public ResponseEntity<List<Reservation>> getReservationsByTable(@RequestBody GetReservationsByTableDTO dto) {
        RestaurantTable table = tableService.getTableById(dto.getTableId());
        List<Reservation> reservations = reservationService.getReservationsByTable(table);
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("by-user")
    public ResponseEntity<List<Reservation>> getReservationsByUser(@RequestBody GetReservationsByUserDTO dto) {
        RestaurantUser user = restaurantUserService.getRestaurantUserById(dto.getUserId());
        List<Reservation> reservations = reservationService.getReservationsByUser(user);
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody CreateReservationDTO reservationDTO) {
        RestaurantTable table = tableService.getTableById(reservationDTO.getTableId());
        RestaurantUser restaurantUser = restaurantUserService.getRestaurantUserById(reservationDTO.getRestaurantUserId());
        Reservation reservation = reservationService.createReservation(
                table,
                restaurantUser,
                reservationDTO.getStartTime(),
                reservationDTO.getEndTime(),
                ReservationStatus.PENDING
        );
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("update-status")
    public ResponseEntity<Reservation> updateReservationStatus(@RequestBody UpdateReservationStatusDTO statusDTO) {
        Reservation updatedReservation = reservationService.updateReservationStatus(statusDTO.getReservationId(), statusDTO.getStatus());
        return ResponseEntity.ok(updatedReservation);
    }

    @PostMapping("delete")
    public ResponseEntity<String> deleteReservation(@RequestBody DeleteReservationDTO dto) {
        reservationService.cancelReservation(dto.getReservationId());
        return ResponseEntity.ok("Reservation canceled successfully.");
    }

    @GetMapping("/client-users")
    public ResponseEntity<List<ClientUser>> getAllClientUsersWithReservations() {
        List<ClientUser> users = reservationService.getAllClientUsersWithReservations();
        return ResponseEntity.ok(users);
    }

}