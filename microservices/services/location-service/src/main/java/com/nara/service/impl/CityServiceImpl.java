package com.nara.service.impl;

import com.nara.mapper.CityMapper;
import com.nara.model.City;
import com.nara.payload.request.CityRequest;
import com.nara.payload.response.CityResponse;
import com.nara.repository.CityRepository;
import com.nara.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    @Override
    public CityResponse createCity(CityRequest cityRequest) throws Exception {
        if(cityRepository.existsByCityCode(cityRequest.getCityCode())){
            throw new Exception("CITY with given code already exist");
        }
//        City city = CityMapper.toEntity(cityRequest);
//        City result = cityRepository.save(city);
        return CityMapper.toResponse(cityRepository.save(CityMapper.toEntity(cityRequest)));
    }

    @Override
    public CityResponse getCityById(Long id) throws Exception {
        City city = cityRepository.findById(id).orElseThrow(
                () -> new Exception("city not exist with given id")
        );
        return CityMapper.toResponse(city);
    }

    @Override
    public CityResponse updateCity(Long id, CityRequest cityRequest) throws Exception {
        City city = cityRepository.findById(id).orElseThrow(
                () -> new Exception("city not exist with given id")
        );
        if(cityRepository.existsByCityCode(cityRequest.getCityCode())){
            throw new Exception("city with given code already exists");
        }
        City updatedCity = cityRepository.save(CityMapper.updateEntity(city, cityRequest));
        return CityMapper.toResponse(updatedCity);
    }

    @Override
    public void deleteCity(Long id) throws Exception {
        City city = cityRepository.findById(id).orElseThrow(
                () -> new Exception("city not exist with given id")
        );
        cityRepository.delete(city);
    }

    @Override
    public Page<CityResponse> getAllCities(Pageable pageable) {
        return cityRepository.findAll(pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
        return  cityRepository.searchByKeyword(keyword, pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable) {
        return cityRepository.findByCountryCodeIgnoreCase(countryCode, pageable).map(CityMapper::toResponse);
    }

    @Override
    public boolean cityExistOrNot(String cityCode) {
        return cityRepository.existsByCityCode(cityCode);
    }

}
