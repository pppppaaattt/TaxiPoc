package org.wildfly.examples.service;

import java.time.LocalDate;
import java.util.List;

import org.wildfly.examples.models.TaxiRide;
import org.wildfly.examples.repository.TaxiRideRepository;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class TaxiRideService {
    @Inject
    private TaxiRideRepository taxiRideRepository;

    public List<TaxiRide> findAll() {
        return taxiRideRepository.findAll();
    }

    public TaxiRide save(TaxiRide taxiRide) {
        return taxiRideRepository.save(taxiRide);
    }

    public List<TaxiRide> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return taxiRideRepository.findByDateRange(startDate, endDate);
    }

}