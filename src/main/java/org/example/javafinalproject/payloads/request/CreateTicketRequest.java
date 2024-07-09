package org.example.javafinalproject.payloads.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateTicketRequest {
    @NotNull(message = "Trip schedule ID is mandatory")
    private Long tripScheduleId;
}
