package com.vehicletrading.vehicle_trading.controller;

import com.vehicletrading.vehicle_trading.entity.Location;
import com.vehicletrading.vehicle_trading.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    // ✅ Create new location
    @PostMapping
    public ResponseEntity<Location> create(@RequestBody Location location) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(locationService.saveLocation(location));
    }

    // ✅ Get all locations
    @GetMapping
    public ResponseEntity<List<Location>> getAll() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    // ✅ Renamed parentId → id
    @GetMapping("/{id}")
    public ResponseEntity<Location> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(locationService.getById(id));
    }

    // ✅ Get by location type
    @GetMapping("/by-type/{locationType}")
    public ResponseEntity<List<Location>> getByLocationType(
            @PathVariable String locationType) {
        return ResponseEntity.ok(locationService.getByLocationType(locationType));
    }

    // ✅ Added update endpoint
    @PutMapping("/{id}")
    public ResponseEntity<Location> update(
            @PathVariable UUID id,
            @RequestBody Location location) {
        return ResponseEntity.ok(locationService.updateLocation(id, location));
    }

    // ✅ Added delete endpoint
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}