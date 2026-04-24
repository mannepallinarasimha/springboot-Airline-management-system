package com.nara.flight_ops_service.service.impl;

import com.nara.flight_ops_service.model.Flight;
import com.nara.flight_ops_service.repository.FlightRepository;
import com.nara.flight_ops_service.service.FlightInstanceService;
import com.nara.payload.request.FlightInstanceRequest;
import com.nara.payload.response.FlightInstanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightInstanceServiceImpl implements FlightInstanceService {
    private final FlightRepository flightRepository;

    @Override
    public FlightInstanceResponse createFlightInstance(Long airlineId, FlightInstanceRequest flightInstanceRequest) {
        Flight flight = flightRepository.findById(flightInstanceRequest.getFlightId()).orElseThrow(() -> new Exception("Flight not found with id :"+flightInstanceRequest.getFlightId()));

        return null;
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
}
