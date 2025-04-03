package com.example.demo.controller;

import com.example.demo.model.restaurantUser.DTO.*;
import com.example.demo.services.RestaurantUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantUsers")
@CrossOrigin(origins = "http://localhost:3000") // Permite conexiones desde React
public class RestaurantUserController {

    @Autowired
    private RestaurantUserService restaurantUserService;

    // ✔ Crear un usuario restaurante (Alta)
    @PostMapping("register")
    public ResponseEntity<RestaurantUserResponseDTO> registerUser(@RequestBody CreateRestaurantUserDTO createRestaurantUserDTO) {
        RestaurantUserResponseDTO response = restaurantUserService.registerRestaurantUser(createRestaurantUserDTO);
        return ResponseEntity.ok(response);
    }

    // ✔ Obtener usuario por ID
    @GetMapping("{id}")
    public ResponseEntity<RestaurantUserDTO> getUserById(@PathVariable UUID id) {
        return restaurantUserService.getRestaurantUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✔ Obtener todos los usuarios restaurantes
    @GetMapping
    public ResponseEntity<List<RestaurantUserDTO>> getAllUsers() {
        List<RestaurantUserDTO> users = restaurantUserService.getAllRestaurantUsers();
        return ResponseEntity.ok(users);
    }

    // ✔ Actualizar usuario restaurante (Modificación)
    @PutMapping("{id}")
    public ResponseEntity<RestaurantUserResponseDTO> updateUser(@PathVariable UUID id, @RequestBody UpdateRestaurantUserDTO updateRestaurantUserDTO) {
        RestaurantUserResponseDTO response = restaurantUserService.updateRestaurantUser(id, updateRestaurantUserDTO);
        return ResponseEntity.ok(response);
    }

    // ✔ Eliminar usuario restaurante (Baja)
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        restaurantUserService.deleteRestaurantUser(id);
        return ResponseEntity.noContent().build();
    }

    // ✔ Iniciar sesión
    @PostMapping("login")
    public ResponseEntity<RestaurantUserResponseDTO> loginUser(@RequestParam String email, @RequestParam String password) {
        return restaurantUserService.loginRestaurantUser(email, password)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
