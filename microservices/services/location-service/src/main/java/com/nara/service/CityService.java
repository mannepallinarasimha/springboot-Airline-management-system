package com.nara.service;

import com.nara.payload.request.CityRequest;
import com.nara.payload.response.CityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {

    CityResponse createCity(CityRequest cityRequest) throws Exception;
    CityResponse getCityById(Long id) throws Exception;
    CityResponse updateCity(Long id, CityRequest cityRequest) throws Exception;
    void deleteCity(Long id) throws Exception;
    Page<CityResponse> getAllCities(Pageable pageable);
    Page<CityResponse> searchCities(String keyword, Pageable pageable);
    Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable);
    boolean cityExistOrNot(String cityCode);
}
