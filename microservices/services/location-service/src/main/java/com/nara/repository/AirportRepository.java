package com.nara.repository;

import com.nara.model.Airport;
import com.nara.model.City;
import com.nara.payload.response.AirportResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByIataCode(String iataCode);
    List<Airport> findByCityId(Long cityId);

}
