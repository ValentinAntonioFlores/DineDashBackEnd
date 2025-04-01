package com.example.demo.controller;

import com.example.demo.model.ClientUser;
import com.example.demo.repository.ClientUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientUsers")
@CrossOrigin(origins = "http://localhost:3000") // Permite conexiones desde el frontend
public class ClientUserController {

    @Autowired
    private ClientUserRepository clientUserRepository;

    @PostMapping("/register")
    public ClientUser registerUser(@RequestBody ClientUser clientUser) {
        return clientUserRepository.save(clientUser);
    }

    @GetMapping("/all")
    public List<ClientUser> getAllUsers() {
        return clientUserRepository.findAll();
    }

    @PostMapping("/login")
    public Optional<ClientUser> loginUser(@RequestBody ClientUser loginRequest) {
        Optional<ClientUser> user = clientUserRepository.findByEmail(loginRequest.getEmail());
        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
            return user; // Usuario autenticado
        }
        return Optional.empty(); // Credenciales incorrectas
    }
}