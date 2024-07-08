package org.example.javafinalproject.repository;

import java.util.Optional;

import org.example.javafinalproject.models.TripSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripScheduleRepository extends JpaRepository<TripSchedule, Long> {
}
