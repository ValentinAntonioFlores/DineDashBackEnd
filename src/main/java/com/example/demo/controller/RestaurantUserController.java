package com.example.demo.controller;

import com.example.demo.jwt.JwtUtility;
import com.example.demo.jwt.TokenBlackListService;
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
    @Autowired
    private JwtUtility jwtUtility;
    @Autowired
    private TokenBlackListService tokenBlackListService;

    @PostMapping("register")
    public ResponseEntity<String> registerRestaurantUser(@RequestBody CreateRestaurantUserDTO createRestaurantUserDTO) {
        try {
            RestaurantUserResponseDTO response = restaurantUserService.registerRestaurantUser(createRestaurantUserDTO);
            return ResponseEntity.ok("Restaurant user registered successfully: " + createRestaurantUserDTO.getNombreRestaurante());
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
    public ResponseEntity<List<RestaurantUserDTO>> getAllRestaurants() {
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
    public ResponseEntity<String> loginRestaurantUser(@RequestBody LoginRestaurantUserDTO loginRequest) {
        Optional<LoginRestaurantUserDTO> user = restaurantUserService.loginRestaurantUser(loginRequest.getEmail(), loginRequest.getContraseña());
        if (user.isPresent()) {
            String token = jwtUtility.generateToken(loginRequest.getEmail());
            return ResponseEntity.ok("Login successful. Token: " + token);
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    @PostMapping("logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        tokenBlackListService.addToBlacklist(jwtToken);
        return ResponseEntity.ok("Logout successful");
    }
}