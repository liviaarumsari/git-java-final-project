package org.example.javafinalproject.payloads.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateTicketRequest {
    @NotNull(message = "Seat number is mandatory")
    @Min(value = 1, message = "Seat number must be at least 1")
    private Integer seatNumber;

    @NotNull(message = "Cancellable is mandatory")
    private Boolean cancellable;

    @NotNull(message = "Trip schedule ID is mandatory")
    private Long tripScheduleId;
}
