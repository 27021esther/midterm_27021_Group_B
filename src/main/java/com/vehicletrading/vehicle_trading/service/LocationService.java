package com.vehicletrading.vehicle_trading.service;

import com.vehicletrading.vehicle_trading.entity.Location;
import com.vehicletrading.vehicle_trading.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getById(UUID id) {
        return locationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Location not found: " + id));
    }
    
    public List<Location> getByLocationType(String locationType) {
        return locationRepository.findByLocationType(locationType);
    }
    
    public Location updateLocation(UUID id, Location location) {
        Location existing = getById(id);
        existing.setCode(location.getCode());
        existing.setLocationType(location.getLocationType());
        existing.setName(location.getName());
        existing.setLocationId(location.getLocationId());
        return locationRepository.save(existing);
    }
    
    public void deleteLocation(UUID id) {
        locationRepository.deleteById(id);
    }
}
