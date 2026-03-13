package com.vehicletrading.vehicle_trading.repository;

import com.vehicletrading.vehicle_trading.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    /**
     * existsBy() – checks if a vehicle with the given VIN already exists.
     * Prevents duplicate vehicle registrations.
     */
    boolean existsByVin(String vin);

    /**
     * Sorting + Pagination: Spring Data JPA automatically applies
     * ORDER BY and LIMIT/OFFSET based on the Pageable parameter.
     * E.g., Pageable = PageRequest.of(0, 10, Sort.by("price").ascending())
     */
    Page<Vehicle> findAll(Pageable pageable);

    /** Find available vehicles with pagination support */
    Page<Vehicle> findByStatus(Vehicle.VehicleStatus status, Pageable pageable);

    /** Find vehicles by make with pagination */
    Page<Vehicle> findByMakeIgnoreCase(String make, Pageable pageable);
}
