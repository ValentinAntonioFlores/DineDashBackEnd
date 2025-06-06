package com.example.demo.controller;

import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.clientUser.DTO.ClientUserDTO;
import com.example.demo.model.reservation.DTO.*;
        import com.example.demo.model.reservation.Reservation;
import com.example.demo.model.reservation.ReservationStatus;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.model.table.RestaurantTable;
import com.example.demo.services.ClientUserService;
import com.example.demo.services.ReservationService;
import com.example.demo.services.RestaurantUserService;
import com.example.demo.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private TableService tableService;
    @Autowired
    private RestaurantUserService restaurantUserService;
    @Autowired
    private ClientUserService clientUserService;

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
        Optional<ClientUser> clientUserOpt = clientUserService.getClientUserEntityById(reservationDTO.getUserId());
        if (clientUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ClientUser clientUser = clientUserOpt.get();

        Reservation reservation = reservationService.createReservation(
                table,
                restaurantUser,
                clientUser,
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

    @PostMapping("by-client-user")
    public ResponseEntity<List<ReservationSimpleDTO>> getReservationsByClientUser(@RequestBody GetReservationsByUserDTO dto) {
        Optional<ClientUser> clientUserOpt = clientUserService.getClientUserEntityById(dto.getUserId());
        if (clientUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ClientUser clientUser = clientUserOpt.get();
        List<Reservation> reservations = reservationService.getReservationsByClientUser(clientUser);

        List<ReservationSimpleDTO> response = reservations.stream()
                .map(r -> new ReservationSimpleDTO(
                        r.getRestaurant().getNombreRestaurante(),  // restaurant name via the table's restaurant
                        r.getStatus().name(),
                        r.getClientUser().getIdUsuario()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }


    @PostMapping("by-restaurant")
    public ResponseEntity<List<ReservationDetailDTO>> getReservationsByRestaurant(@RequestBody GetReservationsByRestaurantDTO dto) {
        List<Reservation> reservations = reservationService.getAllReservations();

        List<ReservationDetailDTO> filtered = reservations.stream()
                .filter(r -> r.getRestaurant().getIdRestaurante().equals(dto.getRestaurantId()))
                .map(r -> new ReservationDetailDTO(
                        r.getId(),
                        r.getClientUser().getNombre(),  // or getUsername()
                        r.getTable().getId(),
                        r.getStatus().name()
                )).toList();

        return ResponseEntity.ok(filtered);
    }

}

