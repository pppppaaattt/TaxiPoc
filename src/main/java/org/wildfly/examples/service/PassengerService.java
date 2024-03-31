package org.wildfly.examples.service;

import java.util.List;

import org.wildfly.examples.models.Passenger;
import org.wildfly.examples.repository.PassengerRepository;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class PassengerService {
    @Inject
    private PassengerRepository passengerRepository;

    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    public Passenger save(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    public List<Passenger> findByAgeUnder(int age) {
        return passengerRepository.findByAgeUnder(age);
    }
}