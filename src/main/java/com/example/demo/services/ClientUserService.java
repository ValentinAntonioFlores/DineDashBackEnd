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
        return clientUserRepository.findById(id).map(user -> new ClientUserDTO(
                user.getIdUsuario(),
                user.getNombre(),
                user.getApellido(),
                user.getEmail()
        ));
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

    // ✔ Actualizar usuario (Modificación)
    public ClientUserDTO updateClient(UUID id, UpdateClientUserDTO updatedUserDTO) {
        ClientUser user = clientUserRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
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
    public Optional<LoginClientUserDTO> loginClient(String email, String password) {
        Optional<ClientUser> client = clientUserRepository.findByEmail(email);
        if (client.isPresent() && password.equals(client.get().getContraseña())) { // Comparar contraseñas sin encriptar
            ClientUser loggedInClient = client.get();
            LoginClientUserDTO responseDTO = new LoginClientUserDTO(
                    loggedInClient.getEmail(),
                    loggedInClient.getContraseña()
            );
            return Optional.of(responseDTO);
        }
        return Optional.empty();
    }
}