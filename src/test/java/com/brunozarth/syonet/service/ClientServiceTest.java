package com.brunozarth.syonet.service;

import com.brunozarth.syonet.model.Client;
import com.brunozarth.syonet.DTO.ClientDTO;
import com.brunozarth.syonet.repository.ClientRepository;
import com.brunozarth.syonet.validation.UniqueEmailValidator;
import jakarta.validation.*;
import org.hibernate.validator.HibernateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Validator validator;

    @Mock
    private UniqueEmailValidator uniqueEmailValidator;

    @BeforeEach
    void setUp() {
        clientService = new ClientService(clientRepository);

        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.afterPropertiesSet();
        validator = factoryBean.getValidator();
    }

    @Test
    void shouldSaveValidClient() {
        ClientDTO dto = new ClientDTO("John Doe", "john@example.com", LocalDate.of(1990, 1, 1));
        Client client = new Client(null, dto.getName(), dto.getEmail(), dto.getBirthdate());

        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client savedClient = clientService.createClient(dto);

        assertNotNull(savedClient);
        assertEquals("John Doe", savedClient.getName());
        assertEquals("john@example.com", savedClient.getEmail());
    }

    @Test
    void shouldNotAllowDuplicateEmail() {
        ClientDTO dto = new ClientDTO("John Doe", "john@example.com", LocalDate.of(1990, 1, 1));
        // Use lenient() to suppress unnecessary stubbing exceptions
        lenient().when(clientRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new Client()));

        // Certifique-se de que o validador esteja corretamente configurado
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setProviderClass(HibernateValidator.class);
        factoryBean.afterPropertiesSet();
        validator = factoryBean.getValidator();

        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Expected validation errors but found none");

        ConstraintViolation<ClientDTO> violation = violations.iterator().next();
        assertEquals("Duplicated Email", violation.getMessage());
    }

}
