package com.online_shopping.repository;

import com.online_shopping.entity.Order;
import com.online_shopping.entity.Product;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
}
