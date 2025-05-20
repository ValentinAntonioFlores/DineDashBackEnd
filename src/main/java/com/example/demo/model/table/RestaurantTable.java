package com.example.demo.model.table;

import com.example.demo.model.restaurantUser.RestaurantUser;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tables", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"restaurant_id", "positionX", "positionY"})
})
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "idRestaurante", nullable = false)
    private RestaurantUser restaurant;

    private int capacity;
    private int positionX;
    private int positionY;
    private boolean isAvailable;

    // Default constructor
    public RestaurantTable() {}

    // Constructor with parameters
    public RestaurantTable(RestaurantUser restaurant, int capacity, int positionX, int positionY, boolean isAvailable) {
        this.restaurant = restaurant;
        this.capacity = capacity;
        this.positionX = positionX;
        this.positionY = positionY;
        this.isAvailable = isAvailable;
    }

    // Getters and setters
    public UUID getId() { return id; }

    public RestaurantUser getRestaurant() { return restaurant; }
    public void setRestaurant(RestaurantUser restaurant) { this.restaurant = restaurant; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getPositionX() { return positionX; }
    public void setPositionX(int positionX) { this.positionX = positionX; }

    public int getPositionY() { return positionY; }
    public void setPositionY(int positionY) { this.positionY = positionY; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}
