package com.example.problem.service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.problem.model.Person;

@Service
public class PeopleService {
    private final Map<String, Person> persons = new ConcurrentHashMap<>();

    public Person register(final String email, final String firstName, final String lastName) {
        final String id = UUID.randomUUID().toString();
        final Person person = new Person(id, email, firstName, lastName);

        if (persons.values().stream().anyMatch(p -> p.getEmail().equalsIgnoreCase(email))) {
            throw new NonUniqueEmailException(email);
        }
        
        persons.put(id, person);
        return person;
    }
    
    public Optional<Person> findById(final String id) {
        return Optional.ofNullable(persons.get(id));
    }
}
