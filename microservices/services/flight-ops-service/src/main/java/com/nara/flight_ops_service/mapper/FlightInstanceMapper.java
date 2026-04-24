package com.nara.flight_ops_service.mapper;

import com.nara.enums.FlightStatus;
import com.nara.flight_ops_service.model.Flight;
import com.nara.flight_ops_service.model.FlightInstance;
import com.nara.payload.request.FlightInstanceRequest;
import com.nara.payload.request.FlightRequest;
import com.nara.payload.response.*;

public class FlightInstanceMapper {

    public static FlightInstance toEntity(FlightInstanceRequest flightRequest, Flight flight){
        if(flight == null) return null;

        return FlightInstance.builder()
                .flight(flight)
                .airlineId(flight.getAirlineId())
                .scheduleAirportId(flightRequest.getScheduledId())
                .departureAirportId(flightRequest.getDepartureAirportId() != null ? flightRequest.getDepartureAirportId() : null)
                .arrivalAirportId(flightRequest.getArrivalAirportId()!= null ? flightRequest.getArrivalAirportId(): null)
                .departureDateTime(flightRequest.getDepartureDateTime())
                .arrivalDateTime(flightRequest.getArrivalDateTime())
                .status(FlightStatus.SCHEDULED)
                .maxAdvanceBookingDays(flightRequest.getMaxAdvanceBookingDays())
                .maxAdvanceBookingDays(flightRequest.getMaxAdvanceBookingDays())
                .isActive(flightRequest.getIsActive() != null ? flightRequest.getIsActive(): true)
                .build();
    }

    public static FlightInstanceResponse toResponse(FlightInstance flightInstance,
                                                    AircraftResponse aircraftResponse,
                                                    AirlineResponse airlineResponse,
                                                    AirportResponse departureAirport,
                                                    AirportResponse arrivalAirport
                                            ){
        if(flightInstance == null) return null;

        return FlightInstanceResponse.builder()
                .id(flightInstance.getId())
                .flightId(flightInstance.getFlight() != null ? flightInstance.getFlight().getId() : null)
                .flightNumber(flightInstance.getFlight() != null ? flightInstance.getFlight().getFlightNumber() : null)
                .aircraftId(flightInstance.getFlight().getAircraftId())
                .aircraftModel(aircraftResponse.getModel())
                .aircraftCode(aircraftResponse.getCode())
                .airlineId(flightInstance.getAirlineId())
                .airlineName(airlineResponse.getName())
                .airlineLogo(airlineResponse.getLogoUrl())
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureDateTime(flightInstance.getDepartureDateTime())
                .arrivalDateTime(flightInstance.getArrivalDateTime())
                .formattedDuration(flightInstance.getFormattedDuration())
                .totalSeats(flightInstance.getTotalSeats())
                .availableSeats(flightInstance.getAvailableSeats())
                .status(flightInstance.getStatus())
                .minAdvanceBookingDays(flightInstance.getMinAdvanceBookingDays())
                .maxAdvanceBookingDays(flightInstance.getMaxAdvanceBookingDays())
                .isActive(flightInstance.getIsActive())
                .build();
    }

    public static void updateEntity(FlightInstanceRequest flightInstanceRequest, FlightInstance existingFlightInstance){
        if(flightInstanceRequest == null || existingFlightInstance == null) return;
        if(flightInstanceRequest.getDepartureAirportId() != null) existingFlightInstance.setDepartureAirportId(flightInstanceRequest.getDepartureAirportId());
        if(flightInstanceRequest.getArrivalAirportId() != null) existingFlightInstance.setArrivalAirportId(flightInstanceRequest.getArrivalAirportId());
        if(flightInstanceRequest.getDepartureDateTime() != null) existingFlightInstance.setDepartureDateTime(flightInstanceRequest.getDepartureDateTime());
        if(flightInstanceRequest.getArrivalDateTime() != null) existingFlightInstance.setArrivalDateTime(flightInstanceRequest.getArrivalDateTime());
        if(flightInstanceRequest.getAvailableSeats() != null) existingFlightInstance.setAvailableSeats(flightInstanceRequest.getAvailableSeats());
        if(flightInstanceRequest.getStatus() != null) existingFlightInstance.setStatus(flightInstanceRequest.getStatus());
        if(flightInstanceRequest.getMinAdvanceBookingDays() != null) existingFlightInstance.setMinAdvanceBookingDays(flightInstanceRequest.getMinAdvanceBookingDays());
        if(flightInstanceRequest.getMaxAdvanceBookingDays() != null) existingFlightInstance.setMaxAdvanceBookingDays(flightInstanceRequest.getMaxAdvanceBookingDays());
        if(flightInstanceRequest.getIsActive() != null) existingFlightInstance.setIsActive(flightInstanceRequest.getIsActive());

    }


}
