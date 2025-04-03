package com.example.demo.services;

import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.model.restaurantUser.DTO.CreateRestaurantUserDTO;
import com.example.demo.model.restaurantUser.DTO.RestaurantUserDTO;
import com.example.demo.model.restaurantUser.DTO.RestaurantUserResponseDTO;
import com.example.demo.model.restaurantUser.DTO.UpdateRestaurantUserDTO;
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

    // ✔ Crear un usuario restaurante (Alta)
    public RestaurantUserResponseDTO registerRestaurantUser(CreateRestaurantUserDTO createRestaurantUserDTO) {
        // Verificar si el email ya está registrado
        Optional<RestaurantUser> existingUser = restaurantUserRepository.findByEmail(createRestaurantUserDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("El email ya está registrado.");
        }
        // Crear el nuevo usuario
        RestaurantUser user = new RestaurantUser();
        user.setIdRestaurante(UUID.randomUUID()); // Inicializar idRestaurante
        user.setNombreRestaurante(createRestaurantUserDTO.getNombreRestaurante());
        user.setEmail(createRestaurantUserDTO.getEmail());
        user.setContraseña(createRestaurantUserDTO.getContraseña()); // Contraseña sin encriptar

        // Guardar el usuario y devolver el DTO de respuesta
        RestaurantUser savedUser = restaurantUserRepository.save(user);
        return new RestaurantUserResponseDTO("Usuario creado exitosamente", savedUser.getIdAdmin());
    }

    // ✔ Obtener usuario por ID
    public Optional<RestaurantUserDTO> getRestaurantUserById(UUID id) {
        return restaurantUserRepository.findById(id)
                .map(user -> new RestaurantUserDTO(user.getIdAdmin(), user.getNombreRestaurante(), user.getEmail()));
    }

    // ✔ Obtener todos los usuarios restaurantes
    public List<RestaurantUserDTO> getAllRestaurantUsers() {
        List<RestaurantUser> users = restaurantUserRepository.findAll();
        return users.stream()
                .map(user -> new RestaurantUserDTO(user.getIdAdmin(), user.getNombreRestaurante(), user.getEmail()))
                .toList();
    }

    // ✔ Actualizar un usuario restaurante (Modificación)
    public RestaurantUserResponseDTO updateRestaurantUser(UUID id, UpdateRestaurantUserDTO updateRestaurantUserDTO) {
        return restaurantUserRepository.findById(id).map(user -> {
            user.setNombreRestaurante(updateRestaurantUserDTO.getNombreRestaurante());
            user.setEmail(updateRestaurantUserDTO.getEmail());
            // Actualizar la contraseña si se proporciona una nueva
            if (updateRestaurantUserDTO.getContraseña() != null) {
                user.setContraseña(updateRestaurantUserDTO.getContraseña()); // Contraseña sin encriptar
            }

            // Guardar el usuario actualizado y devolver el DTO de respuesta
            RestaurantUser savedUser = restaurantUserRepository.save(user);
            return new RestaurantUserResponseDTO("Usuario actualizado exitosamente", savedUser.getIdAdmin());
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // ✔ Eliminar un usuario restaurante (Baja)
    public RestaurantUserResponseDTO deleteRestaurantUser(UUID id) {
        if (restaurantUserRepository.existsById(id)) {
            restaurantUserRepository.deleteById(id);
            return new RestaurantUserResponseDTO("Usuario eliminado exitosamente", id);
        }
        return new RestaurantUserResponseDTO("Usuario no encontrado", id);
    }

    // ✔ Iniciar sesión
    public Optional<RestaurantUserResponseDTO> loginRestaurantUser(String email, String password) {
        Optional<RestaurantUser> user = restaurantUserRepository.findByEmail(email);
        if (user.isPresent() && user.get().getContraseña().equals(password)) { // Comparar contraseñas sin encriptar
            RestaurantUser loggedInUser = user.get();
            RestaurantUserResponseDTO responseDTO = new RestaurantUserResponseDTO("Inicio de sesión exitoso", loggedInUser.getIdAdmin());
            return Optional.of(responseDTO);
        }
        return Optional.empty();
    }
}

