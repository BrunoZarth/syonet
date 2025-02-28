package com.brunozarth.syonet.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class ClientValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidClient() {
        Client Client = new Client(null, "John Doe", "john@example.com", LocalDate.of(1990,1,1));

        Set<ConstraintViolation<Client>> violations = validator.validate(Client);
        assertTrue(violations.isEmpty(), "Valid Client should not have violations");
    }

    @Test
    void testNameIsRequired() {
        Client Client = new Client(null, "", "john@example.com", LocalDate.of(1990,1,1));

        Set<ConstraintViolation<Client>> violations = validator.validate(Client);
        assertFalse(violations.isEmpty(), "Name is required");
    }

    @Test
    void testEmailIsRequiredAndValidFormat() {
        Client Client1 = new Client(null, "John Doe", "", LocalDate.of(1990,1,1));
        Client Client2 = new Client(null, "John Doe", "invalid-email", LocalDate.of(1990,1,1));

        assertFalse(validator.validate(Client1).isEmpty(), "Email is required");
        assertFalse(validator.validate(Client2).isEmpty(), "Email format should be valid");
    }

    @Test
    void testBirthDateIsOptional() {
        Client Client = new Client(null, "John Doe", "john@example.com", null);

        Set<ConstraintViolation<Client>> violations = validator.validate(Client);
        assertTrue(violations.isEmpty(), "BirthDate should be optional");
    }
}

