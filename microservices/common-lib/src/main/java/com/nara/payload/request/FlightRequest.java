package com.nara.payload.request;

import com.nara.enums.FlightStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightRequest {

    @NotBlank(message = "Flight number is required")
    @Size(max = 10)
    private String flightNumber;

    private Long airlineId;

    @NotNull(message = "aircraftId is required")
    private Long aircraftId;

    @NotNull(message = "departureAirportId is required")
    private Long departureAirportId;

    @NotNull(message = "arrival Airport Id  is required")
    private Long arrivalAirportId;

    private FlightStatus status;

}
