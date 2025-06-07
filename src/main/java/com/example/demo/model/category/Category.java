package com.example.demo.model.category;

import com.example.demo.model.restaurantUser.RestaurantUser;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Category {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(optional = false)
    private RestaurantUser restaurantUser;

    public Category() {
    }

    public Category(UUID id, String name, RestaurantUser restaurantUser) {
        this.id = id;
        this.name = name;
        this.restaurantUser = restaurantUser;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RestaurantUser getRestaurant() {
        return restaurantUser;
    }

    public void setRestaurant(RestaurantUser restaurantUser) {
        this.restaurantUser = restaurantUser;
    }
}
