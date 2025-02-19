package com.online_shopping.service;

import com.online_shopping.entity.Product;
import com.online_shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

