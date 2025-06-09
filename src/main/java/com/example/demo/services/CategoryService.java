package com.example.demo.services;

import com.example.demo.model.category.Category;
import com.example.demo.model.category.DTO.CategoryDTO;
import com.example.demo.model.category.DTO.CreateCategoryDTO;
import com.example.demo.model.restaurantUser.DTO.RestaurantUserDTO;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestaurantUserRepository restaurantUserRepository;

    public CategoryDTO toDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getRestaurantUser().getIdRestaurante());
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
    }

    public CategoryDTO createCategory(CreateCategoryDTO dto) {
        Optional<Category> existingCategory = categoryRepository.findByNameAndRestaurantUser_IdRestaurante(dto.getName(), dto.getRestaurantId());
        if (existingCategory.isPresent()) {
            throw new RuntimeException("Category already exists for this restaurant");
        }

        RestaurantUser restaurantUser = restaurantUserRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Category category = new Category();
        category.setName(dto.getName());
        category.setRestaurantUser(restaurantUser);

        Category savedCategory = categoryRepository.save(category);

        return toDTO(savedCategory);
    }



    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }

}
