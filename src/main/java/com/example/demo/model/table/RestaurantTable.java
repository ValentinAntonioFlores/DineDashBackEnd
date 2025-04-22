package com.example.demo.model.table;

import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.repository.TableRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Entity
@Table(name = "Tables", uniqueConstraints = {
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

    public RestaurantTable() {}

    public RestaurantTable(RestaurantUser restaurant, int capacity, int positionX, int positionY, boolean isAvailable) {
        this.restaurant = restaurant;
        this.capacity = capacity;
        this.positionX = positionX;
        this.positionY = positionY;
        this.isAvailable = isAvailable;
    }

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