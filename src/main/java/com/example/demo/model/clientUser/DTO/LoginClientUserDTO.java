package com.example.demo.model.clientUser.DTO;

public class LoginClientUserDTO {

    private String email;
    private String contraseña;

    public LoginClientUserDTO() {}

    public LoginClientUserDTO(String email, String contraseña) {
        this.email = email;
        this.contraseña = contraseña;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return contraseña; }
    public void setContraseña(String password) { this.contraseña = contraseña; }
}
