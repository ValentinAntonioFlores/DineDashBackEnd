package com.example.demo.model.reservation;

import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.clientUser.DTO.ClientUserDTO;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.model.table.RestaurantTable;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "Reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "table_id", nullable = false)
    private RestaurantTable table;

    @ManyToOne
    @JoinColumn(name = "restaurant_user_id", nullable = false)
    private RestaurantUser restaurantUser;

    @ManyToOne
    @JoinColumn(name = "client_user_id", nullable = false)
    private ClientUser clientUser;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

    public Reservation() {}

    public Reservation(RestaurantTable table, RestaurantUser restaurantUser, LocalDateTime startTime, LocalDateTime endTime, ReservationStatus status, ClientUser clientUser, NotificationStatus notificationStatus) {
        this.table = table;
        this.restaurantUser = restaurantUser;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.clientUser = clientUser;
        this.notificationStatus = notificationStatus;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public RestaurantTable getTable() {
        return table;
    }
    public void setTable(RestaurantTable table) {
        this.table = table;
    }

    public RestaurantUser getRestaurantUser() {
        return restaurantUser;
    }
    public void setRestaurantUser(RestaurantUser restaurantUser) {
        this.restaurantUser = restaurantUser;
    }

    public ClientUser getClientUser() { return clientUser;}
    public void setClientUser(ClientUser clientUser) {
        this.clientUser = clientUser;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public ReservationStatus getStatus() {
        return status;
    }
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public int getCapacity() {
        return table.getCapacity();
    }

    public int getPositionX() {
        return table.getPositionX();
    }
    public int getPositionY() {
        return table.getPositionY();
    }
    public UUID getTableId() {
        return table.getId();
    }

    public RestaurantUser getRestaurant() {
        return table.getRestaurant();
    }
    public void setRestaurant(RestaurantUser restaurant) {
        table.setRestaurant(restaurant);
    }

    public void setCapacity(int capacity) {
        table.setCapacity(capacity);
    }

    public void setPositionX(int positionX) {
        table.setPositionX(positionX);
    }
    public void setPositionY(int positionY) {
        table.setPositionY(positionY);
    }

    public boolean isAvailable() {
        return table.isAvailable();
    }
    public void setAvailable(boolean available) {
        table.setAvailable(available);
    }

    public void setUser(RestaurantUser user) {
        this.restaurantUser = user;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}