package com.nara.service.impl;

import com.nara.model.Aircraft;
import com.nara.model.Airline;
import com.nara.payload.request.AircraftRequest;
import com.nara.payload.response.AircraftResponse;
import com.nara.repository.AirlineRepository;
import com.nara.service.AircraftService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AircraftServiceImpl implements AircraftService {
    private final AirlineRepository airlineRepository;

    public AircraftServiceImpl(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    @Override
    public AircraftResponse creatAircraft(AircraftRequest aircraftRequest, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(() -> new Exception("Airline not exists with ownerId : "+ownerId));
        Aircraft aircraft = new Aircraft();
        return null;
    }

    @Override
    public AircraftResponse getAircraftById(Long id) {
        return null;
    }

    @Override
    public List<AircraftResponse> getAllAircraftsByOwner(Long ownerId) {
        return List.of();
    }

    @Override
    public AircraftResponse updateAircraft(AircraftRequest aircraftRequest, Long ownerId) {
        return null;
    }

    @Override
    public void deleteAircraft(Long aircraftId, Long ownerId) {

    }
}
