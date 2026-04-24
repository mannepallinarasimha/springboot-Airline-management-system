package com.nara.flight_ops_service.controller;

import com.nara.enums.FlightStatus;
import com.nara.flight_ops_service.service.FlightService;
import com.nara.payload.request.FlightRequest;
import com.nara.payload.response.FlightResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="api/flights")
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightResponse> createFlight(
            @RequestBody @Valid FlightRequest flightRequest,
            @RequestHeader("AirlineId") Long airlineId
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.createFlight(airlineId, flightRequest));
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<FlightResponse> getFlightById(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(flightService.getFlightById(id));
    }

    @GetMapping(path="airline")
    public ResponseEntity<Page<FlightResponse>> getFlightByAirline(
            @RequestHeader("AirlineId") Long airlineId,
            @RequestParam(required = false) Long departureAirportId,
            @RequestParam(required = false) Long arrivalAirportId,
            Pageable pageable
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(flightService.getFlightsByAirline(airlineId, departureAirportId, arrivalAirportId, pageable));
    }

    @PutMapping(path="update/{id}")
    public ResponseEntity<FlightResponse> updateFlight(
            @PathVariable Long id,
            @RequestBody @Valid FlightRequest flightRequest
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(flightService.updateFlight(id,flightRequest));
    }

    @PatchMapping(path="status/{id}")
    public ResponseEntity<FlightResponse> changeStatus(
            @PathVariable Long id,
            @RequestBody @Valid FlightStatus flightStatus
            ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(flightService.changeFlightStatus(id, flightStatus));
    }

    @DeleteMapping(path="delete/{id}")
    public ResponseEntity<Void> deleteFlight(
            @PathVariable Long id,
            @RequestHeader("AirlineId") Long airlineId
    ) throws Exception {
        flightService.deleteFlight(airlineId, id);
        return ResponseEntity.noContent().build();
    }

}
