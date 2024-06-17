package org.example.javafinalproject.repository;

import org.example.javafinalproject.models.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
    Optional<Agency> findById(Long id);
}
