package com.brunozarth.syonet.service;

import com.brunozarth.syonet.model.Client;
import com.brunozarth.syonet.DTO.ClientDTO;
import com.brunozarth.syonet.repository.ClientRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        clientService = new ClientService(clientRepository);
    }

    @Test
    void shouldSaveValidClient() {
        ClientDTO dto = new ClientDTO("John Doe", "john@example.com", "1990-05-15");
        Client client = new Client(null, dto.getName(), dto.getEmail(), dto.getBirthdate());

        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client savedClient = clientService.createClient(dto);

        assertNotNull(savedClient);
        assertEquals("John Doe", savedClient.getName());
        assertEquals("john@example.com", savedClient.getEmail());
    }

    @Test
    void shouldNotAllowDuplicateEmail() {
        ClientDTO dto = new ClientDTO("John Doe", "john@example.com", "1990-05-15");
        when(clientRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new Client()));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> clientService.createClient(dto));

        assertEquals("Email already in use", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidClient() {
        ClientDTO dto = new ClientDTO("", "invalid-email", "1990-05-15");

        assertThrows(ConstraintViolationException.class, () -> clientService.createClient(dto));
    }
}

