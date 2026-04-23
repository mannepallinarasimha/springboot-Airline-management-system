package com.nara.service.impl;

import com.nara.mapper.AircraftMapper;
import com.nara.model.Aircraft;
import com.nara.model.Airline;
import com.nara.payload.request.AircraftRequest;
import com.nara.payload.response.AircraftResponse;
import com.nara.repository.AircraftRepository;
import com.nara.repository.AirlineRepository;
import com.nara.service.AircraftService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AircraftServiceImpl implements AircraftService {
    private final AirlineRepository airlineRepository;
    private final AircraftRepository aircraftRepository;

    public AircraftServiceImpl(AirlineRepository airlineRepository, AircraftRepository aircraftRepository) {
        this.airlineRepository = airlineRepository;
        this.aircraftRepository = aircraftRepository;
    }

    @Override
    public AircraftResponse creatAircraft(AircraftRequest aircraftRequest, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(() -> new Exception("Airline not exists with ownerId : "+ownerId));
        Aircraft aircraft = AircraftMapper.toEntity(aircraftRequest, airline);
        if(aircraftRepository.existsByCode(aircraft.getCode())){
           throw new Exception("Code already exist with another aircraft.");
        }
        if(aircraft.getSeatingCapacity() < aircraft.getTotalSeats()){
            throw new Exception("Seating capacity cannot be exceeded to total seats");
        }

        return AircraftMapper.toResponse(aircraftRepository.save(aircraft));
    }

    @Override
    public AircraftResponse getAircraftById(Long id) throws Exception {
        Aircraft aircraft = aircraftRepository.findById(id).orElseThrow(() -> new Exception("Aircraft not exist with id :"+id));
        return AircraftMapper.toResponse(aircraft);
    }

    @Override
    public List<AircraftResponse> getAllAircraftsByOwner(Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(() -> new Exception("This owner don't have airline"));
        return aircraftRepository.findByAirlineId(airline.getId()).stream().map(AircraftMapper::toResponse).toList();
    }

    @Override
    public AircraftResponse updateAircraft(Long id, AircraftRequest aircraftRequest, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(() -> new Exception("This owner don't have airline"));
        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId());
        if(aircraft == null){
            throw new Exception("Aircraft not exist with id : "+id);
        }
        if(aircraftRequest.getCode()!= null
                && !aircraft.getCode().equals(aircraftRequest.getCode())
                && aircraftRepository.existsByCode(aircraftRequest.getCode())){
            throw new Exception("Code already exist with another aircraft.");
        }
        AircraftMapper.updateEntity(aircraft, aircraftRequest);
        return AircraftMapper.toResponse(aircraftRepository.save(aircraft));
    }

    @Override
    public void deleteAircraft(Long aircraftId, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(() -> new Exception("This owner don't have airline"));
        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(aircraftId, airline.getId());
        if(aircraft == null){
            throw new Exception("Aircraft not exist with id : "+aircraftId);
        }
        aircraftRepository.delete(aircraft);
    }
}
