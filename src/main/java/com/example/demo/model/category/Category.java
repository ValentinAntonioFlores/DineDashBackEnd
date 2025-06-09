package com.example.demo.model.category;

import com.example.demo.model.restaurantUser.RestaurantUser;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "category", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "restaurant_user_id_restaurante"})
})
public class Category {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_user_id_restaurante", nullable = false)
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

    public RestaurantUser getRestaurantUser() {
        return restaurantUser;
    }

    public void setRestaurantUser(RestaurantUser restaurantUser) {
        this.restaurantUser = restaurantUser;
    }
}
