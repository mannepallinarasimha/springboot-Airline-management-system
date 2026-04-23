package com.nara.model;

import com.nara.enums.AircraftStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, length = 50)
    private String manufacturer;

    @Column(nullable = false)
    private Integer seatingCapacity;

    private Integer economySeats = 0;

    private Integer premiumSeats = 0;

    private Integer firstClassSeats = 0;

    private Integer businessSeats = 0;

    private Integer cruisingSpeedKmh;

    private Integer rangeKm;

    private Integer yearOfManufacture;

    private LocalDate registrationDate;

    private LocalDate nextMaintenanceDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AircraftStatus status = AircraftStatus.ACTIVE;

    private Boolean isAvailable = true;

    @ManyToOne
    private Airline airline;

    private Long currentAirportId;

    private Integer maxAltitudeFt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    public Integer getTotalSeats(){
        return economySeats+premiumSeats+businessSeats+firstClassSeats;
    }

    public Boolean isOperational(){
        return AircraftStatus.ACTIVE.equals(status)
                && Boolean.TRUE.equals(isAvailable);
    }

    public Boolean requireMaintenance(){
        return nextMaintenanceDate != null && nextMaintenanceDate.isBefore(LocalDate.now().plusWeeks(2));
    }
}
