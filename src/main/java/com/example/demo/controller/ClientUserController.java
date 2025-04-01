package com.example.demo.controller;

import com.example.demo.model.ClientUser;
import com.example.demo.services.ClientUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientUsers")
@CrossOrigin(origins = "http://localhost:3000") // Permite conexiones desde React
public class ClientUserController {

    @Autowired
    private ClientUserService clientUserService;

    // ✔ Crear un usuario (Alta)
    @PostMapping("/register")
    public ResponseEntity<ClientUser> registerUser(@RequestBody ClientUser clientUser) {
        return ResponseEntity.ok(clientUserService.registerClient(clientUser));
    }

    // ✔ Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClientUser> getUserById(@PathVariable UUID id) {
        return clientUserService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✔ Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<ClientUser>> getAllUsers() {
        return ResponseEntity.ok(clientUserService.getAllClients());
    }

    // ✔ Actualizar usuario (Modificación)
    @PutMapping("/{id}")
    public ResponseEntity<ClientUser> updateUser(@PathVariable UUID id, @RequestBody ClientUser updatedUser) {
        return ResponseEntity.ok(clientUserService.updateClient(id, updatedUser));
    }

    // ✔ Eliminar usuario (Baja)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        clientUserService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    // ✔ Iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<ClientUser> loginUser(@RequestParam String email, @RequestParam String password) {
        return clientUserService.loginClient(email, password)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

