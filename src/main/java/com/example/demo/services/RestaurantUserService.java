package com.example.demo.services;

import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.clientUser.DTO.ClientUserDTO;
import com.example.demo.model.clientUser.DTO.CreateClientUserDTO;
import com.example.demo.model.restaurantUser.DTO.*;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.model.table.DTO.TableDTO;
import com.example.demo.repository.ClientUserRepository;
import com.example.demo.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RestaurantUserService {

    @Autowired
    private RestaurantUserRepository restaurantUserRepository;

    @Autowired
    private ClientUserRepository clientUserRepository; // Inject ClientUserRepository

    // RestaurantUserService.java

    public List<RestaurantUser> searchRestaurantsByName(String name) {
        return restaurantUserRepository.findByNombreRestauranteContainingIgnoreCase(name);
    }

    public RestaurantUserDTO registerRestaurant(CreateRestaurantUserDTO restaurantUserDTO) {
        // Check if the email exists in ClientUser or RestaurantUser
        Optional<ClientUser> clientUserOptional = clientUserRepository.findByEmail(restaurantUserDTO.getEmail());
        Optional<RestaurantUser> restaurantUserOptional = restaurantUserRepository.findByEmail(restaurantUserDTO.getEmail());

        if (clientUserOptional.isPresent() || restaurantUserOptional.isPresent()) {
            // If the email is found in either repository, throw an exception
            throw new EmailAlreadyRegisteredException("El email ya está registrado: " + restaurantUserDTO.getEmail());
        }

        // Create new RestaurantUser
        RestaurantUser newRestaurantUser = new RestaurantUser(
                restaurantUserDTO.getNombreRestaurante(),
                restaurantUserDTO.getEmail(),
                restaurantUserDTO.getContraseña(), // Contraseña sin encriptar
                restaurantUserDTO.getLatitude(),  // Latitude
                restaurantUserDTO.getLongitude()  // Longitude
        );

        RestaurantUser savedRestaurantUser = restaurantUserRepository.save(newRestaurantUser);
        return new RestaurantUserDTO(
                savedRestaurantUser.getIdRestaurante(),
                savedRestaurantUser.getNombreRestaurante(),
                savedRestaurantUser.getEmail(),
                savedRestaurantUser.getImagen(),
                savedRestaurantUser.getLayout()
                );
    }


    public static class EmailAlreadyRegisteredException extends RuntimeException {
        public EmailAlreadyRegisteredException(String message) {
            super(message);
        }
    }

    public RestaurantUser getRestaurantUserById(UUID id) {
        return restaurantUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<RestaurantUserDTO> getAllRestaurantUsers() {
        return restaurantUserRepository.findAll().stream()
                .map(user -> new RestaurantUserDTO(
                        user.getIdRestaurante(),
                        user.getNombreRestaurante(),
                        user.getEmail(),
                        user.getImagen(),
                        user.getLayout()
                ))
                .toList();
    }

    public RestaurantUserResponseDTO updateRestaurantUser(UUID id, UpdateRestaurantUserDTO updateRestaurantUserDTO) {
        RestaurantUser user = restaurantUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setNombreRestaurante(updateRestaurantUserDTO.getNombreRestaurante());
        user.setEmail(updateRestaurantUserDTO.getEmail());
        user.setContraseña(updateRestaurantUserDTO.getContraseña());

        RestaurantUser updatedUser = restaurantUserRepository.save(user);
        return new RestaurantUserResponseDTO(
                updatedUser.getIdRestaurante(),
                updatedUser.getNombreRestaurante(),
                updatedUser.getEmail()
        );
    }

    public void deleteRestaurantUser(UUID id) {
        restaurantUserRepository.deleteById(id);
    }

    // RestaurantUserService.java
    public Optional<RestaurantUserDTO> loginRestaurant(String email, String password) {
        // Check if the email exists in the ClientUser table
        if (clientUserRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El email pertenece a un ClientUser, no a un RestaurantUser.");
        }

        // Proceed with RestaurantUser login
        Optional<RestaurantUser> optionalUser = restaurantUserRepository.findByEmail(email);

        if (optionalUser.isEmpty()) return Optional.empty();

        RestaurantUser user = optionalUser.get();

        if (!user.getContraseña().equals(password)) return Optional.empty();

        // Convert user to DTO (excluding password)
        RestaurantUserDTO dto = new RestaurantUserDTO(
                user.getIdRestaurante(),
                user.getNombreRestaurante(),
                user.getEmail(),
                user.getImagen(),
                user.getLayout()
        );

        return Optional.of(dto);
    }

    public void uploadImage(UUID userId, String image) {
        RestaurantUser user = getRestaurantUserById(userId);
        user.setImagen(image);
        restaurantUserRepository.save(user);
    }

    public String getImage(UUID userId) {
        return getRestaurantUserById(userId).getImagen();
    }

    public List<RestaurantUserDTO> searchRestaurantsByLocation(double latitude, double longitude, double radius) {
        List<RestaurantUser> restaurants = restaurantUserRepository.findAll(); // Replace with a custom query if needed
        return restaurants.stream()
                .filter(restaurant -> {
                    double distance = calculateDistance(latitude, longitude, restaurant.getLatitude(), restaurant.getLongitude());
                    return distance <= radius;
                })
                .map(restaurant -> new RestaurantUserDTO(
                        restaurant.getIdRestaurante(),
                        restaurant.getNombreRestaurante(),
                        restaurant.getEmail(),
                        restaurant.getImagen(),
                        restaurant.getLayout()
                ))
                .toList();
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // Radius of the Earth in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

}

