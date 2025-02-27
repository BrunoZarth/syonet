package com.brunozarth.syonet.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NewsValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldNotAllowEmptyTitle() {
        News news = new News(null, "", "Description", "http://valid-link.com", false);
        Set<ConstraintViolation<News>> violations = validator.validate(news);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotAllowNullDescription() {
        News news = new News(null, "Valid Title", null, "http://valid-link.com", false);
        Set<ConstraintViolation<News>> violations = validator.validate(news);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldAllowValidNews() {
        News news = new News(null, "Valid Title", "Valid Description", "http://valid-link.com", false);
        Set<ConstraintViolation<News>> violations = validator.validate(news);
        assertTrue(violations.isEmpty());
    }
}

