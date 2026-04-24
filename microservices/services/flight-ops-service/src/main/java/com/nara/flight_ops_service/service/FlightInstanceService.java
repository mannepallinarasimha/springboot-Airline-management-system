package com.nara.flight_ops_service.service;

import com.nara.payload.request.FlightInstanceRequest;
import com.nara.payload.response.FlightInstanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface FlightInstanceService {
    FlightInstanceResponse createFlightInstance(Long airlineId, FlightInstanceRequest flightInstanceRequest) throws Exception;
    FlightInstanceResponse updateFlightInstance(Long userId, FlightInstanceRequest flightInstanceRequest);
    FlightInstanceResponse getFlightInstanceById(Long id) throws Exception;
    Page<FlightInstanceResponse> getByAirlineId(Long airlineId,
                                                Long departureAirportId,
                                                Long arrivalAirportId,
                                                Long flightId,
                                                LocalDate onDate,
                                                Pageable pageable);
    void deleteFlightInstance(Long id);
}
