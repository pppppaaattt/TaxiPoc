package org.wildfly.examples.service;

import java.util.List;

import org.wildfly.examples.models.Driver;
import org.wildfly.examples.repository.DriverRepository;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class DriverService {
    @Inject
    private DriverRepository driverRepository;

    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }
}