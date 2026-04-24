package com.nara.flight_ops_service.repository;

import com.nara.flight_ops_service.model.Flight;
import com.nara.flight_ops_service.model.FlightSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long> {

    List<FlightSchedule> findByFlightAirlineId(Long airlineId);
}
