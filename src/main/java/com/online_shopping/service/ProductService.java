package com.online_shopping.service;

import com.online_shopping.dto.ProductRequest;
import com.online_shopping.entity.Product;
import com.online_shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getMostFrequentlyPurchasedProducts(Long userId) {
        return productRepository.getMostFrequentlyPurchasedProducts(userId);
    }

    public List<Product> getMostRecentlyPurchasedProducts(Long userId) {
        return productRepository.getMostRecentlyPurchasedProducts(userId);
    }

    // Method to update product
    @Transactional
    public Product updateProduct(Long id, ProductRequest productRequest) {
        // Fetch product by ID
        Optional<Product> existingProductOpt = productRepository.findById(id);
        if (!existingProductOpt.isPresent()) {
            throw new RuntimeException("Product not found");
        }

        Product existingProduct = existingProductOpt.get();

        // Update product details
        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setWholesalePrice(productRequest.getWholesalePrice());
        existingProduct.setRetailPrice(productRequest.getRetailPrice());
        existingProduct.setQuantity(productRequest.getQuantity());

        // Save and return updated product
        return productRepository.updateProduct(existingProduct);
    }

    // Method to add a product
    public Product addProduct(ProductRequest productRequest) {
        // Check if a product with the same name already exists (optional validation)
        Optional<Product> existingProduct = productRepository.findByName(productRequest.getName());
        if (existingProduct.isPresent()) {
            throw new RuntimeException("Product with the same name already exists");
        }

        // Create a new product entity from the request DTO
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setWholesalePrice(productRequest.getWholesalePrice());
        product.setRetailPrice(productRequest.getRetailPrice());
        product.setQuantity(productRequest.getQuantity());

        // Save the new product to the database
        return productRepository.save(product);
    }

    public List<Product> getTopMostPopularProducts(Long topN) {
        return productRepository.getTopMostPopularProducts(topN);
    }

    // Get the top N products based on the total quantity sold
    public List<Product> getTotalQuantitySold(Long topN) {
        return productRepository.getTotalQuantitySold(topN);
    }

}

