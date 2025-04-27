package com.example.demo.model.restaurantUser;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "RestaurantUsers")
public class RestaurantUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private UUID idRestaurante; // PK

    @JsonProperty("restaurantName")
    private String nombreRestaurante;

    @Column(unique = true, nullable = false)
    private String email;

    private String contraseña; // ⚠️ La contraseña se guarda en texto plano (encriptar en frontend)

    public RestaurantUser() {}

    public RestaurantUser(String nombreRestaurante, String email, String contraseña) {
        this.nombreRestaurante = nombreRestaurante;
        this.email = email;
        this.contraseña = contraseña;
    }

    public UUID getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(UUID idRestaurante) { this.idRestaurante = idRestaurante; }

    public String getNombreRestaurante() { return nombreRestaurante; }
    public void setNombreRestaurante(String nombreRestaurante) { this.nombreRestaurante = nombreRestaurante; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public boolean isPresent() {
        return idRestaurante != null;
    }
}