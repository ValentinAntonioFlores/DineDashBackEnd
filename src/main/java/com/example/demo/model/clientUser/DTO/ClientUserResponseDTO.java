package com.example.demo.model.clientUser.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ClientUserResponseDTO {

    private String message;

    @JsonProperty("id")
    private UUID idUsuario;

    public ClientUserResponseDTO() {}

    public ClientUserResponseDTO(String message, UUID idUsuario) {
        this.message = message;
        this.idUsuario = idUsuario;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public UUID getIdUsuario() { return idUsuario; }
    public void setIdUsuario(UUID idUsuario) { this.idUsuario = idUsuario; }
}

