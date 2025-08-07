package com.example.louagetunisie.repository;

import com.example.louagetunisie.model.Client;
import com.example.louagetunisie.model.Reservation;
import com.example.louagetunisie.model.Trajet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByClientAndTrajet(Client client, Trajet trajet);

    @Modifying
    @Transactional
    @Query("DELETE FROM Reservation r WHERE r.client.id = :clientId")
    void deleteByClientId(@Param("clientId") Long clientId);

}
