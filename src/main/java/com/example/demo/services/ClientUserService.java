package com.example.demo.services;

import com.example.demo.model.ClientUser;
import com.example.demo.repository.ClientUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientUserService {

    @Autowired
    private ClientUserRepository clientUserRepository;

    // ✔ Crear un usuario (Alta)
    public ClientUser registerClient(ClientUser clientUser) {
        Optional<ClientUser> existingUser = clientUserRepository.findByEmail(clientUser.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("El email ya está registrado.");
        }
        return clientUserRepository.save(clientUser);
    }

    // ✔ Obtener usuario por ID
    public Optional<ClientUser> getClientById(UUID id) {
        return clientUserRepository.findById(id);
    }

    // ✔ Obtener todos los usuarios
    public List<ClientUser> getAllClients() {
        return clientUserRepository.findAll();
    }

    // ✔ Actualizar un usuario (Modificación)
    public ClientUser updateClient(UUID id, ClientUser updatedUser) {
        return clientUserRepository.findById(id).map(client -> {
            client.setNombre(updatedUser.getNombre());
            client.setApellido(updatedUser.getApellido());
            client.setEmail(updatedUser.getEmail());
            client.setContraseña(updatedUser.getContraseña());
            return clientUserRepository.save(client);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // ✔ Eliminar un usuario (Baja)
    public void deleteClient(UUID id) {
        if (!clientUserRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        clientUserRepository.deleteById(id);
    }

    // ✔ Iniciar sesión
    public Optional<ClientUser> loginClient(String email, String password) {
        Optional<ClientUser> client = clientUserRepository.findByEmail(email);
        if (client.isPresent() && client.get().getContraseña().equals(password)) {
            return client;
        }
        return Optional.empty();
    }
}
