package com.example.demo.controller;

import com.example.demo.jwt.JwtUtility;
import com.example.demo.jwt.TokenBlackListService;
import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.clientUser.DTO.*;
import com.example.demo.repository.ClientUserRepository;
import com.example.demo.services.ClientUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/clientUsers")
@CrossOrigin(origins = "http://localhost:3000") // Permite conexiones desde React
public class ClientUserController {

    @Autowired
    private ClientUserService clientUserService;
    @Autowired
    private ClientUserRepository clientUserRepository;
    @Autowired
    private JwtUtility jwtUtility;
    @Autowired
    private TokenBlackListService tokenBlackListService;


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody @Valid CreateClientUserDTO createClientUserDTO, BindingResult bindingResult) {
        // If there are validation errors, return a bad request response with the error messages
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append(" "));
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Validation failed: " + errors.toString());
            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            clientUserService.registerClient(createClientUserDTO);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "User registered successfully.");
            return ResponseEntity.ok(successResponse);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


    // ✔ Obtener usuario por ID
    @GetMapping("{id}")
    public ResponseEntity<ClientUserDTO> getUserById(@PathVariable UUID id) {
        Optional<ClientUserDTO> user = clientUserService.getClientById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    // ✔ Endpoint to get user by email
    @GetMapping("/clientUsers/{email}")
    public ResponseEntity<String> getUserByEmail(@PathVariable String email) {
        Optional<ClientUserDTO> userDTO = clientUserService.getClientByEmail(email);

        if (userDTO.isPresent()) {
            System.out.println("User found: " + userDTO.get().getNombre());
            return ResponseEntity.ok(userDTO.get().getNombre());
        } else {
            System.out.println("User not found with email: " + email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    // ✔ Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<ClientUserDTO>> getAllUsers() {
        return ResponseEntity.ok(clientUserService.getAllClients());
    }

    //Modificacion
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
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginClientUserDTO loginDTO) {
        Optional<ClientUserDTO> user = clientUserService.loginClient(loginDTO.getEmail(), loginDTO.getContraseña());

        if (user.isPresent()) {
            ClientUserDTO loggedUser = user.get();
            String token = jwtUtility.generateToken(loggedUser.getEmail());

            System.out.println(token);
            System.out.println(loginDTO.getEmail());
            System.out.println(loggedUser.getIdUsuario());
            System.out.println(loggedUser.getNombre());
            System.out.println(loggedUser.getApellido());

            LoginResponseDTO response = new LoginResponseDTO(
                    token,
                    loggedUser.getNombre(),
                    loggedUser.getApellido(),
                    loggedUser.getEmail(),
                    loggedUser.getIdUsuario()
            );

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
