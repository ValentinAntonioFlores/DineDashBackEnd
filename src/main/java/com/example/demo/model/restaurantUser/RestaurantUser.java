package com.example.demo.model.restaurantUser;

import com.example.demo.model.category.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.Set;
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

    @ManyToMany
    @JoinTable(
            name = "restaurant_category",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

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

    public Set<Category> getCategories() { return categories; }
    public void setCategories(Set<Category> categories) { this.categories = categories; }

    public boolean isPresent() {
        return this.idRestaurante != null;
    }
}