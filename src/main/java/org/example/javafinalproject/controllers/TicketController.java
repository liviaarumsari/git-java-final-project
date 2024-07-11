package org.example.javafinalproject.controllers;

import jakarta.validation.Valid;
import org.example.javafinalproject.exception.ResourceNotFoundException;
import org.example.javafinalproject.models.*;
import org.example.javafinalproject.payloads.request.CreateTicketRequest;
import org.example.javafinalproject.payloads.response.ApiResponseBuilder;
import org.example.javafinalproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TripScheduleRepository tripScheduleRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public ResponseEntity<?> createTicket(@Valid @RequestBody CreateTicketRequest createTicketRequest, BindingResult bindingResult, Principal principal) {
        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + userEmail));

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(ApiResponseBuilder.buildErrorResponse(errorMessage.toString()));
        }

        TripSchedule tripSchedule = tripScheduleRepository.findById(createTicketRequest.getTripScheduleId()).orElseThrow(() -> new ResourceNotFoundException("Trip schedule not found"));

        LocalDate currentDate = LocalDate.now();
        if (tripSchedule.getTripDate().isBefore(currentDate)) {
            return ResponseEntity.badRequest().body(ApiResponseBuilder.buildErrorResponse("Trip date is in the past"));
        }

        Ticket ticket = new Ticket();
        ticket.setPassenger(user);
        ticket.setJourneyDate(tripSchedule.getTripDate());
        ticket.setTripSchedule(tripSchedule);

        ticketRepository.save(ticket);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseBuilder.buildSuccessResponse(ticket, "Ticket created successfully!"));
    }
}
