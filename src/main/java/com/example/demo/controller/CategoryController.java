package com.example.demo.controller;

import com.example.demo.model.category.Category;
import com.example.demo.model.category.DTO.CategoryDTO;
import com.example.demo.model.category.DTO.CreateCategoryDTO;
import com.example.demo.model.restaurantUser.DTO.RestaurantUserDTO;
import com.example.demo.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Return List<CategoryDTO> instead of List<Category>
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // Ideally also return a DTO here, so add a method to service to get DTO by id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable UUID id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryService.toDTO(category));
    }

    // For create, you may want to return DTO or entity; DTO preferred for consistency
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CreateCategoryDTO request) {
        CategoryDTO categoryDTO = categoryService.createCategory(request);
        return ResponseEntity.ok(categoryDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully.");
    }
}
