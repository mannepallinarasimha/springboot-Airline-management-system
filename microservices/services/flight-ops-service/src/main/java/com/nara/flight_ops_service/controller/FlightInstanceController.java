package com.nara.flight_ops_service.controller;

import com.nara.enums.FlightStatus;
import com.nara.flight_ops_service.service.FlightInstanceService;
import com.nara.flight_ops_service.service.FlightService;
import com.nara.payload.request.FlightInstanceRequest;
import com.nara.payload.request.FlightRequest;
import com.nara.payload.response.ApiResponse;
import com.nara.payload.response.FlightInstanceResponse;
import com.nara.payload.response.FlightResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="api/flight-instances")
public class FlightInstanceController {

    private final FlightInstanceService flightInstanceService;

    @PostMapping
    public ResponseEntity<FlightInstanceResponse> createFlightInstance(
            @RequestBody @Valid FlightInstanceRequest flightInstanceRequest,
            @RequestHeader("X-Airline-Id") Long airlineId
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightInstanceService.createFlightInstance(airlineId, flightInstanceRequest));
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<FlightInstanceResponse> getFlightInstanceById(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(flightInstanceService.getFlightInstanceById(id));
    }

    @GetMapping
    public ResponseEntity<Page<FlightInstanceResponse>> getByAirline(
            @RequestHeader("X-Airline-Id") Long airlineId,
            @RequestParam(required = false) Long departureAirportId,
            @RequestParam(required = false) Long arrivalAirportId,
            @RequestParam(required = false) Long flightId,
            @RequestParam(required = false) LocalDate onDate,
            Pageable pageable
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(flightInstanceService.getByAirlineId(airlineId, departureAirportId, arrivalAirportId, flightId, onDate, pageable));
    }

    @PutMapping(path="update/{id}")
    public ResponseEntity<FlightInstanceResponse> updateFlightInstance(
            @PathVariable Long id,
            @RequestBody FlightInstanceRequest flightInstanceRequest
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(flightInstanceService.updateFlightInstance(id,flightInstanceRequest));
    }

    @DeleteMapping(path="delete/{id}")
    public ResponseEntity<ApiResponse> deleteFlightInstance(
            @PathVariable Long id
    ) throws Exception {
        flightInstanceService.deleteFlightInstance(id);

        return ResponseEntity.ok(new ApiResponse("flight instance deleted"));
    }

}
