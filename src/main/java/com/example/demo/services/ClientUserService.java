package com.example.demo.services;

import com.example.demo.model.clientUser.DTO.*;
import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.repository.ClientUserRepository;
import com.example.demo.repository.RestaurantUserRepository;
import jakarta.persistence.EntityNotFoundException;
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
    @Autowired
    private RestaurantUserRepository restaurantUserRepository; // Inject RestaurantUserRepository

    // ✔ Crear un usuario (Alta)
    public ClientUserDTO registerClient(CreateClientUserDTO clientUserDTO) {
        // Check if the email exists in either ClientUser or RestaurantUser
        if (clientUserRepository.findByEmail(clientUserDTO.getEmail()).isPresent() ||
                restaurantUserRepository.findByEmail(clientUserDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyRegisteredException("El email ya está registrado: " + clientUserDTO.getEmail());
        }

        ClientUser newUser = new ClientUser(
                clientUserDTO.getNombre(),
                clientUserDTO.getApellido(),
                clientUserDTO.getEmail(),
                clientUserDTO.getContraseña(),
                null,
                null
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

        System.out.println("Nombre:" + user.get().getNombre());
        System.out.println("Apellido:" + user.get().getApellido());
        System.out.println("Email:" + user.get().getEmail());
        System.out.println("Id:" + id);
        return user.map(clientUser -> new ClientUserDTO(
                clientUser.getIdUsuario(),
                clientUser.getNombre(),
                clientUser.getApellido(),
                clientUser.getEmail()
        ));
    }

    public Optional<ClientUser> getClientUserEntityById(UUID id) {
        return clientUserRepository.findById(id);
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

    // Modificar usuario
    public ClientUserDTO updateClient(UUID id, UpdateClientUserDTO updatedUserDTO) {
        ClientUser user = clientUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Log the values coming from the updatedUserDTO
        System.out.println("Updating user with firstName: " + updatedUserDTO.getNombre());
        System.out.println("Updating user with lastName: " + updatedUserDTO.getApellido());

        user.setNombre(updatedUserDTO.getNombre());
        user.setApellido(updatedUserDTO.getApellido());
        user.setEmail(updatedUserDTO.getEmail());

        if (updatedUserDTO.getContraseña() != null && !updatedUserDTO.getContraseña().isEmpty()) {
            user.setContraseña(updatedUserDTO.getContraseña());
        }

        // Log the updated user details before saving
        System.out.println("Updated user -> Nombre: " + user.getNombre());
        System.out.println("Updated user -> Apellido: " + user.getApellido());

        ClientUser updatedUser = clientUserRepository.save(user);
//Falta que cambie el mail y que si no quiero cambiar la contraseña no la cambie.
        // Return the updated DTO
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
        // Check if the email exists in the RestaurantUser table
        if (restaurantUserRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El email pertenece a un RestaurantUser, no a un ClientUser.");
        }

        // Proceed with ClientUser login
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

    // Custom exception for email already registered
    public static class EmailAlreadyRegisteredException extends RuntimeException {
        public EmailAlreadyRegisteredException(String message) {
            super(message);
        }
    }

    public GetLocationDTO updateLocation(UUID id, GetLocationDTO newLocation) {
        ClientUser user = clientUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setLatitude(newLocation.getLatitude());
        user.setLongitude(newLocation.getLongitude());

        ClientUser updatedUser = clientUserRepository.save(user);

        return new GetLocationDTO(updatedUser.getLatitude(), updatedUser.getLongitude());
    }

    public GetLocationDTO getLocation(UUID id) {
        ClientUser user = clientUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new GetLocationDTO(user.getLatitude(), user.getLongitude());
    }

    public void updateEmailNotifications(UUID clientId, boolean enabled) {
        ClientUser user = clientUserRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        user.setEmailNotificationsEnabled(enabled);
        clientUserRepository.save(user);
    }

    public boolean getEmailNotifications(UUID clientId) {
        ClientUser user = clientUserRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return user.getEmailNotificationsEnabled();
    }

}