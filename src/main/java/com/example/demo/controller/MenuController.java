package com.example.demo.controller;

import com.example.demo.model.menu.Menu;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping
    public ResponseEntity<Menu> addMenu(@RequestBody Menu menu) {
        Menu createdMenu = menuService.addMenu(menu);
        return ResponseEntity.ok(createdMenu);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(@PathVariable UUID id, @RequestBody Menu updatedMenu) {
        Menu menu = menuService.updateMenu(id, updatedMenu);
        return ResponseEntity.ok(menu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable UUID id) {
        menuService.deleteMenu(id);
        return ResponseEntity.ok("Menu deleted successfully");
    }

    @GetMapping("/restaurant/{restaurantId}/menus")
    public ResponseEntity<List<Menu>> getMenusWithPrices(@PathVariable UUID restaurantId) {
        List<Menu> menus = menuService.getMenusWithPricesByRestaurant(restaurantId);
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Menu>> getMenusByRestaurant(@PathVariable UUID restaurantId) {
        RestaurantUser restaurantUser = new RestaurantUser();
        restaurantUser.setIdRestaurante(restaurantId);
        List<Menu> menus = menuService.getMenusByRestaurant(restaurantUser);
        return ResponseEntity.ok(menus);
    }
}