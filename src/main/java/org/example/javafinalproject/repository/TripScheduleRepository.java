package org.example.javafinalproject.repository;

import org.example.javafinalproject.models.TripSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripScheduleRepository extends JpaRepository<TripSchedule, Long> {
}
