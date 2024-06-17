package org.example.javafinalproject.repository;

import org.example.javafinalproject.models.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Long> {
}
