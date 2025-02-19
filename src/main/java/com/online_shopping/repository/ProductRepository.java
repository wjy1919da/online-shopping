package com.online_shopping.repository;

import com.online_shopping.entity.Order;
import com.online_shopping.entity.OrderItem;
import com.online_shopping.entity.Product;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

// ProductDao.java (使用Criteria API)
@Repository
public class ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Product> findAllInStock() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        query.select(root)
                .where(cb.gt(root.get("quantity"), 0));
        try {
            return entityManager.createQuery(query).getResultList();
        }catch(NoResultException e) {
            return null; // or handle it in a way that fits your application's needs
        }
    }

    public Product getProductById(Long id) {
        // Use JPQL to find a product by ID
        return entityManager.find(Product.class, id);
    }

    public Optional<Product> findById(Long id) {
        // Use JPQL to find a product by ID
        Product product = entityManager.find(Product.class, id);
        return Optional.ofNullable(product);
    }

    @Transactional
    public Product save(Product product) {
        entityManager.persist(product);
        return product;
    }

    // Method to get the top 3 most frequently purchased products
    public List<Product> getMostFrequentlyPurchasedProducts(Long userId) {
        // Create CriteriaBuilder and CriteriaQuery
        // HQL query to get the most frequently purchased products for the given user
        String hql = "SELECT p FROM OrderItem oi " +
                "JOIN oi.product p " +
                "JOIN oi.order o " +
                "WHERE o.orderStatus != 'CANCELLED' " +
                "AND o.user.id = :userId " +
                "GROUP BY p.id " +
                "ORDER BY COUNT(oi) DESC, p.id ASC"; // Order by frequency and then product ID

        // Execute the query and set the parameter
        return entityManager.createQuery(hql, Product.class)
                .setParameter("userId", userId)  // Set the userId parameter
                .setMaxResults(3)  // Limit the results to top 3
                .getResultList();
    }

    // Method to get the top 3 most recently purchased products
    public List<Product> getMostRecentlyPurchasedProducts(Long userId) {
        // HQL query to get the most recently purchased products for the given user
        String hql = "SELECT p FROM OrderItem oi " +
                "JOIN oi.product p " +
                "JOIN oi.order o " +
                "WHERE o.orderStatus != 'CANCELLED' " +  // Exclude canceled orders
                "AND o.user.id = :userId " +  // Filter by userId
                "ORDER BY o.datePlaced DESC, p.id ASC";  // Order by the most recent purchase date, and by product ID in case of tie

        // Execute the query and set the parameter
        return entityManager.createQuery(hql, Product.class)
                .setParameter("userId", userId)  // Bind the userId parameter
                .setMaxResults(3)  // Limit the results to top 3
                .getResultList();
    }
}
