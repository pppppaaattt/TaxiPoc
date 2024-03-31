package org.wildfly.examples.repository;

import java.time.LocalDate;
import java.util.List;

import org.wildfly.examples.models.TaxiRide;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Stateless
public class TaxiRideRepository {
    @PersistenceContext
    private EntityManager em;

    public List<TaxiRide> findAll() {
        CriteriaQuery<TaxiRide> query = em.getCriteriaBuilder().createQuery(TaxiRide.class);
        query.select(query.from(TaxiRide.class));
        return em.createQuery(query).getResultList();
    }

    public TaxiRide save(TaxiRide taxiRide) {
        em.persist(taxiRide);
        return taxiRide;
    }

    public List<TaxiRide> findByDateRange(LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TaxiRide> query = cb.createQuery(TaxiRide.class);
        Root<TaxiRide> root = query.from(TaxiRide.class);
        query.select(root)
             .where(cb.between(root.get("startTime"), startDate.atStartOfDay(), endDate.atTime(23, 59, 59)));
        return em.createQuery(query).getResultList();
    }

  
}