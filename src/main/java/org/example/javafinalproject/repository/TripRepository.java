package org.example.javafinalproject.repository;

import org.example.javafinalproject.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
