package com.example.problem.resource;

import java.net.URI;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import com.example.problem.model.CreatePerson;
import com.example.problem.model.Person;
import com.example.problem.service.NonUniqueEmailException;
import com.example.problem.service.PeopleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Component
@Path( "/people" ) 
@Tag(name = "people")
public class PeopleResource {
    @Autowired private PeopleService service;
    @Context private UriInfo uriInfo;
    
    @Produces({ MediaType.APPLICATION_JSON, "application/problem+json" })
    @GET
    @Path("{id}")
    @Operation(
        description = "Register a new person", 
        responses = {
            @ApiResponse(
                content = @Content(schema = @Schema(implementation = Person.class), mediaType = MediaType.APPLICATION_JSON),
                responseCode = "200"
            ),
            @ApiResponse(
                content = @Content(schema = @Schema(implementation = Problem.class), mediaType = "application/problem+json"),
                responseCode = "404"
            )
        }
    )
    public Person findById(@PathParam("id") String id) {
        return service
            .findById(id)
            .orElseThrow(() -> new PersonNotFoundProblem(id, uriInfo.getRequestUri()));
    }
    
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON, "application/problem+json" })
    @POST
    @Operation(
        description = "Register a new person", 
        responses = {
            @ApiResponse(
                content = @Content(schema = @Schema(implementation = Person.class), mediaType = MediaType.APPLICATION_JSON),
                responseCode = "201"
            ),
            @ApiResponse(
                content = @Content(schema = @Schema(implementation = Problem.class), mediaType = "application/problem+json"),
                responseCode = "400"
            ),
            @ApiResponse(
                content = @Content(schema = @Schema(implementation = Problem.class), mediaType = "application/problem+json"),
                responseCode = "500"
            )
        }
    )
    public Response register(@Valid final CreatePerson payload) {
        try {
            final Person person = service.register(payload.getEmail(), 
                payload.getFirstName(), payload.getLastName());
            
            return Response
                .created(uriInfo.getRequestUriBuilder().path(person.getId()).build())
                .entity(person)
                .build();
        } catch (final NonUniqueEmailException ex) {
            return Response
                .status(Response.Status.BAD_REQUEST)
                .type("application/problem+json")
                .entity(Problem
                    .builder()
                    .withType(URI.create("http://localhost:21020/problems/non-unique-email"))
                    .withInstance(uriInfo.getRequestUri())
                    .withStatus(Status.BAD_REQUEST)
                    .withTitle("The email address is not unique")
                    .withDetail(ex.getMessage())
                    .with("email", payload.getEmail())
                    .build())
                .build();
        }
    }
}
