package org.example.javafinalproject.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tickets")
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Future
    private LocalDate journeyDate;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private User passenger;

    @ManyToOne
    @JoinColumn(name = "trip_schedule_id")
    @JsonManagedReference
    private TripSchedule tripSchedule;
}
