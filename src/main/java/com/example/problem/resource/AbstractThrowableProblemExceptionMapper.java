package com.example.problem.resource;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.zalando.problem.AbstractThrowableProblem;

@Provider
public class AbstractThrowableProblemExceptionMapper implements ExceptionMapper<AbstractThrowableProblem> {
    @Context private UriInfo uriInfo;
    
    @Override
    public Response toResponse(AbstractThrowableProblem ex) {
        return Response
            .status(ex.getStatus().getStatusCode())
            .entity(ex)
            .type("application/problem+json")
            .build();
    }
}
