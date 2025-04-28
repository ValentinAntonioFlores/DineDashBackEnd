package com.example.demo.services;

import com.example.demo.model.clientUser.DTO.*;
import com.example.demo.model.clientUser.ClientUser;
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
                clientUserDTO.getContraseña() // Contraseña sin encriptar
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
        Optional<ClientUser> user = clientUserRepository.findById(id); // Find user by ID
        return user.map(clientUser -> new ClientUserDTO(
                clientUser.getIdUsuario(),
                clientUser.getNombre(),
                clientUser.getApellido(),
                clientUser.getEmail()
        ));
    }


    // ✔ Obtener usuario por email
    public Optional<ClientUserDTO> getClientByEmail(String email) {
        Optional<ClientUser> user = clientUserRepository.findByEmail(email);
        if (user.isPresent()) {
            // Convert the found user to a ClientUserDTO
            ClientUser clientUser = user.get();
            ClientUserDTO clientUserDTO = new ClientUserDTO(
                    clientUser.getIdUsuario(),
                    clientUser.getNombre(),  // firstName
                    clientUser.getApellido(), // lastName
                    clientUser.getEmail()
            );
            return Optional.of(clientUserDTO);
        } else {
            return Optional.empty();  // User not found
        }
    }

    // ✔ Obtener todos los usuarios
    public List<ClientUserDTO> getAllClients() {
        return clientUserRepository.findAll().stream().map(user -> new ClientUserDTO(
                user.getIdUsuario(),
                user.getNombre(),
                user.getApellido(),
                user.getEmail()
        )).collect(Collectors.toList());
    }

    public ClientUserDTO updateClient(UUID id, UpdateClientUserDTO updatedUserDTO) {
        ClientUser user = clientUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setNombre(updatedUserDTO.getNombre());
        user.setApellido(updatedUserDTO.getApellido());
        user.setContraseña(updatedUserDTO.getContraseña()); // Contraseña sin encriptar

        ClientUser updatedUser = clientUserRepository.save(user);
        return new ClientUserDTO(
                updatedUser.getIdUsuario(),
                updatedUser.getNombre(),
                updatedUser.getApellido(),
                updatedUser.getEmail()
        );
    }



    // ✔ Eliminar usuario (Baja)
    public ClientUserResponseDTO deleteClient(UUID id) {
        ClientUser user = clientUserRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        clientUserRepository.delete(user);
        return new ClientUserResponseDTO("Usuario eliminado exitosamente", user.getIdUsuario());
    }

    // ✔ Iniciar sesión
    public Optional<ClientUserDTO> loginClient(String email, String password) {
        Optional<ClientUser> optionalUser = clientUserRepository.findByEmail(email);

        if (optionalUser.isEmpty()) return Optional.empty();

        ClientUser user = optionalUser.get();

        if (!user.getContraseña().equals(password)) return Optional.empty();

        // Convert user to DTO (excluding password)
        ClientUserDTO dto = new ClientUserDTO(
                user.getIdUsuario(),
                user.getNombre(),
                user.getApellido(),
                user.getEmail()
        );

        return Optional.of(dto);
    }

}