package com.example.demo.services;

import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.clientUser.DTO.ClientUserDTO;
import com.example.demo.model.clientUser.DTO.CreateClientUserDTO;
import com.example.demo.model.restaurantUser.DTO.*;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.repository.ClientUserRepository;
import com.example.demo.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RestaurantUserService {

    @Autowired
    private RestaurantUserRepository restaurantUserRepository;

    @Autowired
    private ClientUserRepository clientUserRepository; // Inject ClientUserRepository

    // RestaurantUserService.java

    public RestaurantUserDTO registerRestaurant(CreateRestaurantUserDTO restaurantUserDTO) {
        // Check if the email exists in ClientUser or RestaurantUser
        Optional<ClientUser> clientUserOptional = clientUserRepository.findByEmail(restaurantUserDTO.getEmail());
        Optional<RestaurantUser> restaurantUserOptional = restaurantUserRepository.findByEmail(restaurantUserDTO.getEmail());

        if (clientUserOptional.isPresent() || restaurantUserOptional.isPresent()) {
            // If the email is found in either repository, throw an exception
            throw new EmailAlreadyRegisteredException("El email ya está registrado: " + restaurantUserDTO.getEmail());
        }

        // Create new RestaurantUser
        RestaurantUser newRestaurantUser = new RestaurantUser(
                restaurantUserDTO.getNombreRestaurante(),
                restaurantUserDTO.getEmail(),
                restaurantUserDTO.getContraseña() // Contraseña sin encriptar
        );

        RestaurantUser savedRestaurantUser = restaurantUserRepository.save(newRestaurantUser);
        return new RestaurantUserDTO(
                savedRestaurantUser.getIdRestaurante(),
                savedRestaurantUser.getNombreRestaurante(),
                savedRestaurantUser.getEmail()
        );
    }


    public static class EmailAlreadyRegisteredException extends RuntimeException {
        public EmailAlreadyRegisteredException(String message) {
            super(message);
        }
    }

    public RestaurantUser getRestaurantUserById(UUID id) {
        return restaurantUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<RestaurantUserDTO> getAllRestaurantUsers() {
        return restaurantUserRepository.findAll().stream()
                .map(user -> new RestaurantUserDTO(
                        user.getIdRestaurante(),
                        user.getNombreRestaurante(),
                        user.getEmail()
                ))
                .toList();
    }

    public RestaurantUserResponseDTO updateRestaurantUser(UUID id, UpdateRestaurantUserDTO updateRestaurantUserDTO) {
        RestaurantUser user = restaurantUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setNombreRestaurante(updateRestaurantUserDTO.getNombreRestaurante());
        user.setEmail(updateRestaurantUserDTO.getEmail());
        user.setContraseña(updateRestaurantUserDTO.getContraseña());

        RestaurantUser updatedUser = restaurantUserRepository.save(user);
        return new RestaurantUserResponseDTO(
                updatedUser.getIdRestaurante(),
                updatedUser.getNombreRestaurante(),
                updatedUser.getEmail()
        );
    }

    public void deleteRestaurantUser(UUID id) {
        restaurantUserRepository.deleteById(id);
    }

    // RestaurantUserService.java
    public Optional<RestaurantUserDTO> loginRestaurant(String email, String password) {
        // Check if the email exists in the ClientUser table
        if (clientUserRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El email pertenece a un ClientUser, no a un RestaurantUser.");
        }

        // Proceed with RestaurantUser login
        Optional<RestaurantUser> optionalUser = restaurantUserRepository.findByEmail(email);

        if (optionalUser.isEmpty()) return Optional.empty();

        RestaurantUser user = optionalUser.get();

        if (!user.getContraseña().equals(password)) return Optional.empty();

        // Convert user to DTO (excluding password)
        RestaurantUserDTO dto = new RestaurantUserDTO(
                user.getIdRestaurante(),
                user.getNombreRestaurante(),
                user.getEmail()
        );

        return Optional.of(dto);
    }
}

