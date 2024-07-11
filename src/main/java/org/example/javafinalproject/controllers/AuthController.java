package org.example.javafinalproject.controllers;

import jakarta.validation.Valid;
import org.example.javafinalproject.enums.ERole;
import org.example.javafinalproject.models.Role;
import org.example.javafinalproject.models.User;
import org.example.javafinalproject.payloads.request.LoginRequest;
import org.example.javafinalproject.payloads.request.SignupRequest;
import org.example.javafinalproject.payloads.response.ApiResponseBuilder;
import org.example.javafinalproject.payloads.response.UserInfoResponse;
import org.example.javafinalproject.payloads.response.UserLoginResponse;
import org.example.javafinalproject.repository.RoleRepository;
import org.example.javafinalproject.repository.UserRepository;
import org.example.javafinalproject.security.jwt.JwtUtils;
import org.example.javafinalproject.security.services.AuthenticateService;
import org.example.javafinalproject.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticateService authenticateService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(ApiResponseBuilder.buildErrorResponse(errorMessage.toString()));
        }
        try {
            UserDetailsImpl userDetails = authenticateService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
    
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    
            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
            String jwtToken = jwtUtils.generateTokenFromUsername(userDetails.getUsername());
    
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
    
            UserLoginResponse response = new UserLoginResponse(userDetails.getId(),
                    userDetails.getEmail(),
                    userDetails.getFirstName(),
                    userDetails.getLastName(),
                    userDetails.getMobileNumber(),
                    roles,
                    jwtToken); 
    
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(ApiResponseBuilder.buildSuccessResponse(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponseBuilder.buildErrorResponse("Bad credentials"));
        }
    }    

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(". "));
            return ResponseEntity.badRequest().body(ApiResponseBuilder.buildErrorResponse(errorMessage.toString()));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(ApiResponseBuilder.buildErrorResponse("Error: Email is already taken!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getMobileNumber());

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok().body(ApiResponseBuilder.buildSuccessResponse("User registered successfully!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponseBuilder.buildSuccessResponse("You've been signed out!"));
    }

    @PutMapping("/admin")
    public ResponseEntity<?> upgradeToAdmin(Principal principal) {
        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + userEmail));

        Set<Role> roles = user.getRoles();

        Role adminRole = roleRepository.findByRole(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(adminRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok().body(ApiResponseBuilder.buildSuccessResponse("User upgraded to admin successfully!"));
    }
}
