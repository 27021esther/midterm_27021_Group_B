package com.vehicletrading.vehicle_trading.service;

import com.vehicletrading.vehicle_trading.entity.Feature;
import com.vehicletrading.vehicle_trading.entity.Vehicle;
import com.vehicletrading.vehicle_trading.repository.FeatureRepository;
import com.vehicletrading.vehicle_trading.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final FeatureRepository featureRepository;

    /**
     * Save a vehicle, checking VIN uniqueness first.
     * existsByVin() → Spring Data JPA derives: SELECT COUNT(*) > 0 FROM vehicles WHERE vin = ?
     */
    public Vehicle saveVehicle(Vehicle vehicle) {
        if (vehicleRepository.existsByVin(vehicle.getVin())) {
            throw new IllegalArgumentException(
                "Vehicle with VIN '" + vehicle.getVin() + "' already exists.");
        }
        return vehicleRepository.save(vehicle);
    }

    /**
     * Sorting + Pagination:
     *  - page: zero-based page index
     *  - size: number of records per page
     *  - sortBy: field name to sort by (e.g., "price", "year")
     *  - direction: "asc" or "desc"
     *
     * Spring Data JPA converts this into:
     *   SELECT * FROM vehicles ORDER BY {sortBy} {direction} LIMIT {size} OFFSET {page*size}
     *
     * Pagination improves performance by fetching a subset of records,
     * reducing memory usage and network transfer.
     */
    public Page<Vehicle> getAllVehiclesPaged(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return vehicleRepository.findAll(pageable);
    }

    public Page<Vehicle> getAvailableVehicles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("price").ascending());
        return vehicleRepository.findByStatus(Vehicle.VehicleStatus.AVAILABLE, pageable);
    }

    /**
     * Add features to a vehicle (Many-to-Many).
     * The join table "vehicle_features" gets new rows linking vehicle_id ↔ feature_id.
     */
    public Vehicle addFeaturesToVehicle(Long vehicleId, List<Long> featureIds) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new RuntimeException("Vehicle not found: " + vehicleId));

        Set<Feature> features = new HashSet<>(featureRepository.findAllById(featureIds));
        if (vehicle.getFeatures() == null) {
            vehicle.setFeatures(features);
        } else {
            vehicle.getFeatures().addAll(features);
        }
        return vehicleRepository.save(vehicle);
    }

    public Vehicle getById(Long id) {
        return vehicleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vehicle not found: " + id));
    }
}
