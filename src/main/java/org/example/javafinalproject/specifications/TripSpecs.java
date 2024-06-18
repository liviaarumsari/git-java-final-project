package org.example.javafinalproject.specifications;

import org.example.javafinalproject.models.Trip;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TripSpecs {
    public static Specification<Trip> hasSourceStop(Long sourceStopId) {
        return (trip, cq, cb) -> sourceStopId == null ? cb.conjunction() : cb.equal(trip.get("sourceStop").get("id"), sourceStopId);
    }

    public static Specification<Trip> hasDestStop(Long destStopId) {
        return (trip, cq, cb) -> destStopId == null ? cb.conjunction() : cb.equal(trip.get("destStop").get("id"), destStopId);
    }

    public static Specification<Trip> hasTripDate(LocalDate tripDate) {
        return (trip, cq, cb) -> tripDate == null ? cb.conjunction() : cb.equal(trip.join("tripSchedules").get("tripDate"), tripDate);
    }
}
