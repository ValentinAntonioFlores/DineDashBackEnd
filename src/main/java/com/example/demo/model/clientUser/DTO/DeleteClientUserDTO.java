package com.example.demo.model.clientUser.DTO;

import java.util.UUID;

public class DeleteClientUserDTO {

    private UUID idUsuario;

    public DeleteClientUserDTO() {}

    public DeleteClientUserDTO(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public UUID getIdUsuario() { return idUsuario; }
    public void setIdUsuario(UUID idUsuario) { this.idUsuario = idUsuario; }
}
