package com.brunozarth.syonet.dto;

import com.brunozarth.syonet.DTO.NewsDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class NewsDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationWithValidData() {
        NewsDTO newsDTO = new NewsDTO("Valid Title", "Valid Description", "http://valid-link.com");

        Set<ConstraintViolation<NewsDTO>> violations = validator.validate(newsDTO);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidationWhenTitleIsBlank() {
        NewsDTO newsDTO = new NewsDTO("", "Valid Description", "http://valid-link.com");

        Set<ConstraintViolation<NewsDTO>> violations = validator.validate(newsDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Title cannot be empty");
    }

    @Test
    void shouldFailValidationWhenDescriptionIsBlank() {
        NewsDTO newsDTO = new NewsDTO("Valid Title", "", "http://valid-link.com");

        Set<ConstraintViolation<NewsDTO>> violations = validator.validate(newsDTO);

        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Description is required");
    }

    @Test
    void shouldAllowNullLink() {
        NewsDTO newsDTO = new NewsDTO("Valid Title", "Valid Description", null);

        Set<ConstraintViolation<NewsDTO>> violations = validator.validate(newsDTO);

        assertThat(violations).isEmpty();
    }
}
