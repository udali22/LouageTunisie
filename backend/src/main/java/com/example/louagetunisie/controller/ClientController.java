package com.example.louagetunisie.controller;

import com.example.louagetunisie.dto.ClientDTO;
import com.example.louagetunisie.model.Client;
import com.example.louagetunisie.repository.UtilisateurRepository;
import com.example.louagetunisie.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;





import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    private ClientService clientService;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    public ClientController(ClientService clientService , UtilisateurRepository utilisateurRepository ) {
        this.clientService = clientService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Client save(@RequestBody Client client){
        return clientService.addClient( client);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<Client> findOne(@PathVariable Long id){
        return clientService.findClient(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> supprimerClient(@PathVariable Long id) {
        clientService.supprimerClient(id);
        return ResponseEntity.ok("Client supprimé avec succès.");
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ClientDTO>> getAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int limit) {

        Pageable pageable = PageRequest.of(page, limit);
        Page<ClientDTO> clients = clientService.getAllClientsDTO(pageable);
        return ResponseEntity.ok(clients);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public Client update(@PathVariable Long id ,@RequestBody Client client){
        clientService.modifierClient(id,client);
        return client;
    }
    @GetMapping("/count")
    @PreAuthorize("hasRole('ADMIN')")
    public long countClients() {
        return utilisateurRepository.countByRole("CLIENT");
    }



}
