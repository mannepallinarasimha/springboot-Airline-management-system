package com.nara.service.impl;

import com.nara.mapper.AirportMapper;
import com.nara.mapper.CityMapper;
import com.nara.model.Airport;
import com.nara.model.City;
import com.nara.payload.request.AirportRequest;
import com.nara.payload.response.AirportResponse;
import com.nara.repository.AirportRepository;
import com.nara.repository.CityRepository;
import com.nara.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;
    @Override
    public AirportResponse createAirport(AirportRequest airportRequest) throws Exception {
        if(airportRepository.findByIataCode(airportRequest.getIataCode()).isPresent()){
            throw new Exception("createAirport : airport with IATA code already exists");
        }
        City city = cityRepository.findById(airportRequest.getCityId()).orElseThrow(() -> new Exception("City not found "));
        Airport airport = AirportMapper.toEntity(airportRequest);
        airport.setCity(city);
        return AirportMapper.toResponse(airportRepository.save(airport));
    }

    @Override
    public AirportResponse getAirportById(Long id) throws Exception {
        return AirportMapper.toResponse(airportRepository.findById(id).orElseThrow(() -> new Exception("getAirportById : Airport not exist with id : "+id)));
    }

    @Override
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream().map(AirportMapper::toResponse).toList();
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest airportRequest) throws Exception {
        Airport existingAirport = airportRepository.findById(id).orElseThrow(() -> new Exception("updateAirport : Airport not exist with id : " + id));
        if(airportRequest.getIataCode() != null
                && !existingAirport.getIataCode().equals(airportRequest.getIataCode())
                && airportRepository.findByIataCode(airportRequest.getIataCode()).isPresent()
        ){
            throw new Exception("updateAirport : airport with IATA code already exists");
        }
        AirportMapper.updateEntity(airportRequest, existingAirport);
        Airport updatedAirport = airportRepository.save(existingAirport);
        return AirportMapper.toResponse(updatedAirport);
    }

    @Override
    public void deleteAirport(Long id) throws Exception {
        airportRepository.delete(airportRepository.findById(id).orElseThrow(() -> new Exception("getAirportById : Airport not exist with id : "+id)));
    }

    @Override
    public List<AirportResponse> getAirportByCityId(Long cityId) {
        return airportRepository.findByCityId(cityId).stream().map(AirportMapper::toResponse).toList();
    }
}
