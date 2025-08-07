package com.example.louagetunisie.repository;

import com.example.louagetunisie.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    @Query("SELECT COUNT(s) FROM Station s")
    Long countStations();

}
