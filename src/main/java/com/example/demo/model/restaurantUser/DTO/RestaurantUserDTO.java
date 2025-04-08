package com.example.demo.model.restaurantUser.DTO;

import java.util.UUID;

public class RestaurantUserDTO {

    private UUID idRestaurante;
    private String nombreRestaurante;
    private String email;

    public RestaurantUserDTO(String nombreRestaurante, String email) {}

    public RestaurantUserDTO(UUID idRestaurante, String nombreRestaurante, String email) {
        this.idRestaurante = idRestaurante;
        this.nombreRestaurante = nombreRestaurante;
        this.email = email;
    }

    public UUID getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(UUID idRestaurante) { this.idRestaurante = idRestaurante; }

    public String getNombreRestaurante() { return nombreRestaurante; }
    public void setNombreRestaurante(String nombreRestaurante) { this.nombreRestaurante = nombreRestaurante; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}