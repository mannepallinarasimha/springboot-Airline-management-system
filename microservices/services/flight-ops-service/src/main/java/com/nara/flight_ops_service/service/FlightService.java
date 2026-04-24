package com.nara.flight_ops_service.service;

import com.nara.enums.FlightStatus;
import com.nara.payload.request.FlightRequest;
import com.nara.payload.response.FlightResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FlightService {
    FlightResponse createFlight(Long airlineId, FlightRequest flightRequest) throws Exception;
    Page<FlightResponse> getFlightsByAirline(Long airlineId,
                                             Long departureAirportId,
                                             Long arrivalAirportId,
                                             Pageable pageable);

    FlightResponse getFlightById(Long flightId) throws Exception;
    FlightResponse updateFlight(Long flightId, FlightRequest flightRequest) throws Exception;
    FlightResponse changeFlightStatus(Long flightId, FlightStatus status);
    void deleteFlight(Long flightId);
}
