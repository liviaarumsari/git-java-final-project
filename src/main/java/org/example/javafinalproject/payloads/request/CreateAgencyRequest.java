package org.example.javafinalproject.payloads.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateAgencyRequest {
    @NotBlank(message = "Code is mandatory")
    @Size(min = 1, max = 20, message = "Code must be between 1 and 20 characters")
    private String code;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;

    @NotBlank(message = "Details is mandatory")
    @Size(min = 10, message = "Details must have minimum of 10 characters")
    private String details;
}

