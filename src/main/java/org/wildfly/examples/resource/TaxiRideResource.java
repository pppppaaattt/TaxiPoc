package org.wildfly.examples.resource;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.wildfly.examples.models.Driver;
import org.wildfly.examples.models.Passenger;
import org.wildfly.examples.models.TaxiRide;
import org.wildfly.examples.service.TaxiRideService;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/taxirides")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaxiRideResource {
    @Inject
    private TaxiRideService taxiRideService;

    @PersistenceContext
    private EntityManager em;
    @GET
    public List<TaxiRide> listTaxiRides(@QueryParam("startDate") LocalDate startDate,
                                        @QueryParam("endDate") LocalDate endDate,
                                        @QueryParam("minCost") BigDecimal minCost,
                                        @QueryParam("maxCost") BigDecimal maxCost,
                                        @QueryParam("minDuration") Duration minDuration,
                                        @QueryParam("maxDuration") Duration maxDuration,
                                        @QueryParam("driver") Driver driver,
                                        @QueryParam("passenger") Passenger passenger,
                                        @QueryParam("ageUnder") int ageUnder) {
    	 CriteriaBuilder cb = em.getCriteriaBuilder();
    	    CriteriaQuery<TaxiRide> query = cb.createQuery(TaxiRide.class);
    	    Root<TaxiRide> root = query.from(TaxiRide.class);

    	    List<Predicate> predicates = new ArrayList<>();

    	    if (startDate != null && endDate != null) {
    	        predicates.add(cb.between(root.get("startTime"), startDate.atStartOfDay(), endDate.atTime(23, 59, 59)));
    	    }

    	    if (minCost != null && maxCost != null) {
    	        predicates.add(cb.between(root.get("cost"), minCost, maxCost));
    	    }

//    	    if (minDuration != null && maxDuration != null) {
//    	        predicates.add(cb.between(cb.diff(root.get("endTime"), root.get("startTime")), minDuration, maxDuration));
//    	    }

    	    if (driver != null) {
    	        predicates.add(cb.equal(root.get("driver"), driver));
    	    }

    	    if (passenger != null) {
    	        predicates.add(cb.isMember(passenger, root.get("passengers")));
    	    }

    	    if (ageUnder > 0) {
    	        Subquery<Passenger> subquery = query.subquery(Passenger.class);
    	        Root<Passenger> passengerRoot = subquery.from(Passenger.class);
    	        subquery.select(passengerRoot)
    	                .where(cb.lt(passengerRoot.get("age"), ageUnder))
    	                .distinct(true);
    	        predicates.add(cb.exists(subquery));
    	    }

    	    query.select(root).where(predicates.toArray(new Predicate[0]));

    	    return em.createQuery(query).getResultList();
    	
    }

    @POST
    public TaxiRide addTaxiRide(TaxiRide taxiRide) {
        return taxiRideService.save(taxiRide);
    }

   
}