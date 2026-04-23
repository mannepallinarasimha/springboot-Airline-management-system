package com.nara.payload.response;

import com.nara.enums.AircraftStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftResponse {

    private Long id;
    private String code;

    private String model;

    private String manufacturer;

    private Integer seatingCapacity;

    private Integer economySeats;

    private Integer premiumSeats;

    private Integer firstClassSeats;

    private Integer businessSeats;

    private Integer cruisingSpeedKmh;

    private Integer rangeKm;

    private Integer maxAltitudeFt;

    private Integer yearOfManufacture;

    private LocalDate registrationDate;

    private LocalDate nextMaintenanceDate;

    private AircraftStatus status;

    private Boolean isAvailable;

    private Long airlineId;
    private String airlineName;
    private String airlineIataCode;

    private Long currentAirportId;
    private Long currentAirportCity;
    private String currentAirportCode;
    private String currentAirportName;

    private Integer totalSeats;
    private Boolean requiredMaintenance;
    private Boolean isOperational;

    private Instant createdAt;
    private Instant updatedAt;

}
