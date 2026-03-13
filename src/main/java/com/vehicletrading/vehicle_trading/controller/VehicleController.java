package com.vehicletrading.vehicle_trading.controller;

import com.vehicletrading.vehicle_trading.entity.Vehicle;
import com.vehicletrading.vehicle_trading.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    // POST /api/vehicles
    @PostMapping
    public ResponseEntity<Vehicle> create(@RequestBody Vehicle vehicle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.saveVehicle(vehicle));
    }

    /**
     * GET /api/vehicles?page=0&size=10&sortBy=price&direction=asc
     *
     * Demonstrates Sorting + Pagination:
     *  - page: which page (0-indexed)
     *  - size: number of items per page
     *  - sortBy: field to sort by (e.g., "price", "year", "make")
     *  - direction: "asc" or "desc"
     *
     * The Page<Vehicle> response includes:
     *  - content: the actual list of vehicles
     *  - totalElements: total count without pagination
     *  - totalPages: number of pages
     *  - number: current page index
     */
    @GetMapping
    public ResponseEntity<Page<Vehicle>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(vehicleService.getAllVehiclesPaged(page, size, sortBy, direction));
    }

    // GET /api/vehicles/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getById(id));
    }

    // GET /api/vehicles/available?page=0&size=5
    @GetMapping("/available")
    public ResponseEntity<Page<Vehicle>> getAvailable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(vehicleService.getAvailableVehicles(page, size));
    }

    /**
     * PUT /api/vehicles/{vehicleId}/features
     * Adds features to a vehicle — demonstrates Many-to-Many association.
     * Body: list of feature IDs, e.g. [1, 2, 3]
     */
    @PutMapping("/{vehicleId}/features")
    public ResponseEntity<Vehicle> addFeatures(
            @PathVariable Long vehicleId,
            @RequestBody List<Long> featureIds) {
        return ResponseEntity.ok(vehicleService.addFeaturesToVehicle(vehicleId, featureIds));
    }
}
