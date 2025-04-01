
package com.example.demo.controller;

import com.example.demo.model.RestaurantUser;
import com.example.demo.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/usuariosRestaurante")
@CrossOrigin(origins = "http://localhost:3000")
public class RestaurantUserController {

    @Autowired
    private RestaurantUserRepository usuarioRestauranteRepository;


    // 📌 Registrar un nuevo usuario restaurante
    @PostMapping("/register")
    public RestaurantUser register(@RequestBody RestaurantUser usuario) {
        return usuarioRestauranteRepository.save(usuario);
    }

    // 📌 Obtener todos los usuarios restaurante
    @GetMapping("/all")
    public List<RestaurantUser> getAllUsuariosRestaurante() {
        return usuarioRestauranteRepository.findAll();
    }

    // 📌 Obtener un usuario restaurante por email (para login)
    @PostMapping("/login")
    public RestaurantUser login(@RequestBody RestaurantUser usuario) {
        Optional<RestaurantUser> usuarioRestaurante = usuarioRestauranteRepository.findByEmail(usuario.getEmail());
        if (usuarioRestaurante.isPresent() && usuario.getContraseña().equals(usuarioRestaurante.get().getContraseña())) {
            return usuarioRestaurante.get(); // Usuario autenticado
        }

        return usuario;
    }
}
