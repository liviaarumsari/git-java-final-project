package org.example.javafinalproject.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Integer fare;

    @NotBlank
    private String journeyTime;

    @ManyToOne
    @JoinColumn(name = "source_stop_id")
    @JsonBackReference
    private Stop sourceStop;

    @ManyToOne
    @JoinColumn(name = "dest_stop_id")
    @JsonBackReference
    private Stop destStop;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    @JsonBackReference
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    @JsonBackReference
    private Agency agency;

    @OneToMany(mappedBy = "tripDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<TripSchedule> tripSchedules = new HashSet<>();

    public Trip() {
    }

    public Trip(Integer fare, String journeyTime, Stop sourceStop, Stop destStop, Bus bus, Agency agency) {
        this.fare = fare;
        this.journeyTime = journeyTime;
        this.sourceStop = sourceStop;
        this.destStop = destStop;
        this.bus = bus;
        this.agency = agency;
    }
}
