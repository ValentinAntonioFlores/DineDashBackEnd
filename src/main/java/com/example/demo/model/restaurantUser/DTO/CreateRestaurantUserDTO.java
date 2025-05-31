package com.example.demo.model.restaurantUser.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateRestaurantUserDTO {

    @JsonProperty("restaurantName")
    private String nombreRestaurante;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String contraseña;

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    public CreateRestaurantUserDTO() {}

    public CreateRestaurantUserDTO(String nombreRestaurante, String email, String contraseña, double latitude, double longitude) {
        this.nombreRestaurante = nombreRestaurante;
        this.email = email;
        this.contraseña = contraseña;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNombreRestaurante() { return nombreRestaurante; }
    public void setNombreRestaurante(String nombreRestaurante) { this.nombreRestaurante = nombreRestaurante; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}