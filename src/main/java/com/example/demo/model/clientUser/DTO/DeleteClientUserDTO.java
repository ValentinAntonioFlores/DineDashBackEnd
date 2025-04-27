package com.example.demo.model.clientUser.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class DeleteClientUserDTO {

    @JsonProperty("id")
    private UUID idUsuario;

    public DeleteClientUserDTO() {}

    public DeleteClientUserDTO(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public UUID getIdUsuario() { return idUsuario; }
    public void setIdUsuario(UUID idUsuario) { this.idUsuario = idUsuario; }
}
