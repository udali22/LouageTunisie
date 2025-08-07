package com.example.louagetunisie;

import com.example.louagetunisie.model.Utilisateur;
import com.example.louagetunisie.repository.UtilisateurRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initAdminIfNeeded() {
        if (!utilisateurRepository.existsByEmail("admin@mail.com")) {
            Utilisateur admin = new Utilisateur();
            admin.setNom("Admin");
            admin.setPrenom("Super");
            admin.setEmail("admin@mail.com");
            admin.setMotDePasse(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            admin.setTelephone(12345678);
            admin.setCin(11111111);
            utilisateurRepository.save(admin);
            System.out.println("✅ Admin créé avec succès");
        }
    }
}

