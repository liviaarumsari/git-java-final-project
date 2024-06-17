package org.example.javafinalproject.payloads.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class ProfileUpdateRequest {
    @Size(min = 1, max = 255, message = "First name must be between 1 and 255 characters")
    private String firstName;

    @Size(min = 1, max = 255, message = "Last name must be between 1 and 255 characters")
    private String lastName;

    @Size(min = 1, max = 20, message = "Mobile number must be between 1 and 20 characters")
    private String mobileNumber;

    @Size(min = 6, max = 40, message = "Password must be between 6 and 40 characters")
    private String password;
}

