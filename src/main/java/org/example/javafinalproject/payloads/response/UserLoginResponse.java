package org.example.javafinalproject.payloads.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserLoginResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private List<String> roles;
    private String token;

    public UserLoginResponse(Long id, String email, String firstName, String lastName, String mobileNumber, List<String> roles, String token) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.roles = roles;
        this.token = token;
    }
}
