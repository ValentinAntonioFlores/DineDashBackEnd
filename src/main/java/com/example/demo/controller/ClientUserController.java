package com.example.demo.controller;

import com.example.demo.jwt.JwtUtility;
import com.example.demo.jwt.TokenBlackListService;
import com.example.demo.model.clientUser.DTO.*;
import com.example.demo.services.ClientUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/clientUsers")
@CrossOrigin(origins = "http://localhost:3000") // Permite conexiones desde React
public class ClientUserController {

    @Autowired
    private ClientUserService clientUserService;
    @Autowired
    private JwtUtility jwtUtility;
    @Autowired
    private TokenBlackListService tokenBlackListService;

    // ✔ Crear un usuario (Alta)
    @PostMapping("register")
    public ResponseEntity<ClientUserDTO> registerUser(@RequestBody CreateClientUserDTO clientUserDTO) {
        return ResponseEntity.ok(clientUserService.registerClient(clientUserDTO));
    }

    // ✔ Obtener usuario por ID
    @GetMapping("{id}")
    public ResponseEntity<ClientUserDTO> getUserById(@PathVariable UUID id) {
        Optional<ClientUserDTO> user = clientUserService.getClientById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // ✔ Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<ClientUserDTO>> getAllUsers() {
        return ResponseEntity.ok(clientUserService.getAllClients());
    }

    // ✔ Actualizar usuario (Modificación)
    @PutMapping("{id}")
    public ResponseEntity<ClientUserDTO> updateUser(@PathVariable UUID id, @RequestBody UpdateClientUserDTO updatedUserDTO) {
        return ResponseEntity.ok(clientUserService.updateClient(id, updatedUserDTO));
    }

    // ✔ Eliminar usuario (Baja)
    @DeleteMapping("{id}")
    public ResponseEntity<ClientUserResponseDTO> deleteUser(@PathVariable UUID id) {
        return ResponseEntity.ok(clientUserService.deleteClient(id));
    }

    // ✔ Iniciar sesión
    @PostMapping("login")
    public ResponseEntity<String> loginUser(@RequestBody LoginClientUserDTO loginDTO) {
        Optional<LoginClientUserDTO> user = clientUserService.loginClient(loginDTO.getEmail(), loginDTO.getPassword());
        if (user.isPresent()) {
            String token = jwtUtility.generateToken(loginDTO.getEmail());
            return ResponseEntity.ok("Login successful. Token: " + token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    // ✔ Cerrar sesión
    @PostMapping("logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        tokenBlackListService.addToBlacklist(jwtToken);
        return ResponseEntity.ok("Logout successful");
    }
}
