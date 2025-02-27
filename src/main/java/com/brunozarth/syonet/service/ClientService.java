package com.brunozarth.syonet.service;


import com.brunozarth.syonet.DTO.ClientDTO;
import com.brunozarth.syonet.model.Client;
import com.brunozarth.syonet.repository.ClientRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class ClientService {

    private final ClientRepository ClientRepository;
    private final Validator validator;

    public ClientService(ClientRepository ClientRepository) {
        this.ClientRepository = ClientRepository;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public Client createClient(ClientDTO dto) {
        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new jakarta.validation.ConstraintViolationException(violations);
        }

        if (ClientRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        Client Client = new Client(null, dto.getName(), dto.getEmail(), dto.getBirthdate());
        return ClientRepository.save(Client);
    }
}

