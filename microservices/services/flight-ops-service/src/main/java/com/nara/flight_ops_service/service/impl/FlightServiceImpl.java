package com.nara.flight_ops_service.service.impl;

import com.nara.enums.FlightStatus;
import com.nara.flight_ops_service.mapper.FlightMapper;
import com.nara.flight_ops_service.model.Flight;
import com.nara.flight_ops_service.repository.FlightRepository;
import com.nara.flight_ops_service.service.FlightService;
import com.nara.payload.request.FlightRequest;
import com.nara.payload.response.AircraftResponse;
import com.nara.payload.response.AirlineResponse;
import com.nara.payload.response.AirportResponse;
import com.nara.payload.response.FlightResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;

    @Override
    public FlightResponse createFlight(Long airlineId, FlightRequest flightRequest) throws Exception {

        //todo - watch airlineId

        if(flightRepository.existsByFlightNumber(flightRequest.getFlightNumber())){
            throw new Exception("Flight with number already exists");
        }
        Flight flight = FlightMapper.toEntity(flightRequest);
        flight.setAirlineId(airlineId);
        Flight savedFlight = flightRepository.save(flight);
        return convertToFlightResponse(savedFlight);
    }

    @Override
    public Page<FlightResponse> getFlightsByAirline(Long airlineId,
                                                    Long departureAirportId,
                                                    Long arrivalAirportId,
                                                    Pageable pageable) {
        // todo - watch airlineId
        return flightRepository.findByAirlineId(airlineId, departureAirportId, arrivalAirportId, pageable).map(this::convertToFlightResponse);
    }

    @Override
    public FlightResponse getFlightById(Long flightId) throws Exception {
        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new Exception("Flight not found with id :"+flightId));
        return convertToFlightResponse(flight);
    }

    /*
    scenario-1
    F-450 , now user want to update to F-451 -> in this scenario existsByFlightNumber() will work

    scenario-2
    F-451 -> user don't want to change flight but user wants to change departureAirportId ->

     */

    @Override
    public FlightResponse updateFlight(Long flightId, FlightRequest flightRequest) throws Exception {
        Flight existing = flightRepository.findById(flightId).orElseThrow(() -> new Exception("Flight not found with id :"+flightId));
        if(flightRequest.getFlightNumber() !=null &&
                flightRepository.existsByFlightNumberAndIdNot(flightRequest.getFlightNumber(), flightId)
        ){
            throw new Exception("Flight with number already exists");
        }
        FlightMapper.updateEntity(flightRequest, existing);
        Flight updated = FlightMapper.toEntity(flightRequest);

        return convertToFlightResponse(updated);
    }

    @Override
    public FlightResponse changeFlightStatus(Long flightId, FlightStatus status) throws Exception {
        Flight existing = flightRepository.findById(flightId).orElseThrow(() -> new Exception("Flight not found with id :"+flightId));
        existing.setStatus(status);

        return convertToFlightResponse(flightRepository.save(existing));
    }

    @Override
    public void deleteFlight(Long airlineId, Long flightId) throws Exception {
        //todo - watch airlineId
        Flight existing = flightRepository.findByAirlineIdAndId(airlineId, flightId).orElseThrow(() -> new Exception("Flight not found with id :"+flightId));
        flightRepository.delete(existing);
    }

    public FlightResponse convertToFlightResponse(Flight flight){

        //todo - watch airlineId -  service to service communication

        AircraftResponse aircraftResponse = AircraftResponse.builder().id(flight.getAircraftId()).build();
        AirlineResponse airlineResponse = AirlineResponse.builder().id(flight.getAirlineId()).build();
        AirportResponse departureAirport = AirportResponse.builder().id(flight.getDepartureAirportId()).build();
        AirportResponse arrivalAirport = AirportResponse.builder().id(flight.getArrivalAirportId()).build();
        return FlightMapper.toResponse(flight, aircraftResponse, airlineResponse, departureAirport, arrivalAirport);
    }
}
