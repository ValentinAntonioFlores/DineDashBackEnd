package com.example.demo.services;

import com.example.demo.model.menu.Menu;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public Menu addMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public Menu updateMenu(UUID menuId, Menu updatedMenu) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));
        menu.setName(updatedMenu.getName());
        menu.setProducts(updatedMenu.getProducts());
        return menuRepository.save(menu);
    }

    public void deleteMenu(UUID menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new IllegalArgumentException("Menu not found");
        }
        menuRepository.deleteById(menuId);
    }
    public List<Menu> getMenusWithPricesByRestaurant(UUID restaurantId) {
        RestaurantUser restaurantUser = new RestaurantUser();
        restaurantUser.setIdRestaurante(restaurantId);
        return menuRepository.findByRestaurantUser(restaurantUser);
    }

    public List<Menu> getMenusByRestaurant(RestaurantUser restaurantUser) {
        return menuRepository.findByRestaurantUser(restaurantUser);
    }
}