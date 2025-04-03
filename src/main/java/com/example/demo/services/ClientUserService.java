package com.example.demo.services;

import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.clientUser.DTO.ClientUserDTO;
import com.example.demo.model.clientUser.DTO.ClientUserResponseDTO;
import com.example.demo.model.clientUser.DTO.CreateClientUserDTO;
import com.example.demo.model.clientUser.DTO.UpdateClientUserDTO;
import com.example.demo.repository.ClientUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientUserService {

    @Autowired
    private ClientUserRepository clientUserRepository;

    // ✔ Crear un usuario (Alta)
    public ClientUserDTO registerClient(CreateClientUserDTO clientUserDTO) {
        Optional<ClientUser> existingUser = clientUserRepository.findByEmail(clientUserDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("El email ya está registrado.");
        }

        ClientUser newUser = new ClientUser(
                clientUserDTO.getNombre(),
                clientUserDTO.getApellido(),
                clientUserDTO.getEmail(),
                clientUserDTO.getContraseña()
        );

        ClientUser savedUser = clientUserRepository.save(newUser);
        return new ClientUserDTO(
                savedUser.getIdUsuario(),
                savedUser.getNombre(),
                savedUser.getApellido(),
                savedUser.getEmail()
        );
    }

    // ✔ Obtener usuario por ID
    public Optional<ClientUserDTO> getClientById(UUID id) {
        return clientUserRepository.findById(id)
                .map(client -> new ClientUserDTO(
                        client.getIdUsuario(),
                        client.getNombre(),
                        client.getApellido(),
                        client.getEmail()
                ));
    }

    // ✔ Obtener todos los usuarios
    public List<ClientUserDTO> getAllClients() {
        return clientUserRepository.findAll().stream()
                .map(client -> new ClientUserDTO(
                        client.getIdUsuario(),
                        client.getNombre(),
                        client.getApellido(),
                        client.getEmail()
                ))
                .collect(Collectors.toList());
    }

    // ✔ Actualizar un usuario (Modificación)
    public ClientUserDTO updateClient(UUID id, UpdateClientUserDTO updatedUserDTO) {
        return clientUserRepository.findById(id).map(client -> {
            if (updatedUserDTO.getNombre() != null) client.setNombre(updatedUserDTO.getNombre());
            if (updatedUserDTO.getApellido() != null) client.setApellido(updatedUserDTO.getApellido());
            if (updatedUserDTO.getEmail() != null) client.setEmail(updatedUserDTO.getEmail());
            if (updatedUserDTO.getContraseña() != null) client.setContraseña(updatedUserDTO.getContraseña());

            ClientUser savedUser = clientUserRepository.save(client);
            return new ClientUserDTO(
                    savedUser.getIdUsuario(),
                    savedUser.getNombre(),
                    savedUser.getApellido(),
                    savedUser.getEmail()
            );
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // ✔ Eliminar un usuario (Baja)
    public ClientUserResponseDTO deleteClient(UUID id) {
        if (!clientUserRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        clientUserRepository.deleteById(id);
        return new ClientUserResponseDTO("Usuario eliminado correctamente", id);
    }

    // ✔ Iniciar sesión
    public Optional<ClientUserDTO> loginClient(String email, String password) {
        Optional<ClientUser> client = clientUserRepository.findByEmail(email);
        if (client.isPresent() && client.get().getContraseña().equals(password)) {
            ClientUser user = client.get();
            return Optional.of(new ClientUserDTO(
                    user.getIdUsuario(),
                    user.getNombre(),
                    user.getApellido(),
                    user.getEmail()
            ));
        }
        return Optional.empty();
    }
}
