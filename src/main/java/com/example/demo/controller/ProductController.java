package com.example.demo.controller;

import com.example.demo.model.product.Product;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product createdProduct = productService.addProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product updatedProduct) {
        Product product = productService.updateProduct(id, updatedProduct);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Product>> getProductsByRestaurant(@PathVariable UUID restaurantId) {
        RestaurantUser restaurantUser = new RestaurantUser();
        restaurantUser.setIdRestaurante(restaurantId);
        List<Product> products = productService.getProductsByRestaurant(restaurantUser);
        return ResponseEntity.ok(products);
    }
}