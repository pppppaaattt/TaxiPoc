package org.wildfly.examples.repository;

import java.util.List;

import org.wildfly.examples.models.Driver;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;

@Stateless
public class DriverRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Driver> findAll() {
        CriteriaQuery<Driver> query = em.getCriteriaBuilder().createQuery(Driver.class);
        query.select(query.from(Driver.class));
        return em.createQuery(query).getResultList();
    }

    public Driver save(Driver driver) {
        em.persist(driver);
        return driver;
    }
}