package com.nara.mapper;

import com.nara.model.City;
import com.nara.payload.request.CityRequest;
import com.nara.payload.response.CityResponse;

public class CityMapper {
    public static City toEntity(CityRequest cityRequest){

        if(cityRequest == null){
            return null;
        }
        return City.builder()
                .name(cityRequest.getName())
                .cityCode(cityRequest.getCityCode())
                .countryCode(cityRequest.getCountryCode())
                .countryName(cityRequest.getCountryName())
                .regionCode(cityRequest.getRegionCode())
                .timeZone(cityRequest.getTimeZoneOffset())
                .build();
    }

    public static CityResponse toResponse(City city){

        if(city == null){
            return null;
        }
        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .cityCode(city.getCityCode())
                .countryCode(city.getCountryCode())
                .countryName(city.getCountryName())
                .regionCode(city.getRegionCode())
                .build();
    }

    public static City updateEntity(City city, CityRequest cityRequest){
        if(cityRequest.getName() != null){
            city.setName(cityRequest.getName().trim());
        }
        if(cityRequest.getCityCode() != null){
            city.setCityCode(cityRequest.getCityCode().toLowerCase().trim());
        }
        if(cityRequest.getCountryCode() != null){
            city.setCountryCode(cityRequest.getCountryCode().toLowerCase().trim());
        }
        if(cityRequest.getCountryName() != null){
            city.setCountryName(cityRequest.getCountryName().toLowerCase().trim());
        }
        if(cityRequest.getRegionCode() != null){
            city.setRegionCode(cityRequest.getRegionCode().toLowerCase().trim());
        }
        return city;
    }
}
