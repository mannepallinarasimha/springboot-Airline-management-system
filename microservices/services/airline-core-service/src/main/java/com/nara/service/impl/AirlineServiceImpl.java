package com.nara.service.impl;

import com.nara.enums.AirlineStatus;
import com.nara.payload.request.AirlineRequest;
import com.nara.payload.response.AirlineDropDownItem;
import com.nara.payload.response.AirlineResponse;
import com.nara.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {
    @Override
    public AirlineResponse createAirline(AirlineRequest airlineRequest, Long ownerId) {
        return null;
    }

    @Override
    public AirlineResponse getAirlineByOwner(Long ownerId) {
        return null;
    }

    @Override
    public AirlineResponse getAirlineById(Long airlineId) {
        return null;
    }

    @Override
    public Page<AirlineResponse> getAllAirlines(Pageable pageable) {
        return null;
    }

    @Override
    public AirlineResponse updateAirline(AirlineRequest airlineRequest, Long ownerId) {
        return null;
    }

    @Override
    public void deleteAirline(Long id, Long ownerId) {

    }

    @Override
    public AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus airlineStatus) {
        return null;
    }

    @Override
    public List<AirlineDropDownItem> getAirlineDropDown() {
        return List.of();
    }
}
