package org.example.javafinalproject.controllers;

import org.example.javafinalproject.models.Stop;
import org.example.javafinalproject.payloads.response.ApiResponseBuilder;
import org.example.javafinalproject.repository.StopRepository;
import org.example.javafinalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.criteria.CriteriaBuilder.In;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/stop")
public class StopController {
    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity<?> getAllStops(Principal principal) {
        String userEmail = principal.getName();
        userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + userEmail));

        List<Stop> stops = stopRepository.findAll();

        return ResponseEntity.ok().body(ApiResponseBuilder.buildSuccessResponse(stops));
    }

    @GetMapping("/order")
    public ResponseEntity<?> getStopOrder(
        @RequestParam(required = true) Long sourceStop,
        @RequestParam(required = true) Long destStop
    ) {
        Optional<Stop> sourceNumber = stopRepository.findById(sourceStop);
        Optional<Stop> destNumber = stopRepository.findById(destStop);

        Integer stopOne = sourceNumber.get().getNumber();
        Integer stopTwo = destNumber.get().getNumber();

        Integer bigger;
        Integer smaller;
        if (stopOne > stopTwo) {
            bigger = stopOne;
            smaller = stopTwo;
        } else {
            bigger = stopTwo;
            smaller = stopOne;
        }

        List<Integer> numbers = new ArrayList<Integer>();
        for (Integer i = smaller; numbers.size() < bigger; i++) {
            numbers.add(i);
        }

        List<Stop> stops;
        if (stopTwo == bigger) {
            stops = stopRepository.findByNumberIn(numbers, Sort.by(Sort.Direction.ASC, "number"));
        } else {
            stops = stopRepository.findByNumberIn(numbers, Sort.by(Sort.Direction.DESC, "number"));
        }
        
        return ResponseEntity.ok().body(ApiResponseBuilder.buildSuccessResponse(stops));
    }
}
