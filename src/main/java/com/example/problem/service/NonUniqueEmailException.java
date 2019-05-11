package com.example.problem.service;

public class NonUniqueEmailException extends RuntimeException {
    private static final long serialVersionUID = -7983226503777292282L;
    
    public NonUniqueEmailException(final String email) {
        super("The email '" + email + "' is not unique and is already registered");
    }
}
