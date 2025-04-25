package com.example.demo.model.clientUser.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginClientUserDTO {

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String contraseña;

    public LoginClientUserDTO() {}

    public LoginClientUserDTO(String email, String contraseña) {
        this.email = email;
        this.contraseña = contraseña;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
}
