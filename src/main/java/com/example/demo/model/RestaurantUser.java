package com.example.demo.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "RestaurantUser")
public class RestaurantUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idAdmin;

    @Column(nullable = false)
    private UUID idRestaurante; // FK a Restaurante

    private String nombreRestaurante;

    @Column(unique = true, nullable = false)
    private String email;

    private String contraseña; // ⚠️ La contraseña se guarda en texto plano (encriptar en frontend)

    public RestaurantUser() {}

    public RestaurantUser(UUID idRestaurante, String nombreRestaurante, String email, String contraseña) {
        this.idRestaurante = idRestaurante;
        this.nombreRestaurante = nombreRestaurante;
        this.email = email;
        this.contraseña = contraseña;
    }

    public UUID getIdAdmin() { return idAdmin; }
    public void setIdAdmin(UUID idAdmin) { this.idAdmin = idAdmin; }

    public UUID getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(UUID idRestaurante) { this.idRestaurante = idRestaurante; }

    public String getNombreRestaurante() { return nombreRestaurante; }
    public void setNombreRestaurante(String nombreRestaurante) { this.nombreRestaurante = nombreRestaurante; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
}
