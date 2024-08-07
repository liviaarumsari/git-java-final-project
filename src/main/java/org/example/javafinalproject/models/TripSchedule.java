package org.example.javafinalproject.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trip_schedules")
@Getter
@Setter
public class TripSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate tripDate;

    @Min(0)
    private int availableSeats;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    @JsonBackReference
    private Trip tripDetail;

    @OneToMany(mappedBy = "tripSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<Ticket> tickets = new HashSet<>();

    public TripSchedule() {
    }

    public TripSchedule(LocalDate tripDate, int availableSeats, Trip tripDetail) {
        this.tripDate = tripDate;
        this.availableSeats = availableSeats;
        this.tripDetail = tripDetail;
    }
}
