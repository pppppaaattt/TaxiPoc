package org.wildfly.examples.resource;

import java.util.List;

import org.wildfly.examples.models.Driver;
import org.wildfly.examples.service.DriverService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/drivers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DriverResource {
    @Inject
    private DriverService driverService;

    @GET
    public List<Driver> listDrivers() {
        return driverService.findAll();
    }

    @POST
    public Driver addDriver(Driver driver) {
        return driverService.save(driver);
    }
}