package com.example.demo.model.restaurantUser.DTO;

import java.util.UUID;

public class RestaurantUserDTO {
    private UUID id;
    private String nombreRestaurante;
    private String email;

    public RestaurantUserDTO() {}

    public RestaurantUserDTO(UUID id, String nombreRestaurante, String email) {
        this.id = id;
        this.nombreRestaurante = nombreRestaurante;
        this.email = email;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombreRestaurante() { return nombreRestaurante; }
    public void setNombreRestaurante(String nombreRestaurante) { this.nombreRestaurante = nombreRestaurante; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
