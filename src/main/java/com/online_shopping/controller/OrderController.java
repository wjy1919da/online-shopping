package com.online_shopping.controller;

import com.online_shopping.dto.OrderDto;
import com.online_shopping.dto.OrderItemDto;
import com.online_shopping.entity.Order;
import com.online_shopping.entity.User;
import com.online_shopping.repository.UserRepository;
import com.online_shopping.service.OrderService;
import com.online_shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService ;

    @Autowired
    private UserRepository userRepository ;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto) {
        System.out.println("createOrder");
        User currentUser = userService.getCurrentUser();
        //System.out.println(currentUser.toString());

        
        Order order = orderService.createOrder(orderDto,currentUser);

        return ResponseEntity.ok(order);

    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        Integer page = 1;
        Integer size = 10 ;
        List<Order> orders = orderService.getAllOrders(page,size);
        System.out.println("getAllOrders size "+orders.size());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable("id") Long id) {
        Order order = orderService.cancelOrder(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Order> completeOrder(@PathVariable("id") Long id) {
        Order order = orderService.completeOrder(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }
}

