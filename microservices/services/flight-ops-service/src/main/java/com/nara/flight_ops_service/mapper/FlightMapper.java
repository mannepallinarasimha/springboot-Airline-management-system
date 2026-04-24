package com.nara.flight_ops_service.mapper;

import com.nara.flight_ops_service.model.Flight;
import com.nara.payload.request.FlightRequest;
import com.nara.payload.response.AircraftResponse;
import com.nara.payload.response.AirlineResponse;
import com.nara.payload.response.AirportResponse;
import com.nara.payload.response.FlightResponse;

public class FlightMapper {

    public static Flight toEntity(FlightRequest flightRequest){
        if(flightRequest == null) return null;

        return Flight.builder()
                .flightNumber(flightRequest.getFlightNumber())
                .aircraftId(flightRequest.getAircraftId())
                .departureAirportId(flightRequest.getDepartureAirportId())
                .arrivalAirportId(flightRequest.getArrivalAirportId())
                .build();
    }

    public static FlightResponse toResponse(Flight flightRequest,
                                            AircraftResponse aircraftResponse,
                                            AirlineResponse airlineResponse,
                                            AirportResponse departureAirport,
                                            AirportResponse arrivalAirport
                                            ){
        if(flightRequest == null) return null;

        return FlightResponse.builder()
                .id(flightRequest.getId())
                .flightNumber(flightRequest.getFlightNumber())
                .airline(airlineResponse)
                .aircraft(aircraftResponse)
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .status(flightRequest.getStatus())
                .createdAt(flightRequest.getCreatedAt())
                .updatedAt(flightRequest.getUpdatedAt())
                .build();
    }

    public static void updateEntity(FlightRequest flightRequest, Flight existingFlight){
        if(flightRequest == null || existingFlight == null) return;
        if(flightRequest.getFlightNumber() != null) existingFlight.setFlightNumber(flightRequest.getFlightNumber());
        if(flightRequest.getAircraftId() != null) existingFlight.setAircraftId(flightRequest.getAircraftId());
        if(flightRequest.getDepartureAirportId() != null) existingFlight.setDepartureAirportId(flightRequest.getDepartureAirportId());
        if(flightRequest.getArrivalAirportId() != null) existingFlight.setArrivalAirportId(flightRequest.getArrivalAirportId());
        if(flightRequest.getStatus() != null) existingFlight.setStatus(flightRequest.getStatus());
    }


}
