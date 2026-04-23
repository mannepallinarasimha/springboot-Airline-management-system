package com.nara.mapper;

import com.nara.model.Aircraft;
import com.nara.model.Airline;
import com.nara.payload.request.AircraftRequest;
import com.nara.payload.response.AircraftResponse;

public class AircraftMapper {
    public static Aircraft toEntity(AircraftRequest aircraftRequest, Airline airline){
        if(aircraftRequest ==  null) return null;
        return Aircraft.builder()
                .code(aircraftRequest.getCode())
                .model(aircraftRequest.getModel())
                .manufacturer(aircraftRequest.getManufacturer())
                .seatingCapacity(aircraftRequest.getSeatingCapacity())
                .economySeats(aircraftRequest.getEconomySeats())
                .premiumSeats(aircraftRequest.getPremiumSeats())
                .businessSeats(aircraftRequest.getBusinessSeats())
                .firstClassSeats(aircraftRequest.getFirstClassSeats())
                .rangeKm(aircraftRequest.getRangeKm())
                .cruisingSpeedKmh(aircraftRequest.getCruisingSpeedKmh())
                .maxAltitudeFt(aircraftRequest.getMaxAltitudeFt())
                .yearOfManufacture(aircraftRequest.getYearOfManufacture())
                .registrationDate(aircraftRequest.getRegistrationDate())
                .nextMaintenanceDate(aircraftRequest.getNextMaintenanceDate())
                .status(aircraftRequest.getStatus())
                .isAvailable(aircraftRequest.getIsAvailable())
                .airline(airline)
                .currentAirportId(aircraftRequest.getCurrentAirportId())
                .build();
    }

    public static AircraftResponse toResponse(Aircraft aircraft){
        if(aircraft ==  null) return null;
        return AircraftResponse.builder()
                .code(aircraft.getCode())
                .model(aircraft.getModel())
                .manufacturer(aircraft.getManufacturer())
                .seatingCapacity(aircraft.getSeatingCapacity())
                .economySeats(aircraft.getEconomySeats())
                .premiumSeats(aircraft.getPremiumSeats())
                .businessSeats(aircraft.getBusinessSeats())
                .firstClassSeats(aircraft.getFirstClassSeats())
                .rangeKm(aircraft.getRangeKm())
                .cruisingSpeedKmh(aircraft.getCruisingSpeedKmh())
                .maxAltitudeFt(aircraft.getMaxAltitudeFt())
                .yearOfManufacture(aircraft.getYearOfManufacture())
                .registrationDate(aircraft.getRegistrationDate())
                .nextMaintenanceDate(aircraft.getNextMaintenanceDate())
                .status(aircraft.getStatus())
                .isAvailable(aircraft.getIsAvailable())
                //Airline information
                .airlineId(aircraft.getAirline()!= null ? aircraft.getAirline().getId() : null)
                .airlineName(aircraft.getAirline()!= null ? aircraft.getAirline().getName() : null)
                .airlineIataCode(aircraft.getAirline()!= null ? aircraft.getAirline().getIataCode() : null)
                //Airport is cross-service - only ID available here
                .currentAirportId(aircraft.getCurrentAirportId())
                //Computed fields
                .totalSeats(aircraft.getTotalSeats())
                .requiredMaintenance(aircraft.requireMaintenance())
                .isOperational(aircraft.isOperational())
                //Audit
                .createdAt(aircraft.getCreatedAt())
                .updatedAt(aircraft.getUpdatedAt())
                .build();
    }

    public static void updateEntity(Aircraft aircraft, AircraftRequest aircraftRequest){
        if(aircraft ==  null|| aircraftRequest == null) return;

                aircraft.setCode(aircraftRequest.getCode());
                aircraft.setModel(aircraftRequest.getModel());
                aircraft.setManufacturer(aircraftRequest.getManufacturer());
                aircraft.setSeatingCapacity(aircraftRequest.getSeatingCapacity());
                aircraft.setEconomySeats(aircraftRequest.getEconomySeats());
                aircraft.setPremiumSeats(aircraftRequest.getPremiumSeats());
                aircraft.setBusinessSeats(aircraftRequest.getBusinessSeats());
                aircraft.setFirstClassSeats(aircraftRequest.getFirstClassSeats());
                aircraft.setRangeKm(aircraftRequest.getRangeKm());
                aircraft.setCruisingSpeedKmh(aircraftRequest.getCruisingSpeedKmh());
                aircraft.setMaxAltitudeFt(aircraftRequest.getMaxAltitudeFt());
                aircraft.setYearOfManufacture(aircraftRequest.getYearOfManufacture());
                aircraft.setRegistrationDate(aircraftRequest.getRegistrationDate());
                aircraft.setNextMaintenanceDate(aircraftRequest.getNextMaintenanceDate());
                aircraft.setStatus(aircraftRequest.getStatus());
                aircraft.setIsAvailable(aircraftRequest.getIsAvailable());
                aircraft.setCurrentAirportId(aircraftRequest.getCurrentAirportId());
    }

}
