package com.nara.flight_ops_service.service.impl;

import com.nara.flight_ops_service.mapper.FlightInstanceMapper;
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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlightInstanceServiceImpl implements FlightInstanceService {
    private final FlightRepository flightRepository;
    private final FlightInstanceRepository flightInstanceRepository;

    @Override
    public FlightInstanceResponse createFlightInstance(Long airlineId, FlightInstanceRequest flightInstanceRequest) throws Exception {

        // todo - watch airlineId
        Flight flight = flightRepository.findById(flightInstanceRequest.getFlightId()).orElseThrow(() -> new Exception("Flight not found with id :"+flightInstanceRequest.getFlightId()));
        // todo - dummy aircraft -  when we impl fiegn client will fecth aircraft response -  service to service communication
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
    public FlightInstanceResponse updateFlightInstance(Long userId, FlightInstanceRequest flightInstanceRequest) throws Exception {
        FlightInstance existing = flightInstanceRepository.findById(userId).orElseThrow(()-> new Exception("Flight instance not found with id :"+userId));
        FlightInstanceMapper.updateEntity(flightInstanceRequest, existing);

        return convertToFlightInstanceResponse(flightInstanceRepository.save(existing));
    }

    @Override
    public FlightInstanceResponse getFlightInstanceById(Long id) throws Exception {
        FlightInstance existingFlightInstance = flightInstanceRepository.findById(id).orElseThrow(()-> new Exception("Flight instance not found with id :"+id));

        return convertToFlightInstanceResponse(existingFlightInstance);
    }

    @Override
    public Page<FlightInstanceResponse> getByAirlineId(Long airlineId,
                                                       Long departureAirportId,
                                                       Long arrivalAirportId,
                                                       Long flightId,
                                                       LocalDate onDate,
                                                       Pageable pageable) {
        // todo - watch airlineId
        LocalDateTime start = onDate !=null? onDate.atStartOfDay(): null;
        LocalDateTime end = onDate !=null? onDate.plusDays(1).atStartOfDay(): null;
        return flightInstanceRepository
                .findByAirlineId(airlineId, departureAirportId, arrivalAirportId, flightId, start, end, pageable)
                .map(this::convertToFlightInstanceResponse);
    }

    @Override
    public void deleteFlightInstance(Long id) throws Exception {
        FlightInstance existing = flightInstanceRepository.findById(id).orElseThrow(()-> new Exception("Flight instance not found with id :"+id));
        flightInstanceRepository.delete(existing);
    }

    private FlightInstanceResponse convertToFlightInstanceResponse(FlightInstance flightInstance){

        // todo - watch airlineId-  service to service communication
        AirlineResponse airlineResponse = AirlineResponse.builder().id(flightInstance.getAirlineId()).build();
        AirportResponse departureAirport = AirportResponse.builder().id(flightInstance.getDepartureAirportId()).build();
        AirportResponse arrivalAirport = AirportResponse.builder().id(flightInstance.getArrivalAirportId()).build();
        AircraftResponse aircraftResponse=  AircraftResponse.builder().id(flightInstance.getFlight().getAircraftId()).build();

        return FlightInstanceMapper.toResponse(flightInstance, aircraftResponse, airlineResponse, departureAirport, arrivalAirport);

    }
}
