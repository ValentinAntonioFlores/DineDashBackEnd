package com.example.demo.services;

import com.example.demo.model.category.Category;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestaurantUserRepository restaurantUserRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
    }


    public Category createCategory(String name, UUID restaurantId) {
        Optional<Category> existingCategory = categoryRepository.findByName(name);
        if (existingCategory.isPresent()) {
            throw new RuntimeException("Category already exists with name: " + name);
        }

        RestaurantUser restaurantUser = restaurantUserRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with ID: " + restaurantId));

        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurantUser); // set the restaurant
        return categoryRepository.save(category);
    }


    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}