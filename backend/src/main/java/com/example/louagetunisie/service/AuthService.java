package com.example.louagetunisie.service;

import com.example.louagetunisie.dto.LoginRequest;
import com.example.louagetunisie.dto.LoginResponse;
import com.example.louagetunisie.dto.RegisterRequest;
import com.example.louagetunisie.model.*;
import com.example.louagetunisie.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ChauffeurRepository chauffeurRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private VehiculeRepository vehiculeRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {

        boolean emailExists = clientRepository.existsByEmail(request.getEmail())
                || chauffeurRepository.existsByEmail(request.getEmail());

        if (emailExists) {
            throw new RuntimeException("Cet email est déjà utilisé !");
        }

        // Vérifications d'unicité spécifiques aux chauffeurs
        if (request.getRole().equalsIgnoreCase("CHAUFFEUR")) {

            if (vehiculeRepository.findByMatricule(request.getMatricule()).isPresent()) {
                throw new RuntimeException("Ce matricule est déjà associé à un autre véhicule.");
            }

            if (chauffeurRepository.existsByNumLicence(request.getNumLicence())) {
                throw new RuntimeException("Ce numéro de licence est déjà utilisé.");
            }
        }

        String hashedPassword = passwordEncoder.encode(request.getMotDePasse());

        if (request.getRole().equalsIgnoreCase("CLIENT")) {
            Client client = new Client();
            client.setNom(request.getNom());
            client.setPrenom(request.getPrenom());
            client.setEmail(request.getEmail());
            client.setMotDePasse(hashedPassword);
            client.setRole("CLIENT");
            client.setTelephone(request.getTelephone());
            client.setCin(request.getCin());

            clientRepository.save(client);

        } else if (request.getRole().equalsIgnoreCase("CHAUFFEUR")) {
            Chauffeur chauffeur = new Chauffeur();
            chauffeur.setNom(request.getNom());
            chauffeur.setPrenom(request.getPrenom());
            chauffeur.setEmail(request.getEmail());
            chauffeur.setMotDePasse(hashedPassword);
            chauffeur.setRole("CHAUFFEUR");
            chauffeur.setTelephone(request.getTelephone());
            chauffeur.setCin(request.getCin());
            chauffeur.setPermis(request.getPermis());
            chauffeur.setNumLicence(request.getNumLicence());

            Vehicule vehicule = vehiculeRepository.findByMatricule(request.getMatricule())
                    .orElseGet(() -> {
                        Vehicule newVehicule = new Vehicule();
                        newVehicule.setMatricule(request.getMatricule());
                        return vehiculeRepository.save(newVehicule);
                    });

            chauffeur.setVehicule(vehicule);
            chauffeurRepository.save(chauffeur);

        } else {
            throw new RuntimeException("Rôle invalide : " + request.getRole());
        }
    }


    public LoginResponse login(LoginRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (utilisateur == null || !passwordEncoder.matches(request.getMotDePasse(), utilisateur.getMotDePasse())) {
            throw new RuntimeException("Email ou mot de passe invalide !");
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiration = LocalDateTime.now().plusHours(3);

        Session session = new Session();
        session.setToken(token);
        session.setExpiration(expiration);
        session.setUtilisateur(utilisateur);

        sessionRepository.save(session);

        return new LoginResponse(
                token,
                utilisateur.getEmail(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getRole(),
                utilisateur.getId()
        );
    }
}
