package com.example.demo.services;

import com.example.demo.model.product.DTO.GetProductDTO;
import com.example.demo.model.product.DTO.UpdateProductDTO;
import com.example.demo.model.product.Product;
import com.example.demo.model.restaurantUser.RestaurantUser;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public GetProductDTO updateProduct(UUID productId, UpdateProductDTO updateDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.setName(updateDto.getName());
        product.setDescription(updateDto.getDescription());
        product.setPrice(updateDto.getPrice());
        product.setImage(updateDto.getImage());
        product.setCategory(updateDto.getCategory());

        Product savedProduct = productRepository.save(product);

        // Return as DTO
        return new GetProductDTO(
                savedProduct.getRestaurantUser().getIdRestaurante(), // assuming product has restaurant relationship
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getImage(),
                savedProduct.getCategory()
        );
    }

    public void deleteProduct(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("Product not found");
        }
        productRepository.deleteById(productId);
    }

    public List<GetProductDTO> getProductsByRestaurantId(UUID restaurantId) {
        List<Product> products = productRepository.findByRestaurantUser_IdRestaurante(restaurantId);
        return products.stream()
                .map(product -> new GetProductDTO(
                        product.getRestaurantUser().getIdRestaurante(),
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getImage(),
                        product.getCategory()
                ))
                .collect(Collectors.toList());
    }

}