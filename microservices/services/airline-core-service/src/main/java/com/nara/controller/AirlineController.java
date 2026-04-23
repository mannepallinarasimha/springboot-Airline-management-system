package com.nara.controller;

import com.nara.enums.AirlineStatus;
import com.nara.payload.request.AirlineRequest;
import com.nara.payload.response.AirlineDropDownItem;
import com.nara.payload.response.AirlineResponse;
import com.nara.payload.response.ApiResponse;
import com.nara.service.AirlineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/airlines")
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineService airlineService;

    @PostMapping
    public ResponseEntity<AirlineResponse> createAirline(
            @RequestBody @Valid AirlineRequest airlineRequest,
            @RequestHeader("X-User-Id") Long userId
            ){
        return  ResponseEntity.status(HttpStatus.CREATED).body(airlineService.createAirline(airlineRequest, userId));
    }

    @GetMapping(path="admin")
    public ResponseEntity<AirlineResponse> getAirlineByOwner(
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        return  ResponseEntity.status(HttpStatus.OK).body(airlineService.getAirlineByOwner(userId));
    }
    @GetMapping(path="/{id}")
    public ResponseEntity<AirlineResponse> getAirlineById(
            @PathVariable Long id
    ) throws Exception {
        return  ResponseEntity.status(HttpStatus.OK).body(airlineService.getAirlineById(id));
    }
    @GetMapping
    public ResponseEntity<Page<AirlineResponse>> getAllAirlines(Pageable pageable) throws Exception {
        return  ResponseEntity.status(HttpStatus.OK).body(airlineService.getAllAirlines(pageable));
    }
    @GetMapping(path="dropdown")
    public ResponseEntity<List<AirlineDropDownItem>> getAirlinesForDropDown() throws Exception {
        return  ResponseEntity.status(HttpStatus.OK).body(airlineService.getAirlineDropDown());
    }

    @PutMapping
    public ResponseEntity<AirlineResponse> getAirlineById(
            @RequestBody @Valid AirlineRequest airlineRequest,
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        return  ResponseEntity.status(HttpStatus.OK).body(airlineService.updateAirline(airlineRequest, userId));
    }

    @DeleteMapping(path="delete/{id}")
    public ResponseEntity<ApiResponse> deleteAirline(
            @PathVariable Long id,
            @RequestHeader("X-Owner-Id") Long ownerId
    ) throws Exception {
        airlineService.deleteAirline(id, ownerId);
        return ResponseEntity.ok(new ApiResponse("Deleted Successfully"));
    }

    @PostMapping(path="/{id}/approve")
    public ResponseEntity<AirlineResponse> approveAirline(@PathVariable Long id) throws Exception {
        return  ResponseEntity.status(HttpStatus.OK).body(airlineService.changeStatusByAdmin(id, AirlineStatus.ACTIVE));
    }

    @PostMapping(path="/{id}/suspend")
    public ResponseEntity<AirlineResponse> suspendAirline(@PathVariable Long id) throws Exception {
        return  ResponseEntity.status(HttpStatus.OK).body(airlineService.changeStatusByAdmin(id, AirlineStatus.INACTIVE));
    }

    @PostMapping(path="/{id}/ban")
    public ResponseEntity<AirlineResponse> banAirline(@PathVariable Long id) throws Exception {
        return  ResponseEntity.status(HttpStatus.OK).body(airlineService.changeStatusByAdmin(id, AirlineStatus.BANNED));
    }
}
