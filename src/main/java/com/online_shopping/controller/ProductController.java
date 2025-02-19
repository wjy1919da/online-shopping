package com.online_shopping.controller;


import com.online_shopping.entity.Product;
import com.online_shopping.repository.ProductRepository;
import com.online_shopping.service.ProductService;
import com.online_shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getProductAll(){

        // Get all products in stock
        List<Product> productsInStock = productRepository.findAllInStock();

        // If the list is not empty, return the list of products with HTTP 200
        if (!productsInStock.isEmpty()) {
            return ResponseEntity.ok(productsInStock);
        } else {
            // If no products are in stock, return HTTP 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    // Get product by id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Product product = productRepository.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/frequent/{topN}")
    public ResponseEntity<List<Product>> getMostFrequentlyPurchasedProducts(
            @PathVariable int topN) {
        Long userId = userService.findByUsername("admin").getId() ;
        List<Product> products = productService.getMostFrequentlyPurchasedProducts(userId);
        return ResponseEntity.ok(products);
    }

    // Get top 3 most recently purchased products
    @GetMapping("/recent/{topN}")
    public ResponseEntity<List<Product>> getMostRecentlyPurchasedProducts(
            @PathVariable int topN) {

        Long userId = userService.findByUsername("admin").getId() ;
        List<Product> products = productService.getMostRecentlyPurchasedProducts(userId);
        return ResponseEntity.ok(products);
    }

}
