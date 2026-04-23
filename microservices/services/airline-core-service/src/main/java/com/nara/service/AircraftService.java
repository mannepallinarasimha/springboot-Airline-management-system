package com.nara.service;

import com.nara.payload.request.AircraftRequest;
import com.nara.payload.response.AircraftResponse;

import java.util.List;

public interface AircraftService {
    AircraftResponse creatAircraft(AircraftRequest aircraftRequest, Long ownerId) throws Exception;
    AircraftResponse getAircraftById(Long id) throws Exception;
    List<AircraftResponse> getAllAircraftsByOwner(Long ownerId) throws Exception;
    AircraftResponse updateAircraft(Long id,AircraftRequest aircraftRequest, Long ownerId) throws Exception;
    void deleteAircraft(Long aircraftId, Long ownerId) throws Exception;

}
