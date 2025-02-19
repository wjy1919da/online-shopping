package com.online_shopping.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

import com.online_shopping.entity.Product;
import com.online_shopping.entity.User;
import com.online_shopping.entity.Watchlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class WatchlistRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Watchlist addToWatchlist(User user, Product product) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Watchlist> cq = cb.createQuery(Watchlist.class);
        Root<Watchlist> root = cq.from(Watchlist.class);

        Predicate userPredicate = cb.equal(root.get("user"), user);
        Predicate productPredicate = cb.equal(root.get("product"), product);
        cq.select(root).where(cb.and(userPredicate, productPredicate));

        List<Watchlist> results = entityManager.createQuery(cq).getResultList();
        if (!results.isEmpty()) {
            return null;  // Product already in the watchlist
        }

        // If not found, create new Watchlist entry
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        watchlist.setProduct(product);
        entityManager.persist(watchlist);
        return watchlist;
    }

    @Transactional
    public List<Watchlist> getAllWatchlists(User user) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Watchlist> cq = cb.createQuery(Watchlist.class);
        Root<Watchlist> root = cq.from(Watchlist.class);

        cq.select(root).where(cb.equal(root.get("user"), user));
        return entityManager.createQuery(cq).getResultList();
    }

    @Transactional
    public void removeFromWatchlist(User user, Product product) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Watchlist> cq = cb.createQuery(Watchlist.class);
        Root<Watchlist> root = cq.from(Watchlist.class);

        Predicate userPredicate = cb.equal(root.get("user"), user);
        Predicate productPredicate = cb.equal(root.get("product"), product);
        cq.select(root).where(cb.and(userPredicate, productPredicate));

        Watchlist watchlist = entityManager.createQuery(cq).getSingleResult();
        if (watchlist != null) {
            entityManager.remove(watchlist);
        }
    }
}
