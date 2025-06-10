package com.example.demo.model.clientUser;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "ClientUsers")
public class ClientUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idUsuario;

    private String nombre;
    private String apellido;

    @Column(unique = true, nullable = false)
    private String email;

    private String contraseña; // ⚠️ La contraseña se guarda en texto plano (encriptar en frontend)

    // Google OAuth2 fields
    private String googleId;
    private Boolean isGoogleUser = false;

    @Column(nullable = true)
    private Double latitude;

    @Column(nullable = true)
    private Double longitude;

    @Column(nullable = true)
    private Double latitude;

    @Column(nullable = true)
    private Double longitude;


    public ClientUser() {}

    public ClientUser(String nombre, String apellido, String email, String contraseña, Double latitude, Double longitude) {
    private Boolean emailNotificationsEnabled = true;

    public Boolean getEmailNotificationsEnabled() {
        return emailNotificationsEnabled;
    }

    public void setEmailNotificationsEnabled(Boolean emailNotificationsEnabled) {
        this.emailNotificationsEnabled = emailNotificationsEnabled;
    }

    public ClientUser(String nombre, String apellido, String email, String contraseña, Double latitude, Double longitude) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contraseña = contraseña;
        this.latitude = latitude;
        this.longitude = longitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Constructor for Google OAuth2 users
    public ClientUser(String nombre, String apellido, String email, String googleId, Boolean isGoogleUser) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.googleId = googleId;
        this.isGoogleUser = isGoogleUser;
        this.contraseña = null; // Google users don't need a password
    }

    public UUID getIdUsuario() { return idUsuario; }
    public void setIdUsuario(UUID idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getGoogleId() { return googleId; }
    public void setGoogleId(String googleId) { this.googleId = googleId; }

    public Boolean getIsGoogleUser() { return isGoogleUser; }
    public void setIsGoogleUser(Boolean isGoogleUser) { this.isGoogleUser = isGoogleUser; }
}

}