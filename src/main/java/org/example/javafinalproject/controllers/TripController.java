package org.example.javafinalproject.controllers;

import jakarta.validation.Valid;
import org.example.javafinalproject.exception.ResourceNotFoundException;
import org.example.javafinalproject.models.*;
import org.example.javafinalproject.payloads.request.CreateTripRequest;
import org.example.javafinalproject.payloads.response.ApiResponseBuilder;
import org.example.javafinalproject.repository.*;
import org.example.javafinalproject.specifications.TripSpecs;
import org.example.javafinalproject.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/trip")
public class TripController {
    @Autowired
    private BusRepository busRepository;

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripScheduleRepository tripScheduleRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createTrip(@Valid @RequestBody CreateTripRequest createTripRequest,
            BindingResult bindingResult, Principal principal) {
        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + userEmail));

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(ApiResponseBuilder.buildErrorResponse(errorMessage.toString()));
        }

        Stop sourceStop = stopRepository.findById(createTripRequest.getSourceStop())
                .orElseThrow(() -> new ResourceNotFoundException("Source stop not found"));
        Stop destStop = stopRepository.findById(createTripRequest.getDestStop())
                .orElseThrow(() -> new ResourceNotFoundException("Dest stop not found"));
        Bus bus = busRepository.findById(createTripRequest.getBus())
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found"));
        Agency agency = agencyRepository.findById(createTripRequest.getAgency())
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found"));

        if (!Objects.equals(agency.getOwner().getId(), user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponseBuilder.buildErrorResponse("Couldn't add bus to agency that you didn't own"));
        }

        Trip trip = new Trip();
        trip.setFare(createTripRequest.getFare());
        trip.setJourneyTime(createTripRequest.getJourneyTime());
        trip.setSourceStop(sourceStop);
        trip.setDestStop(destStop);
        trip.setBus(bus);
        trip.setAgency(agency);

        tripRepository.save(trip);

        Set<TripSchedule> tripSchedules = createTripRequest.getTripSchedules().stream().map(scheduleRequest -> {
            TripSchedule tripSchedule = new TripSchedule();
            LocalDate tripDate = DateUtil.parseDate(scheduleRequest.getTripDate());
            tripSchedule.setTripDate(tripDate);
            tripSchedule.setAvailableSeats(scheduleRequest.getAvailableSeats());
            tripSchedule.setTripDetail(trip);
            return tripScheduleRepository.save(tripSchedule);
        }).collect(Collectors.toSet());

        trip.setTripSchedules(tripSchedules);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseBuilder.buildSuccessResponse(trip, "Trip created successfully!"));
    }

    @GetMapping("")
    public ResponseEntity<?> getAllTrips(
            @RequestParam(required = false) Long sourceStop,
            @RequestParam(required = false) Long destStop,
            @RequestParam(required = false) String tripDate) {

        LocalDate parsedTripDate = tripDate != null ? DateUtil.parseDate(tripDate) : null;

        List<Trip> trips = tripRepository.findAll(Specification.where(
                TripSpecs.hasSourceStop(sourceStop)
                        .and(TripSpecs.hasDestStop(destStop))
                        .and(TripSpecs.hasTripDate(parsedTripDate))));

        return ResponseEntity.ok().body(ApiResponseBuilder.buildSuccessResponse(trips));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getDetailTrip(@PathVariable Long id) {
        Trip trip = tripRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Trip not found"));
            
        return ResponseEntity.ok().body(ApiResponseBuilder.buildSuccessResponse(trip));
    }
}
