package org.wildfly.examples.repository;

import java.util.List;

import org.wildfly.examples.models.Passenger;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Stateless
public class PassengerRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Passenger> findAll() {
        CriteriaQuery<Passenger> query = em.getCriteriaBuilder().createQuery(Passenger.class);
        query.select(query.from(Passenger.class));
        return em.createQuery(query).getResultList();
    }

    public Passenger save(Passenger passenger) {
        em.persist(passenger);
        return passenger;
    }

    public List<Passenger> findByAgeUnder(int age) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Passenger> query = cb.createQuery(Passenger.class);
        Root<Passenger> root = query.from(Passenger.class);
        query.select(root).where(cb.lt(root.get("age"), age));
        return em.createQuery(query).getResultList();
    }
}