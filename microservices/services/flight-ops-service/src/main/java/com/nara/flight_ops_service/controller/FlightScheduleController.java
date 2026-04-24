package com.nara.flight_ops_service.controller;

import com.nara.flight_ops_service.service.FlightScheduleService;
import com.nara.payload.request.FlightInstanceRequest;
import com.nara.payload.request.FlightScheduleRequest;
import com.nara.payload.response.ApiResponse;
import com.nara.payload.response.FlightInstanceResponse;
import com.nara.payload.response.FlightScheduleResponse;
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
@RequestMapping(path="api/schedules")
public class FlightScheduleController {

    private final FlightScheduleService flightScheduleService;
    @PostMapping
    public ResponseEntity<FlightScheduleResponse> createFlightSchedule(
            @RequestBody @Valid FlightScheduleRequest flightScheduleRequest,
            @RequestHeader("X-Airline-Id") Long airlineId
    ) throws Exception {

        //todo - watch for airlineId
        return ResponseEntity.status(HttpStatus.CREATED).body(flightScheduleService.createFlightSchedule(airlineId, flightScheduleRequest));
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<FlightScheduleResponse> getFlightScheduleById(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(flightScheduleService.getFlightScheduleById(id));
    }

    @GetMapping
    public ResponseEntity<?> getFlightSchedules(
            @RequestHeader("X-Airline-Id") Long airlineId
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(flightScheduleService.getFlightScheduleByAirline(airlineId));
    }

    @PutMapping(path="update/{id}")
    public ResponseEntity<FlightScheduleResponse> updateFlightSchedule(
            @PathVariable Long id,
            @RequestBody FlightScheduleRequest flightScheduleRequest
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(flightScheduleService.updateFlightSchedule(id,flightScheduleRequest));
    }

    @DeleteMapping(path="delete/{id}")
    public ResponseEntity<ApiResponse> deleteFlightSchedule(
            @PathVariable Long id
    ) throws Exception {
        flightScheduleService.deleteFlightSchedule(id);

        return ResponseEntity.ok(new ApiResponse("flight schedule deleted"));
    }
}
