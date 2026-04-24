package com.nara.payload.request;

import com.nara.enums.FlightStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightInstanceRequest {

    @NotNull(message = "Flight ID is mandatory")
    private Long flightId;

    private Long scheduledId;

    private Long departureAirportId;

    private Long arrivalAirportId;

    @NotNull(message = "departureDateTime is mandatory")
    private LocalDateTime departureDateTime;

    @NotNull(message = "arrivalDateTime is mandatory")
    private LocalDateTime arrivalDateTime;

    @NotNull(message = "totalSeats is mandatory")
    @Positive
    private Integer totalSeats;

    @PositiveOrZero
    private Integer availableSeats;

    private FlightStatus status;

    private Integer minAdvanceBookingDays;
    private Integer maxAdvanceBookingDays;
    private Boolean isActive;
}
