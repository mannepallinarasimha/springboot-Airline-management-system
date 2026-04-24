package com.nara.flight_ops_service.mapper;

import com.nara.enums.FlightStatus;
import com.nara.flight_ops_service.model.Flight;
import com.nara.flight_ops_service.model.FlightInstance;
import com.nara.flight_ops_service.model.FlightSchedule;
import com.nara.payload.request.FlightInstanceRequest;
import com.nara.payload.request.FlightScheduleRequest;
import com.nara.payload.response.*;

public class FlightScheduleMapper {

    public static FlightSchedule toEntity(FlightScheduleRequest request, Flight flight){
        if(request== null || flight == null) return null;

        return FlightSchedule.builder()
                .flight(flight)
                .departureAirportId(flight.getDepartureAirportId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .operatingDays(request.getOperatingDays())
                .isActive(request.getIsActive()!=null ? request.getIsActive(): true)
                .build();
    }

    public static FlightScheduleResponse toResponse(FlightSchedule flightSchedule,
                                                    AirportResponse arrival,
                                                    AirportResponse departure
                                            ){
        if(flightSchedule == null) return null;

        return FlightScheduleResponse.builder()
                .id(flightSchedule.getId())
                .flightId(flightSchedule.getFlight() != null ? flightSchedule.getFlight().getId() : null)
                .flightNumber(flightSchedule.getFlight() != null ? flightSchedule.getFlight().getFlightNumber() : null)
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .startDate(flightSchedule.getStartDate())
                .endDate(flightSchedule.getEndDate())
                .operatingDays(flightSchedule.getOperatingDays())
                .isActive(flightSchedule.getIsActive())
                .build();
    }

    public static void updateEntity(FlightScheduleRequest flightScheduleRequest, FlightSchedule existingFlightSchedule){
        if(flightScheduleRequest == null || existingFlightSchedule == null) return;
        if(flightScheduleRequest.getDepartureTime() != null) existingFlightSchedule.setDepartureTime(flightScheduleRequest.getDepartureTime());
        if(flightScheduleRequest.getArrivalTime() != null) existingFlightSchedule.setArrivalTime(flightScheduleRequest.getArrivalTime());
        if(flightScheduleRequest.getStartDate() != null) existingFlightSchedule.setStartDate(flightScheduleRequest.getStartDate());
        if(flightScheduleRequest.getEndDate() != null) existingFlightSchedule.setEndDate(flightScheduleRequest.getEndDate());
        if(flightScheduleRequest.getOperatingDays() != null) existingFlightSchedule.setOperatingDays(flightScheduleRequest.getOperatingDays());
        if(flightScheduleRequest.getIsActive() != null) existingFlightSchedule.setIsActive(flightScheduleRequest.getIsActive());

    }


}
