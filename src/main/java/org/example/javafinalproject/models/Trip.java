package org.example.javafinalproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trips")
@Getter
@Setter
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(0)
    private double fare;

    @NotBlank
    private String journeyTime;

    @ManyToOne
    @JoinColumn(name = "source_stop_id")
    private Stop sourceStop;

    @ManyToOne
    @JoinColumn(name = "dest_stop_id")
    private Stop destStop;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @OneToMany(mappedBy = "tripDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TripSchedule> tripSchedules = new HashSet<>();

    public Trip() {
    }

    public Trip(double fare, String journeyTime, Stop sourceStop, Stop destStop, Bus bus, Agency agency) {
        this.fare = fare;
        this.journeyTime = journeyTime;
        this.sourceStop = sourceStop;
        this.destStop = destStop;
        this.bus = bus;
        this.agency = agency;
    }
}
