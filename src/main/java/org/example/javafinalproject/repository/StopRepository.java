package org.example.javafinalproject.repository;

import org.example.javafinalproject.models.Stop;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;



public interface StopRepository extends JpaRepository<Stop, Long> {
  List<Stop> findByNumberIn(List<Integer> numbers, Sort sort);
}
