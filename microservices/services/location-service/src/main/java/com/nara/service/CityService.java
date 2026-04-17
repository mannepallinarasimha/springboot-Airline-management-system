package com.nara.service;

import com.nara.payload.response.CityResponse;

public interface CityService {

    CityResponse createCity(CityRequest cityRequest);
}
