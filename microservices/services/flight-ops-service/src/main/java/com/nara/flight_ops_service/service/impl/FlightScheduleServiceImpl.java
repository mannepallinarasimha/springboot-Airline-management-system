package com.nara.flight_ops_service.service.impl;

import com.nara.flight_ops_service.service.FlightScheduleService;
import com.nara.payload.request.FlightScheduleRequest;
import com.nara.payload.response.FlightScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {
    @Override
    public FlightScheduleResponse createFlightSchedule(Long userId, FlightScheduleRequest flightScheduleRequest) {
        return null;
    }

    @Override
    public FlightScheduleResponse getFlightScheduleById(Long id) {
        return null;
    }

    @Override
    public List<FlightScheduleResponse> getFlightScheduleByAirline(Long userId) {
        return List.of();
    }

    @Override
    public FlightScheduleResponse updateFlightSchedule(Long id, FlightScheduleRequest flightScheduleRequest) {
        return null;
    }

    @Override
    public void deleteFlightSchedule(Long id) {

    }
}
