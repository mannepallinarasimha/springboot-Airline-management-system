package com.nara.controller;

import com.nara.payload.request.AircraftRequest;
import com.nara.payload.response.AircraftResponse;
import com.nara.service.AircraftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="api/aircrafts")
public class AircraftController {

    private final AircraftService aircraftService;

    @PostMapping
    public ResponseEntity<AircraftResponse> createAircraft(
            @RequestBody @Valid AircraftRequest aircraftRequest,
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED).body(aircraftService.creatAircraft(aircraftRequest, userId));
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<AircraftResponse> getAircraftById(
            @PathVariable Long id
    ) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(aircraftService.getAircraftById(id));
    }

    @GetMapping(path="all")
    public ResponseEntity<List<AircraftResponse>> getAllAircrafts(
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(aircraftService.getAllAircraftsByOwner(userId));
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<AircraftResponse> updateAircraft(
            @PathVariable Long id,
            @RequestBody @Valid AircraftRequest aircraftRequest,
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(aircraftService.updateAircraft(id, aircraftRequest, userId));
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deleteAircraft(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        aircraftService.deleteAircraft(id, userId);
        return ResponseEntity.noContent().build();
    }
}
