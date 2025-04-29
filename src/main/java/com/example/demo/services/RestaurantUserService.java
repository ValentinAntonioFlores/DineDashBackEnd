package com.example.demo.services;

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

    public RestaurantUserResponseDTO registerRestaurantUser(CreateRestaurantUserDTO restaurantUserDTO) {
        Optional<RestaurantUser> existingUser = restaurantUserRepository.findByEmail(restaurantUserDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlreadyRegisteredException("El email ya está registrado: " + restaurantUserDTO.getEmail());
        }

        RestaurantUser newUser = new RestaurantUser(
                restaurantUserDTO.getNombreRestaurante(),
                restaurantUserDTO.getEmail(),
                restaurantUserDTO.getContraseña()
        );

        RestaurantUser savedUser = restaurantUserRepository.save(newUser);
        return new RestaurantUserResponseDTO(
                savedUser.getIdRestaurante(),
                savedUser.getNombreRestaurante(),
                savedUser.getEmail()
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

    public Optional<LoginRestaurantUserDTO> loginRestaurantUser(String email, String password) {
        // Check if the email exists in the ClientUser table
        if (clientUserRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El email pertenece a un ClientUser, no a un RestaurantUser.");
        }

        // Proceed with RestaurantUser login
        Optional<RestaurantUser> user = restaurantUserRepository.findByEmail(email);
        if (user.isPresent() && password.equals(user.get().getContraseña())) {
            RestaurantUser loggedInUser = user.get();
            return Optional.of(new LoginRestaurantUserDTO(
                    loggedInUser.getNombreRestaurante(),
                    loggedInUser.getEmail()
            ));
        }
        return Optional.empty();
    }
}
