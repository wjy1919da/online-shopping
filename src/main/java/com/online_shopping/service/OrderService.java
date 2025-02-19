package com.online_shopping.service;

import com.online_shopping.dto.OrderDto;
import com.online_shopping.dto.OrderItemDto;
import com.online_shopping.dto.OrderResponse;
import com.online_shopping.dto.UserDto;
import com.online_shopping.entity.Order;
import com.online_shopping.entity.OrderItem;
import com.online_shopping.entity.Product;
import com.online_shopping.entity.User;
import com.online_shopping.exception.InvalidOrderStatusException;
import com.online_shopping.exception.OrderNotFoundException;
import com.online_shopping.repository.OrderItemRepository;
import com.online_shopping.repository.OrderRepository;
import com.online_shopping.repository.ProductRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository ;



    @Transactional
    public Order createOrder(OrderDto orderDto,User user) {
        Order order = new Order();
        order.setDatePlaced(Instant.now());
        order.setOrderStatus("PROCESSING");
        // Assuming a user is available for the order
        order.setUser(user); // Get the user from the JWT or other context

        // Save the order and update stock based on order items
        // Save pruduct  to orderItem 表中
        orderRepository.save(order);
        List<OrderItemDto> orderItems = orderDto.getOrder();
        for (OrderItemDto item : orderItems) {

            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Check if the requested quantity is available in stock
            if (product.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // Reduce the product's stock
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);

            // Create and save the order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPurchasedPrice(product.getRetailPrice());
            orderItem.setWholesalePrice(product.getWholesalePrice());
            orderItemRepository.save(orderItem);  // Save each order item
        }
        return order;
    }


    public List<Order> getAllOrders(Integer page ,Integer size) {
        return orderRepository.findAllOrders(page,size);
    }


    public Order getOrderById(Long id) {

        return orderRepository.findOrderById(id);
    }


    @Transactional
    public Order cancelOrder(Long id) {
        // Find the order by ID
        Order order = orderRepository.findOrderById(id);

        if (order == null) {
            throw new OrderNotFoundException("Order not found");  // You can create a custom exception for this
        }
//        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(id) ;
//        order.setItems(orderItemList);
//        System.out.println("orderItemList  size "+order.getItems().size());
        // Check if the order status is "PROCESSING" (cancellable state)

        if ("PROCESSING".equals(order.getOrderStatus())) {
            // Update the order status to "CANCELLED"
            order.setOrderStatus("CANCELLED");

            // Increment stock for each product in the order
            for (OrderItem item : order.getItems()) {

                Product product = item.getProduct();
                // Increment the product stock based on the quantity in the canceled order
                product.setQuantity(product.getQuantity() + item.getQuantity());
                productRepository.save(product);  // Save the updated product stock
            }

            // Save the updated order
            orderRepository.save(order);
        } else {
            throw new InvalidOrderStatusException("Order cannot be cancelled because it is not in PROCESSING status.");
        }

        return order;
    }

    @Transactional
    public Order completeOrder(Long id) {
        // Find the order by ID
        Order order = orderRepository.findOrderById(id);

        if (order == null) {
            throw new OrderNotFoundException("Order not found");
        }

        // Check if the order status is "PROCESSING" (completable state)
        if ("PROCESSING".equals(order.getOrderStatus())) {
            // Update the order status to "COMPLETED"
            order.setOrderStatus("COMPLETED");
            orderRepository.save(order);
        } else {
            throw new InvalidOrderStatusException("Order cannot be completed because it is not in PROCESSING status.");
        }

        return order;
    }

    public OrderResponse convertToDTO(Order order) {
        UserDto userDto = new UserDto();
        userDto.setId(order.getUser().getId());
        userDto.setUsername(order.getUser().getUsername());
        userDto.setEmail(order.getUser().getEmail());
        userDto.setRole(order.getUser().getRole());

        OrderResponse OrderRes = new OrderResponse();
        OrderRes.setId(order.getId());
        OrderRes.setDatePlaced(order.getDatePlaced().toString());
        OrderRes.setOrderStatus(order.getOrderStatus());
        OrderRes.setUser(userDto);

        return OrderRes;
    }

}

