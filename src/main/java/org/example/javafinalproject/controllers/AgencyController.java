package org.example.javafinalproject.controllers;

import jakarta.validation.Valid;
import org.example.javafinalproject.models.Agency;
import org.example.javafinalproject.models.User;
import org.example.javafinalproject.payloads.request.CreateAgencyRequest;
import org.example.javafinalproject.payloads.response.ApiResponseBuilder;
import org.example.javafinalproject.repository.AgencyRepository;
import org.example.javafinalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
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

    @GetMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllAgencies(Principal principal) {
        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + userEmail));
        return ResponseEntity.ok().body(ApiResponseBuilder.buildSuccessResponse(user.getAgencies()));
    }

    @PostMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createAgency(@Valid @RequestBody CreateAgencyRequest createAgencyRequest, BindingResult bindingResult, Principal principal) {
        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + userEmail));

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(ApiResponseBuilder.buildErrorResponse(errorMessage.toString()));
        }

        Agency newAgency = new Agency(createAgencyRequest.getCode(), createAgencyRequest.getName(), createAgencyRequest.getDetails(), user);
        agencyRepository.save(newAgency);

        return ResponseEntity.ok().body(ApiResponseBuilder.buildSuccessResponse(newAgency, "Agency created successfully!"));
    }
}
