package org.example.javafinalproject.payloads.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateTripRequest {
    @NotNull(message = "Fare is mandatory")
    @Min(value = 1, message = "Fare must be at least 1")
    private Integer fare;

    @NotBlank(message = "Journey time is mandatory")
    private String journeyTime;

    @NotNull(message = "Source stop ID is mandatory")
    private Long sourceStop;

    @NotNull(message = "Destiny stop is mandatory")
    private Long destStop;

    @NotNull(message = "Bus is mandatory")
    private Long bus;

    @NotNull(message = "Agency is mandatory")
    private Long agency;

    @Valid
    @NotEmpty(message = "Trip schedules are mandatory")
    private List<TripScheduleRequest> tripSchedules;

    @Getter
    public static class TripScheduleRequest {
        @NotBlank(message = "Trip date is mandatory")
        private String tripDate;

        @NotNull(message = "Available seats is mandatory")
        @Min(value = 1, message = "Available seats must be at least 1")
        private Integer availableSeats;
    }
}
