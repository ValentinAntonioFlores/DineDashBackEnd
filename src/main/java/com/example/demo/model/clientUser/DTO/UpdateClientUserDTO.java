package com.example.demo.model.clientUser.DTO;


import org.antlr.v4.runtime.misc.NotNull;

public class UpdateClientUserDTO {

    private String nombre;
    private String apellido;

    @NotNull
    private String email;

    private String contraseña; // Opcional, solo se actualiza si se proporciona

    public UpdateClientUserDTO() {}

    public UpdateClientUserDTO(String nombre, String apellido, String email, String contraseña) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contraseña = contraseña;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

}
