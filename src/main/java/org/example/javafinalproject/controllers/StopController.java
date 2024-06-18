package org.example.javafinalproject.controllers;

import org.example.javafinalproject.models.Stop;
import org.example.javafinalproject.payloads.response.ApiResponseBuilder;
import org.example.javafinalproject.repository.StopRepository;
import org.example.javafinalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/stop")
public class StopController {
    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllStops(Principal principal) {
        String userEmail = principal.getName();
        userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + userEmail));

        List<Stop> stops = stopRepository.findAll();

        return ResponseEntity.ok().body(ApiResponseBuilder.buildSuccessResponse(stops));
    }
}
