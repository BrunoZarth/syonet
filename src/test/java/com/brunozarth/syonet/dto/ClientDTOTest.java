package com.brunozarth.syonet.dto;

import com.brunozarth.syonet.DTO.ClientDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class ClientDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidClientDTO() {
        ClientDTO dto = new ClientDTO("John Doe", "john@example.com", LocalDate.of(1990,1,1));

        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Valid DTO should not have violations");
    }

    @Test
    void testNameIsRequired() {
        ClientDTO dto = new ClientDTO("", "john@example.com", LocalDate.of(1990,1,1));

        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Name should be required");
    }

    @Test
    void testEmailIsRequiredAndValidFormat() {
        ClientDTO dto1 = new ClientDTO("John Doe", "", LocalDate.of(1990,1,1));
        ClientDTO dto2 = new ClientDTO("John Doe", "invalid-email", LocalDate.of(1990,1,1));

        assertFalse(validator.validate(dto1).isEmpty(), "Email should be required");
        assertFalse(validator.validate(dto2).isEmpty(), "Email format should be valid");
    }
}

