package com.online_shopping.repository;

import com.online_shopping.entity.Order;
import com.online_shopping.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

//    public List<Order> findAllOrders() {
//        Session session = entityManager.unwrap(Session.class);
//        Criteria criteria = session.createCriteria(Order.class);
//        return criteria.list();
//    }

    public List<Order> findAllOrders(int page, int size) {
        // Create CriteriaBuilder and CriteriaQuery
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);

        // Define the root (the main entity we're querying)
        Root<Order> root = criteriaQuery.from(Order.class);

        // Build the query to select all orders
        criteriaQuery.select(root);

        // Create the query
        var query = entityManager.createQuery(criteriaQuery);

        // Set pagination (page number and size)
        query.setFirstResult((page - 1) * size); // Set the starting point
        query.setMaxResults(size); // Set the page size

        // Execute the query and return the results
        return query.getResultList();
    }


    public Order findOrderById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Order.class, id);
    }


    public List<Order> findOrdersByUser(Long userId) {
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Order.class);
        criteria.add(Restrictions.eq("user.id", userId));
        return criteria.list();
    }

    @Transactional
    public Order save(Order order) {
        entityManager.persist(order);
        return order;
    }
}
