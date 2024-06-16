package org.example.javafinalproject.security.services;

import org.example.javafinalproject.models.User;
import org.example.javafinalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetailsImpl authenticate(String email, String rawPassword) throws AuthenticationException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            return UserDetailsImpl.build(user);
        } else {
            throw new BadCredentialsException("Invalid email or password");
        }
    }
}

