
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


    @PostMapping("/register")
    public RestaurantUser register(@RequestBody RestaurantUser usuario) {
        return usuarioRestauranteRepository.save(usuario);
    }

    @GetMapping("/all")
    public List<RestaurantUser> getAllUsuariosRestaurante() {
        return usuarioRestauranteRepository.findAll();
    }

    @PostMapping("/login")
    public RestaurantUser login(@RequestBody RestaurantUser usuario) {
        Optional<RestaurantUser> usuarioRestaurante = usuarioRestauranteRepository.findByEmail(usuario.getEmail());
        if (usuarioRestaurante.isPresent() && usuario.getContraseña().equals(usuarioRestaurante.get().getContraseña())) {
            return usuarioRestaurante.get();
        }

        return usuario;
    }
}
