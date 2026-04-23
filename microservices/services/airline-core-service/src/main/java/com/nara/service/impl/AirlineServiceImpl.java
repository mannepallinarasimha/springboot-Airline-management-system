package com.nara.service.impl;

import com.nara.enums.AirlineStatus;
import com.nara.mapper.AirlineMapper;
import com.nara.model.Airline;
import com.nara.payload.request.AirlineRequest;
import com.nara.payload.response.AirlineDropDownItem;
import com.nara.payload.response.AirlineResponse;
import com.nara.repository.AirlineRepository;
import com.nara.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {
    private final AirlineRepository airlineRepository;

    @Override
    public AirlineResponse createAirline(AirlineRequest airlineRequest, Long ownerId) {
        Airline airline = AirlineMapper.toEntity(airlineRequest, ownerId);
        Airline savedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    public AirlineResponse getAirlineByOwner(Long ownerId) throws Exception {
        Airline airline  =  airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline NOT found with ownerId : "+ownerId));
        return AirlineMapper.toResponse(airline);
    }

    @Override
    public AirlineResponse getAirlineById(Long airlineId) throws Exception {
        Airline airline  =  airlineRepository.findById(airlineId)
                .orElseThrow(() -> new Exception("Airline NOT found with ID : "+airlineId));
        return AirlineMapper.toResponse(airline);
    }

    @Override
    public Page<AirlineResponse> getAllAirlines(Pageable pageable) {
        return airlineRepository.findAll(pageable).map(AirlineMapper::toResponse);
    }

    @Override
    public AirlineResponse updateAirline(AirlineRequest airlineRequest, Long ownerId) throws Exception {
        Airline airline  =  airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline NOT found with ownerId : "+ownerId));

        AirlineMapper.updateEntity(airline, airlineRequest);
        Airline savedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    public void deleteAirline(Long id, Long ownerId) throws Exception {
        Airline airline  =  airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline NOT found with ownerId : "+ownerId));
        airlineRepository.delete(airline);
    }

    @Override
    public AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus airlineStatus) throws Exception {
        Airline airline  =  airlineRepository.findById(airlineId)
                .orElseThrow(() -> new Exception("Airline NOT found with ID : "+airlineId));
        airline.setStatus(airlineStatus);
        Airline updatedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(updatedAirline);
    }

    @Override
    public List<AirlineDropDownItem> getAirlineDropDown() {
        return airlineRepository.findByStatus(AirlineStatus.ACTIVE).stream().map(
                a -> AirlineDropDownItem.builder()
                        .id(a.getId())
                        .name(a.getName())
                        .iataCode(a.getIataCode())
                        .iataCode(a.getIataCode())
                        .logoUrl(a.getLogoUrl())
                        .build()
        ).toList();
    }
}
