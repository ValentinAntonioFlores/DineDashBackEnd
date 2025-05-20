package com.example.demo.services;

import com.example.demo.model.category.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
    }

    public Category createCategory(String name) {
        Optional<Category> existingCategory = categoryRepository.findByName(name);
        if (existingCategory.isPresent()) {
            throw new RuntimeException("Category already exists with name: " + name);
        }
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}