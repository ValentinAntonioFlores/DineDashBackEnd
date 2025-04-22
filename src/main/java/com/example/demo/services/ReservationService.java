package com.example.demo.services;

import com.example.demo.model.reservation.Reservation;
import com.example.demo.model.reservation.ReservationStatus;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.model.table.RestaurantTable;
import com.example.demo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation createReservation(RestaurantTable table, RestaurantUser user, LocalDateTime startTime, LocalDateTime endTime, ReservationStatus status) {
        boolean hasOverlap = reservationRepository.existsByTableAndStartTimeBeforeAndEndTimeAfter(table, startTime, endTime);
        if (hasOverlap) {
            throw new IllegalArgumentException("The table is already reserved for the selected time range.");
        }

        Reservation reservation = new Reservation();
        reservation.setTable(table);
        reservation.setUser(user);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setStatus(status);

        return reservationRepository.save(reservation);
    }

    public Reservation getReservationById(UUID reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID: " + reservationId));
    }
    public Reservation cancelReservation(UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID: " + reservationId));

        reservation.setStatus(ReservationStatus.REJECTED);
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservationStatus(UUID reservationId, ReservationStatus status) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID: " + reservationId));

        reservation.setStatus(status);
        return reservationRepository.save(reservation);
    }
}