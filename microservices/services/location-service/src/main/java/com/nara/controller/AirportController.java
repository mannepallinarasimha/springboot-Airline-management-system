package com.nara.controller;

import com.nara.payload.request.AirportRequest;
import com.nara.payload.response.AirportResponse;
import com.nara.service.AirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/airports")
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<AirportResponse> createAirport(@RequestBody @Valid AirportRequest airportRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(airportService.createAirport(airportRequest));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AirportResponse> getAirportById(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(airportService.getAirportById(id));
    }

//    @GetMapping(path = "city/{cityId}")
//    public ResponseEntity<List<AirportResponse>> getAllAirports(@PathVariable("cityId") Long cityId) throws Exception {
//        return ResponseEntity.ok(airportService.getAirportById(id));
//    }
    
}
