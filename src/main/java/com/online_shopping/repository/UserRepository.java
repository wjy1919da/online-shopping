package com.online_shopping.repository;


import javax.persistence.EntityManager;

import com.online_shopping.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Save User
    @Transactional
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    // Find User by Username using Criteria API
    public User findByUsername(String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(builder.equal(root.get("username"), username));

        try {
            return entityManager.createQuery(query).getSingleResult();
        }catch(NoResultException e) {
            return null; // or handle it in a way that fits your application's needs
        }

    }

    // Find User by Email using Criteria API
    public User findByEmail(String email) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(builder.equal(root.get("email"), email));

        try {
            return entityManager.createQuery(query).getSingleResult();
        }catch(NoResultException e) {
            return null; // or handle it in a way that fits your application's needs
        }


    }
}

