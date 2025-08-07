package com.example.louagetunisie.service;


import com.example.louagetunisie.dto.ReservationDTO;
import com.example.louagetunisie.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.louagetunisie.dto.ClientDTO;
import com.example.louagetunisie.model.Client;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class ClientService  {
    private ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    private final SessionRepository sessionRepository;
    private final ReclamationRepository reclamationRepository;
    private final EvaluationRepository evaluationRepository;
    private final ReservationRepository reservationRepository;
    private final UtilisateurRepository utilisateurRepository;
    private WebSocketEventService socketEventService;


    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder, SessionRepository sessionRepository, ReclamationRepository reclamationRepository, EvaluationRepository evaluationRepository, ReservationRepository reservationRepository, UtilisateurRepository utilisateurRepository , WebSocketEventService socketEventService ) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.sessionRepository = sessionRepository;
        this.reclamationRepository = reclamationRepository;
        this.evaluationRepository = evaluationRepository;
        this.reservationRepository = reservationRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.socketEventService = socketEventService;
    }
    public Client addClient( Client client ){
          return clientRepository.save(client);  }

    public Optional<Client> findClient(Long id ){
        return clientRepository.findById(id);
    }

    @Transactional
    public void supprimerClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new IllegalArgumentException("Client introuvable avec id: " + id);
        }


        sessionRepository.deleteByUtilisateurId(id);
        reclamationRepository.deleteByClientId(id);
        evaluationRepository.deleteByClientId(id);
        reservationRepository.deleteByClientId(id);


        clientRepository.deleteById(id);


        utilisateurRepository.deleteById(id);
    }
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    public Page<ClientDTO> getAllClientsDTO(Pageable pageable) {
        return clientRepository.findAll(pageable)
                .map(client -> {
                    List<ReservationDTO> reservationsDTO = client.getHistoriqueReservation().stream()
                            .map(res -> new ReservationDTO(
                                    res.getTrajet().getId(),  // Assure-toi que res.getTrajet() n’est pas null
                                    res.getNombrePlaces()
                            ))
                            .collect(Collectors.toList());

                    return new ClientDTO(
                            client.getId(),
                            client.getNom(),
                            client.getPrenom(),
                            client.getEmail(),
                            String.valueOf(client.getTelephone()),
                            reservationsDTO,
                            client.getCin()
                    );
                });
    }



    public Client modifierClient(Long id ,Client client ){
        Client clientExistant =  clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable"));
        if(client.getNom() != null) clientExistant.setNom(client.getNom());
        if(client.getPrenom() != null) clientExistant.setPrenom(client.getPrenom());
        if(client.getEmail() != null) clientExistant.setEmail(client.getEmail());
        if (client.getMotDePasse() != null) {
            String motDePasseCrypt = passwordEncoder.encode(client.getMotDePasse());
            clientExistant.setMotDePasse(motDePasseCrypt);
        }
        if(client.getTelephone() != null) clientExistant.setTelephone(client.getTelephone());

        clientRepository.save(clientExistant);
        return clientExistant;
    }
}
