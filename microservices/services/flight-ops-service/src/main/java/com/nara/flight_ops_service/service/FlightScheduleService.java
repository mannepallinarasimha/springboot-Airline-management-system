package com.nara.flight_ops_service.service;

import com.nara.payload.request.FlightScheduleRequest;
import com.nara.payload.response.FlightScheduleResponse;

import java.util.List;

public interface FlightScheduleService {

    FlightScheduleResponse createFlightSchedule(Long userId, FlightScheduleRequest flightScheduleRequest);

    FlightScheduleResponse getFlightScheduleById(Long id);

    List<FlightScheduleResponse> getFlightScheduleByAirline(Long userId);

    FlightScheduleResponse updateFlightSchedule(Long id, FlightScheduleRequest flightScheduleRequest);

    void deleteFlightSchedule(Long id);
}
