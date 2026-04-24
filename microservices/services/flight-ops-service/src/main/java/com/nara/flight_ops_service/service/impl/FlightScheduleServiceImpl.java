package com.nara.flight_ops_service.service.impl;

import com.nara.enums.FlightStatus;
import com.nara.flight_ops_service.mapper.FlightScheduleMapper;
import com.nara.flight_ops_service.model.Flight;
import com.nara.flight_ops_service.model.FlightSchedule;
import com.nara.flight_ops_service.repository.FlightRepository;
import com.nara.flight_ops_service.repository.FlightScheduleRepository;
import com.nara.flight_ops_service.service.FlightInstanceService;
import com.nara.flight_ops_service.service.FlightScheduleService;
import com.nara.payload.request.FlightInstanceRequest;
import com.nara.payload.request.FlightScheduleRequest;
import com.nara.payload.response.AirlineResponse;
import com.nara.payload.response.AirportResponse;
import com.nara.payload.response.FlightScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {
    private final FlightRepository flightRepository;
    private final FlightScheduleRepository flightScheduleRepository;
    private final FlightInstanceService flightInstanceService;

    @Override
    public FlightScheduleResponse createFlightSchedule(Long airlineId, FlightScheduleRequest request) throws Exception {

        // todo - watch for airlineId
        Flight flight = flightRepository
                .findById(request.getFlightId()).orElseThrow(() -> new Exception("flight not found with id"));

        // start date - 12th march -> end date should be after this date like 13, 14 etc...
        if(request.getEndDate().isBefore(request.getStartDate())){
            throw new Exception("end date is before start date");
        }
        FlightSchedule savedSchedule = FlightScheduleMapper.toEntity(request, flight);

        //we need to create flights from start date to enddate
        List<DayOfWeek> operationDays = savedSchedule.getOperatingDays();
        LocalDate startDate = savedSchedule.getStartDate();
        LocalDate endDate = savedSchedule.getEndDate();

        FlightInstanceRequest flightInstanceRequest = FlightInstanceRequest.builder()
                .scheduledId(savedSchedule.getId())
                .flightId(flight.getId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureAirportId(flight.getDepartureAirportId())
                .status(FlightStatus.SCHEDULED)
                .build();

        for(LocalDate date = startDate; !date.isAfter(endDate); date=date.plusDays(1)){
            if(operationDays.contains(date.getDayOfWeek())){
                flightInstanceRequest.setDepartureDateTime(LocalDateTime.of(date, savedSchedule.getDepartureTime()));
                flightInstanceRequest.setArrivalDateTime(LocalDateTime.of(date, savedSchedule.getArrivalTime()));
                flightInstanceService.createFlightInstance(airlineId, flightInstanceRequest);
            }

        }
        return convertToFlightSchedule(savedSchedule);
    }

    @Override
    public FlightScheduleResponse getFlightScheduleById(Long id) throws Exception {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(() -> new Exception("flight schedule not found with id: "+id));

        return convertToFlightSchedule(flightSchedule);
    }

    @Override
    public List<FlightScheduleResponse> getFlightScheduleByAirline(Long userId) {
        // todo - watch for airlineId
        List<FlightSchedule> schedules = flightScheduleRepository.findByFlightAirlineId(userId);

        return schedules.stream().map(this::convertToFlightSchedule).toList();
    }

    @Override
    public FlightScheduleResponse updateFlightSchedule(Long id, FlightScheduleRequest flightScheduleRequest) throws Exception {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(() -> new Exception("flight schedule not found with id: "+id));
        FlightScheduleMapper.updateEntity(flightScheduleRequest, flightSchedule);

        return convertToFlightSchedule(flightScheduleRepository.save(flightSchedule));
    }

    @Override
    public void deleteFlightSchedule(Long id) throws Exception {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(() -> new Exception("flight schedule not found with id: "+id));
        flightScheduleRepository.delete(flightSchedule);
    }

    private FlightScheduleResponse convertToFlightSchedule(FlightSchedule flightSchedule){

        // todo - service to service communication
        AirportResponse departureAirport = AirportResponse.builder().id(flightSchedule.getDepartureAirportId()).build();
        AirportResponse arrivalAirport = AirportResponse.builder().id(flightSchedule.getArrivalAirportId()).build();

        return FlightScheduleMapper.toResponse(flightSchedule, arrivalAirport, departureAirport);
    }
}
