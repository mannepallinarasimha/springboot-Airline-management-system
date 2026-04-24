package com.nara.flight_ops_service.service.impl;

import com.nara.flight_ops_service.mapper.FlightInstanceMapper;
import com.nara.flight_ops_service.mapper.FlightMapper;
import com.nara.flight_ops_service.model.Flight;
import com.nara.flight_ops_service.model.FlightInstance;
import com.nara.flight_ops_service.repository.FlightInstanceRepository;
import com.nara.flight_ops_service.repository.FlightRepository;
import com.nara.flight_ops_service.service.FlightInstanceService;
import com.nara.payload.request.FlightInstanceRequest;
import com.nara.payload.response.AircraftResponse;
import com.nara.payload.response.AirlineResponse;
import com.nara.payload.response.AirportResponse;
import com.nara.payload.response.FlightInstanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightInstanceServiceImpl implements FlightInstanceService {
    private final FlightRepository flightRepository;
    private final FlightInstanceRepository flightInstanceRepository;

    @Override
    public FlightInstanceResponse createFlightInstance(Long airlineId, FlightInstanceRequest flightInstanceRequest) throws Exception {
        Flight flight = flightRepository.findById(flightInstanceRequest.getFlightId()).orElseThrow(() -> new Exception("Flight not found with id :"+flightInstanceRequest.getFlightId()));
        //dummy aircraft -  when we impl figen client will fecth aircraft response
        AircraftResponse aircraftResponse = AircraftResponse.builder()
                .id(1L)
                .totalSeats(90)
                .build();
        FlightInstance flightInstance = FlightInstanceMapper.toEntity(flightInstanceRequest, flight);
        flightInstance.setTotalSeats(aircraftResponse.getTotalSeats());
        flightInstance.setAvailableSeats(aircraftResponse.getTotalSeats());
        FlightInstance savedInstance = flightInstanceRepository.save(flightInstance);

        //todo -  once flight instance is saved into DB- need to create seats for this flight
        // todo - create seat instances


        return convertToFlightInstanceResponse(savedInstance);
    }

    @Override
    public FlightInstanceResponse updateFlightInstance(Long userId, FlightInstanceRequest flightInstanceRequest) {
        return null;
    }

    @Override
    public FlightInstanceResponse getFlightInstanceById(Long id) {
        return null;
    }

    @Override
    public Page<FlightInstanceResponse> getByAirlineId(Long airlineId, Long departureAirportId, Long arrivalAirportId, Long flightId, Long onDate, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteFlightInstance(Long id) {

    }

    private FlightInstanceResponse convertToFlightInstanceResponse(FlightInstance flightInstance){
        AirlineResponse airlineResponse = AirlineResponse.builder().id(flightInstance.getAirlineId()).build();
        AirportResponse departureAirport = AirportResponse.builder().id(flightInstance.getDepartureAirportId()).build();
        AirportResponse arrivalAirport = AirportResponse.builder().id(flightInstance.getArrivalAirportId()).build();
        AircraftResponse aircraftResponse=  AircraftResponse.builder().id(flightInstance.getFlight().getAircraftId()).build();

        return FlightInstanceMapper.toResponse(flightInstance, aircraftResponse, airlineResponse, departureAirport, arrivalAirport);

    }
}
