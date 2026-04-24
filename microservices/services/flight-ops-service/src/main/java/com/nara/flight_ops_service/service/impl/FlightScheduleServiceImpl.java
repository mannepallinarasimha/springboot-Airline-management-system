package com.nara.flight_ops_service.service.impl;

import com.nara.flight_ops_service.model.Flight;
import com.nara.flight_ops_service.repository.FlightRepository;
import com.nara.flight_ops_service.service.FlightScheduleService;
import com.nara.payload.request.FlightScheduleRequest;
import com.nara.payload.response.FlightScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {
    private final FlightRepository flightRepository;

    @Override
    public FlightScheduleResponse createFlightSchedule(Long userId, FlightScheduleRequest request) throws Exception {
        Flight flight = flightRepository
                .findById(request.getFlightId()).orElseThrow(() -> new Exception("flight not found with id"));

        // start date - 12th march -> end date should be after this date like 13, 14 etc...
        if(request.getEndDate().isBefore(request.getStartDate())){
            throw new Exception("end date is before start date");
        }

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
