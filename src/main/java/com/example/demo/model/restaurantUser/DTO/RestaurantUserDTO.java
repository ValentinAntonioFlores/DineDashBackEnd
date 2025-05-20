package com.example.demo.model.restaurantUser.DTO;

import com.example.demo.model.table.DTO.TableDTO;
import com.example.demo.model.table.RestaurantTable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class RestaurantUserDTO {

    private UUID idRestaurante;

    @JsonProperty("restaurantName")
    private String nombreRestaurante;

    @JsonProperty("email")
    private String email;

    @JsonProperty("imageBase64")
    private String imagenBase64;

    private List<RestaurantTable> layout; // NEW FIELD


    public RestaurantUserDTO(UUID idRestaurante, String nombreRestaurante, String email, String imagenBase64, List<RestaurantTable> layout) {
        this.idRestaurante = idRestaurante;
        this.nombreRestaurante = nombreRestaurante;
        this.email = email;
        this.imagenBase64 = imagenBase64;
        this.layout = layout;

    }

    public UUID getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(UUID idRestaurante) { this.idRestaurante = idRestaurante; }

    public String getNombreRestaurante() { return nombreRestaurante; }
    public void setNombreRestaurante(String nombreRestaurante) { this.nombreRestaurante = nombreRestaurante; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getImagen() { return imagenBase64; }
    public void setImagen(String imagenBase64) { this.imagenBase64 = imagenBase64; }

    public List<RestaurantTable> getLayout() { return layout; }
    public void setLayout(List<RestaurantTable> layout) { this.layout = layout; }
}