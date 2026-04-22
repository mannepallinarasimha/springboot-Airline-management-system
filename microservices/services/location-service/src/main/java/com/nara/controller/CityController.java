package com.nara.controller;

import com.nara.payload.request.CityRequest;
import com.nara.payload.response.ApiResponse;
import com.nara.payload.response.CityResponse;
import com.nara.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="api/cities")
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseEntity<CityResponse> createCity(@RequestBody @Valid CityRequest cityRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.createCity(cityRequest));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CityResponse> getCityById(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(cityService.getCityById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CityResponse>> getCities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(cityService.getAllCities(pageable));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CityResponse> updateCity(@PathVariable("id") Long id, @Valid @RequestBody CityRequest cityRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(cityService.updateCity(id, cityRequest));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deleteCity(@PathVariable("id") Long id) throws Exception {
        cityService.deleteCity(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("City deleted successfully"));
    }

    @GetMapping(path = "/search")
    public ResponseEntity<Page<CityResponse>> searchCities(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(cityService.searchCities(keyword, pageable));
    }

    @GetMapping(path = "/country/{countryCode}")
    public ResponseEntity<Page<CityResponse>> getCitiesByCountryCode(
            @RequestParam String countryCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(cityService.getCitiesByCountryCode(countryCode, pageable));
    }

    @GetMapping(path = "/exists/{cityCode}")
    public ResponseEntity<Boolean> checkCityExists(
            @PathVariable String cityCode
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(cityService.cityExistOrNot(cityCode.toUpperCase()));
    }

}
