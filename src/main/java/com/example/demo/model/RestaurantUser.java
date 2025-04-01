package com.example.demo.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "restaurant_users")
public class RestaurantUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idAdmin;

    @Column(nullable = false)
    private UUID idRestaurante; // Clave foránea a Restaurante

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String contraseña;

    public RestaurantUser(UUID idRestaurante, String nombre, String email, String contraseña) {
        this.idRestaurante = idRestaurante;
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
    }

    public RestaurantUser() {

    }

    public UUID getIdAdmin() {
        return idAdmin;
    }

    public UUID getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(UUID idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
