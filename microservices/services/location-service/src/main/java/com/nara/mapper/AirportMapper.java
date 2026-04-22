package com.nara.mapper;

import com.nara.model.Airport;
import com.nara.model.City;
import com.nara.payload.request.AirportRequest;
import com.nara.payload.request.CityRequest;
import com.nara.payload.response.AirportResponse;
import com.nara.payload.response.CityResponse;

public class AirportMapper {
    public static Airport toEntity(AirportRequest airportRequest){

        if(airportRequest == null){
            return null;
        }
        return Airport.builder()
                .iataCode(airportRequest.getIataCode())
                .name(airportRequest.getName())
//                .timeZoneId(airportRequest.getTimeZone())
                .address(airportRequest.getAddress())
                .geoCode(airportRequest.getGeoCode())
                .build();
    }

    public static AirportResponse toResponse(Airport airport){

        if(airport == null){
            return null;
        }
        return AirportResponse.builder()
                .id(airport.getId())
                .itiaCode(airport.getIataCode())
                .name(airport.getName())
//                .timeZone(airport.getTimeZoneId())
                .detailedName(airport.getDetailedName())
                .address(airport.getAddress())
                .city(CityMapper.toResponse(airport.getCity()))
                .geoCode(airport.getGeoCode())
                .build();
    }

    public static void updateEntity(AirportRequest airportRequest, Airport existingAirport){
        if(airportRequest == null || existingAirport ==  null){
            return;
        }
        if(airportRequest.getIataCode() != null){
            existingAirport.setIataCode(airportRequest.getIataCode());
        }
        if(airportRequest.getName() != null){
            existingAirport.setName(airportRequest.getName());
        }
//        if(airportRequest.getTimeZone() != null){
//            existingAirport.setTimeZone(airportRequest.getTimeZone());
//        }
        if(airportRequest.getAddress() != null){
            existingAirport.setAddress(airportRequest.getAddress());
        }
        if(airportRequest.getGeoCode() != null){
            existingAirport.setGeoCode(existingAirport.getGeoCode());
        }
    }
}
