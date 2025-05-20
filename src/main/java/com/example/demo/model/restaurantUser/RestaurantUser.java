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

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RestaurantTable> layout;  // Relationship to Table (your separate tables)


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

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public List<RestaurantTable> getLayout() { return layout; }
    public void setLayout(List<RestaurantTable> layout) { this.layout = layout; }

    public boolean isPresent() {
        return idRestaurante != null;
    }
}