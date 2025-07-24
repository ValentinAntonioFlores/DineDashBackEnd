package com.example.demo.model.clientUser.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ClientUserDTO {

    @JsonProperty("id")
    private UUID idUsuario;

    @JsonProperty("firstName")
    private String nombre;

    @JsonProperty("lastName")
    private String apellido;

    @JsonProperty("email")
    private String email;




    public ClientUserDTO() {}

    public ClientUserDTO(UUID idUsuario, String nombre, String apellido, String email) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    public UUID getIdUsuario() { return idUsuario; }
    public void setIdUsuario(UUID idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

}
