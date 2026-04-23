package com.nara.service;

import com.nara.enums.AirlineStatus;
import com.nara.payload.request.AirlineRequest;
import com.nara.payload.response.AirlineDropDownItem;
import com.nara.payload.response.AirlineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirlineService {
    AirlineResponse createAirline(AirlineRequest airlineRequest, Long ownerId);
    AirlineResponse getAirlineByOwner(Long ownerId);
    AirlineResponse getAirlineById(Long airlineId);
    Page<AirlineResponse> getAllAirlines(Pageable pageable);
    AirlineResponse updateAirline(AirlineRequest airlineRequest, Long ownerId);
    void deleteAirline(Long id, Long ownerId);
    AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus airlineStatus);
    List<AirlineDropDownItem>  getAirlineDropDown();
}
