package com.example.demo.controller;

import com.example.demo.model.clientUser.DTO.ClientUserDTO;
import com.example.demo.model.clientUser.DTO.ClientUserResponseDTO;
import com.example.demo.model.clientUser.DTO.CreateClientUserDTO;
import com.example.demo.model.clientUser.DTO.UpdateClientUserDTO;
import com.example.demo.services.ClientUserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // ✔ Crear un usuario (Alta)
    @PostMapping("register")
    public ResponseEntity<ClientUserDTO> registerUser(@RequestBody CreateClientUserDTO clientUserDTO) {
        return ResponseEntity.ok(clientUserService.registerClient(clientUserDTO));
    }

    // ✔ Obtener usuario por ID
    @GetMapping("/{id}")
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
    @PutMapping("/{id}")
    public ResponseEntity<ClientUserDTO> updateUser(@PathVariable UUID id, @RequestBody UpdateClientUserDTO updatedUserDTO) {
        return ResponseEntity.ok(clientUserService.updateClient(id, updatedUserDTO));
    }

    // ✔ Eliminar usuario (Baja)
    @DeleteMapping("/{id}")
    public ResponseEntity<ClientUserResponseDTO> deleteUser(@PathVariable UUID id) {
        return ResponseEntity.ok(clientUserService.deleteClient(id));
    }

    // ✔ Iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<ClientUserDTO> loginUser(@RequestParam String email, @RequestParam String password) {
        Optional<ClientUserDTO> user = clientUserService.loginClient(email, password);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.status(401).build());
    }
}
