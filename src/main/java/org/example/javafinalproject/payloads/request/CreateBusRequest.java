package org.example.javafinalproject.payloads.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateBusRequest {
    @NotBlank(message = "Code is mandatory")
    private String code;

    @NotNull(message = "Capacity is mandatory")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @NotBlank(message = "Make is mandatory")
    private String make;

    @NotNull(message = "Agency ID is mandatory")
    private Long agencyId;
}
