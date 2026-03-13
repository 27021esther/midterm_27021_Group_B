package com.vehicletrading.vehicle_trading.controller;

import com.vehicletrading.vehicle_trading.entity.Feature;
import com.vehicletrading.vehicle_trading.repository.FeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/features")
@RequiredArgsConstructor
public class FeatureController {

    private final FeatureRepository featureRepository;

    // POST /api/features
    @PostMapping
    public ResponseEntity<Feature> create(@RequestBody Feature feature) {
        if (featureRepository.existsByNameIgnoreCase(feature.getName())) {
            throw new IllegalArgumentException("Feature already exists: " + feature.getName());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(featureRepository.save(feature));
    }

    // GET /api/features
    @GetMapping
    public ResponseEntity<List<Feature>> getAll() {
        return ResponseEntity.ok(featureRepository.findAll());
    }
}
