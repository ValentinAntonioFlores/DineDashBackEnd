package com.example.demo.controller;

import com.example.demo.model.RestaurantUser;
import com.example.demo.services.RestaurantUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantUsers")
@CrossOrigin(origins = "http://localhost:3000") // Permite conexiones desde React
public class RestaurantUserController {

    @Autowired
    private RestaurantUserService restaurantUserService;

    // ✔ Crear un usuario restaurante (Alta)
    @PostMapping("/register")
    public ResponseEntity<RestaurantUser> registerUser(@RequestBody RestaurantUser restaurantUser) {
        return ResponseEntity.ok(restaurantUserService.registerRestaurantUser(restaurantUser));
    }

    // ✔ Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantUser> getUserById(@PathVariable UUID id) {
        return restaurantUserService.getRestaurantUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✔ Obtener todos los usuarios restaurantes
    @GetMapping
    public ResponseEntity<List<RestaurantUser>> getAllUsers() {
        return ResponseEntity.ok(restaurantUserService.getAllRestaurantUsers());
    }

    // ✔ Actualizar usuario restaurante (Modificación)
    @PutMapping("/{id}")
    public ResponseEntity<RestaurantUser> updateUser(@PathVariable UUID id, @RequestBody RestaurantUser updatedUser) {
        return ResponseEntity.ok(restaurantUserService.updateRestaurantUser(id, updatedUser));
    }

    // ✔ Eliminar usuario restaurante (Baja)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        restaurantUserService.deleteRestaurantUser(id);
        return ResponseEntity.noContent().build();
    }

    // ✔ Iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<RestaurantUser> loginUser(@RequestParam String email, @RequestParam String password) {
        return restaurantUserService.loginRestaurantUser(email, password)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
