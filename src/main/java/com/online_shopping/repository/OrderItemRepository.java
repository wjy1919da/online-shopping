package com.online_shopping.repository;

import com.online_shopping.entity.Order;
import com.online_shopping.entity.OrderItem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderItemRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public OrderItem save(OrderItem orderItem) {
        entityManager.persist(orderItem);
        return orderItem;
    }

    public List<OrderItem> findByOrderId(Long orderId){
        String jpql = "SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId";

        // Create a TypedQuery
        TypedQuery<OrderItem> query = entityManager.createQuery(jpql, OrderItem.class);

        // Set the orderId parameter
        query.setParameter("orderId", orderId);

        // Execute the query and return the result list
        return query.getResultList();
    }

}
