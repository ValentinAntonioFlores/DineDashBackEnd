package com.example.demo.services;

import com.example.demo.model.RestaurantUser;
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
    public RestaurantUser registerRestaurantUser(RestaurantUser restaurantUser) {
        Optional<RestaurantUser> existingUser = restaurantUserRepository.findByEmail(restaurantUser.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("El email ya está registrado.");
        }
        return restaurantUserRepository.save(restaurantUser);
    }

    // ✔ Obtener usuario por ID
    public Optional<RestaurantUser> getRestaurantUserById(UUID id) {
        return restaurantUserRepository.findById(id);
    }

    // ✔ Obtener todos los usuarios restaurantes
    public List<RestaurantUser> getAllRestaurantUsers() {
        return restaurantUserRepository.findAll();
    }

    // ✔ Actualizar un usuario restaurante (Modificación)
    public RestaurantUser updateRestaurantUser(UUID id, RestaurantUser updatedUser) {
        return restaurantUserRepository.findById(id).map(user -> {
            user.setIdRestaurante(updatedUser.getIdRestaurante());
            user.setNombreRestaurante(updatedUser.getNombreRestaurante());
            user.setEmail(updatedUser.getEmail());
            user.setContraseña(updatedUser.getContraseña()); // No encripta, el frontend lo maneja
            return restaurantUserRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // ✔ Eliminar un usuario restaurante (Baja)
    public void deleteRestaurantUser(UUID id) {
        if (!restaurantUserRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        restaurantUserRepository.deleteById(id);
    }

    // ✔ Iniciar sesión
    public Optional<RestaurantUser> loginRestaurantUser(String email, String password) {
        Optional<RestaurantUser> user = restaurantUserRepository.findByEmail(email);
        if (user.isPresent() && user.get().getContraseña().equals(password)) {
            return user;
        }
        return Optional.empty();
    }
}
