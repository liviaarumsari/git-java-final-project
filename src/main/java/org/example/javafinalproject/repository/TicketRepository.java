package org.example.javafinalproject.repository;

import org.example.javafinalproject.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
