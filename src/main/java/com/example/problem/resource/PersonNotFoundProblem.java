package com.example.problem.resource;

import java.net.URI;
import java.util.Map;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class PersonNotFoundProblem extends AbstractThrowableProblem {
    private static final long serialVersionUID = 7662154827584418806L;
    private static final URI TYPE = URI.create("http://localhost:21020/problems/person-not-found");
    
    public PersonNotFoundProblem(final String id, final URI instance) {
        super(TYPE, "Person is not found", Status.NOT_FOUND, 
            "Person with identifier '" + id + "' is not found", instance, 
                null, Map.of("id", id));
    }
}
