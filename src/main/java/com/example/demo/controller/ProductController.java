package com.example.demo.controller;

import com.example.demo.model.product.DTO.GetProductDTO;
import com.example.demo.model.product.DTO.UpdateProductDTO;
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
    public ResponseEntity<GetProductDTO> updateProductById(
            @PathVariable UUID id,
            @RequestBody UpdateProductDTO updatedProductDto) {

        GetProductDTO updatedProduct = productService.updateProduct(id, updatedProductDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<GetProductDTO>> getProductsByRestaurant(@PathVariable UUID restaurantId) {
        List<GetProductDTO> products = productService.getProductsByRestaurantId(restaurantId);
        return ResponseEntity.ok(products);
    }

}