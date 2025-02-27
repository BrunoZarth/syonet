package com.brunozarth.syonet.controller;

import com.brunozarth.syonet.DTO.ClientDTO;
import com.brunozarth.syonet.model.Client;
import com.brunozarth.syonet.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        try {
            Client client = clientService.createClient(clientDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(client);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
