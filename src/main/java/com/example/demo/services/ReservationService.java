package com.example.demo.services;

import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.clientUser.DTO.ClientUserDTO;
import com.example.demo.model.reservation.Reservation;
import com.example.demo.model.reservation.ReservationStatus;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.model.table.RestaurantTable;
import com.example.demo.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation createReservation(RestaurantTable table, RestaurantUser restaurantUser, ClientUser clientUser, LocalDateTime startTime, LocalDateTime endTime, ReservationStatus status) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }

        boolean hasOverlap = reservationRepository.existsByTableAndStartTimeBeforeAndEndTimeAfter(table, startTime, endTime);
        if (hasOverlap) {
            throw new IllegalArgumentException("The table is already reserved for the selected time range.");
        }

        Reservation reservation = new Reservation();
        reservation.setTable(table);
        reservation.setRestaurantUser(restaurantUser);
        reservation.setClientUser(clientUser);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setStatus(status);

        return reservationRepository.save(reservation);
    }

    public Reservation getReservationById(UUID reservationId) {
        try {
            return reservationRepository.findById(reservationId)
                    .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with ID: " + reservationId));
        } catch (ReservationNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Reservation cancelReservation(UUID reservationId) {
        Reservation reservation = getReservationById(reservationId);
        reservation.setStatus(ReservationStatus.REJECTED);
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservationStatus(UUID reservationId, ReservationStatus status) {
        Reservation reservation = getReservationById(reservationId);
        reservation.setStatus(status);
        return reservationRepository.save(reservation);
    }


    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByUser(RestaurantUser restaurantUser) {
        return reservationRepository.findByRestaurantUser(restaurantUser); // Update 'user' to 'restaurantUser' here
    }


    public List<Reservation> getReservationsByTable(RestaurantTable table) {
        return reservationRepository.findByTable(table);
    }


    private static class ReservationNotFoundException extends Exception {
        public ReservationNotFoundException(String s) {
        }
    }

    public List<Reservation> getReservationsByClientUser(ClientUser clientUser) {
        return reservationRepository.findByClientUser(clientUser);
    }

    public List<UUID> getReservedTableIds(UUID restaurantId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Reservation> reservations = reservationRepository.findAcceptedReservationsWithOverlap(restaurantId, startTime, endTime);
        return reservations.stream()
                .map(res -> res.getTable().getId())
                .distinct()
                .toList();
    }

    public boolean userHasReservationOnDay(ClientUser user, RestaurantUser restaurant, LocalDate date) {
        List<Reservation> reservations = getReservationsByClientUser(user);
        return reservations.stream()
                .anyMatch(r ->
                        r.getRestaurant().getIdRestaurante().equals(restaurant.getIdRestaurante()) &&
                                r.getStartTime().toLocalDate().equals(date)
                );
    }


}