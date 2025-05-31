package com.example.demo.services;

import com.example.demo.model.product.Product;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(UUID productId, Product updatedProduct) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setImage(updatedProduct.getImage());
        return productRepository.save(product);
    }

    public void deleteProduct(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("Product not found");
        }
        productRepository.deleteById(productId);
    }

    public List<Product> getProductsByRestaurant(RestaurantUser restaurantUser) {
        return productRepository.findByRestaurantUser(restaurantUser);
    }
}