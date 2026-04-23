package com.nara.service;

import com.nara.payload.request.AircraftRequest;
import com.nara.payload.response.AircraftResponse;

import java.util.List;

public interface AircraftService {
    AircraftResponse creatAircraft(AircraftRequest aircraftRequest, Long ownerId) throws Exception;
    AircraftResponse getAircraftById(Long id);
    List<AircraftResponse> getAllAircraftsByOwner(Long ownerId);
    AircraftResponse updateAircraft(AircraftRequest aircraftRequest, Long ownerId);
    void deleteAircraft(Long aircraftId, Long ownerId);

}
