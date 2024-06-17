package org.example.javafinalproject.controllers;

import jakarta.validation.Valid;
import org.example.javafinalproject.models.Agency;
import org.example.javafinalproject.models.Bus;
import org.example.javafinalproject.models.User;
import org.example.javafinalproject.payloads.request.CreateAgencyRequest;
import org.example.javafinalproject.payloads.request.CreateBusRequest;
import org.example.javafinalproject.payloads.response.ApiResponseBuilder;
import org.example.javafinalproject.repository.AgencyRepository;
import org.example.javafinalproject.repository.BusRepository;
import org.example.javafinalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/bus")
public class BusController {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createBus(@Valid @RequestBody CreateBusRequest createBusRequest, BindingResult bindingResult, Principal principal) {
        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + userEmail));

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(ApiResponseBuilder.buildErrorResponse(errorMessage.toString()));
        }

        Optional<Agency> agency = agencyRepository.findById(createBusRequest.getAgencyId());
        if (agency.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponseBuilder.buildErrorResponse("Invalid agency ID"));
        }

        if (!Objects.equals(agency.get().getOwner().getId(), user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponseBuilder.buildErrorResponse("Couldn't add bus to agency that you didn't own"));
        }

        Bus newBus = new Bus(createBusRequest.getCode(), createBusRequest.getCapacity(), createBusRequest.getMake(), agency.get());
        busRepository.save(newBus);

        return ResponseEntity.ok().body(ApiResponseBuilder.buildSuccessResponse(newBus, "Bus created successfully!"));
    }
}
