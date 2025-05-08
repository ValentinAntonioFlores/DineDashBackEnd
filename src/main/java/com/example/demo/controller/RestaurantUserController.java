package com.example.demo.controller;

import com.example.demo.jwt.JwtUtility;
import com.example.demo.jwt.TokenBlackListService;
import com.example.demo.model.restaurantUser.DTO.*;
import com.example.demo.model.restaurantUser.DTO.RestaurantLoginResponseDTO;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.services.RestaurantUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;


import java.util.*;

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

    // RestaurantUserController.java

    @PostMapping("register")
    public ResponseEntity<Map<String, String>> registerRestaurantUser(@RequestBody @Valid CreateRestaurantUserDTO createRestaurantUserDTO, BindingResult bindingResult) {
        // If there are validation errors, return a bad request response with the error messages
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append(" "));
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Validation failed: " + errors.toString());
            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            restaurantUserService.registerRestaurant(createRestaurantUserDTO);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Restaurant registered successfully.");
            return ResponseEntity.ok(successResponse);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


    @GetMapping("{id}")
    public ResponseEntity<RestaurantUserDTO> getUserById(@PathVariable UUID id) {
        try {
            RestaurantUser user = restaurantUserService.getRestaurantUserById(id);
            RestaurantUserDTO userDTO = new RestaurantUserDTO(user.getIdRestaurante(), user.getNombreRestaurante(), user.getEmail(), user.getImagen());
            return ResponseEntity.ok(userDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<RestaurantUserDTO>> getAllRestaurants() {
        List<RestaurantUserDTO> users = restaurantUserService.getAllRestaurantUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateUser(@PathVariable UUID id, @RequestBody UpdateRestaurantUserDTO updateRestaurantUserDTO) {
        try {
            restaurantUserService.updateRestaurantUser(id, updateRestaurantUserDTO);
            return ResponseEntity.ok("User updated successfully with name: " + updateRestaurantUserDTO.getNombreRestaurante() + " and email: " + updateRestaurantUserDTO.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        try {
            RestaurantUser user = restaurantUserService.getRestaurantUserById(id);
            if (user.isPresent()) {
                restaurantUserService.deleteRestaurantUser(id);
                return ResponseEntity.ok("User deleted successfully with name: " + user.getNombreRestaurante());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // RestaurantUserController.java
    @PostMapping("login")
    public ResponseEntity<RestaurantLoginResponseDTO> loginRestaurant(@RequestBody LoginRestaurantUserDTO loginDTO) {
        Optional<RestaurantUserDTO> user = restaurantUserService.loginRestaurant(loginDTO.getEmail(), loginDTO.getPassword());

        if (user.isPresent()) {
            RestaurantUserDTO loggedInUser = user.get();
            String token = jwtUtility.generateToken(loggedInUser.getEmail());

            System.out.println(token);
            System.out.println(loginDTO.getEmail());
            System.out.println(loggedInUser.getIdRestaurante());
            System.out.println(loggedInUser.getNombreRestaurante());

            RestaurantLoginResponseDTO response = new RestaurantLoginResponseDTO(
                    token,
                    loggedInUser.getNombreRestaurante(),
                    loggedInUser.getEmail(),
                    loggedInUser.getIdRestaurante(),
                    loggedInUser.getImagen(),
                    "restaurant" // <-- userType added

            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PostMapping("logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        tokenBlackListService.addToBlacklist(jwtToken);
        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<String> uploadImage(@PathVariable UUID id, @RequestBody UpdateRestaurantImageDTO imageDTO) {
        System.out.println(id);
        System.out.println("Request received at /{id}/image");
//        byte[] imageBytes = Base64.getDecoder().decode(imageDTO.getImagenBase64());
        String image = imageDTO.getImagenBase64();
        System.out.println(image);
        restaurantUserService.uploadImage(id, image );
        return ResponseEntity.ok("Image uploaded successfully.");
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<String> getImage(@PathVariable UUID id) {
        String image = restaurantUserService.getImage(id);
        if (image == null) return ResponseEntity.notFound().build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // adjust if needed
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @GetMapping("/public/restaurants")
    public List<PublicRestaurantDTO> getPublicRestaurants() {
        List<RestaurantUser> restaurants = restaurantUserRepository.findAll();

        return restaurants.stream()
                .map(r -> new PublicRestaurantDTO(
                        r.getId(),
                        r.getRestaurantName(),
                        r.getImageUrl(),     // or whatever field you store image in
                        r.getLayout()        // optional: layout preview
                ))
                .collect(Collectors.toList());
    }
}