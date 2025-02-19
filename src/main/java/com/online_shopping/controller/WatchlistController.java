package com.online_shopping.controller;

import com.online_shopping.entity.User;
import com.online_shopping.entity.Watchlist;
import com.online_shopping.service.UserService;
import com.online_shopping.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private UserService  userService ;

    @PostMapping("/product/{productId}")
    public ResponseEntity<Watchlist> addToWatchlist(@RequestParam Long userId, @PathVariable Long productId) {
//        User user = userService.getCurrentUser() ;
        User user = userService.findByUsername("admin") ;
        Watchlist watchlist = watchlistService.addToWatchlist(user, productId);
        if (watchlist == null) {
            return ResponseEntity.badRequest().body(null);  // Already in watchlist
        }
        return ResponseEntity.ok(watchlist);
    }

    @GetMapping("/products/all")
    public ResponseEntity<List<Watchlist>> getAllWatchlist(@RequestParam Long userId) {
//        User user = userService.getCurrentUser() ;
        User user = userService.findByUsername("admin") ;
        List<Watchlist> watchlist = watchlistService.getAllWatchlist(user);
        return ResponseEntity.ok(watchlist);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> removeFromWatchlist(@RequestParam Long userId, @PathVariable Long productId) {
//        User user = userService.getCurrentUser() ;
        User user = userService.findByUsername("admin") ;
        watchlistService.removeFromWatchlist(user, productId);
        return ResponseEntity.noContent().build();
    }
}

