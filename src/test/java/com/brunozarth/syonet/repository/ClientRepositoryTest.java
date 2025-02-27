package com.brunozarth.syonet.repository;

import com.brunozarth.syonet.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    private Client client1, client2;

    @BeforeEach
    void setUp() {
        client1 = new Client();
        client1.setName("John Doe");
        client1.setEmail("john.doe@example.com");
        client1.setBirthdate(null);

        client2 = new Client();
        client2.setName("Jane Doe");
        client2.setEmail("jane.doe@example.com");
        client2.setBirthdate(null);
    }

    @Test
    void shouldSaveClient() {
        Client savedClient = clientRepository.save(client1);

        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getId()).isNotNull();
        assertThat(savedClient.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void shouldFindClientById() {
        Client savedClient = clientRepository.save(client1);

        Optional<Client> foundClient = clientRepository.findById(savedClient.getId());

        assertThat(foundClient).isPresent();
        assertThat(foundClient.get().getName()).isEqualTo("John Doe");
    }

    @Test
    void shouldFindClientByEmail() {
        clientRepository.save(client1);

        Optional<Client> foundClient = clientRepository.findByEmail("john.doe@example.com");

        assertThat(foundClient).isPresent();
        assertThat(foundClient.get().getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void shouldFindAllClients() {
        clientRepository.save(client1);
        clientRepository.save(client2);

        List<Client> clients = clientRepository.findAll();

        assertThat(clients).hasSize(2);
    }

    @Test
    void shouldUpdateClient() {
        Client savedClient = clientRepository.save(client1);
        savedClient.setName("John Doe Updated");

        Client updatedClient = clientRepository.save(savedClient);

        assertThat(updatedClient.getName()).isEqualTo("John Doe Updated");
    }

    @Test
    void shouldDeleteClient() {
        Client savedClient = clientRepository.save(client1);

        clientRepository.deleteById(savedClient.getId());

        Optional<Client> deletedClient = clientRepository.findById(savedClient.getId());
        assertThat(deletedClient).isEmpty();
    }
}
