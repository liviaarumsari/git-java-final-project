package org.example.javafinalproject.repository;

import java.util.Optional;

import org.example.javafinalproject.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface TripRepository extends JpaRepository<Trip, Long>, JpaSpecificationExecutor<Trip> {
  Optional<Trip> findById(Long id);
}
