package org.example.javafinalproject.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "buses")
@Getter
@Setter
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String code;

    @NotNull
    private int capacity;

    private String make;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    @JsonBackReference
    private Agency agency;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<Trip> trips = new HashSet<>();

    public Bus() {
    }

    public Bus(String code, int capacity, String make, Agency agency) {
        this.code = code;
        this.capacity = capacity;
        this.make = make;
        this.agency = agency;
    }
}
