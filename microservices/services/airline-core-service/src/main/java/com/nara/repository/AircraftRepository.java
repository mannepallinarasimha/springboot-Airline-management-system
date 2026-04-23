package com.nara.repository;

import com.nara.enums.AirlineStatus;
import com.nara.model.Aircraft;
import com.nara.model.Airline;
import com.nara.payload.response.AircraftResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    List<Aircraft> findByAirlineId(Long airlineId);

    Boolean existsByCode(String code);
    Aircraft findByIdAndAirlineId(Long id, Long airlineId);
}
