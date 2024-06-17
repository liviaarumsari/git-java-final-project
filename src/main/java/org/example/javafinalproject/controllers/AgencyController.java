package org.example.javafinalproject.controllers;

import jakarta.validation.Valid;
import org.example.javafinalproject.models.Agency;
import org.example.javafinalproject.models.User;
import org.example.javafinalproject.payloads.request.CreateAgencyRequest;
import org.example.javafinalproject.repository.AgencyRepository;
import org.example.javafinalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/agency")
public class AgencyController {
    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody CreateAgencyRequest createAgencyRequest, Principal principal) {
        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + userEmail));

        Agency newAgency = new Agency(createAgencyRequest.getCode(), createAgencyRequest.getName(), createAgencyRequest.getDetails(), user);
        agencyRepository.save(newAgency);

        return ResponseEntity.ok(newAgency);
    }
}
