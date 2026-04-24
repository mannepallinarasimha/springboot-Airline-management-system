package com.nara.payload.request;

import com.nara.payload.response.AirportResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightScheduleRequest {
    @NotNull(message = "flightId is required")
    private Long flightId;
    private String flightNumber;

    @NotNull(message = "departureTime is required")
    private LocalTime departureTime;

    @NotNull(message = "arrivalTime is required")
    private LocalTime arrivalTime;


    @NotNull(message = "startDate is required")
    private LocalDate startDate;

    @NotNull(message = "endDate is required")
    private LocalDate endDate;

    private List<DayOfWeek> operatingDays;

    private Boolean isActive;
}
