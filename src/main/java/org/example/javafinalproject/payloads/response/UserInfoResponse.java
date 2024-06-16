package org.example.javafinalproject.payloads.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserInfoResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private List<String> roles;

    public UserInfoResponse(Long id, String email, String firstName, String lastName, String mobileNumber, List<String> roles) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.roles = roles;
    }
}
