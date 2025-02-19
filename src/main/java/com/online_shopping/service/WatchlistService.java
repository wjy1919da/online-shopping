package com.online_shopping.service;

import com.online_shopping.entity.Product;
import com.online_shopping.entity.User;
import com.online_shopping.entity.Watchlist;
import com.online_shopping.repository.ProductRepository;
import com.online_shopping.repository.UserRepository;
import com.online_shopping.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private UserRepository userRepository;  // Assuming a UserRepository
    @Autowired
    private ProductRepository productRepository;  // Assuming a ProductRepository

    public Watchlist addToWatchlist(User user, Long productId) {
        //User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        return watchlistRepository.addToWatchlist(user, product);
    }

    public List<Watchlist> getAllWatchlist(User user) {
        //User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return watchlistRepository.getAllWatchlists(user);
    }

    public void removeFromWatchlist(User user, Long productId) {
        //User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        watchlistRepository.removeFromWatchlist(user, product);
    }
}

