package com.brunozarth.syonet.service;

import com.brunozarth.syonet.DTO.ClientDTO;
import com.brunozarth.syonet.model.Client;
import com.brunozarth.syonet.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client createClient(ClientDTO clientDTO) {
        Client client = new Client(null, clientDTO.getName(), clientDTO.getEmail(), clientDTO.getBirthdate());
        return clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Client updateClient(Long id, ClientDTO clientDTO) {
        return clientRepository.findById(id)
                .map(existingClient -> {
                    existingClient.setName(clientDTO.getName());
                    existingClient.setEmail(clientDTO.getEmail());
                    existingClient.setBirthdate(clientDTO.getBirthdate());
                    return clientRepository.save(existingClient);
                })
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
