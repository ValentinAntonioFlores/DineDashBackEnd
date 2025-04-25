package com.example.demo.model.clientUser.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.antlr.v4.runtime.misc.NotNull;

public class UpdateClientUserDTO {

    @JsonProperty("firstname")
    private String nombre;

    @JsonProperty("lastname")
    private String apellido;

    @JsonProperty("password")
    private String contraseña; // Opcional, solo se actualiza si se proporciona

    public UpdateClientUserDTO() {}

    public UpdateClientUserDTO(String nombre, String apellido, String contraseña) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.contraseña = contraseña;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

}
