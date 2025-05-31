package com.example.demo.model.restaurantUser;

import com.example.demo.model.table.RestaurantTable;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "RestaurantUsers")
public class RestaurantUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idRestaurante;

    @JsonProperty("restaurantName")
    private String nombreRestaurante;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonProperty("password")
    private String contraseña;

    @Column(name = "imagen", columnDefinition = "TEXT")
    private String imagen; // Columna para almacenar la imagen

    @Column(nullable = true)
    private Double latitude; // Latitude for geolocation

    @Column(nullable = true)
    private Double longitude; // Longitude for geolocation

    // Google OAuth2 fields
    private String googleId;
    private Boolean isGoogleUser = false;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RestaurantTable> layout;  // Relationship to Table (your separate tables)


    public RestaurantUser() {}

    private Boolean emailNotificationsEnabled = true;

    public Boolean getEmailNotificationsEnabled() {
        return emailNotificationsEnabled;
    }

    public void setEmailNotificationsEnabled(Boolean emailNotificationsEnabled) {
        this.emailNotificationsEnabled = emailNotificationsEnabled;
    }

    public RestaurantUser(String nombreRestaurante, String email, String contraseña, Double latitude, Double longitude) {
        this.nombreRestaurante = nombreRestaurante;
        this.email = email;
        this.contraseña = contraseña;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Constructor for Google OAuth2 users
    public RestaurantUser(String nombreRestaurante, String email, String googleId, Boolean isGoogleUser, Double latitude, Double longitude) {
        this.nombreRestaurante = nombreRestaurante;
        this.email = email;
        this.googleId = googleId;
        this.isGoogleUser = isGoogleUser;
        this.contraseña = null; // Google users don't need a password
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UUID getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(UUID idRestaurante) { this.idRestaurante = idRestaurante; }

    public String getNombreRestaurante() { return nombreRestaurante; }
    public void setNombreRestaurante(String nombreRestaurante) { this.nombreRestaurante = nombreRestaurante; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public List<RestaurantTable> getLayout() { return layout; }
    public void setLayout(List<RestaurantTable> layout) { this.layout = layout; }

    public boolean isPresent() {
        return idRestaurante != null;
    }

    public String getGoogleId() { return googleId; }
    public void setGoogleId(String googleId) { this.googleId = googleId; }

    public Boolean getIsGoogleUser() { return isGoogleUser; }
    public void setIsGoogleUser(Boolean isGoogleUser) { this.isGoogleUser = isGoogleUser; }
}
