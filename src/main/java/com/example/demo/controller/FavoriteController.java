package com.example.demo.controller;

import com.example.demo.model.Favorite.DTO.FavoriteDTO;
import com.example.demo.model.clientUser.ClientUser;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.model.Favorite.Favorite;

import com.example.demo.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/mark")
    public ResponseEntity<String> markAsFavorite(@RequestParam UUID clientId, @RequestParam UUID restaurantId) {
        ClientUser clientUser = new ClientUser();
        clientUser.setIdUsuario(clientId);

        RestaurantUser restaurantUser = new RestaurantUser();
        restaurantUser.setIdRestaurante(restaurantId);

        favoriteService.markAsFavorite(clientUser, restaurantUser);
        return ResponseEntity.ok("Restaurant marked as favorite.");
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<List<FavoriteDTO>> getFavorites(@PathVariable UUID clientId) {
        List<Favorite> favorites = favoriteService.getFavoritesForClient(clientId); // ✅ now matches service method

        List<FavoriteDTO> dtoList = favorites.stream()
                .map(fav -> new FavoriteDTO(fav.getRestaurantUser().getIdRestaurante()))
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFavorite(@RequestParam UUID clientId, @RequestParam UUID restaurantId) {
        ClientUser clientUser = new ClientUser();
        clientUser.setIdUsuario(clientId);

        RestaurantUser restaurantUser = new RestaurantUser();
        restaurantUser.setIdRestaurante(restaurantId);

        favoriteService.removeFavorite(clientUser, restaurantUser);
        return ResponseEntity.ok("Favorite removed.");
    }



}
