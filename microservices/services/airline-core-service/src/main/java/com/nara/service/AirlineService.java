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
    AirlineResponse getAirlineByOwner(Long ownerId) throws Exception;
    AirlineResponse getAirlineById(Long airlineId) throws Exception;
    Page<AirlineResponse> getAllAirlines(Pageable pageable);
    AirlineResponse updateAirline(AirlineRequest airlineRequest, Long ownerId) throws Exception;
    void deleteAirline(Long id, Long ownerId) throws Exception;
    AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus airlineStatus) throws Exception;
    List<AirlineDropDownItem>  getAirlineDropDown();
}
