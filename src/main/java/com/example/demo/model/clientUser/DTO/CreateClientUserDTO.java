package com.example.demo.model.clientUser.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class CreateClientUserDTO {

    @NotNull
    @JsonProperty("firstName")
    private String nombre;

    @NotNull
    @JsonProperty("lastName")
    private String apellido;

    @NotNull
    @JsonProperty("email")
    private String email;

    @NotNull
    @JsonProperty("password")
    private String contraseña;

    private Double latitude;

    private Double longitude;

    public CreateClientUserDTO() {}

    public CreateClientUserDTO(String nombre, String apellido, String email, String contraseña, Double latitude, Double longitude) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contraseña = contraseña;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

}
