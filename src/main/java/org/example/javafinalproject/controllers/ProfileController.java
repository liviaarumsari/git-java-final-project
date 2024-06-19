package org.example.javafinalproject.controllers;

import jakarta.validation.Valid;
import org.example.javafinalproject.models.User;
import org.example.javafinalproject.payloads.request.ProfileUpdateRequest;
import org.example.javafinalproject.payloads.response.ApiResponseBuilder;
import org.example.javafinalproject.payloads.response.UserInfoResponse;
import org.example.javafinalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getProfile(Principal principal) {
        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + userEmail));

        List<String> roles = user.getRoles().stream()
                .map(item -> item.getRole().toString())
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getMobileNumber(),
                roles);

        return ResponseEntity.ok().body(ApiResponseBuilder.buildSuccessResponse(response));
    }

    @PutMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody ProfileUpdateRequest profileUpdateRequest, Principal principal) {
        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + userEmail));

        if (profileUpdateRequest.getFirstName() != null) {
            user.setFirstName(profileUpdateRequest.getFirstName());
        }
        if (profileUpdateRequest.getLastName() != null) {
            user.setLastName(profileUpdateRequest.getLastName());
        }
        if (profileUpdateRequest.getMobileNumber() != null) {
            user.setMobileNumber(profileUpdateRequest.getMobileNumber());
        }
        if (profileUpdateRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(profileUpdateRequest.getPassword()));
        }

        userRepository.save(user);

        return ResponseEntity.ok(ApiResponseBuilder.buildSuccessResponse("Profile updated successfully!"));
    }
}
