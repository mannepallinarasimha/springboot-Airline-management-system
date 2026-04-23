package com.nara.payload.request;

import com.nara.enums.AircraftStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftRequest {
    @NotBlank(message = "Aircraft code is mandatory")
    private String code;

    @NotBlank(message = "Aircraft model is mandatory")
    private String model;

    @NotBlank(message = "Aircraft manufacturer is mandatory")
    private String manufacturer;

    @NotNull(message = "Seating Capacity is mandatory")
    @Positive(message = "Seating Capacity must be positive")
    private Integer seatingCapacity;

    @Positive(message = "economySeats Capacity must be positive")
    private Integer economySeats;

    @Positive(message = "premiumSeats Capacity must be positive")
    private Integer premiumSeats;

    @Positive(message = "firstClassSeats Capacity must be positive")
    private Integer firstClassSeats;

    @Positive(message = "businessSeats Capacity must be positive")
    private Integer businessSeats;

    @Positive(message = "cruising Speed Kmh Capacity must be positive")
    private Integer cruisingSpeedKmh;

    @Positive(message = "range Km Capacity must be positive")
    private Integer rangeKm;

    @Positive(message = "max Altitude Ft must be positive")
    private Integer maxAltitudeFt;

    @Positive(message = "year of manufacturer must be positive")
    private Integer yearOfManufacture;

    private LocalDate registrationDate;

    private LocalDate nextMaintenanceDate;

    @NotNull(message = "status is required")
    private AircraftStatus status;

    @NotNull(message = "Availability is required")
    private Boolean isAvailable;

    private Long currentAirportId;

}
