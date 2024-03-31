package org.wildfly.examples.resource;

import java.util.List;

import org.wildfly.examples.models.Passenger;
import org.wildfly.examples.service.PassengerService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/passengers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PassengerResource {
    @Inject
    private PassengerService passengerService;

    @GET
    public List<Passenger> listPassengers() {
        return passengerService.findAll();
    }

    @POST
    public Passenger addPassenger(Passenger passenger) {
        return passengerService.save(passenger);
    }

    @GET
    @Path("/ageunder/{age}")
    public List<Passenger> findByAgeUnder(@PathParam("age") int age) {
        return passengerService.findByAgeUnder(age);
    }
}