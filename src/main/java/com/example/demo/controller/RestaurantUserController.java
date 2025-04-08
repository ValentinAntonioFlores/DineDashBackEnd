package com.example.demo.controller;

import com.example.demo.model.restaurantUser.DTO.*;
import com.example.demo.services.RestaurantUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantUsers")
@CrossOrigin(origins = "http://localhost:3000")
public class RestaurantUserController {

    @Autowired
    private RestaurantUserService restaurantUserService;

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody CreateRestaurantUserDTO createRestaurantUserDTO) {
        CreateRestaurantUserDTO restaurantUserDTO = new CreateRestaurantUserDTO(
                createRestaurantUserDTO.getNombreRestaurante(),
                createRestaurantUserDTO.getEmail(),
                createRestaurantUserDTO.getContraseña()
        );
        try {
            RestaurantUserResponseDTO response = restaurantUserService.registerRestaurantUser(restaurantUserDTO);
            return ResponseEntity.ok("Usuario registrado existosamente con nombre: " + restaurantUserDTO.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<RestaurantUserDTO> getUserById(@PathVariable UUID id) {
        return restaurantUserService.getRestaurantUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RestaurantUserDTO>> getAllUsers() {
        List<RestaurantUserDTO> users = restaurantUserService.getAllRestaurantUsers();
        return ResponseEntity.ok(users);
    }
    @PutMapping("{id}")
    public ResponseEntity<String> updateUser(@PathVariable UUID id, @RequestBody UpdateRestaurantUserDTO updateRestaurantUserDTO) {
        try {
            RestaurantUserResponseDTO response = restaurantUserService.updateRestaurantUser(id, updateRestaurantUserDTO);
            return ResponseEntity.ok("User updated successfully with name: " + updateRestaurantUserDTO.getNombreRestaurante() + " and email: " + updateRestaurantUserDTO.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        try {
            Optional<RestaurantUserDTO> user = restaurantUserService.getRestaurantUserById(id);
            if (user.isPresent()) {
                restaurantUserService.deleteRestaurantUser(id);
                return ResponseEntity.ok("User deleted successfully with name: " + user.get().getNombreRestaurante());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
        Optional<LoginRestaurantUserDTO> user = restaurantUserService.loginRestaurantUser(email, password);
        if (user.isPresent()) {
            return ResponseEntity.ok("Login successful for user: " + email);
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}